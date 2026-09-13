/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.players.OldUsersConverter
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
 *  net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal
 *  net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
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
import com.inventorypets.entities.SmallFirelessBall;
import com.inventorypets.helper.ProxyHelper;
import com.inventorypets.init.ModSoundEvents;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.Nullable;

public class MiniQuantumBlazeEntity
extends Monster {
    private float allowedHeightOffset = 0.5f;
    private int nextHeightOffsetChangeTick;
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(MiniQuantumBlazeEntity.class, (EntityDataSerializer)EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(MiniQuantumBlazeEntity.class, (EntityDataSerializer)EntityDataSerializers.OPTIONAL_UUID);

    public MiniQuantumBlazeEntity(EntityType<? extends MiniQuantumBlazeEntity> entity, Level level) {
        super(entity, level);
        this.setPathfindingMalus(PathType.WATER, -1.0f);
        this.setPathfindingMalus(PathType.LAVA, 8.0f);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0f);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0f);
        this.xpReward = 10;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, (Goal)new BlazeAttackGoal(this));
        this.goalSelector.addGoal(5, (Goal)new MoveTowardsRestrictionGoal((PathfinderMob)this, 1.0));
        this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
        this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]).setAlertOthers(new Class[0]));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 200.0).add(Attributes.MOVEMENT_SPEED, (double)0.6f).add(Attributes.FOLLOW_RANGE, 100.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0).add(Attributes.ATTACK_DAMAGE, 8.0);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (Object)0);
        builder.define(OWNER_UNIQUE_ID, Optional.empty());
    }

    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.mqbbreathe.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_32235_) {
        return ModSoundEvents.mqbhit.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSoundEvents.mqbdeath.get();
    }

    public float getBrightness() {
        return 2.0f;
    }

    public void aiStep() {
        Player entityplayer;
        if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }
        if (!this.level().isClientSide && this.getOwnerId() != null && (entityplayer = this.level().getPlayerByUUID(this.getOwnerId())) != null) {
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
        if (this.level().isClientSide) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.level().playLocalSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
            }
            ProxyHelper.spawnPortalEffects2((ClientLevel)this.level(), (int)this.getX(), (int)this.getY(), (int)this.getZ());
        }
        super.aiStep();
    }

    public boolean isSensitiveToWater() {
        return false;
    }

    protected void customServerAiStep() {
        LivingEntity livingentity;
        --this.nextHeightOffsetChangeTick;
        if (this.nextHeightOffsetChangeTick <= 0) {
            this.nextHeightOffsetChangeTick = 100;
            this.allowedHeightOffset = 0.5f + (float)this.random.nextGaussian() * 3.0f;
        }
        if ((livingentity = this.getTarget()) != null && livingentity.getEyeY() > this.getEyeY() + (double)this.allowedHeightOffset && this.canAttack(livingentity)) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, ((double)0.3f - vec3.y) * (double)0.3f, 0.0));
            this.hasImpulse = true;
        }
        super.customServerAiStep();
    }

    public boolean causeFallDamage(float p_149683_, float p_149684_, DamageSource p_149685_) {
        return false;
    }

    public boolean isOnFire() {
        return this.isCharged();
    }

    private boolean isCharged() {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    void setCharged(boolean p_32241_) {
        byte b0 = (Byte)this.entityData.get(DATA_FLAGS_ID);
        b0 = p_32241_ ? (byte)(b0 | 1) : (byte)(b0 & 0xFFFFFFFE);
        this.entityData.set(DATA_FLAGS_ID, (Object)b0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getOwnerId() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getOwnerId().toString());
        }
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
    }

    @Nullable
    public UUID getOwnerId() {
        return ((Optional)this.entityData.get(OWNER_UNIQUE_ID)).orElse(null);
    }

    public void setOwnerId(@Nullable UUID p_184754_1_) {
        this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
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
                    this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0f, 1.0f);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                }
            }
            return flag2;
        }
        return false;
    }

    protected void dropCustomDeathLoot(DamageSource damage, int slot, boolean bool) {
        int h = this.random.nextInt(100);
        if (h >= 2 && h <= 20) {
            this.spawnAtLocation((ItemLike)InventoryPets.NUGGET_COAL.get(), 1);
        } else if (h == 1) {
            this.spawnAtLocation((ItemLike)InventoryPets.NUGGET_DIAMOND.get(), 1);
        }
    }

    static class BlazeAttackGoal
    extends Goal {
        private final MiniQuantumBlazeEntity blaze;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        public BlazeAttackGoal(MiniQuantumBlazeEntity p_32247_) {
            this.blaze = p_32247_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.blaze.getTarget();
            return livingentity != null && livingentity.isAlive() && this.blaze.canAttack(livingentity);
        }

        public void start() {
            this.attackStep = 0;
        }

        public void stop() {
            this.blaze.setCharged(false);
            this.lastSeen = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.blaze.getTarget();
            if (livingentity != null && livingentity.getUUID() == this.blaze.getOwnerId()) {
                this.blaze.setTarget(null);
            } else if (livingentity != null) {
                boolean flag = this.blaze.getSensing().hasLineOfSight((Entity)livingentity);
                this.lastSeen = flag ? 0 : ++this.lastSeen;
                double d0 = this.blaze.distanceToSqr((Entity)livingentity);
                if (d0 < 4.0) {
                    if (!flag) {
                        return;
                    }
                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.blaze.doHurtTarget((Entity)livingentity);
                    }
                    this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getX() - this.blaze.getX();
                    double d2 = livingentity.getY(0.5) - this.blaze.getY(0.5);
                    double d3 = livingentity.getZ() - this.blaze.getZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.blaze.setCharged(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.blaze.setCharged(false);
                        }
                        if (this.attackStep > 1) {
                            double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5;
                            if (!this.blaze.isSilent()) {
                                this.blaze.level().levelEvent((Player)null, 1018, this.blaze.blockPosition(), 0);
                            }
                            for (int i = 0; i < 1; ++i) {
                                Vec3 vec3 = new Vec3(this.blaze.getRandom().triangle(d1, 2.297 * d4), d2, this.blaze.getRandom().triangle(d3, 2.297 * d4));
                                SmallFirelessBall smallfireball = new SmallFirelessBall(this.blaze.level(), (LivingEntity)this.blaze, vec3.normalize());
                                smallfireball.setPos(smallfireball.getX(), this.blaze.getY(0.5) + 0.5, smallfireball.getZ());
                                this.blaze.level().addFreshEntity((Entity)smallfireball);
                            }
                        }
                    }
                    this.blaze.getLookControl().setLookAt((Entity)livingentity, 10.0f, 10.0f);
                } else if (this.lastSeen < 5) {
                    this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0);
                }
                super.tick();
            }
        }

        private double getFollowDistance() {
            return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}

