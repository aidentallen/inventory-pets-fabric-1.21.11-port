/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.saveddata.SavedData
 *  net.minecraft.world.level.saveddata.SavedData$Factory
 *  net.neoforged.fml.util.thread.SidedThreadGroups
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.server.ServerLifecycleHooks
 */
package com.inventorypets.inventory;

import com.inventorypets.init.ModDataComponents;
import com.inventorypets.inventory.Chest;
import com.inventorypets.inventory.ChestData;
import java.util.HashMap;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ChestManager
extends SavedData {
    private static final String NAME = "inventorypets_chest_data";
    private static final HashMap<UUID, ChestData> data = new HashMap();
    public static final ChestManager blankClient = new ChestManager();

    public static ChestManager get() {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            return (ChestManager)ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory(ChestManager::new, ChestManager::load), NAME);
        }
        return blankClient;
    }

    public ChestData getOrCreateChest(UUID uuid, Chest tier) {
        return data.computeIfAbsent(uuid, id -> {
            this.setDirty();
            return new ChestData((UUID)id, tier);
        });
    }

    public IItemHandler getCapability(UUID uuid) {
        if (data.containsKey(uuid)) {
            return data.get(uuid).getHandler();
        }
        return null;
    }

    public IItemHandler getCapability(ItemStack stack) {
        UUID uuid;
        if (stack.has(ModDataComponents.CHEST_UUID) && data.containsKey(uuid = (UUID)stack.get(ModDataComponents.CHEST_UUID))) {
            return data.get(uuid).getHandler();
        }
        return null;
    }

    public static ChestManager load(CompoundTag nbt, HolderLookup.Provider pRegistries) {
        if (nbt.contains("Chests")) {
            ListTag list = nbt.getList("Chests", 10);
            list.forEach(chestNBT -> ChestData.fromNBT((CompoundTag)chestNBT, pRegistries).ifPresent(chest -> data.put(chest.getUuid(), (ChestData)chest)));
        }
        return new ChestManager();
    }

    @Nonnull
    public CompoundTag save(CompoundTag compound, HolderLookup.Provider pRegistries) {
        ListTag chests = new ListTag();
        data.forEach((uuid, ChestData2) -> chests.add((Object)ChestData2.toNBT(pRegistries)));
        compound.put("Chests", (Tag)chests);
        return compound;
    }
}

