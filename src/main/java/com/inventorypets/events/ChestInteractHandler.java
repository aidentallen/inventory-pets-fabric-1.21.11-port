/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 */
package com.inventorypets.events;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid="inventorypets")
public class ChestInteractHandler {
    @SubscribeEvent
    public static void notify(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND).copy();
        ItemStack mainStack = player.getItemInHand(InteractionHand.MAIN_HAND).copy();
        if (offStack != ItemStack.EMPTY && mainStack.getItem().getDescriptionId().toLowerCase().contains("item.inventorypets")) {
            event.setCanceled(true);
        }
    }
}

