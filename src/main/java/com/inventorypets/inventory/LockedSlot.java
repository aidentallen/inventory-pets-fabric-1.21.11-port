/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package com.inventorypets.inventory;

import javax.annotation.Nonnull;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LockedSlot
extends Slot {
    private final boolean locked;

    public LockedSlot(Inventory inventory, int slotIndex, int x, int y, boolean lock) {
        super((Container)inventory, slotIndex, x, y);
        this.locked = lock;
    }

    public boolean mayPickup(@Nonnull Player p_82869_1_) {
        return !this.locked;
    }

    public boolean mayPlace(@Nonnull ItemStack p_75214_1_) {
        return !this.locked;
    }
}

