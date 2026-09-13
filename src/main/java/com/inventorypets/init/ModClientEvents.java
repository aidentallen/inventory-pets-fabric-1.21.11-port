/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRenderers
 *  net.minecraft.client.renderer.entity.ThrownItemRenderer
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.EntityRenderersEvent$RegisterLayerDefinitions
 *  net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
 */
package com.inventorypets.init;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModKeys;
import com.inventorypets.models.AnvilPetModel;
import com.inventorypets.models.BedPetModel;
import com.inventorypets.render.AnvilPetRenderer;
import com.inventorypets.render.BedPetRenderer;
import com.inventorypets.render.BillGatesRenderer;
import com.inventorypets.render.MiniQuantumBlazeRenderer;
import com.inventorypets.render.MiniQuantumEndermanRenderer;
import com.inventorypets.render.SatyaNadellaRenderer;
import com.inventorypets.render.SiamesePetRenderer;
import com.inventorypets.render.SteveBallmerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid="inventorypets")
public class ModClientEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AnvilPetModel.LAYER_LOCATION, AnvilPetModel::create);
        event.registerLayerDefinition(BedPetModel.LAYER_LOCATION, BedPetModel::create);
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(ModKeys.NAMING_KEY);
    }

    public static void RegisterEntityRenderers() {
        EntityRenderers.register(InventoryPets.SIAMESE_ENTITY.get(), SiamesePetRenderer::new);
        EntityRenderers.register(InventoryPets.BILL_GATES_ENTITY.get(), BillGatesRenderer::new);
        EntityRenderers.register(InventoryPets.STEVE_BALLMER_ENTITY.get(), SteveBallmerRenderer::new);
        EntityRenderers.register(InventoryPets.SATYA_NADELLA_ENTITY.get(), SatyaNadellaRenderer::new);
        EntityRenderers.register(InventoryPets.ANVIL_PET_ENTITY.get(), AnvilPetRenderer::new);
        EntityRenderers.register(InventoryPets.BED_PET_ENTITY.get(), BedPetRenderer::new);
        EntityRenderers.register(InventoryPets.MINI_QB_ENTITY.get(), MiniQuantumBlazeRenderer::new);
        EntityRenderers.register(InventoryPets.MINI_QE_ENTITY.get(), MiniQuantumEndermanRenderer::new);
        EntityRenderers.register(InventoryPets.APPLE_ENTITY.get(), ThrownItemRenderer::new);
        EntityRenderers.register(InventoryPets.GOLDEN_APPLE_ENTITY.get(), ThrownItemRenderer::new);
        EntityRenderers.register(InventoryPets.BANANA_ENTITY.get(), ThrownItemRenderer::new);
    }
}

