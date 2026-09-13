/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petJuggernaut
extends Item {
    private int slowDelay = 2400;
    private boolean eatFlag;
    private boolean slowFlag;
    private int slowTimer;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean odFlag = false;
    private boolean customFood = false;
    private Item defaultFood;

    public petJuggernaut(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        Item petFood;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_JUGGERNAUT.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_OBSIDIAN.get();
        }
        if ((petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodJuggernaut.get())))) != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableJuggernaut.get()).booleanValue()) {
            return;
        }
        ++this.slowDelay;
        if (this.slowDelay == (Integer)InventoryPetsConfig.juggernautCooldown.get() * 2400 + 1 && !worldIn.isClientSide && stack.getDamageValue() < 3) {
            String tmpString1 = Component.translatable((String)"tooltip.ip.illuminatiready").getString();
            playerIn.sendSystemMessage((Component)Component.literal((String)(stack.getDisplayName().getString() + tmpString1)));
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.slamready.get(), SoundSource.PLAYERS, 0.5f, 12.0f);
        }
        if (!this.slowFlag) {
            if ((Integer)playerIn.getData(ModDataAttachments.SHIELD) == 1) {
                playerIn.setData(ModDataAttachments.SHIELD, (Object)0);
                playerIn.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0);
            }
        } else {
            if (this.slowTimer == 40) {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.slamready.get(), SoundSource.PLAYERS, 0.7f, 0.9f);
            }
            playerIn.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
            if ((Integer)playerIn.getData(ModDataAttachments.SHIELD) != 1) {
                playerIn.setData(ModDataAttachments.SHIELD, (Object)1);
            }
        }
        if (this.slowFlag && this.slowTimer > 0 && !worldIn.isClientSide) {
            --this.slowTimer;
            if (this.slowTimer <= 0) {
                this.slowFlag = false;
            }
        }
        ++this.chkEat;
        if (!playerIn.isCreative() && stack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                        this.odFlag = false;
                        if (s3.is(InventoryPets.OBSIDIAN_NUGGET)) {
                            this.odFlag = true;
                        }
                    }
                    if (s3.getItem() != petFood && (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || !this.odFlag || this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s3.getItem() != Blocks.OBSIDIAN.asItem() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 0.7f);
                    this.eatFlag = true;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !playerIn.isCreative() && stack.getDamageValue() >= 3 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                s2 = playerIn.getInventory().getItem(k);
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.OBSIDIAN_NUGGET)) {
                        this.odFlag = true;
                    }
                }
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Blocks.OBSIDIAN.asItem() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 0.7f);
                this.complainFlag = false;
                this.eatFlag = true;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.juggerout.get(), SoundSource.PLAYERS, 0.2f, 1.7f);
                    this.complainFlag = true;
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableJuggernaut.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (this.slowDelay <= (Integer)InventoryPetsConfig.juggernautCooldown.get() * 2400 && this.slowDelay > 10 && !worldIn.isClientSide && !playerIn.isCreative()) {
            Float cooldown = Float.valueOf((float)((double)((Integer)InventoryPetsConfig.juggernautCooldown.get() * 2400 - this.slowDelay) / 2400.0));
            Object coolstring = cooldown.toString();
            coolstring = ((String)coolstring).substring(0, ((String)coolstring).length()) + "00";
            String minstring = ((String)coolstring).substring(0, 1);
            String secstring = ((String)coolstring).substring(1, 4);
            float secs = Float.parseFloat(secstring) * 60.0f;
            int secs2 = Math.round(secs);
            Object secs3 = String.valueOf(secs2);
            if (((String)secs3).length() < 2 && !minstring.equals("0")) {
                secs3 = "0" + (String)secs3;
            }
            if (itemstack.getDamageValue() < 3) {
                String tmpString1 = Component.translatable((String)"tooltip.ip.illuminaticooldown").getString();
                String tmpString2 = Component.translatable((String)"tooltip.ip.illuminatiremaining").getString();
                String tmpString3 = Component.translatable((String)"tooltip.ip.illuminatiseconds").getString();
                if (minstring.equals("0")) {
                    playerIn.sendSystemMessage((Component)Component.literal((String)(itemstack.getDisplayName().getString() + tmpString1 + (String)secs3 + tmpString3 + tmpString2)));
                } else {
                    playerIn.sendSystemMessage((Component)Component.literal((String)(itemstack.getDisplayName().getString() + tmpString1 + minstring + ":" + (String)secs3 + tmpString3)));
                }
            }
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.juggerout.get(), SoundSource.PLAYERS, 0.2f, 4.5f);
        } else if (!worldIn.isClientSide && !playerIn.isCreative() && itemstack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
            this.eatFlag = false;
            ItemStack itemstack2 = itemstack;
            for (int k = 0; k < 36; ++k) {
                ItemStack s2 = playerIn.getInventory().getItem(k);
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.OBSIDIAN_NUGGET)) {
                        this.odFlag = true;
                    }
                }
                if ((s2 == ItemStack.EMPTY || !this.odFlag || ((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) && (s2 == ItemStack.EMPTY || s2.getItem() != Blocks.IRON_BLOCK.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue())) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                }
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(itemstack2, 0);
                this.slowDelay = (Integer)InventoryPetsConfig.juggernautCooldown.get() * 2400 + 2;
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 0.7f);
                this.eatFlag = true;
            }
            if (!this.eatFlag && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                int dmg = itemstack.getDamageValue();
                if (dmg == 0) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                    this.eatFlag = true;
                }
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.juggerout.get(), SoundSource.PLAYERS, 0.2f, 1.7f);
            }
        }
        if (!(worldIn.isClientSide || itemstack.getDamageValue() >= 3 && !playerIn.isCreative() || this.slowDelay <= (Integer)InventoryPetsConfig.juggernautCooldown.get() * 2400 && !playerIn.isCreative())) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.slam.get(), SoundSource.PLAYERS, 0.5f, 0.9f);
            this.slowTimer = (Integer)InventoryPetsConfig.juggernautShieldWallDuration.get() * 1260;
            this.slowFlag = true;
            this.slowDelay = 0;
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
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

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        if ((Integer)InventoryPetsConfig.juggernautShieldWallDuration.get() > 1) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petjuggernaut1", (Object[])new Object[0]) + " (" + String.valueOf(InventoryPetsConfig.juggernautShieldWallDuration.get()) + " " + I18n.get((String)"tooltip.ip.petminutes", (Object[])new Object[0]) + ")" + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petjuggernaut1", (Object[])new Object[0]) + " (" + String.valueOf(InventoryPetsConfig.juggernautShieldWallDuration.get()) + " " + I18n.get((String)"tooltip.ip.petminute", (Object[])new Object[0]) + ")" + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petjuggernaut2", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + "(" + String.valueOf(InventoryPetsConfig.juggernautCooldown.get()) + " " + I18n.get((String)"tooltip.ip.petminutecooldown", (Object[])new Object[0]) + ")")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodJuggernaut.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_OBSIDIAN.get()).getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.IRON_BLOCK.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.youtuber", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.designedby", (Object[])new Object[0]) + " Jayg3r")));
        if (((Boolean)InventoryPetsConfig.disableJuggernaut.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

