/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
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
 *  net.minecraft.world.item.JukeboxSong
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petJukebox
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean playFlag = false;
    private boolean hbFlag = true;
    private boolean pauseFlag = false;
    private int lastPlayed = 0;
    private int loopCount = 0;
    private int eposX;
    private int eposY;
    private int eposZ;
    private BlockPos bp1;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.REDSTONE;

    public petJukebox(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_JUKEBOX.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodJukebox.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableJukebox.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = playerIn.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_JUKEBOX.get()) continue;
            slotNo = i;
        }
        if (!(playerIn.isCreative() || petFood == Items.AIR || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10)) {
            this.eatFlag = false;
            block1: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (s3.getItem() != petFood && (s3.getItem() != Items.REDSTONE || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.5f);
                    this.eatFlag = true;
                    this.ticktime = 120 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 160 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block1;
                }
            }
        }
        if (!playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide) {
            int i;
            ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                hotbar[i] = playerIn.getInventory().getItem(i);
            }
            this.hbFlag = false;
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                int dmg;
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_JUKEBOX.get() || this.chkEat <= 40 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                this.eatFlag = false;
                for (int k = 0; k < 36; ++k) {
                    ItemStack s2 = playerIn.getInventory().getItem(k);
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || (s2.getItem() != petFood || !this.customFood) && (s2.getItem() != Items.REDSTONE || this.customFood)) continue;
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(playerIn, s2);
                    }
                    this.setDamage(hotbar[i], 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.5f);
                    this.eatFlag = true;
                }
                if (this.eatFlag || hotbar[i] == ItemStack.EMPTY || (dmg = hotbar[i].getDamageValue()) != 0) continue;
                hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                if (hotbar[i].getDamageValue() == 1) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.shorted.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
                }
                this.eatFlag = true;
            }
            if (this.hbFlag) {
                this.chkEat = 0;
                this.ticktime = 120 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 160 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        block19: {
            ItemStack itemstack;
            block21: {
                block20: {
                    itemstack = playerIn.getItemInHand(handIn);
                    if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
                        return InteractionResultHolder.fail((Object)itemstack);
                    }
                    if (((Boolean)InventoryPetsConfig.disableJukebox.get()).booleanValue()) {
                        return InteractionResultHolder.fail((Object)itemstack);
                    }
                    this.loopCount = 0;
                    if (!worldIn.isClientSide || itemstack.getDamageValue() != 0 || this.playFlag || this.pauseFlag) break block20;
                    for (int k = this.lastPlayed; k < 36; ++k) {
                        ItemStack s2 = playerIn.getInventory().getItem(k);
                        if (s2 != ItemStack.EMPTY && s2.has(DataComponents.JUKEBOX_PLAYABLE)) {
                            Optional optional = JukeboxSong.fromStack((HolderLookup.Provider)worldIn.registryAccess(), (ItemStack)s2);
                            int i = worldIn.registryAccess().registryOrThrow(Registries.JUKEBOX_SONG).getId((Object)((JukeboxSong)((Holder)optional.get()).value()));
                            this.eposX = (int)playerIn.getX();
                            this.eposY = (int)playerIn.getY();
                            this.eposZ = (int)playerIn.getZ();
                            this.bp1 = new BlockPos(this.eposX, this.eposY, this.eposZ);
                            worldIn.levelEvent(1011, this.bp1, 0);
                            worldIn.gameEvent((Entity)playerIn, (Holder)GameEvent.JUKEBOX_STOP_PLAY, this.bp1);
                            worldIn.levelEvent(playerIn, 1010, this.bp1, i);
                            this.playFlag = true;
                            this.lastPlayed = k;
                        } else {
                            if (k < 35) continue;
                            this.lastPlayed = 0;
                            k = -1;
                            ++this.loopCount;
                            if (this.loopCount <= 2) {
                                continue;
                            }
                        }
                        break block19;
                    }
                    break block19;
                }
                if (!worldIn.isClientSide || itemstack.getDamageValue() != 0 || !this.playFlag || playerIn.isCrouching()) break block21;
                worldIn.levelEvent(1011, this.bp1, 0);
                worldIn.gameEvent((Entity)playerIn, (Holder)GameEvent.JUKEBOX_STOP_PLAY, this.bp1);
                this.playFlag = false;
                this.pauseFlag = true;
                if (this.lastPlayed < 35) break block19;
                this.lastPlayed = 0;
                break block19;
            }
            if (worldIn.isClientSide && itemstack.getDamageValue() == 0 && this.pauseFlag && !playerIn.isCrouching()) {
                this.playFlag = true;
                this.pauseFlag = false;
                if (this.lastPlayed >= 35) {
                    this.lastPlayed = 0;
                }
                for (int k = this.lastPlayed; k < 36; ++k) {
                    ItemStack s2 = playerIn.getInventory().getItem(k);
                    if (s2 != ItemStack.EMPTY && s2.has(DataComponents.JUKEBOX_PLAYABLE)) {
                        this.eposX = (int)playerIn.getX();
                        this.eposY = (int)playerIn.getY();
                        this.eposZ = (int)playerIn.getZ();
                        this.bp1 = new BlockPos(this.eposX, this.eposY, this.eposZ);
                        Optional optional = JukeboxSong.fromStack((HolderLookup.Provider)worldIn.registryAccess(), (ItemStack)s2);
                        int i = worldIn.registryAccess().registryOrThrow(Registries.JUKEBOX_SONG).getId((Object)((JukeboxSong)((Holder)optional.get()).value()));
                        worldIn.levelEvent(playerIn, 1010, this.bp1, i);
                        this.playFlag = true;
                        this.lastPlayed = k;
                    } else {
                        if (k < 35) continue;
                        this.lastPlayed = 0;
                        k = -1;
                        ++this.loopCount;
                        if (this.loopCount <= 2) {
                            continue;
                        }
                    }
                    break;
                }
            } else if (worldIn.isClientSide && itemstack.getDamageValue() == 0 && playerIn.isCrouching()) {
                this.playFlag = false;
                this.pauseFlag = false;
                ++this.lastPlayed;
                if (this.lastPlayed >= 35) {
                    this.lastPlayed = 0;
                }
                for (int k = this.lastPlayed; k < 36; ++k) {
                    ItemStack s2 = playerIn.getInventory().getItem(k);
                    if (s2 != ItemStack.EMPTY && s2.has(DataComponents.JUKEBOX_PLAYABLE)) {
                        this.eposX = (int)playerIn.getX();
                        this.eposY = (int)playerIn.getY();
                        this.eposZ = (int)playerIn.getZ();
                        worldIn.levelEvent(1011, this.bp1, 0);
                        worldIn.gameEvent((Entity)playerIn, (Holder)GameEvent.JUKEBOX_STOP_PLAY, this.bp1);
                        this.bp1 = new BlockPos(this.eposX, this.eposY, this.eposZ);
                        Optional optional = JukeboxSong.fromStack((HolderLookup.Provider)worldIn.registryAccess(), (ItemStack)s2);
                        int i = worldIn.registryAccess().registryOrThrow(Registries.JUKEBOX_SONG).getId((Object)((JukeboxSong)((Holder)optional.get()).value()));
                        worldIn.levelEvent(playerIn, 1010, this.bp1, i);
                        this.playFlag = true;
                        this.lastPlayed = k;
                    } else {
                        if (k < 35) continue;
                        this.lastPlayed = 0;
                        ++this.loopCount;
                        k = -1;
                        if (this.loopCount <= 2) {
                            continue;
                        }
                    }
                    break;
                }
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petjukebox1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petjukebox2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petjukebox3", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodJukebox.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.REDSTONE.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableJukebox.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

