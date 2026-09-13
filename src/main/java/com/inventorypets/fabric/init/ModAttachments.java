package com.inventorypets.fabric.init;

import com.inventorypets.fabric.InventoryPetsFabric;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModAttachments {
    public static final Map<String, AttachmentType<?>> ALL = new LinkedHashMap<>();

    public static AttachmentType<Integer> SHIELD;
    public static AttachmentType<Integer> CLOUD_FLIGHT;
    public static AttachmentType<Integer> RAPID_SHOT;
    public static AttachmentType<Integer> POWER_UP;
    public static AttachmentType<Integer> SLOT;
    public static AttachmentType<Integer> DAMAGE;
    public static AttachmentType<String> NAME;
    public static AttachmentType<Double> TIME;
    public static AttachmentType<Integer> MULTIPLIER;
    public static AttachmentType<Integer> KEY_INPUT;
    public static AttachmentType<Double> CREATURE_SPEED;
    public static AttachmentType<String> BIOME_TO_SEND;
    public static AttachmentType<String> STRUCTURE_TO_SEND;
    public static AttachmentType<Integer> BIOME_FLAG;
    public static AttachmentType<Integer> STRUCTURE_FLAG;

    private ModAttachments() {
    }

    public static void initialize() {
        SHIELD = integer("shield", false);
        CLOUD_FLIGHT = integer("cloudflight", false);
        RAPID_SHOT = integer("rapidshot", false);
        POWER_UP = integer("powerup", false);
        SLOT = integer("slot", false);
        DAMAGE = integer("damage", false);
        NAME = string("name", false);
        TIME = decimal("time");
        MULTIPLIER = integer("multiplier", false);
        KEY_INPUT = integer("keyinput", false);
        CREATURE_SPEED = decimal("creaturespeed");
        BIOME_TO_SEND = string("biometosend", false);
        STRUCTURE_TO_SEND = string("structuretosend", false);
        BIOME_FLAG = integer("biomeflag", false);
        STRUCTURE_FLAG = integer("structureflag", false);

        for (int i = 1; i <= 12; i++) integer("gift" + i, true);
        for (int i = 1; i <= 12; i++) integer("holiday" + i, true);
    }

    private static AttachmentType<Integer> integer(String path, boolean copyOnDeath) {
        AttachmentType<Integer> type = AttachmentRegistry.create(id(path), builder -> {
            builder.initializer(() -> 0).persistent(Codec.INT);
            if (copyOnDeath) builder.copyOnDeath();
        });
        ALL.put(path, type);
        return type;
    }

    private static AttachmentType<String> string(String path, boolean copyOnDeath) {
        AttachmentType<String> type = AttachmentRegistry.create(id(path), builder -> {
            builder.initializer(() -> "").persistent(Codec.STRING);
            if (copyOnDeath) builder.copyOnDeath();
        });
        ALL.put(path, type);
        return type;
    }

    private static AttachmentType<Double> decimal(String path) {
        AttachmentType<Double> type = AttachmentRegistry.create(id(path), builder ->
                builder.initializer(() -> 0.0).persistent(Codec.DOUBLE));
        ALL.put(path, type);
        return type;
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, path);
    }
}
