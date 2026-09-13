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
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
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
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.border.WorldBorder
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;

public class petEnderman
extends Item {
    private int useDelay = 0;
    private boolean eatFlag;
    private boolean complainFlag = false;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood;

    public petEnderman(Item.Properties properties) {
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
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_ENDERMAN.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodEnderman.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableEnderman.get()).booleanValue()) {
            return;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_OBSIDIAN.get();
        }
        ++this.useDelay;
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && this.useDelay > 10 && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                        this.odFlag = false;
                        if (s3.is(InventoryPets.OBSIDIAN_NUGGET) && !this.customFood) {
                            this.odFlag = true;
                        }
                    }
                    if (!(s3.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s3.getItem() != Blocks.OBSIDIAN.asItem() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() >= 3 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.OBSIDIAN_NUGGET) && !this.customFood) {
                        this.odFlag = true;
                    }
                }
                if (s2.isEmpty() || this.eatFlag || this.useDelay <= 10 || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Blocks.OBSIDIAN.asItem() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.5f);
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
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
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
        if (((Boolean)InventoryPetsConfig.disableEnderman.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldIn.isClientSide && itemstack.getDamageValue() < 3 && this.useDelay > 10) {
            int yaw = (int)playerIn.getYRot();
            if (yaw < 0) {
                yaw += 360;
            }
            yaw += 22;
            int facing = (yaw %= 360) / 45;
            int px1 = Mth.floor((double)playerIn.getX());
            int py1 = Mth.floor((double)playerIn.getY());
            int pz1 = Mth.floor((double)playerIn.getZ());
            int dist = (int)(Math.random() * 25.0 + 5.0);
            int widst = (int)(Math.random() * 4.0 + 1.0);
            int polarst = (int)(Math.random() * 2.0);
            if (polarst == 1) {
                widst *= -1;
            }
            int v1 = 0;
            WorldBorder wb = playerIn.level().getWorldBorder();
            ServerPlayer serverPlayer = (ServerPlayer)playerIn;
            if (facing == 0) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 + widst, py1 + v1, pz1 + dist + v1);
                    BlockPos bp2 = new BlockPos(px1 + widst, py1 + v1 + 1, pz1 + dist + v1);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 + widst), (double)(py1 + v1), (double)(pz1 + dist + v1));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + widst, py1 + v1, pz1 + dist + v1), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 1) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 - dist - v1 + widst, py1 + v1, pz1 + dist + v1 + widst);
                    BlockPos bp2 = new BlockPos(px1 - dist - v1 + widst, py1 + v1 + 1, pz1 + dist + v1 + widst);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 - dist - v1 + widst), (double)(py1 + v1), (double)(pz1 + dist + v1 + widst));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 - dist - v1 + widst, py1 + v1, pz1 + dist + v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 2) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 - dist - v1, py1 + v1, pz1 + widst);
                    BlockPos bp2 = new BlockPos(px1 - dist - v1, py1 + v1 + 1, pz1 + widst);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 - dist - v1), (double)(py1 + v1), (double)(pz1 + widst));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 - dist - v1, py1 + v1, pz1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 3) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 - dist - v1 + widst, py1 + v1, pz1 - dist - v1 + widst);
                    BlockPos bp2 = new BlockPos(px1 - dist - v1 + widst, py1 + v1 + 1, pz1 - dist - v1 + widst);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 - dist - v1 + widst), (double)(py1 + v1), (double)(pz1 - dist - v1 + widst));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 - dist - v1 + widst, py1 + v1, pz1 - dist - v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 4) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 + widst, py1 + v1, pz1 - dist - v1);
                    BlockPos bp2 = new BlockPos(px1 + widst, py1 + v1 + 1, pz1 - dist - v1);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 + widst), (double)(py1 + v1), (double)(pz1 - dist - v1));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + widst, py1 + v1, pz1 - dist - v1), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 5) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 + dist + v1 + widst, py1 + v1, pz1 - dist - v1 + widst);
                    BlockPos bp2 = new BlockPos(px1 + dist + v1 + widst, py1 + v1 + 1, pz1 - dist - v1 + widst);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 + dist + v1 + widst), (double)(py1 + v1), (double)(pz1 - dist - v1 + widst));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + dist + v1 + widst, py1 + v1, pz1 - dist - v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 6) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 + dist + v1, py1 + v1, pz1 + widst);
                    BlockPos bp2 = new BlockPos(px1 + dist + v1, py1 + v1 + 1, pz1 + widst);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 + dist + v1), (double)(py1 + v1), (double)(pz1 + widst));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + dist + v1, py1 + v1, pz1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            } else if (facing == 7) {
                for (v1 = 0; v1 < 20; ++v1) {
                    BlockPos bp1 = new BlockPos(px1 + dist + v1 + widst, py1 + v1, pz1 + dist + v1 + widst);
                    BlockPos bp2 = new BlockPos(px1 + dist + v1 + widst, py1 + v1 + 1, pz1 + dist + v1 + widst);
                    if (worldIn.getBlockState(bp1).getBlock() == Blocks.AIR && worldIn.getBlockState(bp2).getBlock() == Blocks.AIR && wb.isWithinBounds(bp1)) {
                        serverPlayer.moveTo((double)(px1 + dist + v1 + widst), (double)(py1 + v1), (double)(pz1 + dist + v1 + widst));
                        PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + dist + v1 + widst, py1 + v1, pz1 + dist + v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        v1 = 20;
                        continue;
                    }
                    if (v1 != 19) continue;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                }
            }
            this.useDelay = 0;
        } else if (itemstack.getDamageValue() > 2) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petenderman1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        if (!((Boolean)InventoryPetsConfig.disableEndermanAutoTeleport.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petenderman2", (Object[])new Object[0]))));
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodEnderman.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)"item.inventorypets.nugget_obsidian", (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.OBSIDIAN.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.RED) + I18n.get((String)"tooltip.ip.mob", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableEnderman.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

