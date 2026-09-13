/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.inventorypets.items;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.init.ModSoundEvents;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class itemPetrifier
extends Item {
    RandomPoolAlias rand = new RandomPoolAlias();

    public itemPetrifier(Item.Properties properties) {
        super(properties);
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!((Boolean)InventoryPetsConfig.disablePetrifier.get()).booleanValue()) {
            if (!worldIn.isClientSide) {
                ItemStack s2 = ItemStack.EMPTY;
                boolean foodFound = false;
                playerIn.getInventory();
                for (int k = 0; k < 36; ++k) {
                    s2 = playerIn.getInventory().getItem(k);
                    if (s2.getItem() != Items.NETHER_STAR) continue;
                    foodFound = true;
                    break;
                }
                if (worldIn.isRaining() && playerIn.getXRot() < -60.0f) {
                    if (!playerIn.isCreative()) {
                        s2.shrink(1);
                        if (s2.getCount() == 0) {
                            this.removeItem(playerIn, s2);
                        }
                    }
                    if (foodFound || playerIn.isCreative()) {
                        ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)InventoryPets.PET_CLOUD.get()));
                        worldIn.addFreshEntity((Entity)entityitem);
                        this.playSound(playerIn);
                    } else if (!foodFound && !playerIn.isCreative()) {
                        Float pitchAdj = Float.valueOf(this.rand.nextFloat() * (float)this.rand.nextInt(3));
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                        playerIn.sendSystemMessage((Component)Component.translatable((String)"info.petrifier.requiresnetherstar"));
                    }
                } else if (itemPetrifier.isLookingAtSun(playerIn, 10, false, false)) {
                    if (!playerIn.isCreative()) {
                        s2.shrink(1);
                        if (s2.getCount() == 0) {
                            this.removeItem(playerIn, s2);
                        }
                    }
                    if (foodFound || playerIn.isCreative()) {
                        ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)InventoryPets.PET_SUN.get()));
                        worldIn.addFreshEntity((Entity)entityitem);
                        this.playSound(playerIn);
                    } else if (!foodFound && !playerIn.isCreative()) {
                        Float pitchAdj = Float.valueOf(this.rand.nextFloat() * (float)this.rand.nextInt(3));
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                        playerIn.sendSystemMessage((Component)Component.translatable((String)"info.petrifier.requiresnetherstar"));
                    }
                } else if (itemPetrifier.isLookingAtSun(playerIn, 8, true, false)) {
                    if (!playerIn.isCreative()) {
                        s2.shrink(1);
                        if (s2.getCount() == 0) {
                            this.removeItem(playerIn, s2);
                        }
                    }
                    if (foodFound || playerIn.isCreative()) {
                        ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)InventoryPets.PET_MOON.get()));
                        worldIn.addFreshEntity((Entity)entityitem);
                        this.playSound(playerIn);
                    } else if (!foodFound && !playerIn.isCreative()) {
                        Float pitchAdj = Float.valueOf(this.rand.nextFloat() * (float)this.rand.nextInt(3));
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (Holder)SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
                        playerIn.sendSystemMessage((Component)Component.translatable((String)"info.petrifier.requiresnetherstar"));
                    }
                }
            }
            playerIn.swing(handIn);
            return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
        }
        return InteractionResultHolder.fail((Object)itemstack);
    }

    public void playSound(Player playerIn) {
        Float pitchAdj = Float.valueOf(this.rand.nextFloat() / 4.0f);
        if (this.rand.nextBoolean()) {
            pitchAdj = Float.valueOf(pitchAdj.floatValue() * -1.0f);
        }
        playerIn.level().playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.petrifier.get(), SoundSource.PLAYERS, 1.0f, 1.0f + pitchAdj.floatValue());
    }

    public void removeItem(Player ep, ItemStack removeitem) {
        Inventory inventoryPlayer = ep.getInventory();
        for (int i = 0; i < 36; ++i) {
            ItemStack j;
            if (inventoryPlayer.getItem(i) == ItemStack.EMPTY || (j = inventoryPlayer.getItem(i)) == ItemStack.EMPTY || j.getItem() != removeitem.getItem()) continue;
            inventoryPlayer.setItem(i, ItemStack.EMPTY);
            break;
        }
    }

    public static boolean isLookingAtSun(Player playerIn, int threshold, boolean checkMoon, boolean throughWalls) {
        double absVector;
        double absMoon;
        double dotProd;
        double angle;
        Level worldIn = playerIn.level();
        double moonHeightVal = Math.sin((double)((worldIn.getSunAngle(0.0f) + 0.25f) * 2.0f) * Math.PI);
        double time = worldIn.getDayTime();
        double correctAngle = -1.0;
        Vec3 vector = playerIn.getLookAngle();
        if (checkMoon) {
            if (time > 23215.0 || time <= 6000.0) {
                return false;
            }
            if (6000.0 < time && time <= 12785.0) {
                return false;
            }
            if (12785.0 < time && time <= 19000.0) {
                correctAngle = Math.abs(moonHeightVal) + 1.8;
            } else if (19000.0 < time && time <= 23215.0) {
                correctAngle = 1.0 - Math.abs(moonHeightVal) + 3.16;
            }
        } else if (time > 23215.0 || time <= 6000.0) {
            correctAngle = Math.abs(moonHeightVal) - 0.2;
        } else if (6000.0 < time && time <= 12785.0) {
            correctAngle = 1.0 - Math.abs(moonHeightVal) - 2.83;
        } else {
            if (12785.0 < time && time <= 18000.0) {
                return false;
            }
            if (18000.0 < time && time <= 23215.0) {
                return false;
            }
        }
        double correctAngleD = correctAngle * 90.0;
        double correctAngleR = correctAngleD * (Math.PI / 180);
        Vec3 moon = new Vec3(Math.cos(correctAngleR), Math.sin(correctAngleR), 0.0);
        if (checkMoon) {
            vector = new Vec3(-vector.x, -vector.y, -vector.z);
        }
        return (angle = Math.acos((dotProd = moon.dot(vector)) / (absMoon = moon.length()) * (absVector = vector.length())) * 57.29577951308232) < (double)threshold;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petrifier1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petrifier2", (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petrifier3", (Object[])new Object[0]))));
        }
    }
}

