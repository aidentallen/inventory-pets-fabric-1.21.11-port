/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 */
package com.inventorypets.entities;

import com.inventorypets.InventoryPets;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class AppleEntity
extends ThrowableItemProjectile {
    private int soundDelay = 0;

    public AppleEntity(EntityType<? extends AppleEntity> entity, Level worldIn) {
        super(entity, worldIn);
    }

    public AppleEntity(Level world, Player player) {
        super(InventoryPets.APPLE_ENTITY.get(), (LivingEntity)player, world);
    }

    public AppleEntity(Level world, double x, double y, double z) {
        super(InventoryPets.APPLE_ENTITY.get(), x, y, z, world);
    }

    protected Item getDefaultItem() {
        return Items.APPLE;
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        int i = entity instanceof Ghast ? 10 : 3;
        entity.hurt(this.damageSources().thrown((Entity)this, this.getOwner()), (float)i);
        if (!this.level().isClientSide) {
            this.remove(Entity.RemovalReason.KILLED);
        }
        if (!(this.level().isClientSide || this.soundDelay >= 1 || result.getType() != HitResult.Type.BLOCK && result.getType() != HitResult.Type.ENTITY)) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.NEUTRAL, 0.8f, this.level().random.nextFloat() * 0.5f + 0.5f);
        }
        this.spawnParticles();
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent((Entity)this, (byte)3);
            this.discard();
        }
        if (!(this.level().isClientSide || this.soundDelay >= 1 || result.getType() != HitResult.Type.BLOCK && result.getType() != HitResult.Type.ENTITY)) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.NEUTRAL, 0.8f, this.level().random.nextFloat() * 0.5f + 0.5f);
        }
        this.spawnParticles();
    }

    public ItemStack getItem() {
        return new ItemStack((ItemLike)Items.APPLE);
    }

    private void spawnParticles() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        ItemParticleOption itemParticleData = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)Items.APPLE, 1));
        for (int particleNum = 0; particleNum < 4; ++particleNum) {
            this.level().addParticle((ParticleOptions)itemParticleData, x, y, z, this.random.nextGaussian() * 0.1, this.random.nextDouble() * 0.2, this.random.nextGaussian() * 0.11);
        }
    }
}

