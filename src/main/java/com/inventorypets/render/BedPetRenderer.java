/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.resources.Identifier
 *  net.minecraft.util.Mth
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.render;

import com.inventorypets.entities.BedPetEntity;
import com.inventorypets.models.BedPetModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class BedPetRenderer<Type extends BedPetEntity>
extends MobRenderer<Type, BedPetModel<Type>> {
    private static final Identifier PET_TEXTURES = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/bed_pet_entity.png");

    public BedPetRenderer(EntityRendererProvider.Context context) {
        super(context, new BedPetModel(context.bakeLayer(BedPetModel.LAYER_LOCATION)), 0.5f);
    }

    protected void scale(BedPetEntity entity, PoseStack poseStackIn, float sizeIn) {
        poseStackIn.scale(1.0f, 1.0f, 1.0f);
        poseStackIn.translate(0.0, (double)0.001f, 0.0);
        float f1 = entity.getSize();
        float f2 = Mth.lerp((float)sizeIn, (float)entity.oSquish, (float)entity.squish) / (f1 * 0.5f + 1.0f);
        float f3 = 1.0f / (f2 + 1.0f);
        poseStackIn.scale(f3 * f1, 1.0f / f3 * f1, f3 * f1);
    }

    public Identifier getTextureLocation(Type entity) {
        return PET_TEXTURES;
    }
}

