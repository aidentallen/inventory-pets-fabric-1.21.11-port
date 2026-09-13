/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.helper;

import com.inventorypets.init.ModParticles;
import java.util.RandomPoolAlias;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ProxyHelper {
    @OnlyIn(value=Dist.CLIENT)
    public static void spawnPortalEffects2(ClientLevel world, int par2, int par3, int par4) {
        RandomPoolAlias rand = new RandomPoolAlias();
        for (int l = 0; l < 1; ++l) {
            double db0 = 0.5 + (double)par2 + (rand.nextDouble() - 0.5) * (double)1.2f;
            double db1 = (double)par3 + rand.nextDouble() * 2.0;
            double db2 = 0.5 + (double)par4 + (rand.nextDouble() - 0.5) * (double)1.2f;
            double d3 = (rand.nextDouble() - 0.5) * 1.0;
            double d4 = (rand.nextDouble() - 0.5) * 1.0;
            double d5 = (rand.nextDouble() - 0.5) * 1.0;
            world.addParticle((ParticleOptions)ModParticles.QUANTUM_PARTICLE.get(), db0, db1, db2, d3, d4, d5);
        }
    }

    public static void isFalling(Player entityplayer) {
        if (entityplayer.getDeltaMovement().y < -0.4) {
            double x = entityplayer.getDeltaMovement().x;
            double y = entityplayer.getDeltaMovement().y;
            double z = entityplayer.getDeltaMovement().z;
            entityplayer.lerpMotion(x, y += 0.1, z);
            entityplayer.fallDistance = 0.0f;
        }
    }

    public static void Sleep(Player entityplayer) {
        long currentTime = 0L;
        int factorTime = 0;
        currentTime = entityplayer.level().getDayTime() % 24000L;
        factorTime = 24000 - (int)currentTime;
        ServerPlayer serverPlayer = (ServerPlayer)entityplayer;
        ServerLevel ServerLevel2 = (ServerLevel)serverPlayer.level();
        ServerLevel2.setDayTime(ServerLevel2.getDayTime() + (long)factorTime);
    }
}

