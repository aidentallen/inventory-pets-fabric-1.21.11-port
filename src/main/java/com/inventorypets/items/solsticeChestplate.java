/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.equipment.ArmorMaterial
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package com.inventorypets.items;

import com.inventorypets.InventoryPets;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class solsticeChestplate
extends ArmorItem {
    public solsticeChestplate(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        if (this == InventoryPets.SOLSTICE_CHESTPLATE.get() && !EnchantmentHelper.hasAnyEnchantments((ItemStack)stack)) {
            ClientLevel level = Minecraft.getInstance().level;
            Registry reg = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            Holder fireprotection = reg.getHolder(Enchantments.FIRE_PROTECTION).orElse(null);
            Holder projectileprotection = reg.getHolder(Enchantments.PROJECTILE_PROTECTION).orElse(null);
            Holder thorns = reg.getHolder(Enchantments.THORNS).orElse(null);
            if (fireprotection != null && projectileprotection != null && thorns != null) {
                stack.enchant(fireprotection, 4);
                stack.enchant(projectileprotection, 4);
                stack.enchant(thorns, 4);
            }
        }
    }
}

