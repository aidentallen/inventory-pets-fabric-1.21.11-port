/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.hurtingprojectile.Fireball
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 */
package com.inventorypets.entities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SmallFirelessBall
extends Fireball {
    public SmallFirelessBall(Level level, LivingEntity owner, Vec3 movement) {
        super(EntityType.SMALL_FIREBALL, owner, movement, level);
    }

    public SmallFirelessBall(Level level, double x, double y, double z, Vec3 movement) {
        super(EntityType.SMALL_FIREBALL, x, y, z, movement, level);
    }

    protected void onHitEntity(EntityHitResult p_37386_) {
        Entity entity;
        super.onHitEntity(p_37386_);
        if (!this.level().isClientSide && !(entity = p_37386_.getEntity()).fireImmune()) {
            Entity entity1 = this.getOwner();
            boolean flag = entity.hurt(entity.level().damageSources().generic(), 10.0f);
            if (!flag || entity1 instanceof LivingEntity) {
                // empty if block
            }
        }
    }

    protected void onHitBlock(BlockHitResult result) {
        BlockState blockstate = this.level().getBlockState(result.getBlockPos());
        blockstate.onProjectileHit(this.level(), blockstate, result, (Projectile)this);
    }

    protected void onHit(HitResult p_37388_) {
        super.onHit(p_37388_);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_37381_, float p_37382_) {
        return false;
    }
}

