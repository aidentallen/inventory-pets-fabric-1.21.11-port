/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.player.ArrowLooseEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModDataAttachments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;

@EventBusSubscriber(modid="inventorypets")
public class InstaShotHandler {
    @SubscribeEvent
    public static void checkArrowsLoose(ArrowLooseEvent event) {
        if (event.getEntity() instanceof Player) {
            Player entityplayer = event.getEntity();
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = entityplayer.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableQuiver.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_QUIVER.get() && itemchk.getDamageValue() == 0 || (Integer)entityplayer.getData(ModDataAttachments.RAPIDSHOT) != 1 || event.getCharge() >= 200) continue;
                event.setCharge(200);
            }
        }
    }
}

