/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.CustomData
 */
package com.inventorypets.helper;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.pets.petChest;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class ChestHelper {
    public static boolean filterItem(ItemStack stack) {
        if (stack.getItem() == InventoryPets.PET_CHEST.get() || stack.getItem() == InventoryPets.PET_DOUBLE_CHEST.get() || stack.getItem() == InventoryPets.PET_SATED_CHEST.get() || stack.getItem() == InventoryPets.PET_SATED_DOUBLE_CHEST.get() || stack.getItem() == InventoryPets.FEED_BAG.get()) {
            return false;
        }
        return stack.getItem().canFitInsideContainerItems();
    }

    @Nonnull
    public static Optional<UUID> getUUID(@Nonnull ItemStack stack) {
        if (stack.has(ModDataComponents.CHEST_UUID)) {
            return Optional.ofNullable((UUID)stack.get(ModDataComponents.CHEST_UUID));
        }
        if (stack.getItem() instanceof petChest && stack.has(DataComponents.CUSTOM_DATA) && ((CustomData)stack.get(DataComponents.CUSTOM_DATA)).contains("UUID")) {
            return Optional.of(((CustomData)stack.get(DataComponents.CUSTOM_DATA)).getUnsafe().getUUID("UUID"));
        }
        return Optional.empty();
    }
}

