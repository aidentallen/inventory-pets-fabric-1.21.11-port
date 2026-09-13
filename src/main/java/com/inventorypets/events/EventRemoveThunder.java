/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.sound.PlaySoundEvent
 */
package com.inventorypets.events;

import com.inventorypets.config.InventoryPetsConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

@EventBusSubscriber(value={Dist.CLIENT})
public class EventRemoveThunder {
    @SubscribeEvent
    public static void onEvent(PlaySoundEvent event) {
        if ((event.getName().equals("entity.lightning_bolt.thunder") || event.getName().equals("entity.lightning_bolt.impact")) && ((Boolean)InventoryPetsConfig.disableCloudSound.get()).booleanValue()) {
            event.setSound(null);
        }
    }
}

