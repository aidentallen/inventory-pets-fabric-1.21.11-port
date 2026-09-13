/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  net.minecraft.util.Util
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.CatVariantTags
 *  net.minecraft.tags.StructureTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.EntitySpawnReason
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.AvoidEntityGoal
 *  net.minecraft.world.entity.ai.goal.BreedGoal
 *  net.minecraft.world.entity.ai.goal.CatLieOnBedGoal
 *  net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.FollowOwnerGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.LeapAtTargetGoal
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.OcelotAttackGoal
 *  net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal
 *  net.minecraft.world.entity.ai.goal.TemptGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.feline.Cat
 *  net.minecraft.world.entity.animal.feline.CatVariant
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.BedBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.level.storage.loot.LootParams
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.AABB
 *  net.neoforged.neoforge.event.EventHooks
 */
package com.inventorypets.entities;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;

public class SiamesePetEntity
extends Cat {
    private static final Ingredient TEMPT_INGREDIENT = Ingredient.of((ItemLike[])new ItemLike[]{Items.COD, Items.SALMON});
    public static final double TEMPT_SPEED_MOD = 0.6;
    public static final double WALK_SPEED_MOD = 0.8;
    public static final double SPRINT_SPEED_MOD = 1.33;
    private static final EntityDataAccessor<Holder<CatVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(SiamesePetEntity.class, (EntityDataSerializer)EntityDataSerializers.CAT_VARIANT);
    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(SiamesePetEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(SiamesePetEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(SiamesePetEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    private static final ResourceKey<CatVariant> DEFAULT_VARIANT = CatVariant.SIAMESE;
    @Nullable
    private CatAvoidEntityGoal<Player> avoidPlayersGoal;
    @Nullable
    private TemptGoal temptGoal;
    private float lieDownAmount;
    private float lieDownAmountO;
    private float lieDownAmountTail;
    private float lieDownAmountOTail;
    private float relaxStateOneAmount;
    private float relaxStateOneAmountO;
    public static final Map<Integer, Identifier> TEXTURE_BY_TYPE = (Map)Util.make((Object)Maps.newHashMap(), p_28140_ -> p_28140_.put(10, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/cat/siamese.png")));

    public SiamesePetEntity(EntityType<? extends Cat> entitySiamesePet, Level worldIn) {
        super(entitySiamesePet, worldIn);
    }

    protected void registerGoals() {
        this.temptGoal = new CatTemptGoal(this, 0.6, TEMPT_INGREDIENT, true);
        this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
        this.goalSelector.addGoal(1, (Goal)new SitWhenOrderedToGoal((TamableAnimal)this));
        this.goalSelector.addGoal(2, (Goal)new CatRelaxOnOwnerGoal(this));
        this.goalSelector.addGoal(3, (Goal)this.temptGoal);
        this.goalSelector.addGoal(5, (Goal)new CatLieOnBedGoal((Cat)this, 1.1, 8));
        this.goalSelector.addGoal(6, (Goal)new FollowOwnerGoal((TamableAnimal)this, 1.0, 10.0f, 5.0f));
        this.goalSelector.addGoal(7, (Goal)new CatSitOnBlockGoal((Cat)this, 0.8));
        this.goalSelector.addGoal(8, (Goal)new LeapAtTargetGoal((Mob)this, 0.3f));
        this.goalSelector.addGoal(9, (Goal)new OcelotAttackGoal((Mob)this));
        this.goalSelector.addGoal(10, (Goal)new BreedGoal((Animal)this, 0.8));
        this.goalSelector.addGoal(11, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.8, 1.0000001E-5f));
        this.goalSelector.addGoal(12, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 10.0f));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, (double)0.4f).add(Attributes.FOLLOW_RANGE, 48.0).add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    public Holder<CatVariant> getVariant() {
        return (Holder)this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<CatVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    public void setLying(boolean p_28182_) {
        this.entityData.set(IS_LYING, (Object)p_28182_);
    }

    public boolean isLying() {
        return (Boolean)this.entityData.get(IS_LYING);
    }

    void setRelaxStateOne(boolean relaxStateOne) {
        this.entityData.set(RELAX_STATE_ONE, (Object)relaxStateOne);
    }

    boolean isRelaxStateOne() {
        return (Boolean)this.entityData.get(RELAX_STATE_ONE);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId((int)((Integer)this.entityData.get(DATA_COLLAR_COLOR)));
    }

    public void setCollarColor(DyeColor p_28132_) {
        this.entityData.set(DATA_COLLAR_COLOR, (Object)p_28132_.getId());
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, (Object)BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(DEFAULT_VARIANT));
        builder.define(IS_LYING, (Object)false);
        builder.define(RELAX_STATE_ONE, (Object)false);
        builder.define(DATA_COLLAR_COLOR, (Object)DyeColor.RED.getId());
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("variant", this.getVariant().unwrapKey().orElse(DEFAULT_VARIANT).location().toString());
        compound.putByte("CollarColor", (byte)this.getCollarColor().getId());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        Optional.ofNullable(Identifier.tryParse((String)compound.getString("variant"))).map(p_335254_ -> ResourceKey.create((ResourceKey)Registries.CAT_VARIANT, (Identifier)p_335254_)).flatMap(arg_0 -> ((Registry)BuiltInRegistries.CAT_VARIANT).getHolder(arg_0)).ifPresent(this::setVariant);
        if (compound.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId((int)compound.getInt("CollarColor")));
        }
    }

    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            if (d0 == 0.6) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            } else if (d0 == 1.33) {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isTame()) {
            if (this.isInLove()) {
                return SoundEvents.CAT_PURR;
            }
            return this.random.nextInt(4) == 0 ? SoundEvents.CAT_PURREOW : SoundEvents.CAT_AMBIENT;
        }
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    public void hiss() {
        this.playSound(SoundEvents.CAT_HISS, this.getSoundVolume(), this.getVoicePitch());
    }

    protected SoundEvent getHurtSound(DamageSource p_28160_) {
        return SoundEvents.CAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    public boolean causeFallDamage(float p_148859_, float p_148860_, DamageSource p_148861_) {
        return false;
    }

    protected void usePlayerItem(Player p_148866_, InteractionHand p_148867_, ItemStack p_148868_) {
        if (this.isFood(p_148868_)) {
            this.playSound(SoundEvents.CAT_EAT, 1.0f, 1.0f);
        }
        super.usePlayerItem(p_148866_, p_148867_, p_148868_);
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public boolean doHurtTarget(Entity entity) {
        return entity.hurt(entity.level().damageSources().mobAttack((LivingEntity)this), this.getAttackDamage());
    }

    public void tick() {
        super.tick();
        if (this.temptGoal != null && this.temptGoal.isRunning() && !this.isTame() && this.tickCount % 100 == 0) {
            this.playSound(SoundEvents.CAT_BEG_FOR_FOOD, 1.0f, 1.0f);
        }
        this.handleLieDown();
    }

    private void handleLieDown() {
        if ((this.isLying() || this.isRelaxStateOne()) && this.tickCount % 5 == 0) {
            this.playSound(SoundEvents.CAT_PURR, 0.6f + 0.4f * (this.random.nextFloat() - this.random.nextFloat()), 1.0f);
        }
        this.updateLieDownAmount();
        this.updateRelaxStateOneAmount();
    }

    private void updateLieDownAmount() {
        this.lieDownAmountO = this.lieDownAmount;
        this.lieDownAmountOTail = this.lieDownAmountTail;
        if (this.isLying()) {
            this.lieDownAmount = Math.min(1.0f, this.lieDownAmount + 0.15f);
            this.lieDownAmountTail = Math.min(1.0f, this.lieDownAmountTail + 0.08f);
        } else {
            this.lieDownAmount = Math.max(0.0f, this.lieDownAmount - 0.22f);
            this.lieDownAmountTail = Math.max(0.0f, this.lieDownAmountTail - 0.13f);
        }
    }

    private void updateRelaxStateOneAmount() {
        this.relaxStateOneAmountO = this.relaxStateOneAmount;
        this.relaxStateOneAmount = this.isRelaxStateOne() ? Math.min(1.0f, this.relaxStateOneAmount + 0.1f) : Math.max(0.0f, this.relaxStateOneAmount - 0.13f);
    }

    public float getLieDownAmount(float p_28184_) {
        return Mth.lerp((float)p_28184_, (float)this.lieDownAmountO, (float)this.lieDownAmount);
    }

    public float getLieDownAmountTail(float p_28188_) {
        return Mth.lerp((float)p_28188_, (float)this.lieDownAmountOTail, (float)this.lieDownAmountTail);
    }

    public float getRelaxStateOneAmount(float p_28117_) {
        return Mth.lerp((float)p_28117_, (float)this.relaxStateOneAmountO, (float)this.relaxStateOneAmount);
    }

    public boolean canMate(Animal p_28127_) {
        if (!this.isTame()) {
            return false;
        }
        if (!(p_28127_ instanceof Cat)) {
            return false;
        }
        Cat cat = (Cat)p_28127_;
        return cat.isTame() && super.canMate(p_28127_);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        boolean flag = level.getMoonBrightness() > 0.9f;
        TagKey tagkey = flag ? CatVariantTags.FULL_MOON_SPAWNS : CatVariantTags.DEFAULT_SPAWNS;
        BuiltInRegistries.CAT_VARIANT.getRandomElementOf(tagkey, level.getRandom()).ifPresent(this::setVariant);
        ServerLevel serverlevel = level.getLevel();
        if (serverlevel.structureManager().getStructureWithPieceAt(this.blockPosition(), StructureTags.CATS_SPAWN_AS_BLACK).isValid()) {
            this.setVariant((Holder<CatVariant>)BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(CatVariant.ALL_BLACK));
            this.setPersistenceRequired();
        }
        return spawnGroupData;
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult interactionresult1;
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.isTame()) {
            if (this.isOwnedBy((LivingEntity)player)) {
                InteractionResult interactionresult;
                if (item instanceof DyeItem) {
                    DyeItem dyeitem = (DyeItem)item;
                    DyeColor dyecolor = dyeitem.getDyeColor();
                    if (dyecolor != this.getCollarColor()) {
                        if (!this.level().isClientSide()) {
                            this.setCollarColor(dyecolor);
                            itemstack.consume(1, (LivingEntity)player);
                            this.setPersistenceRequired();
                        }
                        return InteractionResult.sidedSuccess((boolean)this.level().isClientSide());
                    }
                } else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!this.level().isClientSide()) {
                        FoodProperties foodproperties = itemstack.getFoodProperties((LivingEntity)this);
                        this.heal(foodproperties != null ? (float)foodproperties.nutrition() : 1.0f);
                        this.usePlayerItem(player, hand, itemstack);
                    }
                    return InteractionResult.sidedSuccess((boolean)this.level().isClientSide());
                }
                if (!(interactionresult = super.mobInteract(player, hand)).consumesAction()) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    return InteractionResult.sidedSuccess((boolean)this.level().isClientSide());
                }
                return interactionresult;
            }
        } else if (this.isFood(itemstack)) {
            if (!this.level().isClientSide()) {
                this.usePlayerItem(player, hand, itemstack);
                this.tryToTame(player);
                this.setPersistenceRequired();
            }
            return InteractionResult.sidedSuccess((boolean)this.level().isClientSide());
        }
        if ((interactionresult1 = super.mobInteract(player, hand)).consumesAction()) {
            this.setPersistenceRequired();
        }
        return interactionresult1;
    }

    public boolean isFood(ItemStack p_28177_) {
        return TEMPT_INGREDIENT.test(p_28177_);
    }

    public boolean removeWhenFarAway(double p_28174_) {
        return !this.isTame() && this.tickCount > 2400;
    }

    public void setTame(boolean tame, boolean applyTamingSideEffects) {
        super.setTame(tame, applyTamingSideEffects);
        this.reassessTameGoals();
    }

    protected void reassessTameGoals() {
        if (this.avoidPlayersGoal == null) {
            this.avoidPlayersGoal = new CatAvoidEntityGoal<Player>(this, Player.class, 16.0f, 0.8, 1.33);
        }
        this.goalSelector.removeGoal(this.avoidPlayersGoal);
        if (!this.isTame()) {
            this.goalSelector.addGoal(4, this.avoidPlayersGoal);
        }
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame((Animal)this, (Player)player)) {
            this.tame(player);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent((Entity)this, (byte)7);
        } else {
            this.level().broadcastEntityEvent((Entity)this, (byte)6);
        }
    }

    public boolean isSteppingCarefully() {
        return this.getPose() == Pose.CROUCHING || super.isSteppingCarefully();
    }

    static class CatTemptGoal
    extends TemptGoal {
        @Nullable
        private Player selectedPlayer;
        private final SiamesePetEntity cat;

        public CatTemptGoal(SiamesePetEntity siamesePetEntity, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
            super((PathfinderMob)siamesePetEntity, p_28220_, (Predicate)p_28221_, p_28222_);
            this.cat = siamesePetEntity;
        }

        public void tick() {
            super.tick();
            if (this.selectedPlayer == null && this.mob.getRandom().nextInt(this.adjustedTickDelay(600)) == 0) {
                this.selectedPlayer = this.player;
            } else if (this.mob.getRandom().nextInt(this.adjustedTickDelay(500)) == 0) {
                this.selectedPlayer = null;
            }
        }

        protected boolean canScare() {
            return this.selectedPlayer != null && this.selectedPlayer.equals((Object)this.player) ? false : super.canScare();
        }

        public boolean canUse() {
            return super.canUse() && !this.cat.isTame();
        }
    }

    static class CatRelaxOnOwnerGoal
    extends Goal {
        private final SiamesePetEntity cat;
        @Nullable
        private Player ownerPlayer;
        @Nullable
        private BlockPos goalPos;
        private int onBedTicks;

        public CatRelaxOnOwnerGoal(SiamesePetEntity siamesePetEntity) {
            this.cat = siamesePetEntity;
        }

        public boolean canUse() {
            if (!this.cat.isTame()) {
                return false;
            }
            if (this.cat.isOrderedToSit()) {
                return false;
            }
            LivingEntity livingentity = this.cat.getOwner();
            if (livingentity instanceof Player) {
                this.ownerPlayer = (Player)livingentity;
                if (!livingentity.isSleeping()) {
                    return false;
                }
                if (this.cat.distanceToSqr((Entity)this.ownerPlayer) > 100.0) {
                    return false;
                }
                BlockPos blockpos = this.ownerPlayer.blockPosition();
                BlockState blockstate = this.cat.level().getBlockState(blockpos);
                if (blockstate.is(BlockTags.BEDS)) {
                    this.goalPos = blockstate.getOptionalValue((Property)BedBlock.FACING).map(p_28209_ -> blockpos.relative(p_28209_.getOpposite())).orElseGet(() -> new BlockPos((Vec3i)blockpos));
                    return !this.spaceIsOccupied();
                }
            }
            return false;
        }

        private boolean spaceIsOccupied() {
            for (SiamesePetEntity cat : this.cat.level().getEntitiesOfClass(SiamesePetEntity.class, new AABB(this.goalPos).inflate(2.0))) {
                if (cat == this.cat || !cat.isLying() && !cat.isRelaxStateOne()) continue;
                return true;
            }
            return false;
        }

        public boolean canContinueToUse() {
            return this.cat.isTame() && !this.cat.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
        }

        public void start() {
            if (this.goalPos != null) {
                this.cat.setInSittingPose(false);
                this.cat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1f);
            }
        }

        public void stop() {
            this.cat.setLying(false);
            float f = this.cat.level().getTimeOfDay(1.0f);
            if (this.ownerPlayer.getSleepTimer() >= 100 && (double)f > 0.77 && (double)f < 0.8 && (double)this.cat.level().getRandom().nextFloat() < 0.7) {
                this.giveMorningGift();
            }
            this.onBedTicks = 0;
            this.cat.setRelaxStateOne(false);
            this.cat.getNavigation().stop();
        }

        private void giveMorningGift() {
            RandomSource randomsource = this.cat.getRandom();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            blockpos$mutableblockpos.set((Vec3i)(this.cat.isLeashed() ? this.cat.getLeashHolder().blockPosition() : this.cat.blockPosition()));
            this.cat.randomTeleport(blockpos$mutableblockpos.getX() + randomsource.nextInt(11) - 5, blockpos$mutableblockpos.getY() + randomsource.nextInt(5) - 2, blockpos$mutableblockpos.getZ() + randomsource.nextInt(11) - 5, false);
            blockpos$mutableblockpos.set((Vec3i)this.cat.blockPosition());
            LootTable loottable = this.cat.level().getServer().reloadableRegistries().getLootTable(BuiltInLootTables.CAT_MORNING_GIFT);
            LootParams lootparams = new LootParams.Builder((ServerLevel)this.cat.level()).withParameter(LootContextParams.ORIGIN, (Object)this.cat.position()).withParameter(LootContextParams.THIS_ENTITY, (Object)this.cat).create(LootContextParamSets.GIFT);
            for (ItemStack itemstack : loottable.getRandomItems(lootparams)) {
                this.cat.level().addFreshEntity((Entity)new ItemEntity(this.cat.level(), (double)blockpos$mutableblockpos.getX() - (double)Mth.sin((float)(this.cat.yBodyRot * ((float)Math.PI / 180))), (double)blockpos$mutableblockpos.getY(), (double)blockpos$mutableblockpos.getZ() + (double)Mth.cos((float)(this.cat.yBodyRot * ((float)Math.PI / 180))), itemstack));
            }
        }

        public void tick() {
            if (this.ownerPlayer != null && this.goalPos != null) {
                this.cat.setInSittingPose(false);
                this.cat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1f);
                if (this.cat.distanceToSqr((Entity)this.ownerPlayer) < 2.5) {
                    ++this.onBedTicks;
                    if (this.onBedTicks > this.adjustedTickDelay(16)) {
                        this.cat.setLying(true);
                        this.cat.setRelaxStateOne(false);
                    } else {
                        this.cat.lookAt((Entity)this.ownerPlayer, 45.0f, 45.0f);
                        this.cat.setRelaxStateOne(true);
                    }
                } else {
                    this.cat.setLying(false);
                }
            }
        }
    }

    static class CatAvoidEntityGoal<T extends LivingEntity>
    extends AvoidEntityGoal<T> {
        private final SiamesePetEntity cat;

        public CatAvoidEntityGoal(SiamesePetEntity p_28191_, Class<T> p_28192_, float p_28193_, double p_28194_, double p_28195_) {
            super((PathfinderMob)p_28191_, p_28192_, p_28193_, p_28194_, p_28195_, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            this.cat = p_28191_;
        }

        public boolean canUse() {
            return !this.cat.isTame() && super.canUse();
        }

        public boolean canContinueToUse() {
            return !this.cat.isTame() && super.canContinueToUse();
        }
    }
}

