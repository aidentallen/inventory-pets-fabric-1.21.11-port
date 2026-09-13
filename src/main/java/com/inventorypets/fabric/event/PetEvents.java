package com.inventorypets.fabric.event;

import com.inventorypets.fabric.config.InventoryPetsJsonConfig;
import com.inventorypets.fabric.init.ModAttachments;
import com.inventorypets.fabric.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class PetEvents {
    private static final Set<UUID> GRANTED_FLIGHT = new HashSet<>();

    private PetEvents() {
    }

    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, level, hand, attacked, hit) -> {
            if (level.isClientSide() || !(attacked instanceof LivingEntity target)) return InteractionResult.PASS;
            if (ModItems.hasPet(player, "pet_blaze")) target.setRemainingFireTicks(100);
            if (ModItems.hasPet(player, "pet_torch")) target.setRemainingFireTicks(160);
            if (ModItems.hasPet(player, "pet_pufferfish")) target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
            if (ModItems.hasPet(player, "pet_wither")) player.heal(2.0f);
            if (ModItems.hasPet(player, "pet_snow_golem")) {
                Vec3 push = target.position().subtract(player.position()).normalize().scale(1.5);
                target.setDeltaMovement(target.getDeltaMovement().add(push.x, 0.35, push.z));
            }
            if (target instanceof Player targetPlayer) {
                if (ModItems.hasPet(player, "pet_cobblestone") && ModItems.hasPet(targetPlayer, "pet_dirt")) {
                    target.hurtServer((net.minecraft.server.level.ServerLevel) level, level.damageSources().playerAttack(player), 4.0f);
                }
                if (ModItems.hasPet(player, "pet_dirt") && ModItems.hasPet(targetPlayer, "pet_cobblestone")) {
                    target.hurtServer((net.minecraft.server.level.ServerLevel) level, level.damageSources().playerAttack(player), 4.0f);
                }
            }
            return InteractionResult.PASS;
        });

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (!(entity instanceof Player player)) return true;
            if (player.getAttachedOrCreate(ModAttachments.SHIELD) > 0) return false;
            if (source.is(DamageTypeTags.IS_EXPLOSION) && ModItems.hasPet(player, "pet_creeper")) return false;
            if (source.is(DamageTypes.IN_WALL) && ModItems.hasPet(player, "pet_silverfish")) return false;
            return true;
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> {
            if (!(entity instanceof Player player)) return true;
            if (ModItems.hasPet(player, "pet_slime")) {
                player.setHealth(player.getMaxHealth());
                player.removeAllEffects();
                return false;
            }
            if (ModItems.hasPet(player, "pet_enderman") && !InventoryPetsJsonConfig.bool("disableEndermanAutoTeleport")) {
                player.setHealth(1.0f);
                player.randomTeleport(player.getX() + levelOffset(player), player.getY() + 2, player.getZ() + levelOffset(player), true);
                return false;
            }
            return true;
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(source.getEntity() instanceof Player player) || !(entity.level() instanceof net.minecraft.server.level.ServerLevel level)) return;
            if (ModItems.hasPet(player, "pet_loot")) {
                player.addItem(new ItemStack(Items.GOLD_NUGGET, 1 + level.random.nextInt(3)));
            }
            if (ModItems.hasPet(player, "pet_pixie")) {
                ExperienceOrb.award(level, entity.position(), Math.max(1, entity.getExperienceReward(level, player)));
            }
            if (ModItems.hasPet(player, "pet_pacman")) {
                player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 200, 2));
            }
        });

        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
            if (!level.isClientSide() && ModItems.hasPet(player, "pet_loot")) {
                ItemStack bonus = new ItemStack(state.getBlock().asItem());
                if (!bonus.isEmpty() && level.random.nextFloat() < 0.25f) player.addItem(bonus);
            }
        });

        UseEntityCallback.EVENT.register((player, level, hand, entity, hit) -> {
            ItemStack held = player.getItemInHand(hand);
            if (held.is(ModItems.item("pet_lead")) && entity instanceof Mob mob) {
                if (!level.isClientSide()) mob.setLeashedTo(player, true);
                return InteractionResult.SUCCESS;
            }
            if ((held.is(ModItems.item("pet_saddle")) || held.is(ModItems.item("pet_flying_saddle"))) && entity instanceof LivingEntity) {
                if (!level.isClientSide()) player.startRiding(entity, true, true);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            ItemStack bow = player.getItemInHand(hand);
            if (!(bow.getItem() instanceof BowItem)) return InteractionResult.PASS;
            if (level.isClientSide()) return InteractionResult.PASS;
            int rapidShotTicks = player.getAttachedOrCreate(ModAttachments.RAPID_SHOT);
            if (rapidShotTicks <= 0) return InteractionResult.PASS;
            ItemStack arrowStack = findArrow(player);
            if (arrowStack.isEmpty() && !player.isCreative()) return InteractionResult.FAIL;
            net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) level;
            Arrow arrow = new Arrow(serverLevel, player, new ItemStack(Items.ARROW), bow.copy());
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setBaseDamage(6.0);
            arrow.setCritArrow(true);
            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3.0f, 0.2f);
            serverLevel.addFreshEntity(arrow);
            serverLevel.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.ARROW_SHOOT,
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative()) arrowStack.shrink(1);
            return InteractionResult.SUCCESS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> server.getPlayerList().getPlayers().forEach(player -> {
            int shieldTicks = player.getAttachedOrCreate(ModAttachments.SHIELD);
            if (shieldTicks > 0) player.setAttached(ModAttachments.SHIELD, shieldTicks - 1);
            int rapidShotTicks = player.getAttachedOrCreate(ModAttachments.RAPID_SHOT);
            if (rapidShotTicks > 0) player.setAttached(ModAttachments.RAPID_SHOT, rapidShotTicks - 1);

            boolean shouldFly = ModItems.hasPet(player, "pet_cloud")
                    || (player.isPassenger() && ModItems.hasPet(player, "pet_flying_saddle"));
            if (shouldFly && !InventoryPetsJsonConfig.bool("disableCloudFly")) {
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = true;
                    player.getAbilities().setFlyingSpeed(0.05f * Math.max(1, InventoryPetsJsonConfig.integer("cloudFlySpeed")));
                    player.onUpdateAbilities();
                }
                GRANTED_FLIGHT.add(player.getUUID());
            } else if (GRANTED_FLIGHT.remove(player.getUUID()) && !player.isCreative() && !player.isSpectator()) {
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                player.getAbilities().setFlyingSpeed(0.05f);
                player.onUpdateAbilities();
            }

            if (ModItems.hasPet(player, "pet_spider") && player.horizontalCollision && !InventoryPetsJsonConfig.bool("disableSpiderJump")) {
                player.setDeltaMovement(player.getDeltaMovement().x, 0.24, player.getDeltaMovement().z);
            }
            if (ModItems.hasPet(player, "pet_squid") && player.isShiftKeyDown() && player.isInWater()) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, -0.08, 0));
            }
            if (ModItems.hasPet(player, "pet_sponge") && player.isInWater()) {
                float y = player.isShiftKeyDown() ? -0.12f : 0.08f;
                player.setDeltaMovement(player.getDeltaMovement().x, y, player.getDeltaMovement().z);
                player.fallDistance = 0;
            }
            if (ModItems.hasPet(player, "pet_magma_cube") && player.isInLava()) {
                float y = player.isShiftKeyDown() ? -0.12f : 0.08f;
                player.setDeltaMovement(player.getDeltaMovement().x, y, player.getDeltaMovement().z);
                player.fallDistance = 0;
            }
            if (ModItems.hasPet(player, "pet_cheetah") || ModItems.hasPet(player, "pet_wolf")) {
                scare(player, Skeleton.class);
            }
            if (ModItems.hasPet(player, "pet_ocelot")) scare(player, Creeper.class);
            if (ModItems.hasPet(player, "pet_siamese")) scare(player, Zombie.class);
            if (ModItems.hasPet(player, "pet_sun")) {
                for (Monster monster : player.level().getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(12))) {
                    if (monster.tickCount < 20 && !monster.hasCustomName() && !monster.isPersistenceRequired()) {
                        monster.discard();
                        continue;
                    }
                    monster.setRemainingFireTicks(40);
                    Vec3 away = monster.position().subtract(player.position()).normalize().scale(0.12);
                    monster.setDeltaMovement(monster.getDeltaMovement().add(away));
                }
            }
        }));
    }

    private static double levelOffset(Player player) {
        return (player.getRandom().nextDouble() - 0.5) * 32.0;
    }

    private static ItemStack findArrow(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(Items.ARROW)) return stack;
        }
        return ItemStack.EMPTY;
    }

    private static <T extends Monster> void scare(Player player, Class<T> type) {
        for (T monster : player.level().getEntitiesOfClass(type, player.getBoundingBox().inflate(10))) {
            Vec3 away = monster.position().subtract(player.position()).normalize();
            monster.getNavigation().moveTo(monster.getX() + away.x * 12, monster.getY(), monster.getZ() + away.z * 12, 1.4);
            monster.setTarget(null);
        }
    }
}
