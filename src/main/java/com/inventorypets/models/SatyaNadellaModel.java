/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HeadedModel
 *  net.minecraft.client.model.HierarchicalModel
 *  net.minecraft.client.model.VillagerLikeModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.npc.villager.AbstractVillager
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.models;

import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.VillagerLikeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class SatyaNadellaModel<T extends Entity>
extends HierarchicalModel<T>
implements HeadedModel,
VillagerLikeModel {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart hatRim;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    protected final ModelPart nose;

    public SatyaNadellaModel(ModelPart p_171051_) {
        this.root = p_171051_;
        this.head = p_171051_.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hatRim = this.hat.getChild("hat_rim");
        this.nose = this.head.getChild("nose");
        this.rightLeg = p_171051_.getChild("right_leg");
        this.leftLeg = p_171051_.getChild("left_leg");
    }

    public static MeshDefinition createBodyModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.ZERO);
        PartDefinition partdefinition2 = partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.ZERO);
        partdefinition2.addOrReplaceChild("hat_rim", CubeListBuilder.create().texOffs(30, 47).addBox(-8.0f, -8.0f, -6.0f, 16.0f, 16.0f, 1.0f), PartPose.rotation((float)-1.5707964f, (float)0.0f, (float)0.0f));
        partdefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.offset((float)0.0f, (float)-2.0f, (float)0.0f));
        PartDefinition partdefinition3 = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f), PartPose.ZERO);
        partdefinition3.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).texOffs(44, 22).addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, true).texOffs(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), PartPose.offsetAndRotation((float)0.0f, (float)3.0f, (float)-1.0f, (float)-0.75f, (float)0.0f, (float)0.0f));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset((float)-2.0f, (float)12.0f, (float)0.0f));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset((float)2.0f, (float)12.0f, (float)0.0f));
        return meshdefinition;
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(T p_104053_, float p_104054_, float p_104055_, float p_104056_, float p_104057_, float p_104058_) {
        boolean flag = false;
        if (p_104053_ instanceof AbstractVillager) {
            flag = ((AbstractVillager)p_104053_).getUnhappyCounter() > 0;
        }
        this.head.yRot = p_104057_ * ((float)Math.PI / 180);
        this.head.xRot = p_104058_ * ((float)Math.PI / 180);
        if (flag) {
            this.head.zRot = 0.3f * Mth.sin((float)(0.45f * p_104056_));
            this.head.xRot = 0.4f;
        } else {
            this.head.zRot = 0.0f;
        }
        this.rightLeg.xRot = Mth.cos((float)(p_104054_ * 0.6662f)) * 1.4f * p_104055_ * 0.5f;
        this.leftLeg.xRot = Mth.cos((float)(p_104054_ * 0.6662f + (float)Math.PI)) * 1.4f * p_104055_ * 0.5f;
        this.rightLeg.yRot = 0.0f;
        this.leftLeg.yRot = 0.0f;
    }

    public ModelPart getHead() {
        return this.head;
    }

    public void hatVisible(boolean p_104060_) {
        this.head.visible = p_104060_;
        this.hat.visible = p_104060_;
        this.hatRim.visible = p_104060_;
    }
}

