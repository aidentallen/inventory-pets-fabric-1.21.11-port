/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodData
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.border.WorldBorder
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.networking.PacketTeleport;
import java.util.RandomPoolAlias;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid="inventorypets")
public class LifeSaveAttackHandler {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SubscribeEvent
    public static void notifyAttack(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        boolean chkFlg = false;
        Player entityplayer = (Player)event.getEntity();
        ServerPlayer serverPlayer = (ServerPlayer)entityplayer;
        if (entityplayer.level().isClientSide || chkFlg) return;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            float eph;
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableSlime.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_SLIME.get() && itemchk.getDamageValue() < 1) {
                float eph2;
                float dmg = event.getAmount();
                if (!(dmg >= (eph2 = entityplayer.getHealth()))) return;
                event.setAmount(0.0f);
                entityplayer.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 100, false, false));
                if (entityplayer.level().isClientSide) return;
                if (!((Boolean)InventoryPetsConfig.disableSlimeReviveSound.get()).booleanValue()) {
                    entityplayer.level().playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.slime_revive.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                }
                if (!((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) return;
                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                return;
            }
            if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableJuggernaut.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_JUGGERNAUT.get()) {
                if (event.getSource() != null && event.getSource().getDirectEntity() instanceof LivingEntity && itemchk.getDamageValue() < 3) {
                    ((LivingEntity)event.getSource().getDirectEntity()).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1, 400, true, true));
                }
                if ((Integer)entityplayer.getData(ModDataAttachments.SHIELD) == 1) {
                    event.setCanceled(true);
                }
            }
            if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableEnderman.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_ENDERMAN.get() || itemchk.getDamageValue() >= 3 || entityplayer.level().isClientSide || ((Boolean)InventoryPetsConfig.disableEndermanAutoTeleport.get()).booleanValue()) continue;
            Level worldIn = entityplayer.level();
            float dmg = event.getAmount();
            if (dmg >= (eph = entityplayer.getHealth()) && (double)eph > 0.5) {
                int chk;
                event.setAmount(0.0f);
                entityplayer.setHealth(0.5f);
                FoodData param = entityplayer.getFoodData();
                if (param != null && (chk = param.getFoodLevel()) > 10) {
                    param.setFoodLevel(10);
                }
                RandomPoolAlias rand = new RandomPoolAlias();
                int facing = rand.nextInt(8);
                double px = entityplayer.getX();
                double py = entityplayer.getY();
                double pz = entityplayer.getZ();
                int px1 = (int)entityplayer.getX();
                int py1 = (int)entityplayer.getY();
                int pz1 = (int)entityplayer.getZ();
                int dist = (int)(Math.random() * 25.0 + 5.0) * -1;
                int widst = (int)(Math.random() * 4.0 + 1.0);
                int polarst = (int)(Math.random() * 2.0);
                int v1cap = 20;
                if (polarst == 1) {
                    widst *= -1;
                }
                WorldBorder wb = entityplayer.level().getWorldBorder();
                if (!worldIn.dimensionType().natural()) {
                    v1cap = 5;
                }
                int v1 = 0;
                if (facing == 0) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 + widst, py1 + v1, pz1 + dist + v1)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 + widst, py1 + v1 + 1, pz1 + dist + v1)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 + widst, py1 + v1 + 1, pz1 + dist + v1))) {
                            entityplayer.moveTo((double)(px1 + widst), (double)(py1 + v1), (double)(pz1 + dist + v1));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + widst, py1 + v1, pz1 + dist + v1), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = 20;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 1) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 - dist - v1 + widst, py1 + v1, pz1 + dist + v1 + widst)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 - dist - v1 + widst, py1 + v1 + 1, pz1 + dist + v1 + widst)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 - dist - v1 + widst, py1 + v1 + 1, pz1 + dist + v1 + widst))) {
                            entityplayer.moveTo((double)(px1 - dist - v1 + widst), (double)(py1 + v1), (double)(pz1 + dist + v1 + widst));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 - dist - v1 + widst, py1 + v1, pz1 + dist + v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 2) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 - dist - v1, py1 + v1, pz1 + widst)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 - dist - v1, py1 + v1 + 1, pz1 + widst)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 - dist - v1, py1 + v1 + 1, pz1 + widst))) {
                            entityplayer.moveTo((double)(px1 - dist - v1), (double)(py1 + v1), (double)(pz1 + widst));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 - dist - v1, py1 + v1, pz1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 3) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 - dist - v1 + widst, py1 + v1, pz1 - dist - v1 + widst)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 - dist - v1 + widst, py1 + v1 + 1, pz1 - dist - v1 + widst)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 - dist - v1 + widst, py1 + v1 + 1, pz1 - dist - v1 + widst))) {
                            entityplayer.moveTo((double)(px1 - dist - v1), (double)(py1 + v1), (double)(pz1 - dist - v1));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 - dist - v1 + widst, py1 + v1, pz1 - dist - v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 4) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 + widst, py1 + v1, pz1 - dist - v1)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 + widst, py1 + v1 + 1, pz1 - dist - v1)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 + widst, py1 + v1 + 1, pz1 - dist - v1))) {
                            entityplayer.moveTo((double)(px1 + widst), (double)(py1 + v1), (double)(pz1 - dist - v1));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + widst, py1 + v1, pz1 - dist - v1), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 5) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 + dist + v1 + widst, py1 + v1, pz1 - dist - v1 + widst)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 + dist + v1 + widst, py1 + v1 + 1, pz1 - dist - v1 + widst)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 + dist + v1 + widst, py1 + v1 + 1, pz1 - dist - v1 + widst))) {
                            entityplayer.moveTo((double)(px1 + dist + v1), (double)(py1 + v1), (double)(pz1 - dist - v1));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + dist + v1 + widst, py1 + v1, pz1 - dist - v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 6) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 + dist + v1, py1 + v1, pz1 + widst)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 + dist + v1, py1 + v1 + 1, pz1 + widst)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 + dist + v1, py1 + v1 + 1, pz1 + widst))) {
                            entityplayer.moveTo((double)(px1 + dist + v1), (double)(py1 + v1), (double)(pz1 + widst));
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + dist + v1, py1 + v1, pz1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                } else if (facing == 7) {
                    for (v1 = 0; v1 < v1cap; ++v1) {
                        if (worldIn.getBlockState(new BlockPos(px1 + dist + v1 + widst, py1 + v1, pz1 + dist + v1 + widst)).getBlock() == Blocks.AIR && worldIn.getBlockState(new BlockPos(px1 + dist + v1 + widst, py1 + v1 + 1, pz1 + dist + v1 + widst)).getBlock() == Blocks.AIR && wb.isWithinBounds(new BlockPos(px1 + dist + v1 + widst, py1 + v1 + 1, pz1 + dist + v1 + widst))) {
                            entityplayer.moveTo(px + (double)dist + (double)v1, py + (double)v1, pz + (double)dist + (double)v1);
                            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketTeleport(px1 + dist + v1 + widst, py1 + v1, pz1 + dist + v1 + widst), (CustomPacketPayload[])new CustomPacketPayload[0]);
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                                itemchk.setDamageValue(itemchk.getDamageValue() + 1);
                            }
                            v1 = v1cap;
                            continue;
                        }
                        if (v1 != v1cap - 1) continue;
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    }
                }
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.zap.get(), SoundSource.PLAYERS, 1.8f, 1.4f);
            }
            i = 9;
        }
    }
}

