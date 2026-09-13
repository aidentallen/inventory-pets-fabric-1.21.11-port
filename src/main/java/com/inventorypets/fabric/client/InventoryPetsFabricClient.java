package com.inventorypets.fabric.client;

import com.inventorypets.fabric.InventoryPetsFabric;
import com.inventorypets.fabric.item.InventoryPetItem;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public final class InventoryPetsFabricClient implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, "controls"));

    @Override
    public void onInitializeClient() {
        KeyMapping openControls = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.inventorypets.pet_controls",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openControls.consumeClick()) openForHeldPet(client);
        });
    }

    private static void openForHeldPet(Minecraft client) {
        if (client.player == null) return;
                ItemStack stack = client.player.getMainHandItem();
                InventoryPetItem pet;
                if (stack.getItem() instanceof InventoryPetItem mainHandPet) {
                    pet = mainHandPet;
                } else {
                    stack = client.player.getOffhandItem();
                    if (!(stack.getItem() instanceof InventoryPetItem offHandPet)) return;
                    pet = offHandPet;
                }
        PetControlScreen.Mode mode = pet.definition().id().equals("pet_biome")
                ? PetControlScreen.Mode.BIOME
                : pet.definition().id().equals("pet_dingot")
                ? PetControlScreen.Mode.STRUCTURE
                : PetControlScreen.Mode.RENAME;
        var customName = stack.get(DataComponents.CUSTOM_NAME);
        client.setScreen(new PetControlScreen(mode, customName == null ? "" : customName.getString()));
    }
}
