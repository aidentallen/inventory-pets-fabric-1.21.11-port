/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package com.inventorypets.handler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class TeleporterPositionHandler {
    public static void teleport(Player player, ServerLevel serverLevel, int x, int y, int z) {
        ServerPlayer serverPlayer = (ServerPlayer)player;
        String checkDim = serverLevel.dimension().location().getPath();
        serverPlayer.teleportTo(serverLevel, (double)x, (double)y, (double)z, 0.0f, 0.0f);
        player.fallDistance = 0.0f;
    }
}

