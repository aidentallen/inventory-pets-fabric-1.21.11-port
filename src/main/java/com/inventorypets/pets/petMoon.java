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
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.arrow.Arrow
 *  net.minecraft.world.entity.projectile.hurtingprojectile.Fireball
 *  net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.scores.PlayerTeam
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
import java.util.RandomPoolAlias;
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
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petMoon
extends Item {
    private int useDelay = 0;
    private boolean eatFlag;
    private boolean slowFlag;
    private int slowTimer;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood;

    public petMoon(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_EMERALD.get();
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_MOON.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodMoon.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableMoon.get()).booleanValue()) {
            return;
        }
        ++this.useDelay;
        if (this.slowFlag && this.slowTimer > 0) {
            --this.slowTimer;
            if (worldIn.isClientSide && this.checkSinglePlayer()) {
                --this.slowTimer;
            }
            if (this.slowTimer <= 0) {
                this.slowFlag = false;
            }
            AABB range = new AABB(playerIn.getX() - 25.0, playerIn.getY() - 25.0, playerIn.getZ() - 25.0, playerIn.getX() + 25.0, playerIn.getY() + 25.0, playerIn.getZ() + 25.0);
            List entities = worldIn.getEntitiesOfClass(Entity.class, range);
            int esize = entities.size();
            for (int k = 0; k <= esize - 1; ++k) {
                double z2;
                double y2;
                double x2;
                int z1;
                int y1;
                int x1;
                double zt;
                double yt;
                TamableAnimal tameable;
                Entity entity = (Entity)entities.get(k);
                RandomPoolAlias rand = new RandomPoolAlias();
                boolean tameFlag = false;
                if (entity instanceof TamableAnimal && (tameable = (TamableAnimal)entity).isTame()) {
                    tameFlag = true;
                }
                if (entity != null && entity == playerIn) continue;
                if (entity != null && !(entity instanceof BananaEntity) && (entity instanceof Fireball || entity instanceof ThrowableItemProjectile || entity instanceof ThrowableProjectile || entity instanceof ItemEntity)) {
                    Fireball entityChk;
                    double xt = entity.getX();
                    yt = entity.getY();
                    zt = entity.getZ();
                    x1 = Mth.floor((double)playerIn.getX());
                    y1 = Mth.floor((double)playerIn.getY());
                    z1 = Mth.floor((double)playerIn.getZ());
                    x2 = xt - (double)x1;
                    y2 = yt - (double)y1;
                    z2 = zt - (double)z1;
                    if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 24 || Mth.abs((int)((int)y2)) >= 24) continue;
                    int killprojectile = rand.nextInt(1000);
                    if (entity instanceof Arrow) {
                        Arrow entityChk2 = (Arrow)entity;
                        if ((entityChk2 == null || entityChk2.getOwner() == null || entityChk2.getOwner() != playerIn) && killprojectile < 2) {
                            entityChk2.remove(Entity.RemovalReason.DISCARDED);
                        }
                    } else if (entity instanceof ThrowableProjectile) {
                        ThrowableProjectile entityChk3 = (ThrowableProjectile)entity;
                        if ((entityChk3 == null || entityChk3.getOwner() == null || entityChk3.getOwner() != playerIn) && killprojectile < 2) {
                            entityChk3.remove(Entity.RemovalReason.DISCARDED);
                        }
                    } else if (entity instanceof Fireball && ((entityChk = (Fireball)entity) == null || entityChk.getOwner() != playerIn && entityChk.getOwner() != null) && killprojectile < 2) {
                        entityChk.remove(Entity.RemovalReason.DISCARDED);
                    }
                    double chkX = entity.getDeltaMovement().x;
                    double chkY = entity.getDeltaMovement().y;
                    double chkZ = entity.getDeltaMovement().z;
                    if (chkX <= 0.0 && chkX >= 0.0) {
                        chkX = 0.0;
                    }
                    chkY = chkY > 0.0 ? 0.01 : (chkY < 0.0 ? 0.07 : 0.0);
                    if (chkZ <= 0.0 && chkZ >= 0.0) {
                        chkZ = 0.0;
                    }
                    double motionX = 0.0;
                    double motionY = 0.07;
                    double motionZ = 0.0;
                    entity.lerpMotion(motionX, motionY, motionZ);
                    continue;
                }
                if (entity != null && entity instanceof Player && ((Boolean)InventoryPetsConfig.enableMoonAffectsPlayers.get()).booleanValue()) {
                    double xt = entity.getX();
                    yt = entity.getY();
                    zt = entity.getZ();
                    x1 = Mth.floor((double)playerIn.getX());
                    y1 = Mth.floor((double)playerIn.getY());
                    z1 = Mth.floor((double)playerIn.getZ());
                    x2 = xt - (double)x1;
                    y2 = yt - (double)y1;
                    z2 = zt - (double)z1;
                    Player entityally = (Player)entity;
                    PlayerTeam tchk = entityally.getTeam();
                    PlayerTeam mchk = playerIn.getTeam();
                    if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 24 || Mth.abs((int)((int)y2)) >= 24 || tchk == mchk) continue;
                    double motionX = 0.0;
                    double motionY = 0.07;
                    double motionZ = 0.0;
                    entity.lerpMotion(motionX, motionY, motionZ);
                    continue;
                }
                if (entity == null || tameFlag || !(entity instanceof LivingEntity)) continue;
                double xt = entity.getX();
                yt = entity.getY();
                zt = entity.getZ();
                x1 = Mth.floor((double)playerIn.getX());
                y1 = Mth.floor((double)playerIn.getY());
                z1 = Mth.floor((double)playerIn.getZ());
                x2 = xt - (double)x1;
                y2 = yt - (double)y1;
                z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 24 || Mth.abs((int)((int)y2)) >= 24) continue;
                double motionX = 0.0;
                double motionY = 0.07;
                double motionZ = 0.0;
                entity.lerpMotion(motionX, motionY, motionZ);
            }
        }
        ++this.chkEat;
        if (!playerIn.isCreative() && stack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block1: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == InventoryPets.NUGGET_EMERALD.get() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.GHAST_TEAR || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.1f);
                    this.eatFlag = true;
                    continue block1;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !playerIn.isCreative() && stack.getDamageValue() >= 3 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                s2 = playerIn.getInventory().getItem(k);
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == InventoryPets.NUGGET_EMERALD.get() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2 == ItemStack.EMPTY || s2.getItem() != Items.GHAST_TEAR || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.1f);
                this.complainFlag = false;
                this.eatFlag = true;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg < 3) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.fade.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
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
        if (((Boolean)InventoryPetsConfig.disableMoon.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 3) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.fade.get(), SoundSource.PLAYERS, 0.7f, 1.0f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldIn.isClientSide && (itemstack.getDamageValue() < 3 || playerIn.isCreative()) && this.useDelay > 40) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.moon.get(), SoundSource.PLAYERS, 100.0f, 1.0f);
            this.slowFlag = true;
            this.slowTimer = 630;
            this.useDelay = 0;
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

    @OnlyIn(value=Dist.CLIENT)
    public boolean checkSinglePlayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmoon1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodMoon.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_EMERALD.get()).getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.GHAST_TEAR.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.aoe", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableMoon.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

