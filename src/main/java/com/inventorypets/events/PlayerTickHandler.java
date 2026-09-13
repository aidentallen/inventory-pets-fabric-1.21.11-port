/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.util.Mth
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.monster.skeleton.Skeleton
 *  net.minecraft.world.entity.monster.zombie.Zombie
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.tick.EntityTickEvent$Post
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.handler.PatreonHandler;
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.networking.PacketKeyInput;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.RandomPoolAlias;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid="inventorypets")
public class PlayerTickHandler {
    private static int delay = 0;
    private static int slotSpider;
    private static int slotSaddle;
    private static int slotFlyingSaddle;
    private static int slotSquid;
    private static int slotOcelot;
    private static int slotEnderman;
    private static int slotNetherPortal;
    private static int slotMagmaCube;
    private static int slotSponge;
    private static int slotCloud;

    @SubscribeEvent
    public static void onLivingUpdateEvent(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player) {
            Player entityplayer = (Player)event.getEntity();
            Level worldIn = entityplayer.level();
            if (++delay > 300) {
                delay = 0;
                boolean patreonFlag = false;
                patreonFlag = PatreonHandler.isPlayerPatreon(entityplayer);
                if (entityplayer.isCreative()) {
                    patreonFlag = true;
                }
                if (!patreonFlag && entityplayer.getInventory().getArmor(2).getItem() != null && entityplayer.getInventory().getArmor(2).getItem() == InventoryPets.PATREON_CHESTPLATE.get()) {
                    entityplayer.getInventory().add(entityplayer.getInventory().getArmor(2));
                    PlayerTickHandler.removeItem(entityplayer, entityplayer.getInventory().getArmor(2));
                }
                if (!patreonFlag && entityplayer.getInventory().getArmor(3).getItem() != null && entityplayer.getInventory().getArmor(3).getItem() == InventoryPets.PATREON_HELMET.get()) {
                    entityplayer.getInventory().add(entityplayer.getInventory().getArmor(3));
                    PlayerTickHandler.removeItem(entityplayer, entityplayer.getInventory().getArmor(3));
                }
            }
            slotSpider = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_SPIDER.get(), 1);
            slotSaddle = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_SADDLE.get(), 3);
            slotFlyingSaddle = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_FLYING_SADDLE.get(), 3);
            slotSquid = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_SQUID.get(), 1);
            slotOcelot = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_OCELOT.get(), 1);
            slotEnderman = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_ENDERMAN.get(), 3);
            slotNetherPortal = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_NETHER_PORTAL.get(), 2);
            slotMagmaCube = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_MAGMA_CUBE.get(), 1);
            slotSponge = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_SPONGE.get(), 2);
            slotCloud = ItemHelper.getPetSlot(entityplayer, (Item)InventoryPets.PET_CLOUD.get(), 3);
            if (entityplayer.horizontalCollision && !((Boolean)InventoryPetsConfig.disableSpider.get()).booleanValue() && slotSpider != -1) {
                double motX = entityplayer.getDeltaMovement().x();
                entityplayer.getDeltaMovement();
                double motZ = entityplayer.getDeltaMovement().z();
                entityplayer.fallDistance = 0.0f;
                if (entityplayer.isCrouching()) {
                    entityplayer.lerpMotion(motX, 0.0, motZ);
                } else {
                    entityplayer.lerpMotion(motX, 0.1976, motZ);
                }
            }
            if (entityplayer.isPassenger() && entityplayer.level().isClientSide && entityplayer.getVehicle() instanceof LivingEntity && (slotSaddle >= 0 || slotFlyingSaddle >= 0)) {
                ItemStack itemchk2 = entityplayer.getMainHandItem();
                ItemStack offchk = entityplayer.getOffhandItem();
                long WINDOW = Minecraft.getInstance().getWindow().getWindow();
                if (itemchk2 != ItemStack.EMPTY && (itemchk2.getItem() == InventoryPets.PET_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue() || itemchk2.getItem() == InventoryPets.PET_FLYING_SADDLE.get() & (Boolean)InventoryPetsConfig.disableFlyingSaddle.get() == false) && itemchk2.getDamageValue() < 3) {
                    if (InputConstants.isKeyDown((long)WINDOW, (int)341) && !(Minecraft.getInstance().screen instanceof Screen) && entityplayer instanceof LocalPlayer) {
                        PacketDistributor.sendToServer((CustomPacketPayload)new PacketKeyInput(2), (CustomPacketPayload[])new CustomPacketPayload[0]);
                    } else if (InputConstants.isKeyDown((long)WINDOW, (int)32) && !(Minecraft.getInstance().screen instanceof Screen) && entityplayer instanceof LocalPlayer) {
                        PacketDistributor.sendToServer((CustomPacketPayload)new PacketKeyInput(1), (CustomPacketPayload[])new CustomPacketPayload[0]);
                    } else if (InputConstants.isKeyDown((long)WINDOW, (int)340) && !(Minecraft.getInstance().screen instanceof Screen)) {
                        entityplayer.stopRiding();
                    }
                    if ((entityplayer.getVehicle() instanceof Skeleton || entityplayer.getVehicle() instanceof Zombie) && entityplayer.getVehicle().isOnFire()) {
                        entityplayer.getVehicle().clearFire();
                    }
                } else if (offchk != ItemStack.EMPTY && (offchk.getItem() == InventoryPets.PET_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue() || offchk.getItem() == InventoryPets.PET_FLYING_SADDLE.get() && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue()) && offchk.getDamageValue() < 3) {
                    if (InputConstants.isKeyDown((long)WINDOW, (int)341) && !(Minecraft.getInstance().screen instanceof Screen) && entityplayer instanceof LocalPlayer) {
                        PacketDistributor.sendToServer((CustomPacketPayload)new PacketKeyInput(2), (CustomPacketPayload[])new CustomPacketPayload[0]);
                    } else if (InputConstants.isKeyDown((long)WINDOW, (int)32) && !(Minecraft.getInstance().screen instanceof Screen) && entityplayer instanceof LocalPlayer) {
                        PacketDistributor.sendToServer((CustomPacketPayload)new PacketKeyInput(1), (CustomPacketPayload[])new CustomPacketPayload[0]);
                    } else if (InputConstants.isKeyDown((long)WINDOW, (int)340) && !(Minecraft.getInstance().screen instanceof Screen)) {
                        entityplayer.stopRiding();
                    }
                    if ((entityplayer.getVehicle() instanceof Skeleton || entityplayer.getVehicle() instanceof Zombie) && entityplayer.getVehicle().isOnFire()) {
                        entityplayer.getVehicle().clearFire();
                    }
                }
            }
            if (entityplayer.isInWater() && (slotSquid >= 0 || slotOcelot >= 0)) {
                boolean flipFlag = false;
                ItemStack bootChk = entityplayer.getInventory().getArmor(0);
                if (!bootChk.isEmpty() && bootChk.getDescriptionId().toString().toLowerCase().contains("flipper")) {
                    flipFlag = true;
                }
                ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
                for (int s = 0; s <= ItemHelper.getHotbarSize() - 1; ++s) {
                    hotbar[s] = entityplayer.getInventory().getItem(s);
                    if (hotbar[s].isEmpty() || !hotbar[s].getDisplayName().toString().toLowerCase().contains("sling")) continue;
                    flipFlag = true;
                }
                if (!((Boolean)InventoryPetsConfig.disableSquid.get()).booleanValue() && slotSquid >= 0) {
                    entityplayer.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 1, false, false));
                    entityplayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 410, 5, false, false));
                    double motX = entityplayer.getDeltaMovement().x();
                    double motY = entityplayer.getDeltaMovement().y();
                    double motZ = entityplayer.getDeltaMovement().z();
                    if (!(entityplayer.getAbilities().flying || flipFlag || ((Boolean)InventoryPetsConfig.disableSquidSpeed.get()).booleanValue() || entityplayer.isSprinting())) {
                        if (entityplayer.isCrouching() && (entityplayer.getDeltaMovement().y() <= -0.01 || entityplayer.getDeltaMovement().y() >= 0.01)) {
                            entityplayer.move(MoverType.PLAYER, new Vec3(entityplayer.getDeltaMovement().x(), entityplayer.getDeltaMovement().y(), entityplayer.getDeltaMovement().z()));
                            entityplayer.lerpMotion(motX *= 1.18, motY *= 1.18, motZ *= 1.18);
                        } else {
                            entityplayer.move(MoverType.PLAYER, new Vec3(entityplayer.getDeltaMovement().x(), entityplayer.getDeltaMovement().y(), entityplayer.getDeltaMovement().z()));
                            entityplayer.lerpMotion(motX *= 1.18, motY *= 1.04, motZ *= 1.18);
                        }
                    }
                }
            } else if (!entityplayer.isInWater() && !((Boolean)InventoryPetsConfig.disableOcelot.get()).booleanValue() && slotOcelot >= 0) {
                entityplayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 410, 5, false, false));
            }
            if (entityplayer.isInWall() && !((Boolean)InventoryPetsConfig.disableEnderman.get()).booleanValue() && slotEnderman >= 0) {
                int x1 = Mth.floor((double)entityplayer.getX());
                int y1 = Mth.floor((double)entityplayer.getY());
                int z1 = Mth.floor((double)entityplayer.getZ());
                double motX = entityplayer.getDeltaMovement().x();
                entityplayer.getDeltaMovement();
                double motZ = entityplayer.getDeltaMovement().z();
                for (int v1 = 1; v1 < 100; ++v1) {
                    if (entityplayer.level().getBlockState(new BlockPos(x1, y1 + v1 + 2, z1)).getBlock() != Blocks.AIR || entityplayer.level().getBlockState(new BlockPos(x1, y1 + v1 + 1, z1)).getBlock() != Blocks.AIR || entityplayer.level().getBlockState(new BlockPos(x1, y1 + v1, z1)).getBlock() == Blocks.AIR) continue;
                    entityplayer.moveTo((double)x1, (double)(y1 + v1 + 1), (double)z1);
                    entityplayer.fallDistance = 0.0f;
                    entityplayer.lerpMotion(motX, (double)0.1f, motZ);
                    break;
                }
            }
            if (entityplayer.hasEffect(MobEffects.NIGHT_VISION) && !worldIn.isClientSide && (!((Boolean)InventoryPetsConfig.disableOcelot.get()).booleanValue() && slotOcelot >= 0 || !((Boolean)InventoryPetsConfig.disableSquid.get()).booleanValue() && slotSquid >= 0)) {
                int nvchk;
                boolean chkFlag = false;
                int chkAmp = entityplayer.getEffect(MobEffects.NIGHT_VISION).getAmplifier();
                if (slotOcelot >= 0 && !entityplayer.isInWater() || slotSquid >= 0 && entityplayer.isInWater()) {
                    chkFlag = true;
                    entityplayer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 410, 5, false, false));
                }
                if (!chkFlag && entityplayer.hasEffect(MobEffects.NIGHT_VISION) && chkAmp == 5 && (nvchk = entityplayer.getEffect(MobEffects.NIGHT_VISION).getDuration()) < 412) {
                    entityplayer.removeEffect(MobEffects.NIGHT_VISION);
                }
            }
            if (entityplayer.isInWall() && !entityplayer.level().dimensionType().natural() && !((Boolean)InventoryPetsConfig.disableNetherPortal.get()).booleanValue() && slotNetherPortal >= 0) {
                int px1 = (int)entityplayer.getX();
                int pz1 = (int)entityplayer.getZ();
                RandomPoolAlias rand = new RandomPoolAlias();
                int ord = rand.nextInt(2);
                if (ord == 0) {
                    ord = -1;
                }
                entityplayer.fallDistance = 0.0f;
                for (int v2 = 117; v2 > 34; --v2) {
                    if (entityplayer.level().getBlockState(new BlockPos(px1 + v2 * ord, v2, pz1 + v2 * ord)).getBlock() != Blocks.AIR || entityplayer.level().getBlockState(new BlockPos(px1 + v2 * ord, v2 + 1, pz1 + v2 * ord)).getBlock() != Blocks.AIR) continue;
                    entityplayer.moveTo((double)(px1 + v2 * ord), (double)v2, (double)(pz1 + v2 * ord));
                    entityplayer.fallDistance = 0.0f;
                }
            }
            if (!((Boolean)InventoryPetsConfig.disableMagmaCube.get()).booleanValue() && slotMagmaCube >= 0 || !((Boolean)InventoryPetsConfig.disableSponge.get()).booleanValue() && slotSponge >= 0) {
                Block lavaChk2 = entityplayer.level().getBlockState(new BlockPos(Mth.floor((double)entityplayer.getX()), Mth.floor((double)entityplayer.getY()) - 1, Mth.floor((double)entityplayer.getZ()))).getBlock();
                boolean jumpFlag = false;
                if (lavaChk2 != null && lavaChk2.getDescriptionId().toString().contains("block.minecraft.lava") && entityplayer.level().isClientSide && !entityplayer.isCrouching()) {
                    double motX = entityplayer.getDeltaMovement().x();
                    entityplayer.getDeltaMovement();
                    double motZ = entityplayer.getDeltaMovement().z();
                    if (!((Boolean)InventoryPetsConfig.disableMagmaCube.get()).booleanValue() && slotMagmaCube >= 0 && !jumpFlag) {
                        entityplayer.setPos(entityplayer.getX(), entityplayer.getY(), entityplayer.getZ());
                        entityplayer.lerpMotion(motX, 0.0, motZ);
                        entityplayer.fallDistance = 0.0f;
                    }
                    jumpFlag = false;
                    jumpFlag = PlayerTickHandler.playerJump(entityplayer);
                    if (jumpFlag && !(Minecraft.getInstance().screen instanceof Screen) && slotMagmaCube >= 0) {
                        entityplayer.jumpFromGround();
                    }
                } else if (lavaChk2 != null && (lavaChk2.getDescriptionId().toString().contains("block.minecraft.water") || lavaChk2.getDescriptionId().toString().contains("seagrass")) && entityplayer.level().isClientSide && !entityplayer.isCrouching()) {
                    double motX = entityplayer.getDeltaMovement().x();
                    entityplayer.getDeltaMovement();
                    double motZ = entityplayer.getDeltaMovement().z();
                    if (!((Boolean)InventoryPetsConfig.disableSponge.get()).booleanValue() && slotSponge >= 0 && !jumpFlag) {
                        entityplayer.moveTo(entityplayer.getX(), entityplayer.getY(), entityplayer.getZ());
                        entityplayer.lerpMotion(motX, 0.0, motZ);
                        entityplayer.fallDistance = 0.0f;
                    }
                    jumpFlag = false;
                    jumpFlag = PlayerTickHandler.playerJump(entityplayer);
                    if (jumpFlag && !(Minecraft.getInstance().screen instanceof Screen) && slotSponge >= 0) {
                        entityplayer.jumpFromGround();
                    }
                }
            }
            if (!(slotCloud < 0 || ((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() || ((Boolean)InventoryPetsConfig.disableCloudFly.get()).booleanValue() || entityplayer.getAbilities().mayfly)) {
                entityplayer.getAbilities().mayfly = true;
                entityplayer.onUpdateAbilities();
                if ((Integer)entityplayer.getData(ModDataAttachments.CLOUD_FLIGHT) == 0) {
                    entityplayer.setData(ModDataAttachments.CLOUD_FLIGHT, (Object)1);
                }
            }
            if (entityplayer.getAbilities().mayfly) {
                boolean shouldStopFlying = true;
                if (entityplayer.isCreative() || entityplayer.isSpectator()) {
                    shouldStopFlying = false;
                } else if (slotCloud >= 0 && !((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() && !((Boolean)InventoryPetsConfig.disableCloudFly.get()).booleanValue()) {
                    shouldStopFlying = false;
                } else if (slotCloud < 0) {
                    if (entityplayer.onGround()) {
                        entityplayer.getAbilities().flying = false;
                        entityplayer.onUpdateAbilities();
                    }
                    if (shouldStopFlying && (Integer)entityplayer.getData(ModDataAttachments.CLOUD_FLIGHT) == 1) {
                        entityplayer.setData(ModDataAttachments.CLOUD_FLIGHT, (Object)0);
                        entityplayer.getAbilities().mayfly = false;
                        entityplayer.getAbilities().flying = false;
                        entityplayer.onUpdateAbilities();
                    }
                }
            }
            if (entityplayer.getAbilities().flying && slotCloud >= 0 && !((Boolean)InventoryPetsConfig.disableCloudFly.get()).booleanValue()) {
                double speedFactor = 1.9;
                double motX = entityplayer.getDeltaMovement().x();
                double motY = entityplayer.getDeltaMovement().y();
                double motZ = entityplayer.getDeltaMovement().z();
                if (!((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() && (Integer)InventoryPetsConfig.cloudFlySpeed.get() == 1) {
                    speedFactor = 1.0;
                } else if (!((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() && (Integer)InventoryPetsConfig.cloudFlySpeed.get() == 2) {
                    speedFactor = 1.2;
                } else if (!((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() && (Integer)InventoryPetsConfig.cloudFlySpeed.get() == 4) {
                    speedFactor = 2.2;
                }
                if (!((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() && !entityplayer.isInWater() && Math.abs(entityplayer.getDeltaMovement().x()) < speedFactor && Math.abs(entityplayer.getDeltaMovement().z()) < speedFactor && !entityplayer.isCrouching() && !entityplayer.verticalCollision) {
                    speedFactor = 1.02;
                    if ((Integer)InventoryPetsConfig.cloudFlySpeed.get() == 1) {
                        speedFactor = 0.55;
                    } else if ((Integer)InventoryPetsConfig.cloudFlySpeed.get() == 2) {
                        speedFactor = 0.8;
                    } else if ((Integer)InventoryPetsConfig.cloudFlySpeed.get() == 4) {
                        speedFactor = 1.05;
                    }
                    entityplayer.move(MoverType.PLAYER, new Vec3(entityplayer.getDeltaMovement().x(), entityplayer.getDeltaMovement().y(), entityplayer.getDeltaMovement().z()));
                    entityplayer.fallDistance = 0.0f;
                    entityplayer.lerpMotion(motX *= speedFactor, motY *= speedFactor, motZ *= speedFactor);
                }
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static boolean playerJump(Player entityplayer) {
        long WINDOW = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown((long)WINDOW, (int)32);
    }

    public static void removeItem(Player ep, ItemStack removeitem) {
        Inventory inventoryPlayer = ep.getInventory();
        for (int i = 0; i < 36; ++i) {
            ItemStack j;
            if (inventoryPlayer.getItem(i) == ItemStack.EMPTY || (j = inventoryPlayer.getItem(i)) == ItemStack.EMPTY || j.getItem() != removeitem.getItem()) continue;
            inventoryPlayer.setItem(i, ItemStack.EMPTY);
            break;
        }
    }
}

