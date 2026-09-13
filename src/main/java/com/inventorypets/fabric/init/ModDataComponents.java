package com.inventorypets.fabric.init;

import com.inventorypets.fabric.InventoryPetsFabric;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class ModDataComponents {
    public static final Map<String, DataComponentType<?>> ALL = new LinkedHashMap<>();
    public static DataComponentType<String> HOME_DIMENSION;
    public static DataComponentType<Boolean> GIVE_ITEMS;
    public static DataComponentType<String> LAST_DIMENSION;
    public static DataComponentType<Integer> LAST_X;
    public static DataComponentType<Integer> LAST_Y;
    public static DataComponentType<Integer> LAST_Z;
    public static DataComponentType<Boolean> RIDE_ON;
    public static DataComponentType<Boolean> LEASH_ON;
    public static DataComponentType<Boolean> ATTRACT_ITEMS;
    public static DataComponentType<Long> SLOW_DELAY;
    public static DataComponentType<UUID> CHEST_UUID;

    private ModDataComponents() {
    }

    public static void initialize() {
        string("biometofind");
        bool("biomecheck");
        string("biomename");
        string("dimname");
        string("biometosend");
        bool("biomeopenme");
        bool("loadedflag");
        HOME_DIMENSION = string("homedimension");
        GIVE_ITEMS = bool("giveitems");
        LAST_DIMENSION = string("lastdimension");
        LAST_X = integer("lastx");
        LAST_Y = integer("lasty");
        LAST_Z = integer("lastz");
        integer("otherlastx");
        integer("otherlasty");
        integer("otherlastz");
        bool("petrifierready");
        RIDE_ON = bool("rideon");
        LEASH_ON = bool("leashon");
        ATTRACT_ITEMS = bool("attractitems");
        SLOW_DELAY = register("slowdelay", Codec.LONG, ByteBufCodecs.VAR_LONG);
        string("structuretofind");
        bool("structurecheck");
        string("structurename");
        string("structuretosend");
        bool("structureopenme");
        CHEST_UUID = register("chest_uuid", UUIDUtil.CODEC, UUIDUtil.STREAM_CODEC);
    }

    private static DataComponentType<String> string(String path) {
        return register(path, Codec.STRING, ByteBufCodecs.STRING_UTF8);
    }

    private static DataComponentType<Boolean> bool(String path) {
        return register(path, Codec.BOOL, ByteBufCodecs.BOOL);
    }

    private static DataComponentType<Integer> integer(String path) {
        return register(path, Codec.INT, ByteBufCodecs.INT);
    }

    private static <T> DataComponentType<T> register(
            String path,
            Codec<T> codec,
            StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
    ) {
        DataComponentType<T> type = DataComponentType.<T>builder()
                .persistent(codec)
                .networkSynchronized(streamCodec)
                .build();
        Identifier id = Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, path);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, type);
        ALL.put(path, type);
        return type;
    }
}
