/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.RenderPlayerEvent$Pre
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid="inventorypets", value={Dist.CLIENT})
public class InvisibleHandler {
    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public static void turnInvisible(RenderPlayerEvent.Pre event) {
        Player entityplayer = event.getEntity();
        if (entityplayer != null) {
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = entityplayer.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableIlluminati.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_ILLUMINATI.get() || itemchk.getDamageValue() >= 3 || entityplayer.getEffect(MobEffects.INVISIBILITY) == null) continue;
                event.setCanceled(true);
                break;
            }
        }
    }
}

