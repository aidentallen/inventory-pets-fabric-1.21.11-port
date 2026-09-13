/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.common.SimpleTier
 */
package com.inventorypets.items;

import com.inventorypets.InventoryPets;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.SimpleTier;

public class solsticeSword
extends SwordItem {
    public solsticeSword(SimpleTier material, Item.Properties properties) {
        super((Tier)material, properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        if (this == InventoryPets.SOLSTICE_SWORD.get()) {
            RandomPoolAlias rand = new RandomPoolAlias();
            if (!EnchantmentHelper.hasAnyEnchantments((ItemStack)stack)) {
                ClientLevel level = Minecraft.getInstance().level;
                Registry reg = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
                Holder unbreaking = reg.getHolder(Enchantments.UNBREAKING).orElse(null);
                Holder sharpness = reg.getHolder(Enchantments.SHARPNESS).orElse(null);
                Holder smite = reg.getHolder(Enchantments.SMITE).orElse(null);
                if (unbreaking != null && sharpness != null && smite != null) {
                    stack.enchant(unbreaking, rand.nextInt(3) + 2);
                    stack.enchant(sharpness, rand.nextInt(3) + 2);
                    stack.enchant(smite, rand.nextInt(3) + 2);
                }
            }
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.solsticesword1", (Object[])new Object[0]))));
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.solsticesword2", (Object[])new Object[0]))));
        }
    }
}

