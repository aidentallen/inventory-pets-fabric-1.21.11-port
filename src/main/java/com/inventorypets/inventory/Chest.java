/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.item.Item
 *  net.neoforged.neoforge.registries.DeferredItem
 */
package com.inventorypets.inventory;

import com.inventorypets.InventoryPets;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public enum Chest {
    FEED_BAG("feed_bag", 18, 2, 9, "feed_bag.png", 256, 256, 4, 82, InventoryPets.FEED_BAG),
    CHEST("chest", 27, 3, 9, "chest.png", 256, 256, 4, 82, InventoryPets.PET_CHEST),
    SATED_CHEST("sated_chest", 27, 3, 9, "chest.png", 256, 256, 4, 82, InventoryPets.PET_SATED_CHEST),
    DOUBLE_CHEST("double_chest", 54, 6, 9, "double_chest.png", 256, 256, 4, 138, InventoryPets.PET_DOUBLE_CHEST),
    SATED_DOUBLE_CHEST("sated_double_chest", 54, 6, 9, "double_chest.png", 256, 256, 4, 138, InventoryPets.PET_SATED_DOUBLE_CHEST);

    public final int slots;
    public final Identifier texture;
    public final int xSize;
    public final int ySize;
    public final int slotXOffset;
    public final int slotYOffset;
    public final int slotRows;
    public final int slotCols;
    public final String name;
    public final DeferredItem<Item> item;

    private Chest(String name, int slots, int rows, int cols, String location, int xSize, int ySize, int slotXOffset, int slotYOffset, DeferredItem<Item> itemIn) {
        this.name = name;
        this.slots = slots;
        this.slotRows = rows;
        this.slotCols = cols;
        this.texture = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)("textures/gui/container/" + location));
        this.xSize = xSize;
        this.ySize = ySize;
        this.slotXOffset = slotXOffset;
        this.slotYOffset = slotYOffset;
        this.item = itemIn;
    }
}

