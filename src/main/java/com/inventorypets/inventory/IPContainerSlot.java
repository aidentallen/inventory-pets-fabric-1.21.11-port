/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.items.IItemHandlerModifiable
 *  net.neoforged.neoforge.items.SlotItemHandler
 *  org.jetbrains.annotations.NotNull
 */
package com.inventorypets.inventory;

import com.inventorypets.helper.ChestHelper;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class IPContainerSlot
extends SlotItemHandler {
    private Predicate<ItemStack> filterPredicate = ChestHelper::filterItem;
    private int index;

    public IPContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.index = index;
    }

    public IPContainerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> filterPredicate) {
        super(itemHandler, index, xPosition, yPosition);
        this.filterPredicate = filterPredicate;
    }

    public int getMaxStackSize(@Nonnull ItemStack stack) {
        return stack.getMaxStackSize();
    }

    public boolean mayPlace(@Nonnull ItemStack stack) {
        if (!super.mayPlace(stack)) {
            return false;
        }
        return this.filterPredicate.test(stack);
    }

    public void initialize(@NotNull ItemStack itemStack) {
        ((IItemHandlerModifiable)this.getItemHandler()).setStackInSlot(this.index, itemStack);
    }
}

