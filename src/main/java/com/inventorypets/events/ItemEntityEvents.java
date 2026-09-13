/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.Container
 *  net.minecraft.world.Containers
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.AreaEffectCloud
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.FlyingMob
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.SlotAccess
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.chicken.Chicken
 *  net.minecraft.world.entity.animal.AbstractCow
 *  net.minecraft.world.entity.animal.dolphin.Dolphin
 *  net.minecraft.world.entity.animal.golem.IronGolem
 *  net.minecraft.world.entity.animal.cow.MushroomCow
 *  net.minecraft.world.entity.animal.feline.Ocelot
 *  net.minecraft.world.entity.animal.pig.Pig
 *  net.minecraft.world.entity.animal.fish.Pufferfish
 *  net.minecraft.world.entity.animal.sheep.Sheep
 *  net.minecraft.world.entity.animal.golem.SnowGolem
 *  net.minecraft.world.entity.animal.squid.Squid
 *  net.minecraft.world.entity.animal.fish.WaterAnimal
 *  net.minecraft.world.entity.animal.wolf.Wolf
 *  net.minecraft.world.entity.animal.equine.AbstractHorse
 *  net.minecraft.world.entity.animal.equine.Horse
 *  net.minecraft.world.entity.boss.wither.WitherBoss
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.item.PrimedTnt
 *  net.minecraft.world.entity.monster.Blaze
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.EnderMan
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.monster.MagmaCube
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.Silverfish
 *  net.minecraft.world.entity.monster.Slime
 *  net.minecraft.world.entity.monster.spider.Spider
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BedBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.ChestBlockEntity
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.EntityMountEvent
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.neoforged.neoforge.event.tick.EntityTickEvent$Post
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.AdvancementHelper;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModSoundEvents;
import java.util.Collection;
import java.util.RandomPoolAlias;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.AbstractCow;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid="inventorypets")
public class ItemEntityEvents {
    private static int jumpTimer = 0;
    private static int jumpDelay = 0;
    private static int msgDelay = 0;
    private static boolean jumpFlag = false;
    static RandomPoolAlias rand = new RandomPoolAlias();

