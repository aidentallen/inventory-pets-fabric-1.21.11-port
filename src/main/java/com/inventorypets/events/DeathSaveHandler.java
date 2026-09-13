/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid="inventorypets")
public class DeathSaveHandler {
    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player entityplayer = (Player)event.getEntity();
            if (!entityplayer.level().isClientSide && !((Boolean)InventoryPetsConfig.disableSlime.get()).booleanValue()) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_SLIME.get() || itemchk.getDamageValue() >= 1) continue;
                    if (!(event.getAmount() > entityplayer.getHealth())) break;
                    event.setAmount(0.0f);
                    entityplayer.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 100));
                    if (entityplayer.level().isClientSide) break;
                    if (!((Boolean)InventoryPetsConfig.disableSlimeReviveSound.get()).booleanValue()) {
                        entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.slime_revive.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    }
                    if (!((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) break;
                    itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                    break;
                }
            }
        }
    }
}

