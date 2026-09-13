/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.skeleton.WitherSkeleton
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.Nullable
 */
package com.inventorypets.blocks;

import com.inventorypets.config.InventoryPetsConfig;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class netherSpawn
extends Block {
    public netherSpawn(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (worldIn.hasNearbyAlivePlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 20.0) && !worldIn.isClientSide && !((Boolean)InventoryPetsConfig.disableMobsSpawnInDungeons.get()).booleanValue()) {
            AABB range = new AABB((double)(pos.getX() - 2), (double)(pos.getY() - 2), (double)(pos.getZ() - 2), (double)(pos.getX() + 2), (double)(pos.getY() + 2), (double)(pos.getZ() + 2));
            List entities = worldIn.getEntitiesOfClass(WitherSkeleton.class, range);
            int esize = entities.size();
            if (esize > 0) {
                return;
            }
            WitherSkeleton entityZ1 = (WitherSkeleton)EntityType.WITHER_SKELETON.create((Level)worldIn);
            entityZ1.setPos((double)pos.getX() - 0.5, (double)(pos.getY() + 1), (double)(pos.getZ() + 1));
            worldIn.addFreshEntity((Entity)entityZ1);
            entityZ1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 3, false, false));
            entityZ1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1, false, false));
            entityZ1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1, false, false));
            worldIn.scheduleTick(pos, (Block)this, 2);
            String[] entnames = new String[]{"Wilt", "Bonedaddy", "Bonehead", "Iago", "Lord Skellington", "Dinnerbone", "Invisible Friend", "Timmy", "Bony", "Skull", "n00b", "Thither", "Thistle", "Spoopy!", "Why didn't the skeleton go to the dance? Because he had no body to go with!"};
            int nameChk = rand.nextInt(15);
            String randName = entnames[nameChk];
            MutableComponent randNamer = Component.literal((String)randName);
            entityZ1.setCustomName((Component)randNamer);
            entityZ1.setCustomNameVisible(true);
        } else {
            worldIn.scheduleTick(pos, (Block)this, 2);
        }
    }

    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    }
}

