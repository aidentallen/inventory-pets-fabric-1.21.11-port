/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber(modid="inventorypets")
public class BurnTimeHandler {
    @SubscribeEvent
    public static void setBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() == InventoryPets.NUGGET_COAL.get()) {
            event.setBurnTime(200);
        }
    }
}

