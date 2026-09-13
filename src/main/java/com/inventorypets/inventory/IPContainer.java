/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package com.inventorypets.inventory;

import com.inventorypets.helper.ChestHelper;
import com.inventorypets.init.ModMenus;
import com.inventorypets.inventory.Chest;
import com.inventorypets.inventory.IPContainerSlot;
import com.inventorypets.inventory.LockedSlot;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class IPContainer
extends AbstractContainerMenu {
    public final IItemHandler handler;
    private final Chest chestType;
    private final UUID uuid;

    public static IPContainer fromNetwork(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        UUID uuidIn = data.readUUID();
        Chest chestType = Chest.values()[data.readInt()];
        return new IPContainer(windowId, playerInventory, uuidIn, chestType, (IItemHandler)new ItemStackHandler(chestType.slots));
    }

    public IPContainer(int windowId, Inventory playerInventory, UUID uuidIn, Chest chestTypeIn, IItemHandler handler) {
        super((MenuType)ModMenus.IPCONTAINER.get(), windowId);
        this.uuid = uuidIn;
        this.handler = handler;
        this.chestType = chestTypeIn;
        this.addPlayerSlots(playerInventory);
        this.addMySlots();
    }

    public Chest getChestType() {
        return this.chestType;
    }

    public boolean stillValid(@Nonnull Player playerIn) {
        return true;
    }

    public void clicked(int slot, int dragType, @Nonnull ClickType clickTypeIn, @Nonnull Player player) {
        if (clickTypeIn == ClickType.SWAP) {
            return;
        }
        if (slot >= 0) {
            this.getSlot((int)slot).container.setChanged();
        }
        super.clicked(slot, dragType, clickTypeIn, player);
    }

    private void addPlayerSlots(Inventory playerInventory) {
        int originX = this.chestType.slotXOffset;
        int originY = this.chestType.slotYOffset;
        for (int col = 0; col < 9; ++col) {
            int x = originX + 3 + 44 + col * 18;
            int y = originY + 58 + 1 + 40;
            Optional<UUID> uuidOptional = ChestHelper.getUUID((ItemStack)playerInventory.items.get(col));
            boolean lockMe = uuidOptional.map(id -> id.compareTo(this.uuid) == 0).orElse(false);
            this.addSlot(new LockedSlot(playerInventory, col, x + 1, y + 1, lockMe));
        }
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = originX + 3 + 44 + col * 18;
                int y = originY + 1 + 40 + row * 18;
                int index = col + row * 9 + 9;
                Optional<UUID> uuidOptional = ChestHelper.getUUID((ItemStack)playerInventory.items.get(index));
                boolean lockMe = uuidOptional.map(id -> id.compareTo(this.uuid) == 0).orElse(false);
                this.addSlot(new LockedSlot(playerInventory, index, x + 1, y + 1, lockMe));
            }
        }
    }

    private void addMySlots() {
        if (this.handler == null) {
            return;
        }
        int guiOffset = 0;
        if (this.getChestType() == Chest.DOUBLE_CHEST || this.getChestType() == Chest.SATED_DOUBLE_CHEST) {
            guiOffset = 2;
        } else if (this.getChestType() == Chest.FEED_BAG) {
            guiOffset = 10;
        }
        int cols = this.chestType.slotCols;
        int rows = this.chestType.slotRows;
        int slot_index = 0;
        block0: for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                int x = 51 + col * 18;
                int y = 15 + guiOffset + 40 + row * 18;
                this.addSlot((Slot)new IPContainerSlot(this.handler, slot_index, x + 1, y + 1));
                if (++slot_index >= this.chestType.slots) continue block0;
            }
        }
    }

    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            int bagslotcount = this.slots.size();
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < playerIn.getInventory().items.size() ? !this.moveItemStackTo(itemstack1, playerIn.getInventory().items.size(), bagslotcount, false) : !this.moveItemStackTo(itemstack1, 0, playerIn.getInventory().items.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}

