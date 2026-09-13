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
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.hurtingprojectile.Fireball
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.entities.BananaEntity;
import com.inventorypets.events.KeyHandler;
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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petDubstep
extends Item {
    private boolean eatFlag;
    private boolean blastFlag = false;
    private int blastTimer = 0;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.NOTE_BLOCK;

    public petDubstep(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        int k;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_DUBSTEP.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodDubstep.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableDubstep.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Blocks.NOTE_BLOCK.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Blocks.STICKY_PISTON.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.1f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() >= 3 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Blocks.NOTE_BLOCK.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Blocks.STICKY_PISTON.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.1f);
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
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.shorted.get(), SoundSource.PLAYERS, 0.5f, 1.1f);
                    this.complainFlag = true;
                }
            }
        }
        if (this.blastFlag && this.blastTimer > 0) {
            --this.blastTimer;
            if (worldIn.isClientSide && this.checkSinglePlayer()) {
                --this.blastTimer;
            }
            if (this.blastTimer <= 0) {
                this.blastFlag = false;
            }
            AABB range = new AABB(entityplayer.getX() - 25.0, entityplayer.getY() - 25.0, entityplayer.getZ() - 25.0, entityplayer.getX() + 25.0, entityplayer.getY() + 25.0, entityplayer.getZ() + 25.0);
            List entities = worldIn.getEntitiesOfClass(Entity.class, range);
            int esize = entities.size();
            for (int k2 = 0; k2 <= esize - 1; ++k2) {
                int v1;
                double z2;
                double y2;
                double x2;
                int z1;
                int y1;
                int x1;
                double zup;
                double xup;
                double zt;
                double yt;
                TamableAnimal tameable;
                Entity entity = (Entity)entities.get(k2);
                boolean tameFlag = false;
                if (entity instanceof TamableAnimal && (tameable = (TamableAnimal)entity).isTame()) {
                    tameFlag = true;
                }
                if (entity != null && entity == entityplayer) continue;
                if (!(entity instanceof BananaEntity) && (entity instanceof Fireball || entity instanceof Projectile || entity instanceof ThrowableProjectile || entity instanceof ItemEntity)) {
                    double xt = entity.getX();
                    yt = entity.getY();
                    zt = entity.getZ();
                    xup = 0.0;
                    zup = 0.0;
                    x1 = Mth.floor((double)entityplayer.getX());
                    y1 = Mth.floor((double)entityplayer.getY());
                    z1 = Mth.floor((double)entityplayer.getZ());
                    x2 = xt - (double)x1;
                    y2 = yt - (double)y1;
                    z2 = zt - (double)z1;
                    if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 24 || Mth.abs((int)((int)y2)) >= 16) continue;
                    xup = x2 > 0.0 ? 0.2 : (x2 == 0.0 ? 0.0 : -0.2);
                    zup = z2 > 0.0 ? 0.2 : (z2 == 0.0 ? 0.0 : -0.2);
                    entity.setPos(xt + xup, yt, zt + zup);
                    if (!entity.isInWall()) continue;
                    for (v1 = 1; v1 < 10 && entity.isInWall(); ++v1) {
                        entity.setPos(xt, yt + (double)v1, zt);
                    }
                    continue;
                }
                if (entity != null && entity instanceof Player && ((Boolean)InventoryPetsConfig.enableDubstepAffectsPlayers.get()).booleanValue()) {
                    double xt = entity.getX();
                    yt = entity.getY();
                    zt = entity.getZ();
                    xup = 0.0;
                    zup = 0.0;
                    x1 = Mth.floor((double)entityplayer.getX());
                    y1 = Mth.floor((double)entityplayer.getY());
                    z1 = Mth.floor((double)entityplayer.getZ());
                    x2 = xt - (double)x1;
                    y2 = yt - (double)y1;
                    z2 = zt - (double)z1;
                    if (Mth.abs((int)((int)x2)) >= 20 || Mth.abs((int)((int)z2)) >= 20 || Mth.abs((int)((int)y2)) >= 12) continue;
                    xup = x2 > 0.0 ? 0.2 : (x2 == 0.0 ? 0.0 : -0.2);
                    zup = z2 > 0.0 ? 0.2 : (z2 == 0.0 ? 0.0 : -0.2);
                    entity.setPos(xt + xup, yt, zt + zup);
                    if (!entity.isInWall()) continue;
                    for (v1 = 1; v1 < 10 && entity.isInWall(); ++v1) {
                        entity.setPos(xt, yt + (double)v1, zt);
                    }
                    continue;
                }
                if (entity == null || tameFlag || !(entity instanceof LivingEntity)) continue;
                double xt = entity.getX();
                yt = entity.getY();
                zt = entity.getZ();
                xup = 0.0;
                zup = 0.0;
                x1 = Mth.floor((double)entityplayer.getX());
                y1 = Mth.floor((double)entityplayer.getY());
                z1 = Mth.floor((double)entityplayer.getZ());
                x2 = xt - (double)x1;
                y2 = yt - (double)y1;
                z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 20 || Mth.abs((int)((int)z2)) >= 20 || Mth.abs((int)((int)y2)) >= 12) continue;
                xup = x2 > 0.0 ? 0.2 : (x2 == 0.0 ? 0.0 : -0.2);
                zup = z2 > 0.0 ? 0.2 : (z2 == 0.0 ? 0.0 : -0.2);
                entity.setPos(xt + xup, yt, zt + zup);
                if (!entity.isInWall()) continue;
                for (v1 = 1; v1 < 10 && entity.isInWall(); ++v1) {
                    entity.setPos(xt, yt + (double)v1, zt);
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        Level worldObj = worldIn;
        Player entityplayer = playerIn;
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableDubstep.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 3) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.shorted.get(), SoundSource.PLAYERS, 0.5f, 1.1f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldObj.isClientSide && (itemstack.getDamageValue() < 3 && this.blastTimer <= 0 || entityplayer.isCreative() && this.blastTimer <= 0)) {
            worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.dubstep.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            this.blastFlag = true;
            this.blastTimer = 580;
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        } else if (!worldObj.isClientSide && (itemstack.getDamageValue() < 3 && this.blastTimer > 0 || entityplayer.isCreative() && this.blastTimer > 0)) {
            worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.shorted.get(), SoundSource.PLAYERS, 0.7f, 1.7f);
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

    @OnlyIn(value=Dist.CLIENT)
    public boolean checkSinglePlayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petdubstep1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodDubstep.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.NOTE_BLOCK.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.STICKY_PISTON.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.aoe", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableDubstep.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

