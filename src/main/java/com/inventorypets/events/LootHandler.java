/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.event.entity.living.LivingDropsEvent
 *  net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$ItemCraftedEvent
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.inventory.Chest;
import com.inventorypets.inventory.ChestData;
import com.inventorypets.inventory.ChestManager;
import com.inventorypets.pets.petSatedChest;
import com.inventorypets.pets.petSatedDoubleChest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.items.IItemHandler;

@EventBusSubscriber(modid="inventorypets")
public class LootHandler {
    @SubscribeEvent
    public static void notifyDrop(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }
        if (event.getSource().getDirectEntity() instanceof Player) {
            Player entityplayer = (Player)event.getSource().getDirectEntity();
            LivingEntity entity = event.getEntity();
            if (!entityplayer.level().isClientSide && !((Boolean)InventoryPetsConfig.disableLoot.get()).booleanValue()) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_LOOT.get() || itemchk.getDamageValue() != 0) continue;
                    int esize = event.getDrops().size();
                    Collection bob = event.getDrops();
                    ArrayList drops = new ArrayList(bob);
                    for (int k = 0; k <= esize - 1; ++k) {
                        drops.get(k);
                        ItemStack itemstack = ((ItemEntity)drops.get(k)).getItem();
                        ItemEntity entityitem = new ItemEntity(entityplayer.level(), entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ() + 0.5, itemstack);
                        entityplayer.level().addFreshEntity((Entity)entityitem);
                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void expDrop(LivingExperienceDropEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }
        if (event.getAttackingPlayer() instanceof Player) {
            Player entityplayer = event.getAttackingPlayer();
            if (!entityplayer.level().isClientSide && !((Boolean)InventoryPetsConfig.disablePixie.get()).booleanValue()) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_PIXIE.get() || itemchk.getDamageValue() != 0) continue;
                    int xp = event.getDroppedExperience();
                    event.setDroppedExperience(xp * 2);
                    event.getAttackingPlayer().level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.lifesteal.get(), SoundSource.PLAYERS, 0.5f, 1.5f);
                    i = 8;
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void checkCrafting(PlayerEvent.ItemCraftedEvent event) {
        block5: {
            block4: {
                IItemHandler origHandler;
                if (event.getCrafting().getItem() != InventoryPets.PET_SATED_CHEST.get()) break block4;
                ItemStack destChest = event.getCrafting();
                ItemStack origChest = event.getInventory().getItem(4);
                if (origChest.getItem() != InventoryPets.PET_CHEST.get() || (origHandler = (IItemHandler)origChest.getCapability(Capabilities.ItemHandler.ITEM)) == null) break block5;
                ChestManager.get().getOrCreateChest(UUID.randomUUID(), Chest.SATED_CHEST);
                ChestData data = petSatedChest.getData(destChest);
                IItemHandler destHandler = data.getHandler();
                int invsize = 27;
                for (int i = 0; i < invsize; ++i) {
                    ItemStack chestStack = origHandler.getStackInSlot(i).copy();
                    if (chestStack.isEmpty()) continue;
                    destHandler.insertItem(i, chestStack, false);
                }
                break block5;
            }
            if (event.getCrafting().getItem() == InventoryPets.PET_SATED_DOUBLE_CHEST.get()) {
                IItemHandler origHandler;
                ItemStack destChest = event.getCrafting();
                ItemStack origChest = event.getInventory().getItem(4);
                if (origChest.getItem() == InventoryPets.PET_DOUBLE_CHEST.get() && (origHandler = (IItemHandler)origChest.getCapability(Capabilities.ItemHandler.ITEM)) != null) {
                    ChestManager.get().getOrCreateChest(UUID.randomUUID(), Chest.SATED_DOUBLE_CHEST);
                    ChestData data = petSatedDoubleChest.getData(destChest);
                    IItemHandler destHandler = data.getHandler();
                    int invsize = 54;
                    for (int i = 0; i < invsize; ++i) {
                        ItemStack chestStack = origHandler.getStackInSlot(i).copy();
                        if (chestStack.isEmpty()) continue;
                        destHandler.insertItem(i, chestStack, false);
                    }
                }
            }
        }
    }
}

