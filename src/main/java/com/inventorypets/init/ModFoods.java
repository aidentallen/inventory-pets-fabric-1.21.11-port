/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.food.FoodProperties$Builder
 */
package com.inventorypets.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties HOLIDAY_COOKIE_FOOD = new FoodProperties.Builder().fast().nutrition(4).saturationModifier(1.2f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 1), 1.0f).alwaysEdible().build();
    public static final FoodProperties EGG_NOG_FOOD = new FoodProperties.Builder().fast().nutrition(4).saturationModifier(1.2f).effect(() -> new MobEffectInstance(MobEffects.HEAL, 100, 1), 1.0f).alwaysEdible().build();
    public static final FoodProperties CANDY_CANE_FOOD = new FoodProperties.Builder().fast().nutrition(2).saturationModifier(2.0f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3000, 1), 1.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 3000, 1), 1.0f).alwaysEdible().build();
    public static final FoodProperties ROCK_CANDY_FOOD = new FoodProperties.Builder().fast().nutrition(2).saturationModifier(2.0f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 1), 1.0f).effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 1), 1.0f).alwaysEdible().build();
}

