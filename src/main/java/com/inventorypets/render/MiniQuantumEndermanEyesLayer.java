/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.EyesLayer
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.LivingEntity
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.render;

import com.inventorypets.models.MiniQuantumEndermanModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class MiniQuantumEndermanEyesLayer<T extends LivingEntity>
extends EyesLayer<T, MiniQuantumEndermanModel<T>> {
    private static final RenderType ENDERMAN_EYES = RenderType.eyes((Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/entity/enderman/q_enderman_eyes.png"));

    public MiniQuantumEndermanEyesLayer(RenderLayerParent<T, MiniQuantumEndermanModel<T>> model) {
        super(model);
    }

    public RenderType renderType() {
        return ENDERMAN_EYES;
    }
}

