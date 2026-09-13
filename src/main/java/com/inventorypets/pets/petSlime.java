/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvents
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
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

public class petSlime
extends Item {
    private int ticktime;
    private int chkEat = 0;
    private boolean eatFlag = false;
    private boolean complainFlag = false;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.GOLDEN_APPLE;

    public petSlime(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        int k;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if ((Integer)InventoryPetsConfig.petEatTimerFactor.get() <= 0) {
            return;
        }
        if (this.newFlag && (Integer)InventoryPetsConfig.petEatTimerFactor.get() > 0 && !this.isDamaged(stack)) {
            this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 100 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
            this.newFlag = false;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_SLIME.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodSlime.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableSlime.get()).booleanValue()) {
            return;
        }
        if (!stack.has(ModDataComponents.GIVE_ITEMS)) {
            stack.set(ModDataComponents.GIVE_ITEMS, (Object)true);
        }
        ++this.chkEat;
        --this.ticktime;
        if (this.ticktime < 0) {
            this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 100 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
        }
        if (!entityplayer.isCreative() && stack.getDamageValue() > 0 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if ((s3.getItem() != petFood || !this.customFood) && (s3.getItem() != Items.GOLDEN_APPLE || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() > 0 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || (s2.getItem() != petFood || !this.customFood) && (s2.getItem() != Items.GOLDEN_APPLE || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                this.complainFlag = false;
                this.eatFlag = true;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.hey.get(), SoundSource.PLAYERS, 0.5f, 0.4f);
                    this.complainFlag = true;
                }
            }
        }
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_SLIME.get() || itemchk.getDamageValue() != 0 || worldIn.isClientSide || stack.getDamageValue() >= 1 || !Boolean.TRUE.equals(stack.get(ModDataComponents.GIVE_ITEMS)) || entityplayer.isCreative() || this.ticktime > 10) continue;
            ItemStack bob2 = new ItemStack((ItemLike)Items.SLIME_BALL, 1);
            ItemEntity entityitem = new ItemEntity(entityplayer.level(), entityplayer.getX() + 0.5, entityplayer.getY() + 0.5, entityplayer.getZ() + 0.5, bob2);
            worldIn.addFreshEntity((Entity)entityitem);
            this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 100 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (((Boolean)InventoryPetsConfig.disableSlime.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() < 1 && Boolean.TRUE.equals(itemstack.get(ModDataComponents.GIVE_ITEMS)) && playerIn.isCrouching()) {
            itemstack.set(ModDataComponents.GIVE_ITEMS, (Object)false);
        } else {
            itemstack.set(ModDataComponents.GIVE_ITEMS, (Object)true);
        }
        if (itemstack.getDamageValue() < 1 && !worldIn.isClientSide) {
            if (itemstack.has(ModDataComponents.GIVE_ITEMS) && Boolean.TRUE.equals(itemstack.get(ModDataComponents.GIVE_ITEMS)) && playerIn.isCrouching()) {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.STONE_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 0.7f, 1.1f);
            } else {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.PLAYERS, 0.7f, 1.3f);
            }
        } else if ((itemstack.getDamageValue() < 1 || playerIn.isCreative()) && playerIn.isCrouching()) {
            if (itemstack.has(ModDataComponents.GIVE_ITEMS) && Boolean.TRUE.equals(itemstack.get(ModDataComponents.GIVE_ITEMS))) {
                playerIn.sendSystemMessage((Component)Component.translatable((String)"item.slime.on", (Object[])new Object[0]));
            } else {
                playerIn.sendSystemMessage((Component)Component.translatable((String)"item.slime.off", (Object[])new Object[0]));
            }
        }
        return InteractionResultHolder.sidedSuccess((Object)itemstack, (boolean)worldIn.isClientSide());
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
        if (!stack.has(ModDataComponents.GIVE_ITEMS)) {
            stack.set(ModDataComponents.GIVE_ITEMS, (Object)true);
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petslime1", (Object[])new Object[0]))));
        if (!stack.has(ModDataComponents.GIVE_ITEMS) || Boolean.TRUE.equals(stack.get(ModDataComponents.GIVE_ITEMS))) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petslime2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.on", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petslime2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.off", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.togglegiveitems", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodSlime.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.GOLDEN_APPLE.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.LIGHT_PURPLE) + I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableSlime.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

