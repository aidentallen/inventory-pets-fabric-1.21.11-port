/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 *  net.minecraft.core.UUIDUtil
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.resources.ResourceKey
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  org.jetbrains.annotations.NotNull
 */
package com.inventorypets.init;

import com.mojang.serialization.Codec;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents((ResourceKey)Registries.DATA_COMPONENT_TYPE, (String)"inventorypets");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> BIOME_TO_FIND = COMPONENTS.register("biometofind", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> BIOME_CHECK = COMPONENTS.register("biomecheck", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> BIOME_NAME = COMPONENTS.register("biomename", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DIM_NAME = COMPONENTS.register("dimname", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> BIOME_TO_SEND = COMPONENTS.register("biometosend", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> BIOME_OPEN_ME = COMPONENTS.register("biomeopenme", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> LOADED_FLAG = COMPONENTS.register("loadedflag", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> HOME_DIMENSION = COMPONENTS.register("homedimension", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> GIVE_ITEMS = COMPONENTS.register("giveitems", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> LAST_DIMENSION = COMPONENTS.register("lastdimension", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LAST_X = COMPONENTS.register("lastx", () -> DataComponentType.builder().persistent((Codec)Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LAST_Y = COMPONENTS.register("lasty", () -> DataComponentType.builder().persistent((Codec)Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LAST_Z = COMPONENTS.register("lastz", () -> DataComponentType.builder().persistent((Codec)Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> OTHER_LAST_X = COMPONENTS.register("otherlastx", () -> DataComponentType.builder().persistent((Codec)Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> OTHER_LAST_Y = COMPONENTS.register("otherlasty", () -> DataComponentType.builder().persistent((Codec)Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> OTHER_LAST_Z = COMPONENTS.register("otherlastz", () -> DataComponentType.builder().persistent((Codec)Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> PETRIFIER_READY = COMPONENTS.register("petrifierready", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> RIDE_ON = COMPONENTS.register("rideon", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> LEASH_ON = COMPONENTS.register("leashon", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ATTRACT_ITEMS = COMPONENTS.register("attractitems", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> SLOW_DELAY = COMPONENTS.register("slowdelay", () -> DataComponentType.builder().persistent((Codec)Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> STRUCTURE_TO_FIND = COMPONENTS.register("structuretofind", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> STRUCTURE_CHECK = COMPONENTS.register("structurecheck", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> STRUCTURE_NAME = COMPONENTS.register("structurename", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> STRUCTURE_TO_SEND = COMPONENTS.register("structuretosend", () -> DataComponentType.builder().persistent((Codec)Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> STRUCTURE_OPEN_ME = COMPONENTS.register("structureopenme", () -> DataComponentType.builder().persistent((Codec)Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> CHEST_UUID = COMPONENTS.register("chest_uuid", () -> DataComponentType.builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build());

    @NotNull
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec) {
        return ModDataComponents.register(name, codec, null);
    }

    @NotNull
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        if (streamCodec == null) {
            return COMPONENTS.register(name, () -> DataComponentType.builder().persistent(codec).build());
        }
        return COMPONENTS.register(name, () -> DataComponentType.builder().persistent(codec).networkSynchronized(streamCodec).build());
    }
}

