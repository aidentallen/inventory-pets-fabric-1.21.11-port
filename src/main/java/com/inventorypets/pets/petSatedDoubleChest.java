/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.SimpleMenuProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.fml.ModList
 *  net.neoforged.neoforge.common.util.FakePlayer
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.inventory.Chest;
import com.inventorypets.inventory.ChestData;
import com.inventorypets.inventory.ChestManager;
import com.inventorypets.inventory.IPContainer;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.FakePlayer;

public class petSatedDoubleChest
extends Item {
    final String name;
    final Chest chestType = Chest.SATED_DOUBLE_CHEST;

    public petSatedDoubleChest(Item.Properties properties) {
        super(properties);
        this.name = "";
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
        if (ModList.get().isLoaded("refinedstorage") && !stack.has(DataComponents.CUSTOM_NAME)) {
            stack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)"item.inventorypets.pet_sated_double_chest", (Object[])new Object[0]));
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_SATED_DOUBLE_CHEST.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        if (((Boolean)InventoryPetsConfig.disableSatedDoubleChest.get()).booleanValue()) {
            return;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableSatedDoubleChest.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldIn.isClientSide && playerIn instanceof ServerPlayer) {
            ChestData data = petSatedDoubleChest.getData(itemstack);
            UUID uuid = data.getUuid();
            playerIn.openMenu((MenuProvider)new SimpleMenuProvider((windowId, playerInventory, playerEntity) -> new IPContainer(windowId, playerInventory, uuid, data.getChestType(), data.getHandler()), itemstack.getHoverName()), buffer -> buffer.writeUUID(uuid).writeInt(data.getChestType().ordinal()));
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.CHEST_OPEN, SoundSource.PLAYERS, 1.0f, 0.8f);
            return InteractionResultHolder.success((Object)itemstack);
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public static ChestData getData(ItemStack stack) {
        UUID uuid;
        if (!(stack.getItem() instanceof petSatedDoubleChest)) {
            return null;
        }
        if (stack.has(ModDataComponents.CHEST_UUID)) {
            uuid = (UUID)stack.get(ModDataComponents.CHEST_UUID);
        } else if (stack.has(DataComponents.CUSTOM_DATA)) {
            CompoundTag tag = ((CustomData)stack.get(DataComponents.CUSTOM_DATA)).copyTag();
            if (tag.contains("UUID")) {
                uuid = tag.getUUID("UUID");
                stack.set(ModDataComponents.CHEST_UUID, (Object)uuid);
                stack.update(DataComponents.CUSTOM_DATA, (Object)CustomData.EMPTY, $ -> $.update(compoundTag -> compoundTag.remove("UUID")));
            } else {
                uuid = UUID.randomUUID();
                stack.set(ModDataComponents.CHEST_UUID, (Object)uuid);
            }
        } else {
            uuid = UUID.randomUUID();
            stack.set(ModDataComponents.CHEST_UUID, (Object)uuid);
        }
        return ChestManager.get().getOrCreateChest(uuid, ((petSatedDoubleChest)stack.getItem()).chestType);
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petchestopen", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.sated", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableSatedDoubleChest.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

