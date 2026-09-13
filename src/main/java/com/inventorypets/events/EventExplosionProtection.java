/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageTypes
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid="inventorypets")
public class EventExplosionProtection {
    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        ItemStack itemchk;
        int i;
        Player entityplayer;
        if (event.getEntity() instanceof Player && !((Boolean)InventoryPetsConfig.disableCreeper.get()).booleanValue()) {
            entityplayer = (Player)event.getEntity();
            if (!entityplayer.level().isClientSide && (event.getSource().toString().contains("explosion") || event.getSource().is(DamageTypes.EXPLOSION))) {
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_CREEPER.get() || itemchk.getDamageValue() >= 3) continue;
                    event.setCanceled(true);
                    break;
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            entityplayer = (Player)event.getEntity();
            if (!entityplayer.level().isClientSide && event.getSource().is(DamageTypes.FALL)) {
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableSheep.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_SHEEP.get() || itemchk.getDamageValue() != 0) continue;
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }
}

