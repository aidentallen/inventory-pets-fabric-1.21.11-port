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
 *  net.minecraft.world.entity.monster.spider.CaveSpider
 *  net.minecraft.world.entity.player.Player
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
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class sandSpawn
extends Block {
    public sandSpawn(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (worldIn.hasNearbyAlivePlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 20.0) && !worldIn.isClientSide && !((Boolean)InventoryPetsConfig.disableMobsSpawnInDungeons.get()).booleanValue()) {
            Player entityplayer = worldIn.getNearestPlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 4.0, false);
            AABB range = new AABB((double)(pos.getX() - 2), (double)(pos.getY() - 2), (double)(pos.getZ() - 2), (double)(pos.getX() + 2), (double)(pos.getY() + 2), (double)(pos.getZ() + 2));
            List entities = worldIn.getEntitiesOfClass(CaveSpider.class, range);
            int esize = entities.size();
            if (esize > 0) {
                return;
            }
            CaveSpider entityZ1 = (CaveSpider)EntityType.CAVE_SPIDER.create((Level)worldIn);
            entityZ1.setPos((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ());
            worldIn.addFreshEntity((Entity)entityZ1);
            entityZ1.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 2, false, false));
            entityZ1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 3, false, false));
            entityZ1.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 2, false, false));
            entityZ1.setTarget((LivingEntity)entityplayer);
            entityZ1.hurt(entityZ1.level().damageSources().magic(), 1.0f);
            String[] entnames = new String[]{"Peter", "Gohma", "Jeff", "Sea Foam", "Serket", "Dinnerbone", "Charlotte", "Aragog", "Arachne", "Loth", "Spider-Ham", "Ungoliant", "Anansi", "Sh", "Hunstman"};
            int nameChk = rand.nextInt(15);
            String randName = entnames[nameChk];
            MutableComponent randNamer = Component.literal((String)randName);
            entityZ1.setCustomName((Component)randNamer);
            entityZ1.setCustomNameVisible(true);
            worldIn.scheduleTick(pos, (Block)this, 2);
        } else {
            worldIn.scheduleTick(pos, (Block)this, 2);
        }
    }

    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    }
}

