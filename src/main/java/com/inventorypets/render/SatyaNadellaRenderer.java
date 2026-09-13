/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer
 *  net.minecraft.client.renderer.entity.layers.CustomHeadLayer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.npc.villager.Villager
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.render;

import com.inventorypets.entities.SatyaNadellaEntity;
import com.inventorypets.models.SatyaNadellaModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.Villager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class SatyaNadellaRenderer
extends MobRenderer<SatyaNadellaEntity, SatyaNadellaModel<SatyaNadellaEntity>> {
    private static final Identifier CEO_SKIN = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/sn.png");

    public SatyaNadellaRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SatyaNadellaModel(renderManagerIn.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
        this.addLayer((RenderLayer)new CustomHeadLayer((RenderLayerParent)this, renderManagerIn.getModelSet(), renderManagerIn.getItemInHandRenderer()));
        this.addLayer((RenderLayer)new CrossedArmsItemLayer((RenderLayerParent)this, renderManagerIn.getItemInHandRenderer()));
    }

    protected void render(Villager entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        float f = 0.9375f;
        if (entitylivingbaseIn.isBaby()) {
            f = (float)((double)f * 0.5);
            this.shadowRadius = 0.25f;
        } else {
            this.shadowRadius = 0.5f;
        }
        matrixStackIn.scale(f, f, f);
    }

    public Identifier getTextureLocation(SatyaNadellaEntity entity) {
        return CEO_SKIN;
    }
}

