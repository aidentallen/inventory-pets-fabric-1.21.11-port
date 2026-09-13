/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.animal.golem.IronGolem
 *  net.minecraft.world.entity.animal.pig.Pig
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.zombie.ZombifiedPiglin
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent
 */
package com.inventorypets.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;

@EventBusSubscriber(modid="inventorypets")
public class LightningHandler {
    @SubscribeEvent
    public static void notifyAttack(EntityStruckByLightningEvent event) {
        if (event.getLightning().hasCustomName() && event.getLightning().getCustomName().getString().equals("invpets") && event.getEntity() instanceof LivingEntity) {
            if (event.getEntity() instanceof Creeper || event.getEntity() instanceof Pig || event.getEntity() instanceof ZombifiedPiglin) {
                event.getEntity().hurt(event.getEntity().level().damageSources().magic(), 4.0f);
            } else if (event.getEntity() instanceof IronGolem) {
                event.getEntity().hurt(event.getEntity().level().damageSources().magic(), 20.0f);
            } else if (!(event.getEntity().hasCustomName() && event.getEntity().getCustomName().getString().contains("Elle") || event.getEntity().getName().getString().toLowerCase().contains("dragon egg"))) {
                event.getEntity().hurt(event.getEntity().level().damageSources().magic(), 8.0f);
            }
        }
    }
}

