/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Util
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.equipment.ArmorMaterial
 *  net.minecraft.world.item.ArmorMaterial$Layer
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package com.inventorypets.init;

import com.inventorypets.InventoryPets;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ModArmorMaterial {
    public static final Holder<ArmorMaterial> SOLSTICE = ModArmorMaterial.register("solstice", () -> new ArmorMaterial((Map)Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.BODY, 10);
    }), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of((ItemLike[])new ItemLike[]{InventoryPets.XEROX_PARC_GUI}), List.of((Object)new ArmorMaterial.Layer(Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"solstice"))), 3.0f, 0.1f));
    public static final Holder<ArmorMaterial> PATREON = ModArmorMaterial.register("patreon", () -> new ArmorMaterial((Map)Util.make(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 7);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.BODY, 10);
    }), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of((ItemLike[])new ItemLike[]{InventoryPets.HOLIDAY_COOKIE}), List.of((Object)new ArmorMaterial.Layer(Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"patreon"))), 0.0f, 0.1f));

    public static Holder<ArmorMaterial> register(String name, Supplier<ArmorMaterial> materialSupplier) {
        return Registry.registerForHolder((Registry)BuiltInRegistries.ARMOR_MATERIAL, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)name), (Object)materialSupplier.get());
    }
}

