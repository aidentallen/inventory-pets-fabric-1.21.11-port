/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
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
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.Calendar;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class petMenorah
extends Item {
    private boolean giftChk = false;

    public petMenorah(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_MENORAH.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (((Boolean)InventoryPetsConfig.disableMenorah.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)playerIn.getItemInHand(handIn));
        }
        if (!worldIn.isClientSide) {
            int day1 = (Integer)playerIn.getData(ModDataAttachments.GIFT1);
            int day2 = (Integer)playerIn.getData(ModDataAttachments.GIFT2);
            int day3 = (Integer)playerIn.getData(ModDataAttachments.GIFT3);
            int day4 = (Integer)playerIn.getData(ModDataAttachments.GIFT4);
            int day5 = (Integer)playerIn.getData(ModDataAttachments.GIFT5);
            int day6 = (Integer)playerIn.getData(ModDataAttachments.GIFT6);
            int day7 = (Integer)playerIn.getData(ModDataAttachments.GIFT7);
            int day8 = (Integer)playerIn.getData(ModDataAttachments.GIFT8);
            int day9 = (Integer)playerIn.getData(ModDataAttachments.GIFT9);
            int day10 = (Integer)playerIn.getData(ModDataAttachments.GIFT10);
            int day11 = (Integer)playerIn.getData(ModDataAttachments.GIFT11);
            int day12 = (Integer)playerIn.getData(ModDataAttachments.GIFT12);
            Long time = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.get(1);
            int mMonth = calendar.get(2);
            int mDay = calendar.get(5);
            this.giftChk = false;
            if (mMonth == (Integer)InventoryPetsConfig.holidayPetMonth.get() - 1 && mDay >= 1 && mDay <= 25) {
                if (mDay == 25 && day12 == 0) {
                    day12 = 1;
                    this.giftChk = true;
                } else if (mDay == 24 && day11 == 0) {
                    day11 = 1;
                    this.giftChk = true;
                } else if (mDay == 23 && day10 == 0) {
                    day10 = 1;
                    this.giftChk = true;
                } else if (mDay == 22 && day9 == 0) {
                    day9 = 1;
                    this.giftChk = true;
                } else if (mDay == 21 && day8 == 0) {
                    day8 = 1;
                    this.giftChk = true;
                } else if (mDay == 20 && day7 == 0) {
                    day7 = 1;
                    this.giftChk = true;
                } else if (mDay == 19 && day6 == 0) {
                    day6 = 1;
                    this.giftChk = true;
                } else if (mDay == 18 && day5 == 0) {
                    day5 = 1;
                    this.giftChk = true;
                } else if (mDay == 17 && day4 == 0) {
                    day4 = 1;
                    this.giftChk = true;
                } else if (mDay == 16 && day3 == 0) {
                    day3 = 1;
                    this.giftChk = true;
                } else if (mDay == 15 && day2 == 0) {
                    day2 = 1;
                    this.giftChk = true;
                } else if (mDay == 14 && day1 == 0) {
                    day1 = 1;
                    this.giftChk = true;
                }
                if (this.giftChk) {
                    playerIn.setData(ModDataAttachments.GIFT1, (Object)day1);
                    playerIn.setData(ModDataAttachments.GIFT2, (Object)day2);
                    playerIn.setData(ModDataAttachments.GIFT3, (Object)day3);
                    playerIn.setData(ModDataAttachments.GIFT4, (Object)day4);
                    playerIn.setData(ModDataAttachments.GIFT5, (Object)day5);
                    playerIn.setData(ModDataAttachments.GIFT6, (Object)day6);
                    playerIn.setData(ModDataAttachments.GIFT7, (Object)day7);
                    playerIn.setData(ModDataAttachments.GIFT8, (Object)day8);
                    playerIn.setData(ModDataAttachments.GIFT9, (Object)day9);
                    playerIn.setData(ModDataAttachments.GIFT10, (Object)day10);
                    playerIn.setData(ModDataAttachments.GIFT11, (Object)day11);
                    playerIn.setData(ModDataAttachments.GIFT12, (Object)day12);
                    ItemStack itemstack = new ItemStack((ItemLike)InventoryPets.HOLIDAY_GIFT.get(), 1);
                    ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, itemstack);
                    worldIn.addFreshEntity((Entity)entityitem);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.sleigh.get(), SoundSource.PLAYERS, 0.6f, 1.0f);
                } else if (!this.giftChk && mDay >= 14 && mDay <= 25) {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.gift.taken", (Object[])new Object[0]));
                } else {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"pet.holiday.dates", (Object[])new Object[0]));
                }
            } else {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.raspberry.get(), SoundSource.PLAYERS, 0.3f, 0.7f);
                playerIn.sendSystemMessage((Component)Component.translatable((String)"item.gift.notholiday", (Object[])new Object[0]));
            }
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmas1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.holiday", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableMenorah.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

