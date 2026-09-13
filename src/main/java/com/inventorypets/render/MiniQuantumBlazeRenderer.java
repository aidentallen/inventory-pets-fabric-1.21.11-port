/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.monster.blaze.BlazeModel
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.resources.Identifier
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.render;

import com.inventorypets.entities.MiniQuantumBlazeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.monster.blaze.BlazeModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class MiniQuantumBlazeRenderer
extends MobRenderer<MiniQuantumBlazeEntity, BlazeModel<MiniQuantumBlazeEntity>> {
    private static final Identifier BLAZE_TEXTURES = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/q_blaze2.png");

    public MiniQuantumBlazeRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, (EntityModel)new BlazeModel(renderManagerIn.bakeLayer(ModelLayers.BLAZE)), 0.2f);
    }

    protected void scale(MiniQuantumBlazeEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        float f = 0.4f;
        matrixStackIn.scale(f, f, f);
    }

    public Identifier getTextureLocation(MiniQuantumBlazeEntity p_114482_) {
        return BLAZE_TEXTURES;
    }
}

