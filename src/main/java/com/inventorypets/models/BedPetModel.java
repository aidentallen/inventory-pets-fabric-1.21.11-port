/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.Entity
 */
package com.inventorypets.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class BedPetModel<Type extends Entity>
extends EntityModel<Type> {
    public static ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"bed_pet_entity"), "main");
    private final ModelPart body;

    public BedPetModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition create() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5f, -4.0f, -5.0f, 9.0f, 3.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(2, 5).addBox(3.0f, -1.0f, -4.5f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(2, 5).addBox(3.0f, -1.0f, 3.5f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(2, 5).addBox(-4.0f, -1.0f, -4.5f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(2, 5).addBox(-4.0f, -1.0f, 3.5f, 1.0f, 1.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
        return LayerDefinition.create((MeshDefinition)meshdefinition, (int)64, (int)16);
    }

    public void setupAnim(Type entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int i, int i1, int i2) {
        this.body.render(poseStack, buffer, i, i1);
    }
}

