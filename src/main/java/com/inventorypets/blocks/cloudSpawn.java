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
 *  net.minecraft.world.entity.monster.Blaze
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.TransparentBlock
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
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class cloudSpawn
extends TransparentBlock {
    public cloudSpawn(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (worldIn.hasNearbyAlivePlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 20.0) && !worldIn.isClientSide && !((Boolean)InventoryPetsConfig.disableMobsSpawnInDungeons.get()).booleanValue()) {
            AABB range = new AABB((double)(pos.getX() - 2), (double)(pos.getY() - 2), (double)(pos.getZ() - 2), (double)(pos.getX() + 2), (double)(pos.getY() + 2), (double)(pos.getZ() + 2));
            List entities = worldIn.getEntitiesOfClass(Blaze.class, range);
            int esize = entities.size();
            if (esize > 0) {
                return;
            }
            Blaze entityB1 = (Blaze)EntityType.BLAZE.create((Level)worldIn);
            entityB1.setPos((double)pos.getX(), (double)(pos.getY() + 2), (double)pos.getZ());
            worldIn.addFreshEntity((Entity)entityB1);
            entityB1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 4, false, false));
            entityB1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1, false, false));
            entityB1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1, false, false));
            String[] entnames = new String[]{"Scout", "Firebrand", "Hot Pocket", "Great Scott", "Filibuster", "Grumm", "Roderick", "Smokey", "Stoked", "Heat Miser", "Bernie", "Hottie", "Rodney", "Fred", "Sooty", "Ragnorok", "Ash", "Smaug"};
            int nameChk = rand.nextInt(18);
            String randName = entnames[nameChk];
            MutableComponent randNamer = Component.literal((String)randName);
            entityB1.setCustomName((Component)randNamer);
            entityB1.setCustomNameVisible(true);
            worldIn.scheduleTick(pos, (Block)this, 2);
        } else {
            worldIn.scheduleTick(pos, (Block)this, 2);
        }
    }

    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    }
}

