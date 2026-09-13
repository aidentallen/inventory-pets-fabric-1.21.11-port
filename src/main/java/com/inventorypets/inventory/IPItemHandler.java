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

public class IPItemHandler
extends ItemStackHandler {
    public IPItemHandler(int size) {
        super(size);
    }

    protected void onContentsChanged(int slot) {
        ChestManager.get().setDirty();
    }

    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return ChestHelper.filterItem(stack);
    }
}

