/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.players.OldUsersConverter
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.DifficultyInstance
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.EntitySpawnReason
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.SpawnGroupData
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.entity.animal.golem.AbstractGolem
 *  net.minecraft.world.entity.monster.Enemy
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.common.CommonHooks
 *  org.jetbrains.annotations.Nullable
 */
package com.inventorypets.entities;

import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.init.ModSoundEvents;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.Nullable;

public class AnvilPetEntity
extends Mob
implements Enemy {
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(AnvilPetEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(AnvilPetEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
    public float squishAmount;
    private boolean wasOnGround;
    private int deathDelay;
    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean customFood;
    private Item defaultFood;

    public AnvilPetEntity(EntityType<? extends Mob> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new SlimeMoveControl(this);
        this.customFood = false;
        this.defaultFood = Items.IRON_NUGGET;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, (Goal)new SlimeFloatGoal(this));
        this.goalSelector.addGoal(2, (Goal)new SlimeAttackGoal(this));
        this.goalSelector.addGoal(3, (Goal)new SlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, (Goal)new SlimeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
        this.setSize(2, true);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_SIZE, (Object)2);
        builder.define(OWNER_UNIQUE_ID, Optional.empty());
        this.deathDelay = 2400;
    }

    protected void setSize(int size, boolean resetHealth) {
        this.entityData.set(ID_SIZE, (Object)size);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.8f);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(48.0);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.0);
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }
        this.xpReward = 0;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.ATTACK_DAMAGE, 0.0).add(Attributes.MAX_HEALTH, 200.0).add(Attributes.FOLLOW_RANGE, 48.0).add(Attributes.MOVEMENT_SPEED, 0.8).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public int getSize() {
        return (Integer)this.entityData.get(ID_SIZE);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Size", this.getSize() - 1);
        compound.putBoolean("wasOnGround", this.wasOnGround);
        if (this.getOwnerId() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getOwnerId().toString());
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return ((Optional)this.entityData.get(OWNER_UNIQUE_ID)).orElse(null);
    }

    public void setOwnerId(@Nullable UUID p_184754_1_) {
        this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.level().getPlayerByUUID(uuid);
        }
        catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        String s;
        int i = compound.getInt("Size");
        if (i < 0) {
            i = 0;
        }
        this.setSize(i + 1, false);
        super.readAdditionalSaveData(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
        if (compound.contains("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = OldUsersConverter.convertMobOwnerIfNecessary((MinecraftServer)this.getServer(), (String)s1).toString();
        }
        if (!s.isEmpty()) {
            try {
                this.setOwnerId(UUID.fromString(s));
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public boolean isTiny() {
        return this.getSize() <= 1;
    }

    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5f;
        this.oSquish = this.squish;
        super.tick();
        --this.deathDelay;
        if (this.deathDelay <= 0) {
            this.remove(Entity.RemovalReason.DISCARDED);
        } else if (this.deathDelay == 90 || this.deathDelay == 60 || this.deathDelay == 30) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSoundEvents.overbell.get(), SoundSource.PLAYERS, 0.4f, 1.0f);
        } else if (this.deathDelay < 20) {
            this.level().addParticle((ParticleOptions)ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY() + 2.0, this.getZ(), 0.0, 0.0, 0.0);
        }
        if (this.onGround() && !this.wasOnGround) {
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            this.squishAmount = -0.5f;
        } else if (!this.onGround() && this.wasOnGround) {
            this.squishAmount = 1.0f;
        }
        this.wasOnGround = this.onGround();
        this.alterSquishAmount();
        AABB range = new AABB(this.getX() - 24.0, this.getY() - 24.0, this.getZ() - 24.0, this.getX() + 24.0, this.getY() + 24.0, this.getZ() + 24.0);
        List entities = this.level().getEntitiesOfClass(AbstractGolem.class, range);
        int esize = entities.size();
        for (int k = 0; k <= esize - 1; ++k) {
            AbstractGolem entitygolem;
            Entity entity = (Entity)entities.get(k);
            if (entity == null || (entitygolem = (AbstractGolem)entity).getTarget() != this) continue;
            entitygolem.setTarget(null);
            Vec3 vec3d = DefaultRandomPos.getPosAway((PathfinderMob)entitygolem, (int)10, (int)4, (Vec3)new Vec3(this.getX(), this.getY(), this.getZ()));
            WorldBorder wb = this.level().getWorldBorder();
            if (vec3d == null || !wb.isWithinBounds(new BlockPos((int)this.getX(), (int)this.getY(), (int)this.getZ())) || !(this.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) >= this.distanceToSqr((Entity)entitygolem))) continue;
            entitygolem.getNavigation().moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0);
        }
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Player entityplayer = player;
        boolean odFlag = false;
        if (itemstack != ItemStack.EMPTY) {
            Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodAnvil.get())));
            if (petFood != this.defaultFood && petFood != Items.AIR) {
                this.customFood = true;
            }
            if (itemstack.getItem() == petFood && this.customFood || itemstack.getItem() == Items.IRON_INGOT && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() != false && !this.customFood || itemstack.getItem() == Items.IRON_NUGGET && !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && !this.customFood) {
                odFlag = true;
            }
            boolean eatFlag = false;
            if (odFlag) {
                ItemStack itemchk2;
                for (int j = 0; j < 36; ++j) {
                    itemchk2 = entityplayer.getInventory().getItem(j);
                    if (itemchk2 == ItemStack.EMPTY || !itemchk2.isRepairable() || !itemchk2.isDamaged() || itemchk2.getDescriptionId().contains("_pet") || itemchk2.getDescriptionId().contains("_canteen")) continue;
                    itemchk2.setDamageValue(itemchk2.getDamageValue() - 30);
                    this.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.anvil.get(), SoundSource.PLAYERS, 0.1f, 1.2f);
                    eatFlag = true;
                }
                for (int j1 = 0; j1 <= 3; ++j1) {
                    itemchk2 = entityplayer.getInventory().getArmor(j1);
                    if (itemchk2 == ItemStack.EMPTY || !itemchk2.isRepairable() || !itemchk2.isDamaged() || itemchk2.getDescriptionId().contains("_pet")) continue;
                    itemchk2.setDamageValue(itemchk2.getDamageValue() - 30);
                    entityplayer.containerMenu.broadcastChanges();
                    ItemStack itemchk3 = itemchk2.copy();
                    this.removeItem(entityplayer, itemchk2);
                    entityplayer.getInventory().armor.set(j1, (Object)itemchk3);
                    this.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.anvil.get(), SoundSource.PLAYERS, 0.1f, 1.2f);
                    eatFlag = true;
                }
                if (eatFlag) {
                    this.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.4f, 1.2f);
                }
                if (!entityplayer.isCreative() && eatFlag) {
                    itemstack.setCount(itemstack.getCount() - 1);
                    if (itemstack.getCount() == 0) {
                        this.removeItem(entityplayer, itemstack);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (!entityplayer.level().isClientSide && itemstack == ItemStack.EMPTY) {
            if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                entityplayer.sendSystemMessage((Component)Component.translatable((String)"info.ip.livinganvilinteracthc", (Object[])new Object[0]));
            } else {
                entityplayer.sendSystemMessage((Component)Component.translatable((String)"info.ip.livinganvilinteract", (Object[])new Object[0]));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    public void removeItem(Player ep, ItemStack removeitem) {
        Inventory inventoryPlayer = ep.getInventory();
        for (int i = 0; i < 36; ++i) {
            ItemStack j;
            if (inventoryPlayer.getItem(i) == ItemStack.EMPTY || (j = inventoryPlayer.getItem(i)) == ItemStack.EMPTY || j.getItem() != removeitem.getItem()) continue;
            inventoryPlayer.setItem(i, ItemStack.EMPTY);
            break;
        }
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (ID_SIZE.equals(key)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    public EntityType<? extends AnvilPetEntity> getType() {
        return super.getType();
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.625f * sizeIn.height();
    }

    protected boolean isDealsDamage() {
        return false;
    }

    protected float getAttackDamage() {
        return (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundEvents.clang.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.clang.get();
    }

    protected SoundEvent getSquishSound() {
        return ModSoundEvents.boing.get();
    }

    protected float getSoundVolume() {
        return 0.2f * (float)this.getSize();
    }

    protected boolean doPlayJumpSound() {
        return this.getSize() > 0;
    }

    public void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
        CommonHooks.onLivingJump((LivingEntity)this);
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        int i = randomsource.nextInt(3);
        if (i < 2 && randomsource.nextFloat() < 0.5f * difficulty.getSpecialMultiplier()) {
            ++i;
        }
        int j = 1 << i;
        this.setSize(j, true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    protected SoundEvent getJumpSound() {
        return this.isTiny() ? ModSoundEvents.boing.get() : ModSoundEvents.boing.get();
    }

    float getSoundPitch() {
        float f = this.isTiny() ? 1.4f : 0.8f;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * f;
    }

    public EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale((float)this.getSize());
    }

    protected boolean spawnCustomParticles() {
        return false;
    }

    static class SlimeMoveControl
    extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final AnvilPetEntity slime;
        private boolean isAggressive;

        public SlimeMoveControl(AnvilPetEntity slimeIn) {
            super((Mob)slimeIn);
            this.slime = slimeIn;
            this.yRot = 180.0f * this.slime.getYRot() / (float)Math.PI;
        }

        public void setDirection(float yRotIn, boolean aggressive) {
            this.yRot = yRotIn;
            this.isAggressive = aggressive;
        }

        public void setWantedMovement(double speedIn) {
            this.speedModifier = speedIn;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0f));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0f);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }
                        this.slime.getJumpControl().jump();
                        if (this.slime.doPlayJumpSound()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
                        }
                    } else {
                        this.slime.xxa = 0.0f;
                        this.slime.zza = 0.0f;
                        this.mob.setSpeed(0.0f);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    static class SlimeFloatGoal
    extends Goal {
        private final AnvilPetEntity slime;

        public SlimeFloatGoal(AnvilPetEntity anvilPetEntity) {
            this.slime = anvilPetEntity;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            anvilPetEntity.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof SlimeMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8f) {
                this.slime.getJumpControl().jump();
            }
            ((SlimeMoveControl)this.slime.getMoveControl()).setWantedMovement(1.2);
        }
    }

    static class SlimeAttackGoal
    extends Goal {
        private final AnvilPetEntity slime;
        private int growTiredTimer;

        public SlimeAttackGoal(AnvilPetEntity anvilPetEntity) {
            this.slime = anvilPetEntity;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity == null) {
                return false;
            }
            return !this.slime.canAttack(livingentity) ? false : this.slime.getMoveControl() instanceof SlimeMoveControl;
        }

        public void start() {
            this.growTiredTimer = SlimeAttackGoal.reducedTickDelay((int)300);
            super.start();
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity == null) {
                return false;
            }
            if (!this.slime.canAttack(livingentity)) {
                return false;
            }
            return --this.growTiredTimer > 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity != null) {
                this.slime.lookAt((Entity)livingentity, 10.0f, 10.0f);
            }
            ((SlimeMoveControl)this.slime.getMoveControl()).setDirection(this.slime.getYRot(), this.slime.isDealsDamage());
        }
    }

    static class SlimeRandomDirectionGoal
    extends Goal {
        private final AnvilPetEntity slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public SlimeRandomDirectionGoal(AnvilPetEntity slimeIn) {
            this.slime = slimeIn;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof SlimeMoveControl;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
                this.chosenDegrees = this.slime.getRandom().nextInt(360);
            }
            ((SlimeMoveControl)this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
        }
    }

    static class SlimeKeepOnJumpingGoal
    extends Goal {
        private final AnvilPetEntity slime;

        public SlimeKeepOnJumpingGoal(AnvilPetEntity slimeIn) {
            this.slime = slimeIn;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            Player entityplayer;
            boolean chkPlayer = false;
            if (this.slime.getTarget() != null && this.slime.getTarget() instanceof Player && (entityplayer = (Player)this.slime.getTarget()) != null) {
                int x = (int)entityplayer.getX();
                int z = (int)entityplayer.getZ();
                int thisx = (int)this.slime.getX();
                int thisz = (int)this.slime.getZ();
                chkPlayer = Math.abs(thisx - x) >= 3 || Math.abs(thisz - z) >= 3;
            }
            return chkPlayer;
        }

        public void tick() {
            ((SlimeMoveControl)this.slime.getMoveControl()).setWantedMovement(1.0);
        }
    }
}

