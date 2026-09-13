/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 */
package com.inventorypets.entities;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BananaEntity
extends ThrowableItemProjectile {
    private int soundDelay = 0;

    public BananaEntity(EntityType<? extends BananaEntity> bananaEntity, Level worldIn) {
        super(bananaEntity, worldIn);
    }

    public BananaEntity(Level world, Player player) {
        super(InventoryPets.BANANA_ENTITY.get(), (LivingEntity)player, world);
    }

    public BananaEntity(Level world, double x, double y, double z) {
        super(InventoryPets.BANANA_ENTITY.get(), x, y, z, world);
    }

    protected Item getDefaultItem() {
        return (Item)InventoryPets.BANANA.get();
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        --this.soundDelay;
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = result.getEntity();
            int i = entity instanceof Ghast ? 24 : 6;
            entity.hurt(this.damageSources().thrown((Entity)this, this.getOwner()), (float)i);
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent((Entity)this, (byte)3);
                this.discard();
            }
        }
        if (!this.level().isClientSide) {
            this.discard();
            Player entityplayer = (Player)this.getOwner();
            if (entityplayer != null && !entityplayer.isCreative()) {
                boolean offFlag = false;
                int slot = (Integer)entityplayer.getData(ModDataAttachments.SLOT);
                if (slot == -100) {
                    offFlag = true;
                }
                int dmg = (Integer)entityplayer.getData(ModDataAttachments.DAMAGE);
                String name = (String)entityplayer.getData(ModDataAttachments.NAME);
                ItemStack itemstack = new ItemStack((ItemLike)InventoryPets.PET_BANANA.get(), 1);
                itemstack.setDamageValue(dmg);
                itemstack.set(DataComponents.CUSTOM_NAME, (Object)Component.literal((String)name));
                if (offFlag) {
                    entityplayer.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                } else if (entityplayer.getInventory().getItem(slot) == null) {
                    entityplayer.getInventory().setItem(slot, itemstack);
                } else if (entityplayer.getInventory().getItem(slot).getItem() == Blocks.AIR.asItem() || entityplayer.getInventory().getItem(slot).getItem() == InventoryPets.PET_BANANA.get()) {
                    entityplayer.getInventory().setItem(slot, itemstack);
                } else {
                    int slot2 = entityplayer.getInventory().getFreeSlot();
                    if (slot2 != -1) {
                        entityplayer.getInventory().setItem(slot2, itemstack);
                    } else {
                        ItemEntity entityitem2 = new ItemEntity(entityplayer.level(), this.getX(), this.getY(), this.getZ(), itemstack);
                        this.level().addFreshEntity((Entity)entityitem2);
                    }
                }
            }
        }
        this.spawnParticles();
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        --this.soundDelay;
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent((Entity)this, (byte)3);
            this.discard();
        }
        if (!(this.level().isClientSide || this.soundDelay >= 1 || result.getType() != HitResult.Type.BLOCK && result.getType() != HitResult.Type.ENTITY)) {
            this.level().playSound(null, this.blockPosition(), ModSoundEvents.splat.get(), SoundSource.NEUTRAL, 0.6f, this.level().random.nextFloat() * 0.5f + 0.5f);
        }
        if (!this.level().isClientSide) {
            this.discard();
            Player entityplayer = (Player)this.getOwner();
            if (entityplayer != null && !entityplayer.isCreative()) {
                boolean offFlag = false;
                int slot = (Integer)entityplayer.getData(ModDataAttachments.SLOT);
                if (slot == -100) {
                    offFlag = true;
                }
                int dmg = (Integer)entityplayer.getData(ModDataAttachments.DAMAGE);
                String name = (String)entityplayer.getData(ModDataAttachments.NAME);
                ItemStack itemstack = new ItemStack((ItemLike)InventoryPets.PET_BANANA.get(), 1);
                itemstack.setDamageValue(dmg);
                itemstack.set(DataComponents.CUSTOM_NAME, (Object)Component.literal((String)name));
                if (offFlag) {
                    entityplayer.setItemInHand(InteractionHand.OFF_HAND, itemstack);
                } else if (entityplayer.getInventory().getItem(slot) == null) {
                    entityplayer.getInventory().setItem(slot, itemstack);
                } else if (entityplayer.getInventory().getItem(slot).getItem() == Blocks.AIR.asItem() || entityplayer.getInventory().getItem(slot).getItem() == InventoryPets.PET_BANANA.get()) {
                    entityplayer.getInventory().setItem(slot, itemstack);
                } else {
                    int slot2 = entityplayer.getInventory().getFreeSlot();
                    if (slot2 != -1) {
                        entityplayer.getInventory().setItem(slot2, itemstack);
                    } else {
                        ItemEntity entityitem2 = new ItemEntity(entityplayer.level(), this.getX(), this.getY(), this.getZ(), itemstack);
                        this.level().addFreshEntity((Entity)entityitem2);
                    }
                }
            }
        }
        this.spawnParticles();
    }

    public ItemStack getItem() {
        return new ItemStack((ItemLike)InventoryPets.BANANA.get());
    }

    private void spawnParticles() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        ItemParticleOption itemParticleData = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)InventoryPets.BANANA.get(), 1));
        for (int particleNum = 0; particleNum < 4; ++particleNum) {
            this.level().addParticle((ParticleOptions)itemParticleData, x, y, z, this.random.nextGaussian() * 0.1, this.random.nextDouble() * 0.2, this.random.nextGaussian() * 0.11);
        }
    }
}

