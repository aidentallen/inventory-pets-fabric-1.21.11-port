/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.AdvancementHolder
 *  net.minecraft.advancements.AdvancementProgress
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 */
package com.inventorypets.helper;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementHelper {
    public static boolean hasAdvancement(ServerPlayer playerMP, Identifier location) {
        AdvancementHolder advancement = null;
        MinecraftServer server = playerMP.getServer();
        try {
            advancement = server.getAdvancements().get(location);
            if (advancement == null) {
                return false;
            }
            AdvancementProgress advancementprogress = playerMP.getAdvancements().getOrStartProgress(advancement);
            if (advancementprogress.isDone()) {
                return true;
            }
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void unlockAdvancement(ServerPlayer player, Identifier location) {
        if (player.level().isClientSide) {
            return;
        }
        MinecraftServer server = player.getServer();
        try {
            ServerPlayer playerMP = player;
            AdvancementHolder advancement = server.getAdvancements().get(location);
            if (advancement == null) {
                return;
            }
            AdvancementProgress advancementprogress = playerMP.getAdvancements().getOrStartProgress(advancement);
            if (advancementprogress.isDone()) {
                return;
            }
            for (String s : advancementprogress.getRemainingCriteria()) {
                playerMP.getAdvancements().award(advancement, s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

