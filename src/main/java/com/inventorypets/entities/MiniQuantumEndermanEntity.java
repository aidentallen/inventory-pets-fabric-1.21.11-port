/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.players.OldUsersConverter
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.TimeUtil
 *  net.minecraft.util.valueproviders.UniformInt
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.NeutralMob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.FloatGoal
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MeleeAttackGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.monster.Endermite
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractThrownPotion
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.alchemy.PotionContents
 *  net.minecraft.world.item.alchemy.Potions
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.pathfinder.PathType
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.event.EventHooks
 *  net.neoforged.neoforge.event.entity.EntityTeleportEvent$EnderEntity
 *  org.jetbrains.annotations.Nullable
 */
package com.inventorypets.entities;

import com.inventorypets.InventoryPets;
import com.inventorypets.helper.ProxyHelper;
import com.inventorypets.init.ModSoundEvents;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.Nullable;

public class MiniQuantumEndermanEntity
extends Monster
implements NeutralMob {
    private static final Identifier SPEED_MODIFIER_ATTACKING_ID = Identifier.withDefaultNamespace((String)"attacking");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING;
    private static final int DELAY_BETWEEN_CREEPY_STARE_SOUND = 400;
    private static final int MIN_DEAGGRESSION_TIME = 600;
    private static final EntityDataAccessor<Boolean> DATA_CREEPY;
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT;
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID;
    private int lastStareSound = Integer.MIN_VALUE;
    private int targetChangeTime;
    private static final UniformInt PERSISTENT_ANGER_TIME;
    private int remainingPersistentAngerTime;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    private int teleportDelay;

    public MiniQuantumEndermanEntity(EntityType<? extends MiniQuantumEndermanEntity> mqe, Level level) {
        super(mqe, level);
        this.setPathfindingMalus(PathType.WATER, -1.0f);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
        this.goalSelector.addGoal(1, (Goal)new EndermanFreezeWhenLookedAt(this));
        this.goalSelector.addGoal(2, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0, false));
        this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0, 0.0f));
        this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(1, (Goal)new EndermanLookForPlayerGoal(this, arg_0 -> ((MiniQuantumEndermanEntity)this).isAngryAt(arg_0)));
        this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Endermite.class, true, false));
        this.targetSelector.addGoal(4, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200.0).add(Attributes.MOVEMENT_SPEED, (double)0.6f).add(Attributes.FOLLOW_RANGE, 100.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0).add(Attributes.ATTACK_DAMAGE, 8.0).add(Attributes.STEP_HEIGHT, 1.0);
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (livingEntity == null) {
            this.targetChangeTime = 0;
            this.entityData.set(DATA_CREEPY, (Object)false);
            this.entityData.set(DATA_STARED_AT, (Object)false);
            attributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING_ID);
        } else {
            this.targetChangeTime = this.tickCount;
            this.entityData.set(DATA_CREEPY, (Object)true);
            if (!attributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING_ID)) {
                attributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }
        super.setTarget(livingEntity);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CREEPY, (Object)false);
        builder.define(DATA_STARED_AT, (Object)false);
        builder.define(OWNER_UNIQUE_ID, Optional.empty());
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void playStareSound() {
        if (this.tickCount >= this.lastStareSound + 400) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5f, 1.0f, false);
            }
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_32513_) {
        if (DATA_CREEPY.equals(p_32513_) && this.hasBeenStaredAt() && this.level().isClientSide) {
            this.playStareSound();
        }
        super.onSyncedDataUpdated(p_32513_);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getOwnerId() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getOwnerId().toString());
        }
        this.addPersistentAngerSaveData(compound);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        String s;
        super.readAdditionalSaveData(compound);
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
        this.readPersistentAngerSaveData(this.level(), compound);
    }

    @Nullable
    public UUID getOwnerId() {
        return ((Optional)this.entityData.get(OWNER_UNIQUE_ID)).orElse(null);
    }

    public void setOwnerId(@Nullable UUID p_184754_1_) {
        this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
    }

    boolean isLookingAtMe(Player playerIn) {
        Vec3 vec3 = playerIn.getViewVector(1.0f).normalize();
        Vec3 vec31 = new Vec3(this.getX() - playerIn.getX(), this.getEyeY() - playerIn.getEyeY(), this.getZ() - playerIn.getZ());
        double d0 = vec31.length();
        double d1 = vec3.dot(vec31 = vec31.normalize());
        return d1 > 1.0 - 0.025 / d0 ? playerIn.hasLineOfSight((Entity)this) : false;
    }

    public void aiStep() {
        if (this.level().isClientSide) {
            for (int i = 0; i < 2; ++i) {
                ProxyHelper.spawnPortalEffects2((ClientLevel)this.level(), (int)this.getX(), (int)this.getY(), (int)this.getZ());
            }
        }
        if (!this.level().isClientSide && this.getOwnerId() != null) {
            Player entityplayer = this.level().getPlayerByUUID(this.getOwnerId());
            if (entityplayer != null) {
                if (Math.abs(entityplayer.getX() - this.getX()) + Math.abs(entityplayer.getY() - this.getY()) + Math.abs(entityplayer.getZ() - this.getZ()) >= 30.0) {
                    this.teleportTowards((Entity)entityplayer);
                }
                if (entityplayer.getLastHurtMob() != null && entityplayer.getLastHurtMob().isAlive()) {
                    this.setTarget(entityplayer.getLastHurtMob());
                }
                if (entityplayer.getLastHurtByMob() != null && entityplayer.getLastHurtByMob().isAlive()) {
                    this.setTarget(entityplayer.getLastHurtByMob());
                }
                if (this.getTarget() == entityplayer) {
                    this.setTarget(null);
                }
            }
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
        this.jumping = false;
        if (!this.isCreepy() || this.isAggressive() || this.random.nextInt(100) == 0) {
            // empty if block
        }
        if (this.getTarget() != null) {
            this.lookAt((Entity)this.getTarget(), 100.0f, 100.0f);
        }
        if (!this.level().isClientSide && this.isAlive()) {
            if (this.getTarget() != null) {
                if (this.getTarget() instanceof LivingEntity) {
                    this.teleportDelay = 0;
                } else if (this.getTarget().distanceToSqr((Entity)this) > 256.0 && this.teleportDelay++ >= 60 && this.teleportTowards((Entity)this.getTarget())) {
                    this.teleportDelay = 0;
                }
            } else {
                this.teleportDelay = 0;
            }
        }
        super.aiStep();
    }

    protected boolean teleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
            double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
            return this.teleport(d0, d1, d2);
        }
        return false;
    }

    boolean teleportTowards(Entity entity) {
        double d1 = entity.getX() + (double)this.random.nextInt(-3, 3);
        double d2 = entity.getY() + (double)this.random.nextInt(1, 3);
        double d3 = entity.getZ() + (double)this.random.nextInt(-3, 3);
        return this.teleport(d1, d2, d3);
    }

    private boolean teleport(double x, double y, double z) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, y, z);
        while (blockpos$mutableblockpos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState((BlockPos)blockpos$mutableblockpos).blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }
        BlockState blockstate = this.level().getBlockState((BlockPos)blockpos$mutableblockpos);
        boolean flag = blockstate.blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            EntityTeleportEvent.EnderEntity event = EventHooks.onEnderTeleport((LivingEntity)this, (double)x, (double)y, (double)z);
            if (event.isCanceled()) {
                return false;
            }
            Vec3 vec3 = this.position();
            boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2) {
                this.level().gameEvent((Holder)GameEvent.TELEPORT, vec3, GameEvent.Context.of((Entity)this));
                if (!this.isSilent()) {
                    this.level().playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0f, 1.0f);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                }
            }
            return flag2;
        }
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.mqeidle.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundEvents.mqehit.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mqedeath.get();
    }

    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        int h = this.random.nextInt(100);
        if (h >= 2 && h <= 20) {
            this.spawnAtLocation((ItemLike)InventoryPets.NUGGET_ENDER.get(), 1);
        } else if (h == 1) {
            this.spawnAtLocation((ItemLike)InventoryPets.NUGGET_DIAMOND.get(), 1);
        }
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        boolean flag = source.getDirectEntity() instanceof AbstractThrownPotion;
        if (!source.is(DamageTypeTags.IS_PROJECTILE) && !flag) {
            boolean flag2 = super.hurt(source, amount);
            if (!this.level().isClientSide() && !(source.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
                this.teleport();
            }
            return flag2;
        }
        boolean flag1 = flag && this.hurtWithCleanWater(source, (AbstractThrownPotion)source.getDirectEntity(), amount);
        for (int i = 0; i < 64; ++i) {
            if (!this.teleport()) continue;
            return true;
        }
        return flag1;
    }

    private boolean hurtWithCleanWater(DamageSource source, AbstractThrownPotion potion, float amount) {
        ItemStack itemstack = potion.getItem();
        PotionContents potioncontents = (PotionContents)itemstack.getOrDefault(DataComponents.POTION_CONTENTS, (Object)PotionContents.EMPTY);
        return potioncontents.is(Potions.WATER) ? super.hurt(source, amount) : false;
    }

    public boolean isCreepy() {
        return (Boolean)this.entityData.get(DATA_CREEPY);
    }

    public boolean hasBeenStaredAt() {
        return (Boolean)this.entityData.get(DATA_STARED_AT);
    }

    public void setBeingStaredAt() {
        this.entityData.set(DATA_STARED_AT, (Object)true);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence();
    }

    public boolean isSensitiveToWater() {
        return false;
    }

    static {
        OWNER_UNIQUE_ID = SynchedEntityData.defineId(MiniQuantumEndermanEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);
        SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_ID, (double)0.15f, AttributeModifier.Operation.ADD_VALUE);
        DATA_CREEPY = SynchedEntityData.defineId(MiniQuantumEndermanEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
        DATA_STARED_AT = SynchedEntityData.defineId(MiniQuantumEndermanEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
        PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds((int)20, (int)39);
    }

    static class EndermanFreezeWhenLookedAt
    extends Goal {
        private final MiniQuantumEndermanEntity enderman;
        @Nullable
        private LivingEntity target;

        public EndermanFreezeWhenLookedAt(MiniQuantumEndermanEntity mqe) {
            this.enderman = mqe;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            this.target = this.enderman.getTarget();
            if (!(this.target instanceof Player)) {
                return false;
            }
            double d0 = this.target.distanceToSqr((Entity)this.enderman);
            return d0 > 256.0 ? false : this.enderman.isLookingAtMe((Player)this.target);
        }

        public void start() {
            this.enderman.getNavigation().stop();
        }

        public void tick() {
            this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    static class EndermanLookForPlayerGoal
    extends NearestAttackableTargetGoal<Player> {
        private final MiniQuantumEndermanEntity enderman;
        @Nullable
        private Player pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();

        public EndermanLookForPlayerGoal(MiniQuantumEndermanEntity mqe, @Nullable Predicate<LivingEntity> predicate) {
            super((Mob)mqe, Player.class, 10, false, false, predicate);
            this.enderman = mqe;
            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(p_32578_ -> mqe.isLookingAtMe((Player)p_32578_));
        }

        public boolean canUse() {
            this.pendingTarget = this.enderman.level().getNearestPlayer(this.startAggroTargetConditions, (LivingEntity)this.enderman);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = this.adjustedTickDelay(5);
            this.teleportTime = 0;
            this.enderman.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.enderman.isLookingAtMe(this.pendingTarget)) {
                    return false;
                }
                this.enderman.lookAt((Entity)this.pendingTarget, 10.0f, 10.0f);
                return true;
            }
            return this.target != null && this.continueAggroTargetConditions.test((LivingEntity)this.enderman, this.target) || super.canContinueToUse();
        }

        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTarget((LivingEntity)null);
            }
            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.target != null && !this.enderman.isPassenger()) {
                    if (this.enderman.isLookingAtMe((Player)this.target)) {
                        if (this.target.distanceToSqr((Entity)this.enderman) < 16.0) {
                            this.enderman.teleport();
                        }
                        this.teleportTime = 0;
                    } else if (this.target.distanceToSqr((Entity)this.enderman) > 256.0 && this.teleportTime++ >= this.adjustedTickDelay(30) && this.enderman.teleportTowards((Entity)this.target)) {
                        this.teleportTime = 0;
                    }
                }
                super.tick();
            }
        }
    }
}

