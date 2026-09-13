/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.inventory;

import com.inventorypets.inventory.Chest;
import com.inventorypets.inventory.IPItemHandler;
import com.inventorypets.inventory.IPStackHandler;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.items.IItemHandler;

public class ChestData {
    private final UUID uuid;
    private Chest chestType;
    private final IPItemHandler inventory;
    private final Optional<IItemHandler> optional;
    private IPStackHandler filter = new IPStackHandler();

    public Optional<IItemHandler> getOptional() {
        return this.optional;
    }

    public IItemHandler getHandler() {
        return this.inventory;
    }

    public Chest getChestType() {
        return this.chestType;
    }

    public ChestData(UUID uuid, Chest chestType) {
        this.uuid = uuid;
        this.chestType = chestType;
        this.inventory = new IPItemHandler(chestType.slots);
        this.optional = Optional.of(this.inventory);
    }

    public ChestData(UUID uuid, CompoundTag incomingNBT, HolderLookup.Provider pRegistries) {
        this.uuid = uuid;
        this.chestType = Chest.values()[Math.min(incomingNBT.getInt("ChestType"), Chest.DOUBLE_CHEST.ordinal())];
        this.inventory = new IPItemHandler(this.chestType.slots);
        if (incomingNBT.getCompound("Inventory").contains("Size") && incomingNBT.getCompound("Inventory").getInt("Size") != this.chestType.slots) {
            incomingNBT.getCompound("Inventory").putInt("Size", this.chestType.slots);
        }
        this.inventory.deserializeNBT(pRegistries, incomingNBT.getCompound("Inventory"));
        this.filter = new IPStackHandler();
        this.filter.deserializeNBT(pRegistries, incomingNBT.getCompound("Filter"));
        this.optional = Optional.of(this.inventory);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public static Optional<ChestData> fromNBT(CompoundTag nbt, HolderLookup.Provider pRegistries) {
        if (nbt.contains("UUID")) {
            UUID uuid = nbt.getUUID("UUID");
            return Optional.of(new ChestData(uuid, nbt, pRegistries));
        }
        return Optional.empty();
    }

    public CompoundTag toNBT(HolderLookup.Provider pRegistries) {
        CompoundTag nbt = new CompoundTag();
        nbt.putUUID("UUID", this.uuid);
        nbt.putInt("ChestType", this.chestType.ordinal());
        nbt.put("Inventory", (Tag)this.inventory.serializeNBT(pRegistries));
        nbt.put("Filter", (Tag)this.filter.serializeNBT(pRegistries));
        return nbt;
    }
}

