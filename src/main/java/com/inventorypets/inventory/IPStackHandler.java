/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.neoforge.items.ItemStackHandler
 */
package com.inventorypets.inventory;

import com.inventorypets.helper.ChestHelper;
import com.inventorypets.inventory.ChestManager;
import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class IPStackHandler
extends ItemStackHandler {
    public IPStackHandler() {
        super(16);
    }

    public void removeItem(int slot) {
        this.setStackInSlot(slot, ItemStack.EMPTY);
        this.onContentsChanged(slot);
    }

    public void setItem(int slot, ItemStack item) {
        if (ChestHelper.filterItem(item)) {
            this.setStackInSlot(slot, item);
            this.onContentsChanged(slot);
        }
    }

    protected void onContentsChanged(int slot) {
        ChestManager.get().setDirty();
    }

    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!ChestHelper.filterItem(stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}

