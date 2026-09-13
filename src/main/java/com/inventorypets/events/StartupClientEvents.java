/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.world.level.block.Block
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.fx.QuantumParticles;
import com.inventorypets.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid="inventorypets", value={Dist.CLIENT})
public class StartupClientEvents {
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)InventoryPets.CLOUD_BLOCK.get()), (RenderType)RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)InventoryPets.CLOUD_SPAWN.get()), (RenderType)RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer((Block)((Block)InventoryPets.SPACE_SPAWN.get()), (RenderType)RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register((ParticleType)ModParticles.QUANTUM_PARTICLE.get(), QuantumParticles.Provider::new);
    }
}

