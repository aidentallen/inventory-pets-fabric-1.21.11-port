/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.LivingEntity
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
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid="inventorypets")
public class KnockbackAttackHandler {
    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player) {
            Player entityplayer = (Player)event.getSource().getDirectEntity();
            if (!entityplayer.level().isClientSide) {
                ItemStack itemchk;
                ItemStack itemchk2;
                int i;
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk2 = entityplayer.getInventory().getItem(i);
                    if (itemchk2 == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableSnowGolem.get()).booleanValue() || itemchk2.getItem() != InventoryPets.PET_SNOW_GOLEM.get() || itemchk2.getDamageValue() != 0) continue;
                    int yaw = (int)entityplayer.getYRot();
                    if (yaw < 0) {
                        yaw += 360;
                    }
                    yaw += 22;
                    int facing = (yaw %= 360) / 45;
                    double xt = event.getEntity().getX();
                    double yt = event.getEntity().getY();
                    double zt = event.getEntity().getZ();
                    int dist = 3;
                    if (facing == 0) {
                        event.getEntity().moveTo(xt, yt + 2.0, zt + (double)dist);
                    } else if (facing == 1) {
                        event.getEntity().moveTo(xt - (double)dist, yt + 2.0, zt + (double)dist);
                    } else if (facing == 2) {
                        event.getEntity().moveTo(xt - (double)dist, yt + 2.0, zt);
                    } else if (facing == 3) {
                        event.getEntity().moveTo(xt - (double)dist, yt + 2.0, zt - (double)dist);
                    } else if (facing == 4) {
                        event.getEntity().moveTo(xt, yt + 2.0, zt - (double)dist);
                    } else if (facing == 5) {
                        event.getEntity().moveTo(xt + (double)dist, yt + 2.0, zt - (double)dist);
                    } else if (facing == 6) {
                        event.getEntity().moveTo(xt + (double)dist, yt + 2.0, zt);
                    } else if (facing == 7) {
                        event.getEntity().moveTo(xt + (double)dist, yt + 2.0, zt + (double)dist);
                    }
                    entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.knockback.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
                    i = 9;
                }
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk2 = entityplayer.getInventory().getItem(i);
                    if (itemchk2 == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableBlaze.get()).booleanValue() || itemchk2.getItem() != InventoryPets.PET_BLAZE.get() || itemchk2.getDamageValue() != 0) continue;
                    if (event.getEntity() instanceof LivingEntity) {
                        event.getEntity().setRemainingFireTicks(80);
                    }
                    i = 9;
                }
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk2 = entityplayer.getInventory().getItem(i);
                    if (itemchk2 == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disablePacMan.get()).booleanValue() || itemchk2.getItem() != InventoryPets.PET_PACMAN.get() || itemchk2.getDamageValue() != 0 || (Integer)entityplayer.getData(ModDataAttachments.POWERUP) != 1) continue;
                    event.setAmount(event.getAmount() * (float)((Integer)InventoryPetsConfig.pacManPowerupFactor.get()).intValue());
                    entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.pm_eatghost.get(), SoundSource.PLAYERS, 0.6f, 1.0f);
                }
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk2 = entityplayer.getInventory().getItem(i);
                    if (itemchk2 == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableQuiver.get()).booleanValue() && itemchk2.getItem() == InventoryPets.PET_QUIVER.get() && itemchk2.getDamageValue() == 0) continue;
                    if (!event.getSource().is(DamageTypes.MOB_PROJECTILE)) break;
                    event.setAmount(event.getAmount() * 1.5f);
                    break;
                }
                if ((itemchk = entityplayer.getInventory().getSelected()) != ItemStack.EMPTY && itemchk.getItem() == InventoryPets.SOLSTICE_SWORD.get() && event.getEntity() instanceof LivingEntity) {
                    int multiplier = (Integer)entityplayer.getData(ModDataAttachments.MULTIPLIER) + 1;
                    Long time = ((Double)entityplayer.getData(ModDataAttachments.TIME)).longValue();
                    long currenttime = System.currentTimeMillis();
                    long difftime = currenttime - time;
                    if (difftime < 3000L) {
                        event.setAmount(event.getAmount() + (float)(multiplier * 9));
                        Float multi = Float.valueOf(0.7f + (float)multiplier * 0.1f);
                        entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.combo.get(), SoundSource.PLAYERS, 0.5f, multi.floatValue());
                    } else if (difftime >= 3000L) {
                        multiplier = 0;
                    }
                    entityplayer.setData(ModDataAttachments.MULTIPLIER, (Object)multiplier);
                    entityplayer.setData(ModDataAttachments.TIME, (Object)currenttime);
                }
            }
        }
    }
}

