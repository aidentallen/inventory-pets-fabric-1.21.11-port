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
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModSoundEvents;
import java.util.Calendar;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class itemGift
extends Item {
    public itemGift(Item.Properties properties) {
        super(properties);
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        playerIn.getItemInHand(handIn);
        if (!worldIn.isClientSide) {
            int day1 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY1);
            int day2 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY2);
            int day3 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY3);
            int day4 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY4);
            int day5 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY5);
            int day6 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY6);
            int day7 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY7);
            int day8 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY8);
            int day9 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY9);
            int day10 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY10);
            int day11 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY11);
            int day12 = (Integer)playerIn.getData(ModDataAttachments.HOLIDAY12);
            Long time = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            int mMonth = calendar.get(2);
            int mDay = calendar.get(5);
            ItemStack bob2 = null;
            if (mMonth == (Integer)InventoryPetsConfig.holidayPetMonth.get() - 1) {
                if (mDay == 25 && day12 == 0) {
                    day12 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.SOLSTICE_SWORD.get(), 1);
                } else if (mDay == 24 && day11 == 0) {
                    day11 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.SOLSTICE_BOOTS.get(), 1);
                } else if (mDay == 23 && day10 == 0) {
                    day10 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.SOLSTICE_LEGGINGS.get(), 1);
                } else if (mDay == 22 && day9 == 0) {
                    day9 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.SOLSTICE_CHESTPLATE.get(), 1);
                } else if (mDay == 21 && day8 == 0) {
                    day8 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.SOLSTICE_HELMET.get(), 1);
                } else if (mDay == 20 && day7 == 0) {
                    day7 = 1;
                    bob2 = new ItemStack((ItemLike)Items.ENDER_PEARL, 7);
                } else if (mDay == 19 && day6 == 0) {
                    day6 = 1;
                    bob2 = new ItemStack((ItemLike)Blocks.IRON_BLOCK, 6);
                } else if (mDay == 18 && day5 == 0) {
                    day5 = 1;
                    bob2 = new ItemStack((ItemLike)Items.ENCHANTED_GOLDEN_APPLE);
                } else if (mDay == 17 && day4 == 0) {
                    day4 = 1;
                    bob2 = new ItemStack((ItemLike)Items.DIAMOND, 4);
                } else if (mDay == 16 && day3 == 0) {
                    day3 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.CANDY_CANE.get(), 3);
                } else if (mDay == 15 && day2 == 0) {
                    day2 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.EGG_NOG.get(), 2);
                } else if (mDay == 14 && day1 == 0) {
                    day1 = 1;
                    bob2 = new ItemStack((ItemLike)InventoryPets.HOLIDAY_COOKIE.get(), 1);
                }
                if (bob2 != ItemStack.EMPTY && bob2 != null) {
                    playerIn.setData(ModDataAttachments.HOLIDAY1, (Object)day1);
                    playerIn.setData(ModDataAttachments.HOLIDAY2, (Object)day2);
                    playerIn.setData(ModDataAttachments.HOLIDAY3, (Object)day3);
                    playerIn.setData(ModDataAttachments.HOLIDAY4, (Object)day4);
                    playerIn.setData(ModDataAttachments.HOLIDAY5, (Object)day5);
                    playerIn.setData(ModDataAttachments.HOLIDAY6, (Object)day6);
                    playerIn.setData(ModDataAttachments.HOLIDAY7, (Object)day7);
                    playerIn.setData(ModDataAttachments.HOLIDAY8, (Object)day8);
                    playerIn.setData(ModDataAttachments.HOLIDAY9, (Object)day9);
                    playerIn.setData(ModDataAttachments.HOLIDAY10, (Object)day10);
                    playerIn.setData(ModDataAttachments.HOLIDAY11, (Object)day11);
                    playerIn.setData(ModDataAttachments.HOLIDAY12, (Object)day12);
                    playerIn.setItemInHand(handIn, bob2.copy());
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.unwrap.get(), SoundSource.PLAYERS, 0.6f, 1.0f);
                }
            } else if (bob2 == ItemStack.EMPTY || bob2 == null) {
                playerIn.setItemInHand(handIn, new ItemStack((ItemLike)Items.ROTTEN_FLESH, 1));
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.raspberry.get(), SoundSource.PLAYERS, 0.3f, 0.7f);
                playerIn.sendSystemMessage((Component)Component.translatable((String)"item.gift.dates", (Object[])new Object[0]));
            }
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

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petchestopen", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmasopen", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmasvalid", (Object[])new Object[0]))));
    }
}

