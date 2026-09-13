/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
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
import com.inventorypets.entities.MiniQuantumBlazeEntity;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid="inventorypets")
public class PufferfishAttackHandler {
    private boolean poisonFlag = false;

    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        Player entityplayer;
        if (event.getEntity() instanceof Player) {
            entityplayer = (Player)event.getEntity();
            if (event.getSource().getDirectEntity() instanceof LivingEntity) {
                LivingEntity entityLivingBase = (LivingEntity)event.getSource().getDirectEntity();
                if (!entityplayer.level().isClientSide && entityLivingBase != null && entityLivingBase instanceof LivingEntity && !((Boolean)InventoryPetsConfig.disablePufferfish.get()).booleanValue()) {
                    for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                        ItemStack itemchk = entityplayer.getInventory().getItem(i);
                        if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_PUFFERFISH.get() || itemchk.getDamageValue() != 0) continue;
                        entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.venom_inject.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
                        if (entityLivingBase != null && entityLivingBase instanceof LivingEntity) {
                            entityLivingBase.hurt(entityplayer.level().damageSources().generic(), event.getAmount() / 2.0f);
                            entityLivingBase.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 3));
                        }
                        i = 9;
                    }
                }
            }
        }
        if (event.getSource().getDirectEntity() instanceof Player && !((Boolean)InventoryPetsConfig.disablePufferfish.get()).booleanValue()) {
            entityplayer = (Player)event.getSource().getDirectEntity();
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = entityplayer.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_PUFFERFISH.get() || itemchk.getDamageValue() != 0) continue;
                LivingEntity entityLivingBase = event.getEntity();
                entityLivingBase.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 3));
            }
        }
        if (event.getEntity() instanceof Player && !((Boolean)InventoryPetsConfig.disableQuantumCrystalMonster.get()).booleanValue()) {
            entityplayer = (Player)event.getEntity();
            if (!entityplayer.level().isClientSide && event.getSource().getDirectEntity() instanceof MiniQuantumBlazeEntity) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_QCM.get() || itemchk.getDamageValue() >= 2) continue;
                    event.setCanceled(true);
                    entityplayer.clearFire();
                }
            }
        }
    }
}

