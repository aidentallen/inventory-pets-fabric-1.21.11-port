package com.inventorypets.fabric.client;

import com.inventorypets.fabric.network.PacketBiomeFinder;
import com.inventorypets.fabric.network.PacketPetNamer;
import com.inventorypets.fabric.network.PacketStructureFinder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class PetControlScreen extends Screen {
    enum Mode { RENAME, BIOME, STRUCTURE }

    private static final String[] BIOMES = {
            "minecraft:desert", "minecraft:plains", "minecraft:forest", "minecraft:jungle",
            "minecraft:badlands", "minecraft:swamp", "minecraft:cherry_grove", "minecraft:mushroom_fields",
            "minecraft:deep_dark", "minecraft:snowy_plains", "minecraft:savanna", "minecraft:ocean"
    };
    private static final String[] STRUCTURES = {
            "minecraft:stronghold", "minecraft:village", "minecraft:ancient_city", "minecraft:trial_chambers",
            "minecraft:woodland_mansion", "minecraft:ocean_monument", "minecraft:fortress", "minecraft:end_city",
            "minecraft:pillager_outpost", "minecraft:mineshaft", "minecraft:buried_treasure", "minecraft:shipwreck"
    };

    private final Mode mode;
    private final String currentName;
    private EditBox nameBox;

    PetControlScreen(Mode mode, String currentName) {
        super(Component.literal(mode == Mode.RENAME ? "Pet Namer" : mode == Mode.BIOME ? "Biome Finder" : "Structure Finder"));
        this.mode = mode;
        this.currentName = currentName;
    }

    @Override
    protected void init() {
        if (mode == Mode.RENAME) {
            nameBox = new EditBox(font, width / 2 - 100, height / 2 - 35, 200, 20, Component.literal("Pet name"));
            nameBox.setMaxLength(64);
            nameBox.setValue(currentName);
            addRenderableWidget(nameBox);
            addRenderableWidget(Button.builder(Component.literal("Save"), button -> {
                ClientPlayNetworking.send(new PacketPetNamer(nameBox.getValue()));
                onClose();
            }).bounds(width / 2 - 60, height / 2, 120, 20).build());
            setInitialFocus(nameBox);
            return;
        }

        String[] entries = mode == Mode.BIOME ? BIOMES : STRUCTURES;
        for (int i = 0; i < entries.length; i++) {
            String id = entries[i];
            int column = i % 3;
            int row = i / 3;
            String label = id.substring(id.indexOf(':') + 1).replace('_', ' ');
            addRenderableWidget(Button.builder(Component.literal(label), button -> {
                if (mode == Mode.BIOME) ClientPlayNetworking.send(new PacketBiomeFinder(id));
                else ClientPlayNetworking.send(new PacketStructureFinder(id));
                onClose();
            }).bounds(width / 2 - 153 + column * 103, height / 2 - 55 + row * 24, 100, 20).build());
        }
    }

    @Override
    public void onClose() {
        if (minecraft != null) minecraft.setScreen(null);
    }
}
