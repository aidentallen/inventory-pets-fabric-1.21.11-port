package com.inventorypets.fabric.item;

import com.inventorypets.fabric.InventoryPetsFabric;
import com.inventorypets.fabric.pet.PetDefinition;
import com.inventorypets.fabric.pet.PetDefinitions;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {
    public static final Map<String, Item> ITEMS = new LinkedHashMap<>();
    public static Item FEED_BAG;
    public static CreativeModeTab CREATIVE_TAB;

    private static final String[] NUGGETS = {
            "nugget_diamond", "nugget_obsidian", "nugget_coal", "nugget_lapis", "nugget_ender",
            "nugget_emerald", "nugget_netherite"
    };
    private static final String[] SPECIAL_SUPPORT_ITEMS = {
            "siamese_gift", "holiday_gift", "banana", "holiday_cookie", "egg_nog", "candy_cane", "rock_candy"
    };

    private ModItems() {
    }

    public static void initialize() {
        for (PetDefinition definition : PetDefinitions.ALL.values()) {
            register(definition.id(), properties -> new InventoryPetItem(definition, properties), new Item.Properties().stacksTo(1));
        }
        for (String id : NUGGETS) register(id, Item::new, new Item.Properties());
        for (String id : SPECIAL_SUPPORT_ITEMS) {
            register(id, properties -> new SupportItem(id, properties), new Item.Properties().stacksTo(16));
        }
        FEED_BAG = register("feed_bag", FeedBagItem::new, new Item.Properties().stacksTo(1));

        CREATIVE_TAB = Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, "inventorypets"),
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.inventorypets"))
                        .icon(() -> new ItemStack(ITEMS.get("pet_blaze")))
                        .displayItems((parameters, output) -> ITEMS.values().forEach(output::accept))
                        .build());
    }

    public static Item item(String path) {
        return ITEMS.get(path);
    }

    public static boolean hasPet(net.minecraft.world.entity.player.Player player, String path) {
        Item pet = ITEMS.get(path);
        if (pet == null) return false;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).is(pet)) return true;
        }
        return false;
    }

    private static Item register(String path, java.util.function.Function<Item.Properties, Item> factory, Item.Properties properties) {
        Identifier id = Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, path);
        ResourceKey<Item> key = ResourceKey.create(net.minecraft.core.registries.Registries.ITEM, id);
        Item item = factory.apply(properties.setId(key));
        Registry.register(BuiltInRegistries.ITEM, key, item);
        ITEMS.put(path, item);
        return item;
    }
}
