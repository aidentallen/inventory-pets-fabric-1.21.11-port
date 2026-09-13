/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
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
 *  net.minecraft.world.level.Level
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.handler.TeleporterPositionHandler;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.networking.PacketTeleport;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;

public class petHouse
extends Item {
    private int useDelay = 59;
    private boolean eatFlag;
    private boolean complainFlag = false;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood;

    public petHouse(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        int k;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_ENDER.get();
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_HOUSE.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodHouse.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableHouse.get()).booleanValue()) {
            return;
        }
        ++this.useDelay;
        if (stack.has(ModDataComponents.HOME_DIMENSION)) {
            stack.set(ModDataComponents.HOME_DIMENSION, (Object)Level.OVERWORLD.location().toString());
        }
        ++this.chkEat;
        if (!playerIn.isCreative() && stack.getDamageValue() > 0 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    this.odFlag = false;
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && s3.is(InventoryPets.ENDER_NUGGET)) {
                        this.odFlag = true;
                    }
                    if (!(s3.getItem() == petFood && this.customFood || this.odFlag && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.ENDER_PEARL || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.4f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !playerIn.isCreative() && stack.getDamageValue() > 0 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = playerIn.getInventory().getItem(k);
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.ENDER_NUGGET)) {
                        this.odFlag = true;
                    }
                }
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || this.odFlag && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.ENDER_PEARL || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.4f);
                this.complainFlag = false;
                this.eatFlag = true;
                break;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.qcm_buzz.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
                    this.complainFlag = true;
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableHouse.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.qcm_buzz.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldIn.isClientSide && itemstack.getDamageValue() == 0 && !playerIn.isCrouching()) {
            if (this.useDelay > 40) {
                this.useDelay = 0;
                ServerPlayer serverPlayer = (ServerPlayer)playerIn;
                BlockPos coordinates = serverPlayer.getRespawnPosition();
                ServerLevel ServerLevel2 = (ServerLevel)worldIn;
                if (coordinates == null) {
                    coordinates = ServerLevel2.getSharedSpawnPos();
                }
                serverPlayer.setRespawnPosition(worldIn.dimension(), coordinates, playerIn.getYRot(), true, false);
                String lastdimension = Level.OVERWORLD.location().toString();
                if (itemstack.has(ModDataComponents.HOME_DIMENSION) && (lastdimension = ((String)itemstack.get(ModDataComponents.HOME_DIMENSION)).trim()).equals("")) {
                    lastdimension = Level.OVERWORLD.location().toString();
                }
                if (!lastdimension.equals(worldIn.dimension().location().toString())) {
                    int sepLoc = lastdimension.indexOf(":");
                    String dimMod = lastdimension.substring(0, sepLoc);
                    String dimName = lastdimension.substring(sepLoc + 1, lastdimension.length());
                    Identifier dimensionLoc = Identifier.fromNamespaceAndPath((String)dimMod, (String)dimName);
                    ResourceKey dimKey = ResourceKey.create((ResourceKey)Registries.DIMENSION, (Identifier)dimensionLoc);
                    ServerLevel dimensionWorld = worldIn.getServer().getLevel(dimKey);
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, coordinates.getX(), coordinates.getY(), coordinates.getZ());
                    serverPlayer.moveTo((double)coordinates.getX(), (double)coordinates.getY(), (double)coordinates.getZ());
                    PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(coordinates.getX(), coordinates.getY(), coordinates.getZ()), (CustomPacketPayload[])new CustomPacketPayload[0]);
                } else {
                    serverPlayer.moveTo((double)coordinates.getX(), (double)coordinates.getY(), (double)coordinates.getZ());
                    PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(coordinates.getX(), coordinates.getY(), coordinates.getZ()), (CustomPacketPayload[])new CustomPacketPayload[0]);
                }
                playerIn.sendSystemMessage((Component)Component.translatable((String)"info.house.return"));
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.doorbell.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
            }
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.useDelay == 0 && !playerIn.isCreative()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        } else if (!worldIn.isClientSide && itemstack.getDamageValue() == 0 && playerIn.isCrouching() && this.useDelay > 60) {
            this.useDelay = 0;
            ServerPlayer serverEntity = (ServerPlayer)playerIn;
            BlockPos coordinates = serverEntity.blockPosition();
            serverEntity.setRespawnPosition(worldIn.dimension(), coordinates, playerIn.getYRot(), true, false);
            itemstack.set(ModDataComponents.HOME_DIMENSION, (Object)worldIn.dimension().location().toString());
            String tempString = Component.translatable((String)"info.house.spawnpoint").getString();
            playerIn.sendSystemMessage((Component)Component.literal((String)(tempString + " " + coordinates.getX() + ", " + coordinates.getY() + ", " + coordinates.getZ())));
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.doorbell.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.pethouse1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.pethouse2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodHouse.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_ENDER.get()).getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.ENDER_PEARL.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_AQUA) + I18n.get((String)"tooltip.ip.fan", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.showSuggestors.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.suggestedby", (Object[])new Object[0]) + " Holt_Stamey")));
        }
        if (((Boolean)InventoryPetsConfig.disableHouse.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

