/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid="inventorypets")
public class LifestealAttackHandler {
    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player) {
            Player entityplayer = (Player)event.getSource().getDirectEntity();
            float eph = entityplayer.getHealth();
            if (!entityplayer.level().isClientSide) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableWither.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_WITHER.get() || itemchk.getDamageValue() != 0) continue;
                    float lifesteal = event.getAmount() / 2.0f;
                    entityplayer.setHealth(eph += lifesteal);
                    entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.lifesteal.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
                    i = 9;
                }
            }
        }
    }
}

