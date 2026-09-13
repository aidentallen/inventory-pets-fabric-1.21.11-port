package com.inventorypets.fabric.item;

import com.inventorypets.fabric.config.InventoryPetsJsonConfig;
import com.inventorypets.fabric.init.ModAttachments;
import com.inventorypets.fabric.init.ModDataComponents;
import com.inventorypets.fabric.pet.PetDefinition;
import com.inventorypets.fabric.pet.PetDefinitions;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class InventoryPetItem extends Item {
    private static final Set<String> ITEM_GIFT_PETS = Set.of(
            "pet_chicken", "pet_iron_golem", "pet_ocelot", "pet_pig", "pet_quiver",
            "pet_sheep", "pet_siamese", "pet_slime", "pet_snow_golem", "pet_spider"
    );
    private static final List<Holder<Potion>> RANDOM_POTIONS = List.of(
            Potions.NIGHT_VISION, Potions.INVISIBILITY, Potions.LEAPING, Potions.FIRE_RESISTANCE,
            Potions.SWIFTNESS, Potions.WATER_BREATHING, Potions.HEALING, Potions.REGENERATION,
            Potions.STRENGTH, Potions.SLOW_FALLING, Potions.LUCK
    );
    private final PetDefinition definition;

    public InventoryPetItem(PetDefinition definition, Properties properties) {
        super(properties);
        this.definition = definition;
    }

    public PetDefinition definition() {
        return definition;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        if (!(entity instanceof ServerPlayer player) || level.getGameTime() % 20 != 0 || isDisabled()) return;
        if (!canFeed(player, false)) return;
        applyPassive(player);
        if (definition.id().equals("pet_black_hole") && stack.getOrDefault(ModDataComponents.ATTRACT_ITEMS, true)) {
            attractItems(level, player);
        }
        if (definition.id().equals("pet_anvil") && level.getGameTime() % 200 == 0) {
            repairOneItem(player, 1);
        }
        int feedInterval = Math.max(1, InventoryPetsJsonConfig.integer("petEatTimerFactor")) * 20;
        if (!isSatedChest() && level.getGameTime() % feedInterval == 0 && canFeed(player, true)) {
            generateItems(player, stack);
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (isDisabled()) {
            player.displayClientMessage(Component.translatable("tooltip.ip.disabled").withStyle(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
        }
        if (definition.id().startsWith("pet_meta")) {
            if (!level.isClientSide()) transformMeta((ServerPlayer) player, hand, stack);
            return InteractionResult.SUCCESS;
        }
        if (definition.id().equals("pet_ender_chest")) {
            if (!level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider(
                        (syncId, inventory, ignored) -> ChestMenu.threeRows(syncId, inventory, player.getEnderChestInventory()),
                        Component.translatable("container.enderchest")));
            }
            return InteractionResult.SUCCESS;
        }
        if (definition.id().contains("chest")) {
            if (!level.isClientSide()) openChest(player, stack);
            return InteractionResult.SUCCESS;
        }
        if (ITEM_GIFT_PETS.contains(definition.id()) && player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                boolean enabled = !stack.getOrDefault(ModDataComponents.GIVE_ITEMS, true);
                stack.set(ModDataComponents.GIVE_ITEMS, enabled);
                player.displayClientMessage(Component.literal("Item gifts: " + (enabled ? "on" : "off")), true);
            }
            return InteractionResult.SUCCESS;
        }
        if (definition.id().equals("pet_black_hole") && player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                boolean enabled = !stack.getOrDefault(ModDataComponents.ATTRACT_ITEMS, true);
                stack.set(ModDataComponents.ATTRACT_ITEMS, enabled);
                player.displayClientMessage(Component.literal("Item attraction: " + (enabled ? "on" : "off")), true);
            }
            return InteractionResult.SUCCESS;
        }
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (player.getCooldowns().isOnCooldown(stack)) return InteractionResult.FAIL;
        if (!canFeed(player, definition.cooldownTicks() > 0)) return InteractionResult.FAIL;

        activate((ServerLevel) level, (ServerPlayer) player, stack);
        if (definition.cooldownTicks() > 0) player.getCooldowns().addCooldown(stack, dynamicCooldown());
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) return super.useOn(context);
        if (definition.id().equals("pet_mooshroom")) {
            ItemStack boneMeal = new ItemStack(Items.BONE_MEAL);
            BlockPos pos = context.getClickedPos();
            if (BoneMealItem.growCrop(boneMeal, context.getLevel(), pos)
                    || BoneMealItem.growWaterPlant(boneMeal, context.getLevel(), pos, context.getClickedFace())) {
                BoneMealItem.addGrowthParticles(context.getLevel(), pos, 15);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (definition.id().equals("pet_torch")) {
            BlockPos target = context.getClickedPos().relative(context.getClickedFace());
            if (context.getLevel().getBlockState(target).canBeReplaced()) {
                context.getLevel().setBlockAndUpdate(target,
                        context.getPlayer() != null && context.getPlayer().isShiftKeyDown()
                                ? Blocks.FIRE.defaultBlockState()
                                : Blocks.TORCH.defaultBlockState());
                if (context.getPlayer() != null) context.getPlayer().getCooldowns().addCooldown(context.getItemInHand(), 4);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        for (int line = 1; line <= definition.tooltipLines(); line++) {
            String key = definition.tooltipStem().equals("petchest")
                    ? "tooltip.ip.petchestopen"
                    : "tooltip.ip." + definition.tooltipStem() + line;
            tooltip.accept(Component.translatable(key).withStyle(ChatFormatting.AQUA));
        }
        String foodId = foodId();
        if (!foodId.isEmpty()) {
            Item food = net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(Identifier.parse(foodId));
            tooltip.accept(Component.translatable("tooltip.ip.favoritefood").append(" ").append(food.getName()).withStyle(ChatFormatting.GRAY));
        }
        if (isSatedChest()) tooltip.accept(Component.translatable("tooltip.ip.sated").withStyle(ChatFormatting.GOLD));
    }

    private void applyPassive(ServerPlayer player) {
        String id = definition.id();
        switch (id) {
            case "pet_blaze" -> effect(player, MobEffects.STRENGTH, 0);
            case "pet_chicken" -> effect(player, MobEffects.SPEED, Math.max(0, InventoryPetsJsonConfig.integer("chickenEffectLevel") - 1));
            case "pet_cheetah" -> effect(player, MobEffects.HASTE, 1);
            case "pet_iron_golem" -> effect(player, MobEffects.ABSORPTION, 1);
            case "pet_magma_cube" -> effect(player, MobEffects.FIRE_RESISTANCE, 0);
            case "pet_ocelot" -> effect(player, MobEffects.NIGHT_VISION, 0);
            case "pet_purplicious_cow" -> effect(player, MobEffects.REGENERATION, 0);
            case "pet_sheep" -> effect(player, MobEffects.SLOW_FALLING, 0);
            case "pet_spider" -> effect(player, MobEffects.JUMP_BOOST, 1);
            case "pet_squid" -> {
                effect(player, MobEffects.WATER_BREATHING, 0);
                effect(player, MobEffects.NIGHT_VISION, 0);
                if (!InventoryPetsJsonConfig.bool("disableSquidSpeed")) effect(player, MobEffects.DOLPHINS_GRACE, 0);
            }
            case "pet_mickerson" -> effect(player, MobEffects.RESISTANCE, 0);
            case "pet_cobblestone" -> {
                if (!ModItems.hasPet(player, "pet_dirt")
                        && (player.level().getBlockState(player.blockPosition().below()).is(Blocks.COBBLESTONE)
                        || player.level().getBlockState(player.blockPosition().below()).is(Blocks.STONE))) {
                    effect(player, MobEffects.SPEED, 2);
                    effect(player, MobEffects.HASTE, 1);
                    effect(player, MobEffects.STRENGTH, 0);
                }
            }
            case "pet_dirt" -> {
                if (!ModItems.hasPet(player, "pet_cobblestone")
                        && (player.level().getBlockState(player.blockPosition().below()).is(Blocks.DIRT)
                        || player.level().getBlockState(player.blockPosition().below()).is(Blocks.GRASS_BLOCK))) {
                    effect(player, MobEffects.SPEED, 2);
                    effect(player, MobEffects.HASTE, 1);
                    effect(player, MobEffects.REGENERATION, 0);
                }
            }
            case "pet_pig" -> {
                if (player.getFoodData().getFoodLevel() < 2) player.getFoodData().setFoodLevel(2);
            }
            case "pet_pacman" -> autoEat(player);
            case "pet_wither" -> player.removeEffect(MobEffects.WITHER);
            case "pet_cow" -> removeNegativeEffects(player);
            default -> {
            }
        }
    }

    private void activate(ServerLevel level, ServerPlayer player, ItemStack stack) {
        String id = definition.id();
        switch (id) {
            case "pet_anvil" -> repairInventory(player);
            case "pet_apple" -> appleAttack(level, player);
            case "pet_banana" -> throwSnowball(level, player, Items.GOLDEN_APPLE);
            case "pet_bed" -> level.setDayTime(level.getDayTime() - level.getDayTime() % 24000L + 24000L);
            case "pet_biome" -> player.displayClientMessage(Component.literal("Biome: ").append(level.getBiome(player.blockPosition()).getRegisteredName()), false);
            case "pet_black_hole" -> slowTime(level, player);
            case "pet_brewing_stand" -> give(player, PotionContents.createItemStack(
                    Items.POTION, RANDOM_POTIONS.get(level.random.nextInt(RANDOM_POTIONS.size()))));
            case "pet_cheetah" -> effect(player, MobEffects.SPEED, 5, 200);
            case "pet_cloud" -> cloud(level, player);
            case "pet_cobblestone" -> replaceBelow(level, player, Blocks.DIRT, Blocks.COBBLESTONE);
            case "pet_cow" -> fillContainer(player, Items.BUCKET, Items.MILK_BUCKET);
            case "pet_crafting_table" -> player.openMenu(new SimpleMenuProvider(
                    (syncId, inventory, ignored) -> new CraftingMenu(syncId, inventory, ContainerLevelAccess.create(level, player.blockPosition())),
                    Component.translatable("container.crafting")));
            case "pet_creeper" -> {
                if (!InventoryPetsJsonConfig.bool("disableCreeperExplosion")) level.explode(player, player.getX(), player.getY(), player.getZ(), 3.0f, Level.ExplosionInteraction.NONE);
            }
            case "pet_dingot" -> player.displayClientMessage(Component.literal("Nearest stronghold can be located with /locate structure minecraft:stronghold"), false);
            case "pet_dirt" -> replaceBelow(level, player, Blocks.COBBLESTONE, Blocks.DIRT);
            case "pet_dubstep" -> blast(level, player, 10, 8.0f, MobEffects.WEAKNESS);
            case "pet_enchanting_table" -> player.openMenu(new SimpleMenuProvider(
                    (syncId, inventory, ignored) -> new EnchantmentMenu(syncId, inventory, ContainerLevelAccess.create(level, player.blockPosition())),
                    Component.translatable("container.enchant")));
            case "pet_enderman" -> teleportForward(player, 16.0);
            case "pet_furnace" -> smeltFirst(level, player);
            case "pet_ghast" -> shootFireball(level, player);
            case "pet_heart" -> healNearby(level, player);
            case "pet_shield" -> shieldAllies(level, player);
            case "pet_house" -> home(player, stack);
            case "pet_illuminati" -> randomItem(level, player);
            case "pet_juggernaut" -> juggernaut(level, player);
            case "pet_jukebox" -> jukebox(level, player, stack);
            case "pet_moon" -> reverseGravity(level, player);
            case "pet_mooshroom" -> fillContainer(player, Items.BOWL, Items.MUSHROOM_STEW);
            case "pet_nether_portal" -> changeDimension(player, stack, Level.NETHER);
            case "pet_end_portal" -> changeDimension(player, stack, Level.END);
            case "pet_pacman" -> effect(player, MobEffects.STRENGTH, 4, 200);
            case "pet_pingot" -> findOre(level, player, !InventoryPetsJsonConfig.bool("disablePingotAutoExtract"));
            case "pet_pufferfish" -> blast(level, player, 8, 2.0f, MobEffects.POISON);
            case "pet_purplicious_cow" -> fillContainer(player, Items.BUCKET, Items.LAVA_BUCKET);
            case "pet_qcm" -> toggleCompanions(level, player, stack, "qcm", 2);
            case "pet_quiver" -> rapidShot(level, player);
            case "pet_siamese" -> toggleCompanions(level, player, stack, "siamese", 1);
            case "pet_silverfish" -> teleportForward(player, 3.0);
            case "pet_slime" -> player.heal(player.getMaxHealth());
            case "pet_snow_golem" -> give(player, new ItemStack(Items.SNOWBALL, 8));
            case "pet_sponge" -> sponge(level, player);
            case "pet_sun" -> blast(level, player, 12, 5.0f, MobEffects.GLOWING);
            case "pet_wither" -> shootWitherVolley(level, player);
            case "pet_wolf" -> toggleCompanions(level, player, stack, "wolf", 1);
            case "pet_april_fool" -> spawnFriendlyVillager(level, player);
            case "pet_christmas_tree", "pet_menorah", "pet_mishumaa_saba", "pet_politically_correct" ->
                    give(player, new ItemStack(ModItems.item("holiday_gift")));
            case "pet_iron_golem", "pet_magma_cube", "pet_spider", "pet_chicken", "pet_ocelot",
                    "pet_pig", "pet_sheep", "pet_squid", "pet_loot", "pet_mickerson", "pet_pixie" ->
                    player.displayClientMessage(Component.literal("Passive ability active."), true);
            case "pet_lead" -> player.displayClientMessage(Component.literal("Use on a mob to leash and tame it."), true);
            case "pet_saddle", "pet_flying_saddle" -> player.displayClientMessage(Component.literal("Use on a mob to ride it."), true);
            case "pet_torch" -> igniteAhead(level, player);
            default -> throw new IllegalStateException("No ability handler registered for " + id);
        }
    }

    private void openChest(Player player, ItemStack stack) {
        int slots = definition.id().contains("double") ? 54 : 27;
        StackBackedContainer container = new StackBackedContainer(stack, slots);
        player.openMenu(new SimpleMenuProvider(
                (syncId, inventory, ignored) -> slots == 54
                        ? ChestMenu.sixRows(syncId, inventory, container)
                        : ChestMenu.threeRows(syncId, inventory, container),
                stack.getHoverName()));
    }

    private boolean canFeed(Player player, boolean consume) {
        if (isSatedChest() || !InventoryPetsJsonConfig.bool("petsMustEat") || player.isCreative()) return true;
        String configured = foodId();
        if (configured.isEmpty()) return true;
        Item food = net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(Identifier.parse(configured));
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack candidate = player.getInventory().getItem(i);
            if (candidate.is(food)) {
                if (consume) candidate.shrink(1);
                return true;
            }
            if (candidate.is(ModItems.FEED_BAG)) {
                List<ItemStack> contents = candidate.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).stream().map(ItemStack::copy).toList();
                for (ItemStack nested : contents) {
                    if (nested.is(food)) {
                        if (consume) {
                            nested.shrink(1);
                            candidate.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(contents));
                        }
                        return true;
                    }
                }
            }
        }
        player.displayClientMessage(Component.literal("Favorite food required: ").append(food.getName()).withStyle(ChatFormatting.RED), true);
        return false;
    }

    private boolean isDisabled() {
        if (definition.id().equals("pet_christmas_tree") || definition.id().equals("pet_menorah")
                || definition.id().equals("pet_mishumaa_saba") || definition.id().equals("pet_politically_correct")) {
            if (InventoryPetsJsonConfig.bool("disableHolidayPets")) return true;
        }
        if (definition.id().equals("pet_april_fool") && InventoryPetsJsonConfig.bool("disableAprilFoolHoliday")) return true;
        String key = definition.disabledConfigKey();
        return !key.isEmpty() && InventoryPetsJsonConfig.bool(key);
    }

    private boolean isSatedChest() {
        return definition.id().startsWith("pet_sated_");
    }

    private String foodId() {
        String key = definition.foodConfigKey();
        return key.isEmpty() ? "" : InventoryPetsJsonConfig.string(key);
    }

    private int dynamicCooldown() {
        if (definition.id().equals("pet_illuminati")) return Math.max(1, InventoryPetsJsonConfig.integer("illuminatiCooldown")) * 1200;
        if (definition.id().equals("pet_juggernaut")) return Math.max(1, InventoryPetsJsonConfig.integer("juggernautCooldown")) * 1200;
        return definition.cooldownTicks();
    }

    private static void effect(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect, int amplifier) {
        effect(entity, effect, amplifier, 40);
    }

    private static void effect(LivingEntity entity, net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect, int amplifier, int duration) {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, true, false, true));
    }

    private static void removeNegativeEffects(ServerPlayer player) {
        player.getActiveEffects().stream().filter(instance -> !instance.getEffect().value().isBeneficial()).map(MobEffectInstance::getEffect).toList().forEach(player::removeEffect);
    }

    private void generateItems(ServerPlayer player, ItemStack petStack) {
        if (InventoryPetsJsonConfig.bool("disablePetsGiveItems")
                || !petStack.getOrDefault(ModDataComponents.GIVE_ITEMS, true)) return;
        ItemStack gift = switch (definition.id()) {
            case "pet_chicken" -> new ItemStack(Items.EGG);
            case "pet_iron_golem" -> new ItemStack(Items.POPPY);
            case "pet_ocelot" -> new ItemStack(Items.COD);
            case "pet_pig" -> new ItemStack(Items.PORKCHOP);
            case "pet_quiver" -> new ItemStack(Items.ARROW, 4);
            case "pet_sheep" -> new ItemStack(Items.WHITE_WOOL);
            case "pet_siamese" -> new ItemStack(ModItems.item("siamese_gift"));
            case "pet_slime" -> new ItemStack(Items.SLIME_BALL);
            case "pet_snow_golem" -> new ItemStack(Items.SNOWBALL, 4);
            case "pet_spider" -> new ItemStack(Items.STRING);
            default -> ItemStack.EMPTY;
        };
        if (!gift.isEmpty()) give(player, gift);
    }

    private static void autoEat(ServerPlayer player) {
        if (player.getFoodData().getFoodLevel() > InventoryPetsJsonConfig.integer("pacManEatLevel")) return;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack candidate = player.getInventory().getItem(i);
            net.minecraft.world.food.FoodProperties food = candidate.get(DataComponents.FOOD);
            if (food != null) {
                player.getFoodData().eat(food);
                candidate.shrink(1);
                return;
            }
        }
    }

    private static void transformMeta(ServerPlayer player, InteractionHand hand, ItemStack original) {
        String[] peaceful = {"pet_chicken", "pet_cow", "pet_mooshroom", "pet_ocelot", "pet_pig", "pet_sheep", "pet_squid"};
        String[] mob = {"pet_blaze", "pet_creeper", "pet_enderman", "pet_ghast", "pet_iron_golem", "pet_magma_cube", "pet_snow_golem", "pet_spider"};
        String[] utility = {"pet_anvil", "pet_bed", "pet_brewing_stand", "pet_chest", "pet_crafting_table", "pet_double_chest", "pet_enchanting_table", "pet_ender_chest", "pet_furnace", "pet_jukebox"};
        String[] special = {"pet_banana", "pet_biome", "pet_dingot", "pet_loot", "pet_pingot", "pet_qcm", "pet_quiver", "pet_sponge"};
        String id = ((InventoryPetItem) original.getItem()).definition.id();
        String[] pool = id.equals("pet_meta_peaceful") ? peaceful
                : id.equals("pet_meta_mob") ? mob
                : id.equals("pet_meta_utility") ? utility
                : id.equals("pet_meta_special") ? special
                : PetDefinitions.ALL.keySet().stream().filter(key -> !key.startsWith("pet_meta")).toArray(String[]::new);
        Item target = ModItems.item(pool[player.getRandom().nextInt(pool.length)]);
        ItemStack transformed = new ItemStack(target);
        Component customName = original.get(DataComponents.CUSTOM_NAME);
        if (customName != null) transformed.set(DataComponents.CUSTOM_NAME, customName);
        player.setItemInHand(hand, transformed);
    }

    private static void repairInventory(ServerPlayer player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item.isDamaged()) item.setDamageValue(Math.max(0, item.getDamageValue() - 8));
        }
    }

    private static void repairOneItem(ServerPlayer player, int amount) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item.isDamaged()) {
                item.setDamageValue(Math.max(0, item.getDamageValue() - amount));
                return;
            }
        }
    }

    private static void throwSnowball(ServerLevel level, ServerPlayer player, Item projectileItem) {
        Snowball projectile = new Snowball(level, player, new ItemStack(projectileItem));
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.8f, 2.0f);
        level.addFreshEntity(projectile);
        level.playSound(null, player.blockPosition(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.8f, 1.0f);
    }

    private static void appleAttack(ServerLevel level, ServerPlayer player) {
        if (player.isShiftKeyDown()) {
            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5),
                    target -> target != player && !player.isAlliedTo(target))) {
                target.hurtServer(level, level.damageSources().playerAttack(player), 10.0f);
                target.setDeltaMovement(target.getDeltaMovement().add(0, -1.5, 0));
            }
            player.setDeltaMovement(player.getDeltaMovement().add(0, -1.0, 0));
            player.hurtMarked = true;
        } else {
            for (int i = 0; i < 4; i++) throwSnowball(level, player, Items.APPLE);
        }
    }

    private static void attractItems(ServerLevel level, ServerPlayer player) {
        Vec3 center = player.position();
        for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(12))) {
            Vec3 direction = center.subtract(item.position()).normalize().scale(0.7);
            item.setDeltaMovement(direction);
        }
    }

    private static void slowTime(ServerLevel level, ServerPlayer player) {
        for (Entity entity : level.getEntities(player, player.getBoundingBox().inflate(25))) {
            if (entity instanceof Player other
                    && (!InventoryPetsJsonConfig.bool("enableBlackHoleAffectsPlayers") || player.isAlliedTo(other))) continue;
            if (entity instanceof LivingEntity living && !player.isAlliedTo(living)) {
                living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 120, 5));
            }
            if (entity instanceof Projectile || entity instanceof ItemEntity || entity instanceof LivingEntity) {
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.08));
            }
        }
    }

    private static void cloud(ServerLevel level, ServerPlayer player) {
        if (player.isShiftKeyDown() && !InventoryPetsJsonConfig.bool("disableCloudLightning")) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);
            if (bolt != null) {
                bolt.setPos(player.position().add(player.getLookAngle().scale(8)));
                level.addFreshEntity(bolt);
            }
        } else if (!InventoryPetsJsonConfig.bool("disableCloudFly")) {
            player.setDeltaMovement(player.getLookAngle().scale(1.2).add(0, 0.35, 0));
            player.hurtMarked = true;
        }
    }

    private static void replaceBelow(ServerLevel level, Player player, net.minecraft.world.level.block.Block from, net.minecraft.world.level.block.Block to) {
        BlockPos pos = player.blockPosition().below();
        if (level.getBlockState(pos).is(from)) level.setBlockAndUpdate(pos, to.defaultBlockState());
    }

    private static void fillContainer(Player player, Item empty, Item filled) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(empty)) {
                stack.shrink(1);
                give(player, new ItemStack(filled));
                return;
            }
        }
    }

    private static void blast(ServerLevel level, ServerPlayer player, double radius, float damage, net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect) {
        AABB area = player.getBoundingBox().inflate(radius);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, entity -> entity != player)) {
            if (target instanceof Player && !allowsPlayers(definitionKeyFor(effect))) continue;
            if (damage > 0) target.hurtServer(level, level.damageSources().playerAttack(player), damage);
            target.addEffect(new MobEffectInstance(effect, 100, 1));
            Vec3 push = target.position().subtract(player.position()).normalize().scale(1.5);
            target.setDeltaMovement(target.getDeltaMovement().add(push));
        }
    }

    private static void juggernaut(ServerLevel level, ServerPlayer player) {
        int duration = Math.max(20, InventoryPetsJsonConfig.integer("juggernautShieldWallDuration") * 1200);
        player.setAttached(ModAttachments.SHIELD, duration);
        effect(player, MobEffects.RESISTANCE, 4, duration);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10),
                target -> target != player && !player.isAlliedTo(target))) {
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, 2));
        }
    }

    private static void jukebox(ServerLevel level, ServerPlayer player, ItemStack petStack) {
        if (player.isShiftKeyDown()) {
            player.connection.send(new ClientboundStopSoundPacket(null, SoundSource.RECORDS));
            return;
        }
        List<Holder<net.minecraft.sounds.SoundEvent>> sounds = new java.util.ArrayList<>();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack candidate = player.getInventory().getItem(i);
            if (candidate.is(Items.MUSIC_DISC_13)) sounds.add(SoundEvents.MUSIC_DISC_13);
            else if (candidate.is(Items.MUSIC_DISC_CAT)) sounds.add(SoundEvents.MUSIC_DISC_CAT);
            else if (candidate.is(Items.MUSIC_DISC_PIGSTEP)) sounds.add(SoundEvents.MUSIC_DISC_PIGSTEP);
            else if (candidate.is(Items.MUSIC_DISC_OTHERSIDE)) sounds.add(SoundEvents.MUSIC_DISC_OTHERSIDE);
            else if (candidate.is(Items.MUSIC_DISC_RELIC)) sounds.add(SoundEvents.MUSIC_DISC_RELIC);
            else if (candidate.is(Items.MUSIC_DISC_CREATOR)) sounds.add(SoundEvents.MUSIC_DISC_CREATOR);
            else if (candidate.is(Items.MUSIC_DISC_PRECIPICE)) sounds.add(SoundEvents.MUSIC_DISC_PRECIPICE);
        }
        if (sounds.isEmpty()) {
            player.displayClientMessage(Component.literal("Jukebox Pet needs a music disc in your inventory."), true);
            return;
        }
        CompoundTag data = petStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int index = data.getInt("jukebox_index").orElse(0) % sounds.size();
        level.playSound(null, player.blockPosition(), sounds.get(index).value(), SoundSource.RECORDS, 1.0f, 1.0f);
        CustomData.update(DataComponents.CUSTOM_DATA, petStack, tag -> tag.putInt("jukebox_index", (index + 1) % sounds.size()));
    }

    private static void reverseGravity(ServerLevel level, ServerPlayer player) {
        for (Entity entity : level.getEntities(player, player.getBoundingBox().inflate(12))) {
            if (entity instanceof Player other
                    && (!InventoryPetsJsonConfig.bool("enableMoonAffectsPlayers") || player.isAlliedTo(other))) continue;
            if (entity instanceof LivingEntity living && !player.isAlliedTo(living)) {
                living.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 120, 3));
            } else if (entity instanceof ItemEntity) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.8, 0));
            }
        }
    }

    private static void rapidShot(ServerLevel level, ServerPlayer player) {
        player.setAttached(ModAttachments.RAPID_SHOT, 600);
        player.displayClientMessage(Component.literal("Insta-shot enabled for 30 seconds."), true);
    }

    private static String definitionKeyFor(net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect) {
        if (effect == MobEffects.POISON) return "enablePufferfishAffectsPlayers";
        if (effect == MobEffects.LEVITATION) return "enableMoonAffectsPlayers";
        if (effect == MobEffects.GLOWING) return "enableSunAffectsPlayers";
        return "enableDubstepAffectsPlayers";
    }

    private static boolean allowsPlayers(String key) {
        return InventoryPetsJsonConfig.bool(key);
    }

    private static void teleportForward(ServerPlayer player, double distance) {
        Vec3 target = player.position().add(player.getLookAngle().scale(distance));
        player.teleportTo(target.x, target.y, target.z);
    }

    private static void smeltFirst(ServerLevel level, Player player) {
        for (int i = 0; i < Math.min(9, player.getInventory().getContainerSize()); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.isEmpty()) continue;
            SingleRecipeInput input = new SingleRecipeInput(stack.copyWithCount(1));
            var recipe = level.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING, input, level);
            if (recipe.isPresent()) {
                ItemStack result = recipe.get().value().assemble(input, level.registryAccess());
                stack.shrink(1);
                give(player, result);
                return;
            }
        }
    }

    private static void shootFireball(ServerLevel level, ServerPlayer player) {
        Vec3 direction = player.getLookAngle();
        LargeFireball fireball = new LargeFireball(level, player, direction, 1);
        fireball.setPos(player.getEyePosition().add(direction.scale(1.5)));
        level.addFreshEntity(fireball);
    }

    private static void shootWitherVolley(ServerLevel level, ServerPlayer player) {
        int count = player.isShiftKeyDown() ? 1 : 3;
        for (int i = 0; i < count; i++) {
            float yawOffset = count == 1 ? 0.0f : (i - 1) * 8.0f;
            double yaw = Math.toRadians(player.getYRot() + yawOffset);
            double pitch = Math.toRadians(player.getXRot());
            Vec3 direction = new Vec3(-Math.sin(yaw) * Math.cos(pitch), -Math.sin(pitch), Math.cos(yaw) * Math.cos(pitch));
            WitherSkull skull = new WitherSkull(level, player, direction);
            skull.setDangerous(player.isShiftKeyDown());
            skull.setPos(player.getEyePosition().add(direction.scale(1.5)));
            level.addFreshEntity(skull);
        }
    }

    private static void healNearby(ServerLevel level, ServerPlayer player) {
        player.heal(8.0f);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(8))) {
            if (target != player && target.isAlliedTo(player)) target.heal(8.0f);
        }
    }

    private static void shieldAllies(ServerLevel level, ServerPlayer player) {
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(32))) {
            if (target == player || target.isAlliedTo(player)) {
                target.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 1240, 1, true, true));
            }
        }
    }

    private static void home(ServerPlayer player, ItemStack stack) {
        if (player.isShiftKeyDown()) {
            stack.set(ModDataComponents.HOME_DIMENSION, player.level().dimension().identifier().toString());
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putInt("home_x", player.blockPosition().getX());
                tag.putInt("home_y", player.blockPosition().getY());
                tag.putInt("home_z", player.blockPosition().getZ());
            });
            player.displayClientMessage(Component.literal("Home set."), true);
            return;
        }
        String dimensionId = stack.get(ModDataComponents.HOME_DIMENSION);
        CompoundTag homeData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (dimensionId == null || !homeData.contains("home_x") || !homeData.contains("home_y") || !homeData.contains("home_z")) {
            player.displayClientMessage(Component.literal("Sneak-right-click to set home first."), true);
            return;
        }
        int x = homeData.getInt("home_x").orElseThrow();
        int y = homeData.getInt("home_y").orElseThrow();
        int z = homeData.getInt("home_z").orElseThrow();
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, Identifier.parse(dimensionId));
        ServerLevel destination = player.level().getServer().getLevel(key);
        if (destination == null) throw new IllegalStateException("Home dimension is unavailable: " + dimensionId);
        player.teleportTo(destination, x + 0.5, y, z + 0.5, Set.of(), player.getYRot(), player.getXRot(), false);
    }

    private static void randomItem(ServerLevel level, ServerPlayer player) {
        if (InventoryPetsJsonConfig.bool("disableIlluminatiGiveItems")) return;
        List<Item> candidates = net.minecraft.core.registries.BuiltInRegistries.ITEM.stream().filter(item -> item != Items.AIR).toList();
        give(player, new ItemStack(candidates.get(level.random.nextInt(Math.min(candidates.size(), Math.max(1, InventoryPetsJsonConfig.integer("illuminatiItemCapLimit")))))));
        effect(player, MobEffects.INVISIBILITY, 0, Math.max(1, InventoryPetsJsonConfig.integer("illuminatiInvisibleDuration")) * 1200);
    }

    private static void changeDimension(ServerPlayer player, ItemStack stack, ResourceKey<Level> destinationKey) {
        boolean returning = player.level().dimension() == destinationKey;
        ResourceKey<Level> targetKey = destinationKey;
        double targetX;
        double targetY;
        double targetZ;
        if (returning) {
            String lastDimension = stack.getOrDefault(ModDataComponents.LAST_DIMENSION, Level.OVERWORLD.identifier().toString());
            targetKey = ResourceKey.create(Registries.DIMENSION, Identifier.parse(lastDimension));
            targetX = stack.getOrDefault(ModDataComponents.LAST_X, player.blockPosition().getX()) + 0.5;
            targetY = stack.getOrDefault(ModDataComponents.LAST_Y, player.blockPosition().getY());
            targetZ = stack.getOrDefault(ModDataComponents.LAST_Z, player.blockPosition().getZ()) + 0.5;
        } else {
            stack.set(ModDataComponents.LAST_DIMENSION, player.level().dimension().identifier().toString());
            stack.set(ModDataComponents.LAST_X, player.blockPosition().getX());
            stack.set(ModDataComponents.LAST_Y, player.blockPosition().getY());
            stack.set(ModDataComponents.LAST_Z, player.blockPosition().getZ());
            double scale = destinationKey == Level.NETHER ? 0.125 : player.level().dimension() == Level.NETHER ? 8.0 : 1.0;
            targetX = player.getX() * scale;
            targetY = player.getY();
            targetZ = player.getZ() * scale;
        }
        ServerLevel destination = player.level().getServer().getLevel(targetKey);
        if (destination == null) return;
        player.teleportTo(destination, targetX, Math.max(destination.getMinY() + 2, targetY), targetZ,
                Set.of(), player.getYRot(), player.getXRot(), false);
    }

    private static void findOre(ServerLevel level, ServerPlayer player, boolean extract) {
        BlockPos origin = player.blockPosition();
        for (int radius = 1; radius <= 12; radius++) {
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
                if (level.getBlockState(pos).is(net.minecraft.tags.BlockTags.DIAMOND_ORES)) {
                    player.displayClientMessage(Component.literal("Ore found at " + pos.toShortString()), false);
                    if (extract) level.destroyBlock(pos, true, player);
                    return;
                }
            }
        }
        player.displayClientMessage(Component.literal("No diamond ore found within 12 blocks."), false);
    }

    private static void sponge(ServerLevel level, ServerPlayer player) {
        int radius = Math.max(1, InventoryPetsJsonConfig.integer("spongeAbsorbBlockRadius"));
        int changed = 0;
        for (BlockPos pos : BlockPos.betweenClosed(player.blockPosition().offset(-radius, -radius, -radius), player.blockPosition().offset(radius, radius, radius))) {
            if (level.getBlockState(pos).is(Blocks.WATER) && changed++ < 256) level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
        if (changed == 0) level.setBlockAndUpdate(player.blockPosition().relative(player.getDirection()), Blocks.WATER.defaultBlockState());
    }

    private static void toggleCompanions(ServerLevel level, ServerPlayer player, ItemStack stack, String role, int count) {
        String tag = "inventorypets." + role + "." + player.getUUID();
        List<Entity> existing = level.getEntities(player, player.getBoundingBox().inflate(128), entity -> entity.getTags().contains(tag));
        if (!existing.isEmpty()) {
            existing.forEach(Entity::discard);
            return;
        }
        for (int i = 0; i < count; i++) {
            net.minecraft.world.entity.TamableAnimal companion;
            if (role.equals("siamese")) {
                companion = EntityType.CAT.create(level, EntitySpawnReason.TRIGGERED);
            } else {
                companion = EntityType.WOLF.create(level, EntitySpawnReason.TRIGGERED);
            }
            if (companion == null) continue;
            companion.tame(player);
            companion.addTag(tag);
            companion.setPersistenceRequired();
            if (role.equals("qcm")) {
                companion.setCustomName(Component.literal(i == 0 ? "Mini Quantum Blaze" : "Mini Quantum Enderman"));
            } else if (stack.has(DataComponents.CUSTOM_NAME)) {
                companion.setCustomName(stack.getHoverName());
            }
            companion.setPos(player.position().add(player.getLookAngle().scale(2)).add(i, 0, 0));
            level.addFreshEntity(companion);
        }
    }

    private static void igniteAhead(ServerLevel level, ServerPlayer player) {
        Vec3 center = player.getEyePosition().add(player.getLookAngle().scale(3));
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class,
                new AABB(center, center).inflate(2), target -> target != player && !player.isAlliedTo(target))) {
            target.setRemainingFireTicks(160);
        }
        BlockPos pos = BlockPos.containing(center);
        if (level.getBlockState(pos).canBeReplaced()) level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
    }

    private static <T extends Entity> void spawn(ServerLevel level, ServerPlayer player, EntityType<T> type) {
        T entity = type.create(level, EntitySpawnReason.TRIGGERED);
        if (entity != null) {
            entity.setPos(player.position().add(player.getLookAngle().scale(2)));
            level.addFreshEntity(entity);
        }
    }

    private static void spawnFriendlyVillager(ServerLevel level, ServerPlayer player) {
        net.minecraft.world.entity.npc.villager.Villager villager = EntityType.VILLAGER.create(level, EntitySpawnReason.TRIGGERED);
        if (villager == null) return;
        villager.setCustomName(Component.literal(switch (level.random.nextInt(3)) {
            case 0 -> "Bill Gates";
            case 1 -> "Steve Ballmer";
            default -> "Satya Nadella";
        }));
        villager.setCustomNameVisible(true);
        villager.setPos(player.position().add(player.getLookAngle().scale(2)));
        level.addFreshEntity(villager);
    }

    private static void give(Player player, ItemStack stack) {
        if (!player.addItem(stack)) player.drop(stack, false);
    }
}
