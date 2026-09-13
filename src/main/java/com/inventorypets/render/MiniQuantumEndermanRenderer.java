/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.render;

import com.inventorypets.entities.MiniQuantumEndermanEntity;
import com.inventorypets.models.MiniQuantumEndermanModel;
import com.inventorypets.render.MiniQuantumEndermanEyesLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.RandomPoolAlias;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class MiniQuantumEndermanRenderer
extends MobRenderer<MiniQuantumEndermanEntity, MiniQuantumEndermanModel<MiniQuantumEndermanEntity>> {
    private static final Identifier ENDERMAN_TEXTURES = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/enderman/mq_enderman.png");
    private final RandomPoolAlias random = new RandomPoolAlias();

    public MiniQuantumEndermanRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MiniQuantumEndermanModel(renderManagerIn.bakeLayer(ModelLayers.ENDERMAN)), 0.2f);
        this.addLayer((RenderLayer)new MiniQuantumEndermanEyesLayer(this));
    }

    protected void scale(MiniQuantumEndermanEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        float f = 0.4f;
        matrixStackIn.scale(f, f, f);
    }

    public void render(MiniQuantumEndermanEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        MiniQuantumEndermanModel endermanmodel = (MiniQuantumEndermanModel)this.getModel();
        super.render((LivingEntity)entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public Vec3 getRenderOffset(MiniQuantumEndermanEntity entityIn, float partialTicks) {
        if (entityIn.isCreepy()) {
            return new Vec3(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
        }
        return super.getRenderOffset((Entity)entityIn, partialTicks);
    }

    public Identifier getTextureLocation(MiniQuantumEndermanEntity entity) {
        return ENDERMAN_TEXTURES;
    }
}

