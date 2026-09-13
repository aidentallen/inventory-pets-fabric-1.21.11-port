/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.monster.skeleton.Skeleton
 *  net.minecraft.world.entity.monster.Slime
 *  net.minecraft.world.entity.monster.zombie.Zombie
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
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid="inventorypets")
public class WallDamageHandler {
    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getSource().is(DamageTypes.IN_WALL)) {
            ItemStack itemchk;
            int i;
            Player entityplayer = (Player)event.getEntity();
            if (!entityplayer.level().isClientSide && !((Boolean)InventoryPetsConfig.disableSilverfish.get()).booleanValue()) {
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_SILVERFISH.get() || itemchk.getDamageValue() != 0) continue;
                    event.setCanceled(true);
                    break;
                }
            }
            if (!entityplayer.level().isClientSide && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue()) {
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_FLYING_SADDLE.get() || !entityplayer.isPassenger() || itemchk.getDamageValue() != 0) continue;
                    event.setCanceled(true);
                    break;
                }
            }
        } else if (event.getEntity() instanceof Player && event.getSource().getDirectEntity() instanceof Slime) {
            Player entityplayer = (Player)event.getEntity();
            Slime slime = (Slime)event.getSource().getDirectEntity();
            if (slime.isVehicle() && slime.getPassengers().get(0) == entityplayer) {
                event.setCanceled(true);
            }
        } else if ((event.getEntity() instanceof Zombie || event.getEntity() instanceof Skeleton) && event.getEntity().isVehicle() && event.getSource().is(DamageTypes.ON_FIRE)) {
            event.setCanceled(true);
        }
    }
}