    @SubscribeEvent
    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        Player player;
        if (++msgDelay > 10000) {
            msgDelay = 2;
        }
        if ((player = event.getEntity()) instanceof Player) {
            Horse entityHorse;
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            Level world = event.getLevel();
            Entity entity = event.getTarget();
            if (entity instanceof Horse && (entityHorse = (Horse)entity).getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() <= 0.11) {
                entityHorse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.225f);
            }
            if (!main.isEmpty() && !world.isClientSide && !((Boolean)InventoryPetsConfig.disablePetrifier.get()).booleanValue() && main.getItem() == InventoryPets.ITEM_PETRIFIER.get() && !world.isClientSide && (entity instanceof Creeper || entity instanceof Chicken || entity instanceof AbstractCow || entity instanceof Blaze || entity instanceof EnderMan || entity instanceof IronGolem || entity instanceof Ghast || entity instanceof MushroomCow || entity instanceof MagmaCube || entity instanceof Ocelot || entity instanceof Pig || entity instanceof Pufferfish || entity instanceof Sheep || entity instanceof Slime || entity instanceof Spider || entity instanceof Silverfish || entity instanceof SnowGolem || entity instanceof Squid || entity instanceof WitherBoss || entity instanceof Wolf)) {
                if (main.has(ModDataComponents.PETRIFIER_READY) && Boolean.TRUE.equals(main.get(ModDataComponents.PETRIFIER_READY))) {
                    int k;
                    Boolean hasDiamond = false;
                    Boolean hasNetherStar = false;
                    ItemStack s2 = ItemStack.EMPTY;
                    ItemStack s3 = ItemStack.EMPTY;
                    for (k = 0; k < 36; ++k) {
                        s2 = player.getInventory().getItem(k);
                        if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            if (s2.getItem() != Items.DIAMOND) continue;
                            hasDiamond = true;
                            break;
                        }
                        if (s2.getItem() != Blocks.DIAMOND_BLOCK.asItem()) continue;
                        hasDiamond = true;
                        break;
                    }
                    for (k = 0; k < 36; ++k) {
                        s3 = player.getInventory().getItem(k);
                        if (s3.getItem() != Items.NETHER_STAR) continue;
                        hasNetherStar = true;
                        break;
                    }
                    if (!(hasDiamond.booleanValue() || hasNetherStar.booleanValue() || player.isCreative() || msgDelay <= 1)) {
                        Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(3));
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                        player.sendSystemMessage((Component)Component.translatable((String)"info.petrifier.requiresdiamond"));
                        msgDelay = 0;
                    }
                    if ((hasDiamond.booleanValue() || player.isCreative()) && Boolean.TRUE.equals(main.get(ModDataComponents.PETRIFIER_READY))) {
                        ItemStack item = ItemStack.EMPTY;
                        if (entity instanceof Creeper) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_CREEPER.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Blaze) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_BLAZE.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Chicken) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_CHICKEN.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof EnderMan) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_ENDERMAN.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                            world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.petrifier.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
                        } else if (entity instanceof Ghast) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_GHAST.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                            if (!AdvancementHelper.hasAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrify_ghast"))) {
                                AdvancementHelper.unlockAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrify_ghast"));
                            }
                        } else if (entity instanceof IronGolem) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_IRON_GOLEM.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof MagmaCube) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_MAGMA_CUBE.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof MushroomCow) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_MOOSHROOM.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof AbstractCow) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_COW.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Ocelot) {
                            int chooseCat = rand.nextInt(10);
                            item = chooseCat <= 6 ? new ItemStack((ItemLike)InventoryPets.PET_OCELOT.get()) : (chooseCat > 6 && chooseCat < 9 ? new ItemStack((ItemLike)InventoryPets.PET_CHEETAH.get()) : new ItemStack((ItemLike)InventoryPets.PET_SIAMESE.get()));
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Pig) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_PIG.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Sheep) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_SHEEP.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Silverfish) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_SILVERFISH.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof SnowGolem) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_SNOW_GOLEM.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Spider) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_SPIDER.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Squid) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_SQUID.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (entity instanceof Wolf) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_WOLF.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s2);
                        } else if (!hasDiamond.booleanValue()) {
                            Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(3));
                            world.playSound(null, player.getX(), player.getY(), player.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                        }
                    }
                    if ((hasNetherStar.booleanValue() || player.isCreative()) && Boolean.TRUE.equals(main.get(ModDataComponents.PETRIFIER_READY))) {
                        ItemStack item = ItemStack.EMPTY;
                        if (entity instanceof Slime && ((Boolean)InventoryPetsConfig.allowPetrifierLegendaries.get()).booleanValue()) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_SLIME.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s3);
                        } else if (entity instanceof Pufferfish && ((Boolean)InventoryPetsConfig.allowPetrifierLegendaries.get()).booleanValue()) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_PUFFERFISH.get());
                            if (!AdvancementHelper.hasAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrify_pufferfish"))) {
                                AdvancementHelper.unlockAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrify_pufferfish"));
                            }
                            ItemEntityEvents.popEntity(world, entity, player, item, s3);
                        } else if (entity instanceof WitherBoss && ((Boolean)InventoryPetsConfig.allowPetrifierLegendaries.get()).booleanValue()) {
                            item = new ItemStack((ItemLike)InventoryPets.PET_WITHER.get());
                            ItemEntityEvents.popEntity(world, entity, player, item, s3);
                            if (!AdvancementHelper.hasAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrify_wither"))) {
                                AdvancementHelper.unlockAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrify_wither"));
                            }
                        }
                    }
                } else {
                    main.set(ModDataComponents.PETRIFIER_READY, (Object)true);
                }
            }
            if (!main.isEmpty() && !world.isClientSide && main.getItem() == InventoryPets.PET_TORCH.get() && !((Boolean)InventoryPetsConfig.disableTorch.get()).booleanValue() && main.getDamageValue() < 1 && entity instanceof Creeper) {
                Creeper creeper = (Creeper)entity;
                creeper.ignite();
            }
            if (!main.isEmpty() && !world.isClientSide && (main.getItem() == InventoryPets.PET_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue() || main.getItem() == InventoryPets.PET_FLYING_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue()) && main.getDamageValue() < 3 && entity instanceof LivingEntity) {
                player.startRiding(entity);
                if (!world.isClientSide) {
                    boolean chkRide;
                    if (!main.has(ModDataComponents.RIDE_ON)) {
                        main.set(ModDataComponents.RIDE_ON, (Object)false);
                    }
                    if (chkRide = Boolean.TRUE.equals(main.get(ModDataComponents.RIDE_ON))) {
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !player.isCreative()) {
                            main.setDamageValue(main.getDamageValue() + 1);
                        }
                        main.set(ModDataComponents.RIDE_ON, (Object)false);
                    } else {
                        main.set(ModDataComponents.RIDE_ON, (Object)true);
                    }
                }
                if (entity instanceof AbstractHorse) {
                    AbstractHorse entityHorse2 = (AbstractHorse)entity;
                    if (!entityHorse2.isTamed()) {
                        entityHorse2.setTamed(true);
                    }
                    if (!entityHorse2.isSaddled()) {
                        entityHorse2.equipSaddle(ItemStack.EMPTY, SoundSource.PLAYERS);
                    }
                }
            } else if (!off.isEmpty() && !world.isClientSide && (off.getItem() == InventoryPets.PET_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue() || off.getItem() == InventoryPets.PET_FLYING_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue()) && off.getDamageValue() < 3 && entity instanceof LivingEntity) {
                boolean chkRide;
                player.startRiding(entity);
                if (!off.has(ModDataComponents.RIDE_ON)) {
                    off.set(ModDataComponents.RIDE_ON, (Object)false);
                }
                if (chkRide = Boolean.TRUE.equals(off.get(ModDataComponents.RIDE_ON))) {
                    if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !player.isCreative()) {
                        off.setDamageValue(off.getDamageValue() + 1);
                    }
                    off.set(ModDataComponents.RIDE_ON, (Object)false);
                } else {
                    off.set(ModDataComponents.RIDE_ON, (Object)true);
                }
                if (entity instanceof AbstractHorse) {
                    AbstractHorse entityHorse3 = (AbstractHorse)entity;
                    if (!entityHorse3.isTamed()) {
                        entityHorse3.setTamed(true);
                    }
                    if (!entityHorse3.isSaddled()) {
                        entityHorse3.equipSaddle(ItemStack.EMPTY, SoundSource.PLAYERS);
                    }
                }
            }
            if (!(main.isEmpty() || world.isClientSide || main.getItem() != InventoryPets.PET_LEAD.get() || ((Boolean)InventoryPetsConfig.disableLead.get()).booleanValue() || main.getDamageValue() >= 3 || entity instanceof Player || !(entity instanceof LivingEntity))) {
                boolean chkRide;
                Mob entityliving = (Mob)entity;
                if (entityliving.isLeashed() || entityliving.getLeashHolder() != null) {
                    entityliving.dropLeash(true, false);
                    entityliving.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                    entityliving.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                    entityliving.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                    entityliving.goalSelector.enableControlFlag(Goal.Flag.LOOK);
                    if (!main.has(ModDataComponents.LEASH_ON)) {
                        main.set(ModDataComponents.LEASH_ON, (Object)false);
                    }
                } else {
                    entityliving.addTag("LeashMe");
                }
                if (!main.has(ModDataComponents.LEASH_ON)) {
                    main.set(ModDataComponents.LEASH_ON, (Object)false);
                }
                if (chkRide = Boolean.TRUE.equals(main.get(ModDataComponents.LEASH_ON))) {
                    if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !player.isCreative()) {
                        main.setDamageValue(main.getDamageValue() + 1);
                    }
                    main.set(ModDataComponents.LEASH_ON, (Object)false);
                    entityliving.goalSelector.tickRunningGoals(true);
                } else {
                    main.set(ModDataComponents.LEASH_ON, (Object)true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void entityMountEvent(EntityMountEvent event) {
        if (event.getEntityMounting() instanceof Player) {
            Player entityplayer = (Player)event.getEntityMounting();
            boolean hasSaddle = false;
            boolean hasFlyingSaddle = false;
            Animal creature = null;
            ItemStack off = entityplayer.getOffhandItem();
            if (!(event.getEntityBeingMounted() instanceof Animal)) {
                return;
            }
            creature = (Animal)event.getEntityBeingMounted();
            if (entityplayer != null) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_SADDLE.get() && itemchk.getDamageValue() < 3) {
                        hasSaddle = true;
                    }
                    if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_FLYING_SADDLE.get() && itemchk.getDamageValue() < 3) {
                        hasFlyingSaddle = true;
                    }
                    if (off == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue() || off.getItem() != InventoryPets.PET_FLYING_SADDLE.get() || off.getDamageValue() >= 3) continue;
                    hasFlyingSaddle = true;
                }
                if (event.isMounting() && (hasSaddle || hasFlyingSaddle)) {
                    double creaturespeed = creature.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
                    if (creaturespeed < 0.75) {
                        entityplayer.setData(ModDataAttachments.CREATURE_SPEED, (Object)creaturespeed);
                    }
                    creature.setSpeed(creature.getSpeed() * 0.1f);
                    if (creature instanceof AbstractHorse) {
                        creature.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
                    } else if (creature instanceof Dolphin) {
                        creature.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
                    }
                } else if (event.isDismounting() && (hasSaddle || hasFlyingSaddle)) {
                    double y;
                    if (creature instanceof AbstractHorse) {
                        creature.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.225);
                    } else if (creature instanceof Dolphin) {
                        creature.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)1.2f);
                    }
                    if (creature instanceof Animal) {
                        Animal animal = creature;
                        animal.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                        animal.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                        animal.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                        animal.goalSelector.enableControlFlag(Goal.Flag.LOOK);
                    } else if (creature instanceof Monster) {
                        Monster monster = (Monster)creature;
                        monster.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                        monster.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                        monster.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                        monster.goalSelector.enableControlFlag(Goal.Flag.LOOK);
                    } else if (creature instanceof WaterAnimal) {
                        WaterAnimal monster = (WaterAnimal)creature;
                        monster.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                        monster.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                        monster.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                        monster.goalSelector.enableControlFlag(Goal.Flag.LOOK);
                    }
                    creature.resetFallDistance();
                    entityplayer.fallDistance = 0.0f;
                    if (!creature.onGround() && (y = (double)event.getLevel().getHeight(Heightmap.Types.WORLD_SURFACE_WG, (int)creature.getX(), (int)creature.getZ())) - (double)((int)creature.getY()) < 4.0) {
                        creature.fallDistance = 0.0f;
                        creature.flyDist = 0.0f;
                        creature.hasImpulse = false;
                        creature.setJumping(false);
                        if (creature instanceof Animal) {
                            Animal animal = creature;
                            animal.getMoveControl().setWantedPosition(creature.getX(), y - 0.5, creature.getZ(), (double)0.01f);
                            animal.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 4, false, false));
                        } else if (creature instanceof WaterAnimal) {
                            WaterAnimal animal = (WaterAnimal)creature;
                            animal.getMoveControl().setWantedPosition(creature.getX(), y - 0.5, creature.getZ(), (double)0.01f);
                            animal.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 4, false, false));
                        }
                    }
                }
            }
        } else {
            return;
        }
    }

    @SubscribeEvent
    public static void handleLivingUpdateEvent(EntityTickEvent.Post event) {
        Mob tempEntity;
        Player entityplayer;
        if (event.getEntity().getTags().contains("LeashMe") && (entityplayer = event.getEntity().level().getNearestPlayer(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 4.0, false)) != null) {
            Mob tempEntity2 = (Mob)event.getEntity();
            tempEntity2.setLeashedTo((Entity)entityplayer, true);
            ItemEntityEvents.tameAnimal((LivingEntity)event.getEntity());
            event.getEntity().removeTag("LeashMe");
        }
        if (event.getEntity() instanceof Mob && (tempEntity = (Mob)event.getEntity()).getLeashHolder() != null && tempEntity.getLeashHolder() instanceof Player) {
            Player entityplayer2 = (Player)tempEntity.getLeashHolder();
            boolean hasLead = false;
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = entityplayer2.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableLead.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_LEAD.get() || itemchk.getDamageValue() >= 3) continue;
                hasLead = true;
                break;
            }
            if (tempEntity instanceof Creeper && hasLead) {
                Creeper creeper = (Creeper)tempEntity;
                creeper.setAggressive(false);
                creeper.setSwellDir(-100);
                creeper.setTarget(null);
            }
            if (tempEntity instanceof Monster) {
                Monster monster = (Monster)tempEntity;
                monster.setTarget(null);
                monster.setAggressive(false);
            }
        }
        boolean hasSaddle = false;
        boolean hasFlyingSaddle = false;
        if (!event.getEntity().getPassengers().isEmpty() && event.getEntity().getPassengers().get(0) instanceof Player) {
            Player entityplayer3 = (Player)event.getEntity().getPassengers().get(0);
            ItemStack off = entityplayer3.getOffhandItem();
            if (entityplayer3 != null) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer3.getInventory().getItem(i);
                    if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_SADDLE.get() && itemchk.getDamageValue() < 3) {
                        hasSaddle = true;
                    }
                    if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_FLYING_SADDLE.get() && itemchk.getDamageValue() < 3) {
                        hasFlyingSaddle = true;
                    }
                    if (off == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue() || off.getItem() != InventoryPets.PET_FLYING_SADDLE.get() || off.getDamageValue() >= 3) continue;
                    hasFlyingSaddle = true;
                }
            }
            if (event.getEntity() instanceof LivingEntity && hasSaddle && !hasFlyingSaddle) {
                creature = (LivingEntity)event.getEntity();
                double x = creature.getDeltaMovement().x();
                double y = creature.getDeltaMovement().y();
                double z = creature.getDeltaMovement().z();
                if (Math.abs(creature.getDeltaMovement().x()) < 0.005) {
                    x = 0.0;
                }
                if (Math.abs(creature.getDeltaMovement().y()) < 0.005) {
                    y = 0.0;
                }
                if (Math.abs(creature.getDeltaMovement().z()) < 0.005) {
                    z = 0.0;
                }
                creature.lerpMotion(x, y, z);
                ItemEntityEvents.travel(creature, creature.xxa, creature.yya, creature.zza, false);
            } else if (event.getEntity() instanceof LivingEntity && hasFlyingSaddle && !hasSaddle) {
                creature = (LivingEntity)event.getEntity();
                double x = creature.getDeltaMovement().x;
                double y = creature.getDeltaMovement().y;
                double z = creature.getDeltaMovement().z;
                if (Math.abs(creature.getDeltaMovement().x) < 0.005) {
                    x = 0.0;
                }
                if (Math.abs(creature.getDeltaMovement().y) < 0.005) {
                    y = 0.0;
                }
                if (Math.abs(creature.getDeltaMovement().z) < 0.005) {
                    z = 0.0;
                }
                creature.lerpMotion(x, y, z);
                ItemEntityEvents.travel(creature, creature.xxa, creature.yya, creature.zza, true);
            } else if (event.getEntity() instanceof LivingEntity && hasFlyingSaddle && hasSaddle) {
                Player entityplayer2 = (Player)event.getEntity().getPassengers().get(0);
                LivingEntity creature = (LivingEntity)event.getEntity();
                double x = creature.getDeltaMovement().x;
                double y = creature.getDeltaMovement().y;
                double z = creature.getDeltaMovement().z;
                if (Math.abs(creature.getDeltaMovement().x) < 0.005) {
                    x = 0.0;
                }
                if (Math.abs(creature.getDeltaMovement().y) < 0.005) {
                    y = 0.0;
                }
                if (Math.abs(creature.getDeltaMovement().z) < 0.005) {
                    z = 0.0;
                }
                creature.lerpMotion(x, y, z);
                if (entityplayer2.getInventory().getSelected().getItem() == InventoryPets.PET_SADDLE.get()) {
                    ItemEntityEvents.travel(creature, creature.xxa, creature.yya, creature.zza, false);
                } else if (entityplayer2.getInventory().getSelected().getItem() == InventoryPets.PET_FLYING_SADDLE.get()) {
                    ItemEntityEvents.travel(creature, creature.xxa, creature.yya, creature.zza, true);
                }
            }
        }
    }

    public static void travel(LivingEntity creature, float strafe, float vertical, float forward, boolean flying) {
        if (creature != null && !creature.getPassengers().isEmpty() && creature instanceof LivingEntity && creature.getPassengers().get(0) instanceof Player) {
            TamableAnimal entityTameable;
            Player entityplayer = (Player)creature.getPassengers().get(0);
            creature.setYRot(entityplayer.getYRot());
            creature.setYHeadRot(entityplayer.getYHeadRot());
            creature.setPos(creature.getX(), creature.getY(), creature.getZ());
            strafe = entityplayer.xxa * 0.5f;
            forward = entityplayer.zza;
            if (forward <= 0.0f) {
                forward *= 0.25f;
            }
            if (creature instanceof Animal) {
                Animal creatureEntity = (Animal)creature;
                creatureEntity.goalSelector.disableControlFlag(Goal.Flag.TARGET);
                creatureEntity.setTarget(null);
            }
            creature.isAlliedTo((Entity)creature.getPassengers().get(0));
            boolean tamedFlag = false;
            if (creature instanceof TamableAnimal && (entityTameable = (TamableAnimal)creature).isTame()) {
                tamedFlag = true;
            }
            if (!tamedFlag) {
                if (creature instanceof Animal) {
                    Animal animal = (Animal)creature;
                    animal.goalSelector.disableControlFlag(Goal.Flag.MOVE);
                    animal.goalSelector.disableControlFlag(Goal.Flag.JUMP);
                    animal.targetSelector.disableControlFlag(Goal.Flag.TARGET);
                    animal.goalSelector.disableControlFlag(Goal.Flag.LOOK);
                    animal.setTarget(null);
                } else if (creature instanceof Monster) {
                    monster = (Monster)creature;
                    monster.goalSelector.disableControlFlag(Goal.Flag.MOVE);
                    monster.goalSelector.disableControlFlag(Goal.Flag.JUMP);
                    monster.targetSelector.disableControlFlag(Goal.Flag.TARGET);
                    monster.goalSelector.disableControlFlag(Goal.Flag.LOOK);
                    monster.setTarget(null);
                } else if (creature instanceof WaterAnimal) {
                    monster = (WaterAnimal)creature;
                    monster.goalSelector.disableControlFlag(Goal.Flag.MOVE);
                    monster.goalSelector.disableControlFlag(Goal.Flag.JUMP);
                    monster.targetSelector.disableControlFlag(Goal.Flag.TARGET);
                    monster.goalSelector.disableControlFlag(Goal.Flag.LOOK);
                    monster.setTarget(null);
                }
            }
            creature.fallDistance = 0.0f;
            if (creature instanceof Creeper) {
                Creeper creeper = (Creeper)creature;
                creeper.setSwellDir(-100);
                creeper.setAggressive(false);
            }
            if (creature instanceof TamableAnimal) {
                TamableAnimal wolf = (TamableAnimal)creature;
                wolf.setInSittingPose(false);
            }
            Float movementSpeed = Float.valueOf((float)creature.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue());
            boolean horseFlag = false;
            if (creature instanceof AbstractHorse) {
                horseFlag = true;
            }
            int keyinput = (Integer)entityplayer.getData(ModDataAttachments.KEY_INPUT);
            double x = creature.getDeltaMovement().x;
            double y = creature.getDeltaMovement().y;
            double z = creature.getDeltaMovement().z;
            if (jumpDelay > 0) {
                --jumpDelay;
                creature.setSpeed(creature.getSpeed() * 0.1f);
                creature.setSpeed(movementSpeed.floatValue());
                creature.travel(new Vec3((double)(entityplayer.xxa * 0.5f), (double)entityplayer.yya, (double)entityplayer.zza));
            } else if (keyinput == 1 && creature instanceof WaterAnimal && creature.isInWater()) {
                d3 = creature.getDeltaMovement().y + 0.11;
                creature.setSpeed(entityplayer.getAbilities().getFlyingSpeed() * (float)(creature.isSprinting() ? 2 : 1));
                creature.yya = 0.8f;
                creature.setSpeed(movementSpeed.floatValue());
                creature.travel(new Vec3((double)entityplayer.xxa, (double)entityplayer.yya, (double)entityplayer.zza));
                y = d3 * 0.4;
                x = creature.getDeltaMovement().x * (double)1.05f;
                z = creature.getDeltaMovement().z * (double)1.05f;
                creature.lerpMotion(x, y, z);
                entityplayer.setData(ModDataAttachments.KEY_INPUT, (Object)0);
            } else if (keyinput == 1 && (creature instanceof FlyingMob || creature instanceof WitherBoss || flying) && !creature.onGround()) {
                d3 = creature.getDeltaMovement().y + 0.22;
                creature.setSpeed(entityplayer.getAbilities().getFlyingSpeed() * (float)(creature.isSprinting() ? 2 : 1));
                creature.yya = 0.8f;
                creature.setSpeed(movementSpeed.floatValue());
                creature.travel(new Vec3((double)entityplayer.xxa, (double)entityplayer.yya, (double)entityplayer.zza));
                y = d3 * 0.6;
                x = creature.getDeltaMovement().x * (double)1.12f;
                z = creature.getDeltaMovement().z * (double)1.12f;
                creature.lerpMotion(x, y, z);
                entityplayer.setData(ModDataAttachments.KEY_INPUT, (Object)0);
            } else if (keyinput == 2 && (creature instanceof FlyingMob || creature instanceof WitherBoss || flying) && !creature.onGround()) {
                d3 = creature.getDeltaMovement().y - 0.22;
                creature.setSpeed(entityplayer.getAbilities().getFlyingSpeed() * (float)(creature.isSprinting() ? 2 : 1));
                creature.setSpeed(movementSpeed.floatValue());
                creature.travel(new Vec3((double)entityplayer.xxa, (double)entityplayer.yya, (double)entityplayer.zza));
                y = d3 * 0.6;
                x = creature.getDeltaMovement().x * (double)1.12f;
                z = creature.getDeltaMovement().z * (double)1.12f;
                creature.lerpMotion(x, y, z);
                entityplayer.setData(ModDataAttachments.KEY_INPUT, (Object)0);
            } else if ((creature instanceof FlyingMob || creature instanceof WitherBoss || flying) && !creature.onGround()) {
                creature.setSpeed(entityplayer.getAbilities().getFlyingSpeed() * (float)(creature.isSprinting() ? 2 : 1));
                if (creature instanceof Dolphin && creature.isInWater()) {
                    creature.setSpeed(movementSpeed.floatValue() / 10.0f);
                } else {
                    creature.setSpeed(movementSpeed.floatValue() * 2.0f);
                }
                creature.travel(new Vec3((double)(entityplayer.xxa * 0.5f), (double)entityplayer.yya, (double)entityplayer.zza));
                y = 0.021;
                x = creature.getDeltaMovement().x * (double)1.12f;
                z = creature.getDeltaMovement().z * (double)1.12f;
                creature.lerpMotion(x, y, z);
            } else if (keyinput == 1 && !horseFlag && !jumpFlag && jumpDelay == 0 && creature.onGround()) {
                jumpFlag = true;
                ++jumpTimer;
                d3 = creature.getDeltaMovement().y + 0.22;
                f = creature.getSpeed();
                creature.yya = 0.8f;
                creature.setSpeed(entityplayer.getAbilities().getFlyingSpeed() * (float)(creature.isSprinting() ? 2 : 1));
                creature.setSpeed(movementSpeed.floatValue());
                creature.travel(new Vec3((double)(entityplayer.xxa * 0.5f), (double)entityplayer.yya, (double)entityplayer.zza));
                y = d3 * 0.6;
                creature.lerpMotion(x, y, z);
                creature.setSpeed(f);
            } else if (keyinput == 1 && jumpFlag && jumpTimer < 6 && jumpDelay == 0) {
                ++jumpTimer;
                d3 = creature.getDeltaMovement().y + 0.22;
                f = creature.getSpeed();
                creature.yya = 0.8f;
                creature.setSpeed(entityplayer.getAbilities().getFlyingSpeed() * (float)(creature.isSprinting() ? 2 : 1));
                creature.setSpeed(movementSpeed.floatValue());
                creature.travel(new Vec3((double)(entityplayer.xxa * 0.5f), (double)entityplayer.yya, (double)entityplayer.zza));
                y = d3 * 0.6;
                creature.lerpMotion(x, y, z);
                creature.setSpeed(f);
                entityplayer.setData(ModDataAttachments.KEY_INPUT, (Object)0);
            } else if (keyinput == 1 && jumpFlag && jumpTimer >= 6) {
                jumpFlag = false;
                jumpTimer = 0;
                jumpDelay = 4;
                creature.yya = 0.0f;
                entityplayer.setData(ModDataAttachments.KEY_INPUT, (Object)0);
            } else if (keyinput == 2) {
                creature.setSpeed(creature.getSpeed() * 0.1f);
                creature.setSpeed(movementSpeed.floatValue() * 0.8f);
                creature.travel(new Vec3((double)(entityplayer.xxa * 0.5f), (double)entityplayer.yya, (double)entityplayer.zza));
                entityplayer.setData(ModDataAttachments.KEY_INPUT, (Object)0);
            } else {
                creature.setSpeed(creature.getSpeed() * 0.1f);
                if (creature instanceof Dolphin) {
                    creature.setSpeed(movementSpeed.floatValue() / 10.0f);
                } else {
                    creature.setSpeed(movementSpeed.floatValue());
                }
                creature.travel(new Vec3((double)(entityplayer.xxa * 0.5f), (double)entityplayer.yya, (double)entityplayer.zza));
                creature.yya = 0.0f;
            }
            if (entityplayer.isCrouching() && creature instanceof AbstractHorse) {
                AbstractHorse horse = (AbstractHorse)creature;
                SlotAccess slotSaddle = horse.getSlot(0);
                slotSaddle.set(ItemStack.EMPTY);
            }
            creature.resetFallDistance();
            if (creature instanceof Animal) {
                Animal animal = (Animal)creature;
                animal.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                animal.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                animal.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                animal.goalSelector.enableControlFlag(Goal.Flag.LOOK);
            } else if (creature instanceof Monster) {
                Monster monster = (Monster)creature;
                monster.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                monster.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                monster.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                monster.goalSelector.enableControlFlag(Goal.Flag.LOOK);
            } else if (creature instanceof WaterAnimal) {
                WaterAnimal monster = (WaterAnimal)creature;
                monster.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                monster.goalSelector.enableControlFlag(Goal.Flag.JUMP);
                monster.targetSelector.enableControlFlag(Goal.Flag.TARGET);
                monster.goalSelector.enableControlFlag(Goal.Flag.LOOK);
            }
            creature.oAttackAnim = creature.attackAnim;
            double d1 = creature.getX() - creature.xo;
            double d0 = creature.getZ() - creature.zo;
            float f2 = Mth.sqrt((float)((float)(d1 * d1 + d0 * d0))) * 4.0f;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            creature.walkAnimation.update(f2 - creature.walkAnimation.speed(), 0.4f);
            creature.walkAnimation.position(creature.walkAnimation.speed());
        } else {
            creature.oAttackAnim = creature.attackAnim;
            creature.setSpeed(creature.getSpeed() * 0.1f);
            creature.travel(new Vec3((double)strafe, (double)vertical, (double)forward));
        }
    }

    public static void tameAnimal(LivingEntity creature) {
        if (creature instanceof Animal) {
            Animal animal = (Animal)creature;
            animal.setTarget(null);
            animal.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            animal.goalSelector.disableControlFlag(Goal.Flag.JUMP);
            animal.targetSelector.disableControlFlag(Goal.Flag.TARGET);
            animal.goalSelector.disableControlFlag(Goal.Flag.LOOK);
        } else if (creature instanceof Monster) {
            monster = (Monster)creature;
            monster.setTarget(null);
            monster.setAggressive(false);
            monster.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            monster.goalSelector.disableControlFlag(Goal.Flag.JUMP);
            monster.targetSelector.disableControlFlag(Goal.Flag.TARGET);
            monster.goalSelector.disableControlFlag(Goal.Flag.LOOK);
        } else if (creature instanceof WaterAnimal) {
            monster = (WaterAnimal)creature;
            monster.setAggressive(false);
            monster.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            monster.goalSelector.disableControlFlag(Goal.Flag.JUMP);
            monster.targetSelector.disableControlFlag(Goal.Flag.TARGET);
            monster.goalSelector.disableControlFlag(Goal.Flag.LOOK);
        }
        creature.fallDistance = 0.0f;
        if (creature instanceof Creeper) {
            Creeper creeper = (Creeper)creature;
            creeper.setAggressive(false);
            creeper.setTarget(null);
            creeper.setSwellDir(-100);
            creeper.goalSelector.removeGoal((Goal)new MeleeAttackGoal((PathfinderMob)creeper, 1.0, false));
            creeper.targetSelector.disableControlFlag(Goal.Flag.TARGET);
        }
        if (creature instanceof Squid) {
            Squid squid = (Squid)creature;
            squid.setMovementVector(0.0f, 0.0f, 0.0f);
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void onItemRightClick(PlayerInteractEvent.RightClickBlock event) {
        ChestBlockEntity techest;
        Player player = event.getEntity();
        Level world = event.getLevel();
        Block block = world.getBlockState(event.getPos()).getBlock();
        Block blockUnder = world.getBlockState(event.getPos().below(1)).getBlock();
        Block blockUnder2 = world.getBlockState(event.getPos().below(2)).getBlock();
        Block blockUnder3 = world.getBlockState(event.getPos().below(3)).getBlock();
        Block blockLeft = world.getBlockState(event.getPos().east(1)).getBlock();
        Block blockLeft2 = world.getBlockState(event.getPos().east(2)).getBlock();
        Block blockRight = world.getBlockState(event.getPos().west()).getBlock();
        Block blockRight2 = world.getBlockState(event.getPos().west(2)).getBlock();
        BlockPos pos = event.getPos();
        if (!event.getLevel().isClientSide && block == Blocks.CHEST && !((Boolean)InventoryPetsConfig.disableTreeTopTNT.get()).booleanValue() && (blockUnder instanceof RotatedPillarBlock || blockUnder2 instanceof RotatedPillarBlock || blockUnder3 instanceof RotatedPillarBlock) && (blockLeft instanceof LeavesBlock || blockLeft2 instanceof LeavesBlock || blockRight instanceof LeavesBlock || blockRight2 instanceof LeavesBlock) && (techest = (ChestBlockEntity)world.getBlockEntity(pos)).hasCustomName() && techest.getCustomName().getString().equals("Tree Top")) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 1.4f, 1.2f);
            if (rand.nextInt(10) < 3) {
                ItemEntityEvents.spawnLingeringCloud(player);
                PrimedTnt entitytntprimed = new PrimedTnt(world, (double)((float)pos.getX() + 0.5f), (double)pos.getY() + 1.0, (double)((float)pos.getZ() + 0.5f), (LivingEntity)player);
                entitytntprimed.setCustomName((Component)Component.literal((String)"One"));
                entitytntprimed.setFuse((int)((short)(world.random.nextInt(entitytntprimed.getFuse() / 4) + 10 + entitytntprimed.getFuse() / 8)));
                world.addFreshEntity((Entity)entitytntprimed);
                PrimedTnt entitytntprimed2 = new PrimedTnt(world, (double)((float)pos.getX() + 0.5f), (double)pos.getY(), (double)((float)pos.getZ() + 0.5f), (LivingEntity)player);
                entitytntprimed.setFuse((int)((short)(world.random.nextInt(entitytntprimed.getFuse() / 4) + 10 + entitytntprimed.getFuse() / 8)));
                entitytntprimed2.setCustomName((Component)Component.literal((String)"Two"));
                world.addFreshEntity((Entity)entitytntprimed2);
            }
        }
        if (!event.getLevel().isClientSide && !((Boolean)InventoryPetsConfig.disablePetrifier.get()).booleanValue() && event.getEntity().getInventory().getSelected().getItem() == InventoryPets.ITEM_PETRIFIER.get()) {
            if (++msgDelay > 10000) {
                msgDelay = 2;
            }
            ItemStack item = ItemStack.EMPTY;
            ItemStack s2 = ItemStack.EMPTY;
            ItemStack itemstack = player.getInventory().getSelected();
            if (!itemstack.has(ModDataComponents.PETRIFIER_READY)) {
                itemstack.set(ModDataComponents.PETRIFIER_READY, (Object)false);
            }
            player.getInventory();
            for (int k = 0; k < 36; ++k) {
                s2 = player.getInventory().getItem(k);
                if ((Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false ? s2.getItem() == Items.DIAMOND : s2.getItem() == Blocks.DIAMOND_BLOCK.asItem()) break;
            }
            if (s2 != ItemStack.EMPTY || player.isCreative()) {
                itemstack.set(ModDataComponents.PETRIFIER_READY, (Object)true);
            } else if (s2 == ItemStack.EMPTY) {
                Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(3));
                world.playSound(null, player.getX(), player.getY(), player.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                if (msgDelay > 1) {
                    player.sendSystemMessage((Component)Component.translatable((String)"info.petrifier.requiresdiamondorblock"));
                    msgDelay = 0;
                }
                event.setCanceled(true);
                return;
            }
            if (block == Blocks.CRAFTING_TABLE) {
                item = new ItemStack((ItemLike)InventoryPets.PET_CRAFTING_TABLE.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.ANVIL) {
                item = new ItemStack((ItemLike)InventoryPets.PET_ANVIL.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block instanceof BedBlock) {
                item = new ItemStack((ItemLike)InventoryPets.PET_BED.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.BREWING_STAND) {
                item = new ItemStack((ItemLike)InventoryPets.PET_BREWING_STAND.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.CHEST) {
                BlockEntity BlockEntity2 = world.getBlockEntity(event.getPos());
                if (BlockEntity2 instanceof Container) {
                    Containers.dropContents((Level)world, (BlockPos)event.getPos(), (Container)((Container)BlockEntity2));
                    world.updateNeighbourForOutputSignal(event.getPos(), block);
                }
                item = new ItemStack((ItemLike)InventoryPets.PET_CHEST.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.COBBLESTONE) {
                item = new ItemStack((ItemLike)InventoryPets.PET_COBBLESTONE.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.DIRT) {
                item = new ItemStack((ItemLike)InventoryPets.PET_DIRT.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.ENDER_CHEST) {
                item = new ItemStack((ItemLike)InventoryPets.PET_ENDER_CHEST.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.petrifier.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
            } else if (block == Blocks.ENCHANTING_TABLE) {
                BlockEntity BlockEntity3 = world.getBlockEntity(event.getPos());
                if (BlockEntity3 instanceof Container) {
                    Containers.dropContents((Level)world, (BlockPos)event.getPos(), (Container)((Container)BlockEntity3));
                    world.updateNeighbourForOutputSignal(event.getPos(), block);
                }
                item = new ItemStack((ItemLike)InventoryPets.PET_ENCHANTING_TABLE.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.FURNACE) {
                BlockEntity BlockEntity4 = world.getBlockEntity(event.getPos());
                if (BlockEntity4 instanceof Container) {
                    Containers.dropContents((Level)world, (BlockPos)event.getPos(), (Container)((Container)BlockEntity4));
                    world.updateNeighbourForOutputSignal(event.getPos(), block);
                }
                item = new ItemStack((ItemLike)InventoryPets.PET_FURNACE.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.petrifier.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
            } else if (block == Blocks.JUKEBOX) {
                BlockEntity BlockEntity5 = world.getBlockEntity(event.getPos());
                if (BlockEntity5 instanceof Container) {
                    Containers.dropContents((Level)world, (BlockPos)event.getPos(), (Container)((Container)BlockEntity5));
                    world.updateNeighbourForOutputSignal(event.getPos(), block);
                }
                item = new ItemStack((ItemLike)InventoryPets.PET_JUKEBOX.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.NETHER_PORTAL) {
                item = new ItemStack((ItemLike)InventoryPets.PET_NETHER_PORTAL.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.END_PORTAL) {
                item = new ItemStack((ItemLike)InventoryPets.PET_END_PORTAL.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.SPONGE) {
                item = new ItemStack((ItemLike)InventoryPets.PET_SPONGE.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else if (block == Blocks.TORCH) {
                item = new ItemStack((ItemLike)InventoryPets.PET_TORCH.get());
                ItemEntityEvents.popBlock(world, event.getPos(), player, item, s2);
            } else {
                Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(3));
                world.playSound(null, player.getX(), player.getY(), player.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                event.setCanceled(true);
                return;
            }
        }
    }

    public static void popBlock(Level world, BlockPos pos, Player player, ItemStack itemstack, ItemStack food) {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        ItemEntity entityitem = new ItemEntity(player.level(), player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, itemstack);
        world.addFreshEntity((Entity)entityitem);
        Float pitchAdj = Float.valueOf(rand.nextFloat() / 4.0f);
        if (rand.nextBoolean()) {
            pitchAdj = Float.valueOf(pitchAdj.floatValue() * -1.0f);
        }
        if (!player.isCreative()) {
            food.shrink(1);
            if (food.getCount() == 0) {
                ItemEntityEvents.removeItem(player, food);
            }
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.petrifier.get(), SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
        if (!AdvancementHelper.hasAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrifier"))) {
            AdvancementHelper.unlockAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrifier"));
        }
    }

    public static void popEntity(Level world, Entity entity, Player player, ItemStack itemstack, ItemStack food) {
        entity.remove(Entity.RemovalReason.DISCARDED);
        player.getMainHandItem().set(ModDataComponents.PETRIFIER_READY, (Object)false);
        ItemEntity entityitem = new ItemEntity(player.level(), player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, itemstack);
        world.addFreshEntity((Entity)entityitem);
        Float pitchAdj = Float.valueOf(rand.nextFloat() / 4.0f);
        if (rand.nextBoolean()) {
            pitchAdj = Float.valueOf(pitchAdj.floatValue() * -1.0f);
        }
        if (!player.isCreative()) {
            food.shrink(1);
            if (food.getCount() == 0) {
                ItemEntityEvents.removeItem(player, food);
            }
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.petrifier.get(), SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
        if (!AdvancementHelper.hasAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrifier"))) {
            AdvancementHelper.unlockAdvancement((ServerPlayer)player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"petrifier"));
        }
    }

    public static void removeItem(Player ep, ItemStack removeitem) {
        Inventory inventoryPlayer = ep.getInventory();
        for (int i = 0; i < 36; ++i) {
            ItemStack j;
            if (inventoryPlayer.getItem(i) == ItemStack.EMPTY || (j = inventoryPlayer.getItem(i)) == ItemStack.EMPTY || j.getItem() != removeitem.getItem()) continue;
            inventoryPlayer.setItem(i, ItemStack.EMPTY);
            break;
        }
    }

    private static void spawnLingeringCloud(Player entityplayer) {
        Collection collection = entityplayer.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entityplayer.level(), entityplayer.getX(), entityplayer.getY(), entityplayer.getZ());
            areaeffectcloudentity.setRadius(2.5f);
            areaeffectcloudentity.setRadiusOnUse(-0.5f);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
            for (MobEffectInstance effectinstance : collection) {
                areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
            }
            entityplayer.level().addFreshEntity((Entity)areaeffectcloudentity);
        }
    }

    @SubscribeEvent
    public static void onEntityTakeDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (entity instanceof Animal) {
            Animal animal = (Animal)entity;
            if (source.is(DamageTypes.FALL) && animal.isLeashed()) {
                event.setAmount(1.0f);
                animal.fallDistance = 0.0f;
            }
        }
    }
}

