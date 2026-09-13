/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 */
package com.inventorypets.events;

import com.inventorypets.init.ModKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid="inventorypets", value={Dist.CLIENT})
public final class KeyHandler {
    public static boolean nflag = false;

    @SubscribeEvent
    public static void onEvent(ClientTickEvent.Post event) {
        ItemStack currentItem;
        LocalPlayer playerIn = Minecraft.getInstance().player;
        if (playerIn != null && ModKeys.NAMING_KEY.isDown() && (currentItem = playerIn.getInventory().getSelected()) != null && currentItem != ItemStack.EMPTY && currentItem.getItem().getDescriptionId().toString().contains("inventorypets") && currentItem.getItem().getDescriptionId().toString().contains("pet_")) {
            nflag = true;
        }
    }
}

