package com.inventorypets.fabric.network;

import com.inventorypets.fabric.item.InventoryPetItem;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public final class ModNetworking {
    private static final Set<String> BIOMES = Set.of(
            "minecraft:desert", "minecraft:plains", "minecraft:forest", "minecraft:jungle",
            "minecraft:badlands", "minecraft:swamp", "minecraft:cherry_grove", "minecraft:mushroom_fields",
            "minecraft:deep_dark", "minecraft:snowy_plains", "minecraft:savanna", "minecraft:ocean");
    private static final Set<String> STRUCTURES = Set.of(
            "minecraft:stronghold", "minecraft:village", "minecraft:ancient_city", "minecraft:trial_chambers",
            "minecraft:woodland_mansion", "minecraft:ocean_monument", "minecraft:fortress", "minecraft:end_city",
            "minecraft:pillager_outpost", "minecraft:mineshaft", "minecraft:buried_treasure", "minecraft:shipwreck");

    private ModNetworking() {
    }

    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(PacketPetNamer.TYPE, PacketPetNamer.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PacketBiomeFinder.TYPE, PacketBiomeFinder.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(PacketStructureFinder.TYPE, PacketStructureFinder.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PacketPetNamer.TYPE, (payload, context) -> {
            ItemStack held = selectedPet(context.player());
            String name = payload.petNamer().strip();
            if (held.isEmpty() || name.length() > 64) return;
            if (name.isEmpty()) held.remove(DataComponents.CUSTOM_NAME);
            else held.set(DataComponents.CUSTOM_NAME, Component.literal(name));
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketBiomeFinder.TYPE, (payload, context) -> {
            ItemStack held = selectedPet(context.player());
            if (!(held.getItem() instanceof InventoryPetItem pet)
                    || !pet.definition().id().equals("pet_biome")
                    || !BIOMES.contains(payload.searchBiome())) return;
            context.player().level().getServer().getCommands().performPrefixedCommand(
                    context.player().createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS),
                    "locate biome " + payload.searchBiome());
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketStructureFinder.TYPE, (payload, context) -> {
            ItemStack held = selectedPet(context.player());
            if (!(held.getItem() instanceof InventoryPetItem pet)
                    || !pet.definition().id().equals("pet_dingot")
                    || !STRUCTURES.contains(payload.searchStructure())) return;
            context.player().level().getServer().getCommands().performPrefixedCommand(
                    context.player().createCommandSourceStack().withPermission(PermissionSet.ALL_PERMISSIONS),
                    "locate structure " + payload.searchStructure());
        });
    }

    private static ItemStack selectedPet(net.minecraft.server.level.ServerPlayer player) {
        ItemStack main = player.getMainHandItem();
        if (main.getItem() instanceof InventoryPetItem) return main;
        ItemStack off = player.getOffhandItem();
        return off.getItem() instanceof InventoryPetItem ? off : ItemStack.EMPTY;
    }
}
