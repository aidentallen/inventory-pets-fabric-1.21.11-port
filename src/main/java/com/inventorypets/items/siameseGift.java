/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 */
package com.inventorypets.items;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModSoundEvents;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class siameseGift
extends Item {
    public siameseGift(Item.Properties properties) {
        super(properties);
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        ItemStack[] giftList = new ItemStack[36];
        RandomPoolAlias rand = new RandomPoolAlias();
        int amt = 1;
        int i = rand.nextInt(36);
        if (!worldIn.isClientSide) {
            giftList[0] = new ItemStack((ItemLike)Items.CHICKEN, amt);
            giftList[1] = new ItemStack((ItemLike)Items.FEATHER, amt);
            giftList[2] = new ItemStack((ItemLike)Items.STRING, amt);
            giftList[3] = new ItemStack((ItemLike)Items.NAME_TAG, amt);
            giftList[4] = new ItemStack((ItemLike)Items.SPIDER_EYE, amt);
            giftList[5] = new ItemStack((ItemLike)Items.GOLD_NUGGET, amt);
            giftList[6] = new ItemStack((ItemLike)Items.IRON_NUGGET, amt);
            giftList[7] = new ItemStack((ItemLike)InventoryPets.NUGGET_COAL.get(), amt);
            giftList[8] = new ItemStack((ItemLike)InventoryPets.NUGGET_DIAMOND.get(), amt);
            giftList[9] = new ItemStack((ItemLike)InventoryPets.NUGGET_LAPIS.get(), amt);
            giftList[10] = new ItemStack((ItemLike)InventoryPets.NUGGET_EMERALD.get(), amt);
            giftList[11] = new ItemStack((ItemLike)InventoryPets.NUGGET_OBSIDIAN.get(), amt);
            giftList[12] = new ItemStack((ItemLike)InventoryPets.NUGGET_ENDER.get(), amt);
            giftList[13] = new ItemStack((ItemLike)Items.COD, amt);
            giftList[14] = new ItemStack((ItemLike)Items.SALMON, amt);
            giftList[15] = rand.nextInt(100) < 5 ? new ItemStack((ItemLike)Items.PUFFERFISH, amt) : new ItemStack((ItemLike)Items.TROPICAL_FISH, amt);
            giftList[16] = new ItemStack((ItemLike)Items.CYAN_DYE.asItem(), amt);
            giftList[17] = new ItemStack((ItemLike)Items.BONE, amt);
            giftList[18] = new ItemStack((ItemLike)Items.SLIME_BALL, amt);
            giftList[19] = new ItemStack((ItemLike)Items.EXPERIENCE_BOTTLE, amt);
            giftList[20] = new ItemStack((ItemLike)Items.MUSIC_DISC_CAT, 1);
            giftList[21] = new ItemStack((ItemLike)Items.MAP, 1);
            giftList[22] = new ItemStack((ItemLike)Items.LEAD, amt);
            giftList[23] = new ItemStack((ItemLike)Items.RABBIT_FOOT, amt);
            giftList[24] = rand.nextInt(50) < 2 ? new ItemStack((ItemLike)Items.ENCHANTED_GOLDEN_APPLE, 1) : (rand.nextInt(50) < 10 ? new ItemStack((ItemLike)Items.GOLDEN_APPLE, 1) : new ItemStack((ItemLike)Items.APPLE, 1));
            giftList[25] = new ItemStack((ItemLike)Items.ENDER_EYE, amt);
            giftList[26] = new ItemStack((ItemLike)Blocks.ACACIA_BUTTON, amt);
            giftList[27] = new ItemStack((ItemLike)Blocks.STONE_BUTTON, amt);
            giftList[28] = new ItemStack((ItemLike)Blocks.RED_MUSHROOM, amt);
            giftList[29] = new ItemStack((ItemLike)Blocks.BROWN_MUSHROOM, amt);
            giftList[30] = new ItemStack((ItemLike)Blocks.COBWEB, amt);
            giftList[31] = new ItemStack((ItemLike)Items.STICK, amt);
            giftList[32] = new ItemStack((ItemLike)Items.CLAY_BALL, amt);
            giftList[33] = new ItemStack((ItemLike)Items.LEATHER, amt);
            giftList[34] = new ItemStack((ItemLike)Items.PAPER, amt);
            giftList[35] = rand.nextInt(50) < 2 ? new ItemStack((ItemLike)Items.TOTEM_OF_UNDYING, amt) : new ItemStack((ItemLike)Items.ARROW, amt);
            ItemStack bob = giftList[i].copy();
            if (handIn == InteractionHand.OFF_HAND) {
                this.removeOffHandItem(playerIn);
            } else {
                this.removeItem(playerIn, itemstack);
            }
            ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob);
            worldIn.addFreshEntity((Entity)entityitem);
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.siamese_gift.get(), SoundSource.PLAYERS, 0.6f, 0.8f);
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
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

    public void removeOffHandItem(Player ep) {
        Inventory inventoryPlayer = ep.getInventory();
        if (inventoryPlayer.getItem(40) != ItemStack.EMPTY) {
            inventoryPlayer.setItem(40, ItemStack.EMPTY);
        }
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petchestopen", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
    }
}

