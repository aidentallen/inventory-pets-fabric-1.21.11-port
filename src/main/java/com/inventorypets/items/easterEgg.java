/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 */
package com.inventorypets.items;

import com.inventorypets.InventoryPets;
import com.inventorypets.entities.BillGatesEntity;
import com.inventorypets.entities.SatyaNadellaEntity;
import com.inventorypets.entities.SteveBallmerEntity;
import com.inventorypets.init.ModSoundEvents;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class easterEgg
extends Item {
    public easterEgg(Item.Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BlockPos blockpos;
        BlockHitResult blockraytraceresult;
        BlockHitResult movingobjectposition;
        if (worldIn.isClientSide) {
            return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
        }
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RandomPoolAlias rand = new RandomPoolAlias();
        int chkceo = rand.nextInt(3);
        if (chkceo == 0) {
            BlockHitResult blockraytraceresult2;
            BlockPos blockpos2;
            BlockHitResult movingobjectposition2 = easterEgg.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
            if (movingobjectposition2 == null) {
                return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
            }
            if (movingobjectposition2.getType() == HitResult.Type.BLOCK && worldIn.mayInteract(playerIn, blockpos2 = (blockraytraceresult2 = movingobjectposition2).getBlockPos()) && playerIn.mayUseItemAt(blockpos2, blockraytraceresult2.getDirection(), itemstack)) {
                Player entityplayer = playerIn;
                BillGatesEntity entityB1 = new BillGatesEntity(InventoryPets.BILL_GATES_ENTITY.get(), worldIn);
                entityB1.setPos(blockpos2.getX(), blockpos2.getY() + 1, blockpos2.getZ());
                worldIn.addFreshEntity((Entity)entityB1);
                entityB1.setCustomName((Component)Component.literal((String)"Bill Gates"));
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.april_fool.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                if (entityB1 != null) {
                    if (entityB1 instanceof LivingEntity && playerIn.getItemInHand(handIn).has(DataComponents.CUSTOM_NAME)) {
                        entityB1.setCustomName(playerIn.getItemInHand(handIn).getDisplayName());
                    }
                    if (!playerIn.isCreative()) {
                        playerIn.getItemInHand(handIn).setCount(playerIn.getItemInHand(handIn).getCount() - 1);
                    }
                }
            }
            return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
        }
        if (chkceo == 1) {
            blockraytraceresult = movingobjectposition = easterEgg.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
            blockpos = blockraytraceresult.getBlockPos();
            if (movingobjectposition == null) {
                return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
            }
            if (movingobjectposition.getType() == HitResult.Type.BLOCK) {
                if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), playerIn.getItemInHand(handIn))) {
                    Player entityplayer = playerIn;
                    SteveBallmerEntity entityB1 = new SteveBallmerEntity(InventoryPets.STEVE_BALLMER_ENTITY.get(), worldIn);
                    entityB1.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    worldIn.addFreshEntity((Entity)entityB1);
                    entityB1.setCustomName((Component)Component.literal((String)"Steve Ballmer"));
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.april_fool.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    entityB1.setTarget((LivingEntity)playerIn);
                    entityB1.setSprinting(true);
                    if (entityB1 != null) {
                        if (entityB1 instanceof LivingEntity && playerIn.getItemInHand(handIn).has(DataComponents.CUSTOM_NAME)) {
                            entityB1.setCustomName(playerIn.getItemInHand(handIn).getDisplayName());
                        }
                        if (!playerIn.isCreative()) {
                            playerIn.getItemInHand(handIn).setCount(playerIn.getItemInHand(handIn).getCount() - 1);
                        }
                    }
                }
                return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
            }
        }
        if (chkceo == 2) {
            blockraytraceresult = movingobjectposition = easterEgg.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
            blockpos = blockraytraceresult.getBlockPos();
            if (movingobjectposition == null) {
                return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
            }
            if (movingobjectposition.getType() == HitResult.Type.BLOCK && worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), playerIn.getItemInHand(handIn))) {
                Player entityplayer = playerIn;
                SatyaNadellaEntity entityB1 = new SatyaNadellaEntity(InventoryPets.SATYA_NADELLA_ENTITY.get(), worldIn);
                entityB1.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                worldIn.addFreshEntity((Entity)entityB1);
                entityB1.setCustomName((Component)Component.literal((String)"Satya Nadella"));
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.april_fool.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                if (entityB1 != null) {
                    if (entityB1 instanceof LivingEntity && playerIn.getItemInHand(handIn).has(DataComponents.CUSTOM_NAME)) {
                        entityB1.setCustomName(playerIn.getItemInHand(handIn).getDisplayName());
                    }
                    if (!playerIn.isCreative()) {
                        playerIn.getItemInHand(handIn).setCount(playerIn.getItemInHand(handIn).getCount() - 1);
                    }
                }
            }
            return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.easteregg", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
    }
}

