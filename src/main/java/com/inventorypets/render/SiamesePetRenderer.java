/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.model.animal.feline.CatModel
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.Identifier
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.AABB
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.render;

import com.inventorypets.entities.SiamesePetEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.animal.feline.CatModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class SiamesePetRenderer
extends MobRenderer<SiamesePetEntity, CatModel<SiamesePetEntity>> {
    private static final Identifier petTextures = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/siamese_pet.png");

    public SiamesePetRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, (EntityModel)new CatModel(renderManagerIn.bakeLayer(ModelLayers.CAT)), 0.4f);
    }

    protected void scale(SiamesePetEntity entity, PoseStack poseStackIn, float size) {
        super.scale((LivingEntity)entity, poseStackIn, size);
        poseStackIn.scale(0.8f, 0.8f, 0.8f);
    }

    public Identifier getTextureLocation(SiamesePetEntity entity) {
        return petTextures;
    }

    protected void setupRotations(SiamesePetEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations((LivingEntity)entity, poseStack, bob, yBodyRot, partialTick, scale);
        float f = entity.getLieDownAmount(partialTick);
        if (f > 0.0f) {
            poseStack.translate(0.4f * f, 0.15f * f, 0.1f * f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.rotLerp((float)f, (float)0.0f, (float)90.0f)));
            BlockPos blockpos = entity.blockPosition();
            for (Player player : entity.level().getEntitiesOfClass(Player.class, new AABB(blockpos).inflate(2.0, 2.0, 2.0))) {
                if (!player.isSleeping()) continue;
                poseStack.translate(0.15f * f, 0.0f, 0.0f);
                break;
            }
        }
    }
}

