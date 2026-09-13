package com.inventorypets.fabric.init;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inventorypets.fabric.InventoryPetsFabric;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModSounds {
    public static final Map<String, SoundEvent> ALL = new LinkedHashMap<>();

    private ModSounds() {
    }

    public static void initialize() {
        try (var stream = ModSounds.class.getResourceAsStream("/assets/inventorypets/sounds.json")) {
            if (stream == null) throw new IllegalStateException("Missing required Inventory Pets sounds.json");
            JsonObject sounds = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
            for (String path : sounds.keySet()) {
                Identifier id = Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, path);
                SoundEvent event = SoundEvent.createVariableRangeEvent(id);
                Registry.register(BuiltInRegistries.SOUND_EVENT, id, event);
                ALL.put(path, event);
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to register Inventory Pets sounds", exception);
        }
    }
}
