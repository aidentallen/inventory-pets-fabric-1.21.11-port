/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.resources.ResourceKey
 *  net.neoforged.neoforge.attachment.AttachmentType
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.NeoForgeRegistries$Keys
 */
package com.inventorypets.init;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create((ResourceKey)NeoForgeRegistries.Keys.ATTACHMENT_TYPES, (String)"inventorypets");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> SHIELD = ATTACHMENT_TYPES.register("shield", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> CLOUD_FLIGHT = ATTACHMENT_TYPES.register("cloudflight", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RAPIDSHOT = ATTACHMENT_TYPES.register("rapidshot", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> POWERUP = ATTACHMENT_TYPES.register("powerup", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> SLOT = ATTACHMENT_TYPES.register("slot", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DAMAGE = ATTACHMENT_TYPES.register("damage", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<String>> NAME = ATTACHMENT_TYPES.register("name", () -> AttachmentType.builder(() -> "").serialize((Codec)Codec.STRING).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> TIME = ATTACHMENT_TYPES.register("time", () -> AttachmentType.builder(() -> 0.0).serialize((Codec)Codec.DOUBLE).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> MULTIPLIER = ATTACHMENT_TYPES.register("multiplier", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> KEY_INPUT = ATTACHMENT_TYPES.register("keyinput", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> CREATURE_SPEED = ATTACHMENT_TYPES.register("creaturespeed", () -> AttachmentType.builder(() -> 0.0).serialize((Codec)Codec.DOUBLE).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<String>> BIOME_TO_SEND = ATTACHMENT_TYPES.register("biometosend", () -> AttachmentType.builder(() -> "").serialize((Codec)Codec.STRING).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<String>> STRUCTURE_TO_SEND = ATTACHMENT_TYPES.register("structuretosend", () -> AttachmentType.builder(() -> "").serialize((Codec)Codec.STRING).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> BIOME_FLAG = ATTACHMENT_TYPES.register("biomeflag", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> STRUCTURE_FLAG = ATTACHMENT_TYPES.register("structureflag", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT1 = ATTACHMENT_TYPES.register("gift1", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT2 = ATTACHMENT_TYPES.register("gift2", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT3 = ATTACHMENT_TYPES.register("gift3", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT4 = ATTACHMENT_TYPES.register("gift4", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT5 = ATTACHMENT_TYPES.register("gift5", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT6 = ATTACHMENT_TYPES.register("gift6", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT7 = ATTACHMENT_TYPES.register("gift7", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT8 = ATTACHMENT_TYPES.register("gift8", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT9 = ATTACHMENT_TYPES.register("gift9", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT10 = ATTACHMENT_TYPES.register("gift10", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT11 = ATTACHMENT_TYPES.register("gift11", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GIFT12 = ATTACHMENT_TYPES.register("gift12", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY1 = ATTACHMENT_TYPES.register("holiday1", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY2 = ATTACHMENT_TYPES.register("holiday2", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY3 = ATTACHMENT_TYPES.register("holiday3", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY4 = ATTACHMENT_TYPES.register("holiday4", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY5 = ATTACHMENT_TYPES.register("holiday5", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY6 = ATTACHMENT_TYPES.register("holiday6", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY7 = ATTACHMENT_TYPES.register("holiday7", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY8 = ATTACHMENT_TYPES.register("holiday8", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY9 = ATTACHMENT_TYPES.register("holiday9", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY10 = ATTACHMENT_TYPES.register("holiday10", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY11 = ATTACHMENT_TYPES.register("holiday11", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> HOLIDAY12 = ATTACHMENT_TYPES.register("holiday12", () -> AttachmentType.builder(() -> 0).serialize((Codec)Codec.INT).copyOnDeath().build());
}

