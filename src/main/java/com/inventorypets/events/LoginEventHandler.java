/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 */
package com.inventorypets.events;

import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.handler.UpdateHandler;
import java.util.Calendar;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid="inventorypets")
public class LoginEventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        String text;
        Player player = event.getEntity();
        if (UpdateHandler.show && ((Boolean)InventoryPetsConfig.showUpdateMessage.get()).booleanValue()) {
            player.sendSystemMessage((Component)Component.translatable((String)UpdateHandler.updateStatus));
        }
        Long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int mMonth = calendar.get(2);
        int mDay = calendar.get(5);
        if (mMonth == 11 && mDay < 26 && mDay < 14 && !((Boolean)InventoryPetsConfig.disableHolidayPets.get()).booleanValue() && ((Boolean)InventoryPetsConfig.showHolidayMessages.get()).booleanValue() && !player.level().isClientSide) {
            text = "info.inventorypets.prepetmas";
            LoginEventHandler.sendChatMessage(text, player);
        }
        if (mMonth == 11 && mDay < 26 && mDay > 13 && !((Boolean)InventoryPetsConfig.disableHolidayPets.get()).booleanValue() && ((Boolean)InventoryPetsConfig.showHolidayMessages.get()).booleanValue() && !player.level().isClientSide) {
            text = "info.inventorypets.petmas";
            LoginEventHandler.sendChatMessage(text, player);
        }
        if (mMonth == 3 && !((Boolean)InventoryPetsConfig.disableAprilFool.get()).booleanValue() && !((Boolean)InventoryPetsConfig.disableAprilFoolHoliday.get()).booleanValue() && ((Boolean)InventoryPetsConfig.showHolidayMessages.get()).booleanValue() && !player.level().isClientSide) {
            text = "info.inventorypets.aprilfools";
            LoginEventHandler.sendChatMessage(text, player);
        }
    }

    public static void sendChatMessage(String text, Player player) {
        MutableComponent component2 = Component.translatable((String)text);
        player.sendSystemMessage((Component)component2);
    }
}

