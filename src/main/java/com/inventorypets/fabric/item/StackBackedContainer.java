package com.inventorypets.fabric.item;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

final class StackBackedContainer extends SimpleContainer {
    private final ItemStack owner;

    StackBackedContainer(ItemStack owner, int slots) {
        super(slots);
        this.owner = owner;
        ItemContainerContents contents = owner.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(getItems());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        NonNullList<ItemStack> copy = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < getContainerSize(); i++) copy.set(i, getItem(i).copy());
        owner.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(copy));
    }
}
