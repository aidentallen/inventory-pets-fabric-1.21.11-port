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
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.MagmaCube
 *  net.minecraft.world.entity.monster.Slime
 *  net.minecraft.world.entity.monster.spider.Spider
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.arrow.Arrow
 *  net.minecraft.world.entity.projectile.hurtingprojectile.Fireball
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile
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
import com.inventorypets.events.KeyHandler;
import com.inventorypets.init.ModDataComponents;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petBlackHole
extends Item {
    private int useDelay = 0;
    private boolean eatFlag;
    private boolean slowFlag;
    private int slowTimer;
    private boolean complainFlag = false;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood;

    public petBlackHole(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        Entity entity;
        int k;
        int esize;
        List entities;
        AABB range;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        this.defaultFood = (Item)InventoryPets.NUGGET_OBSIDIAN.get();
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == this) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBlackHole.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableBlackHole.get()).booleanValue()) {
            return;
        }
        if (!stack.has(ModDataComponents.ATTRACT_ITEMS)) {
            stack.set(ModDataComponents.ATTRACT_ITEMS, (Object)true);
        }
        ++this.useDelay;
        if (Boolean.TRUE.equals(stack.get(ModDataComponents.ATTRACT_ITEMS)) && !worldIn.isClientSide && this.chkEat > 40) {
            range = new AABB(entityplayer.getX() - 6.0, entityplayer.getY() - 6.0, entityplayer.getZ() - 6.0, entityplayer.getX() + 6.0, entityplayer.getY() + 6.0, entityplayer.getZ() + 6.0);
            entities = worldIn.getEntitiesOfClass(ItemEntity.class, range);
            esize = entities.size();
            for (k = 0; k <= esize - 1; ++k) {
                entity = (Entity)entities.get(k);
                if (!(entity instanceof ItemEntity)) continue;
                double xt = entity.getX();
                double yt = entity.getY();
                double zt = entity.getZ();
                int x1 = Mth.floor((double)entityplayer.getX());
                int y1 = Mth.floor((double)entityplayer.getY());
                int z1 = Mth.floor((double)entityplayer.getZ());
                double x2 = xt - (double)x1;
                double y2 = yt - (double)y1;
                double z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 5 || Mth.abs((int)((int)z2)) >= 5 || Mth.abs((int)((int)y2)) >= 5) continue;
                entity.moveTo(entityplayer.blockPosition(), 0.0f, 0.0f);
                break;
            }
        }
        if (this.slowFlag && this.slowTimer > 0) {
            --this.slowTimer;
            if (worldIn.isClientSide && this.checkSinglePlayer()) {
                --this.slowTimer;
            }
            if (this.slowTimer <= 0) {
                this.slowFlag = false;
            }
            range = new AABB(entityplayer.getX() - 25.0, entityplayer.getY() - 25.0, entityplayer.getZ() - 25.0, entityplayer.getX() + 25.0, entityplayer.getY() + 25.0, entityplayer.getZ() + 25.0);
            entities = worldIn.getEntitiesOfClass(Entity.class, range);
            esize = entities.size();
            for (k = 0; k <= esize - 1; ++k) {
                double z2;
                double y2;
                double x2;
                int y1;
                int x1;
                double zt;
                TamableAnimal tameable;
                entity = (Entity)entities.get(k);
                boolean tameFlag = false;
                if (entity instanceof TamableAnimal && (tameable = (TamableAnimal)entity).isTame()) {
                    tameFlag = true;
                }
                if (entity != null && entity == entityplayer) continue;
                if (entity != null && (entity instanceof Fireball || entity instanceof Projectile || entity instanceof ThrowableItemProjectile)) {
                    boolean chkFlag = false;
                    RandomPoolAlias rand = new RandomPoolAlias();
                    int killprojectile = rand.nextInt(1000);
                    if (entity instanceof Arrow) {
                        Arrow entityChk = (Arrow)entity;
                        if (entityChk != null && entityChk.getOwner() != null && entityChk.getOwner().getUUID() == entityplayer.getUUID()) {
                            chkFlag = true;
                        } else if (killprojectile < 2) {
                            entityChk.remove(Entity.RemovalReason.DISCARDED);
                        }
                    } else if (entity instanceof ThrowableProjectile) {
                        ThrowableProjectile entityChk = (ThrowableProjectile)entity;
                        if (entityChk != null && entityChk.getOwner() != null && entityChk.getOwner() == entityplayer) {
                            chkFlag = true;
                        } else if (killprojectile < 2) {
                            entityChk.remove(Entity.RemovalReason.DISCARDED);
                        }
                    } else if (entity instanceof Fireball) {
                        Fireball entityChk = (Fireball)entity;
                        if (entityChk != null && (entityChk.getOwner() == entityplayer || entityChk.getOwner() == null)) {
                            chkFlag = true;
                        } else if (killprojectile < 2) {
                            entityChk.remove(Entity.RemovalReason.DISCARDED);
                        }
                    }
                    double chkX = entity.getDeltaMovement().x;
                    double chkY = entity.getDeltaMovement().y;
                    double chkZ = entity.getDeltaMovement().z;
                    chkX = chkX > 0.0 ? 0.01 : (chkX < 0.0 ? -0.01 : 0.0);
                    chkY = chkY > 0.0 ? 0.01 : (chkY < 0.0 ? -0.01 : 0.0);
                    chkZ = chkZ > 0.0 ? 0.01 : (chkZ < 0.0 ? -0.01 : 0.0);
                    if (chkFlag) continue;
                    entity.lerpMotion(chkX, 0.0, chkZ);
                    continue;
                }
                if (entity != null && (entity instanceof Spider || entity instanceof Slime || entity instanceof MagmaCube)) {
                    double xt = entity.getX();
                    double yt = entity.getY();
                    zt = entity.getZ();
                    x1 = Mth.floor((double)entityplayer.getX());
                    y1 = Mth.floor((double)entityplayer.getY());
                    int z1 = Mth.floor((double)entityplayer.getZ());
                    x2 = xt - (double)x1;
                    y2 = yt - (double)y1;
                    z2 = zt - (double)z1;
                    if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 24 || Mth.abs((int)((int)y2)) >= 24) continue;
                    entity.lerpMotion(-1.0, 0.0, 0.0);
                    continue;
                }
                if (entity != null && entity instanceof Player && entity != entityplayer && ((Boolean)InventoryPetsConfig.enableBlackHoleAffectsPlayers.get()).booleanValue()) {
                    Player entityally = (Player)entity;
                    PlayerTeam tchk = entityally.getTeam();
                    PlayerTeam mchk = entityplayer.getTeam();
                    double xt = entity.getX();
                    double yt = entity.getY();
                    double zt2 = entity.getZ();
                    int x12 = Mth.floor((double)entityplayer.getX());
                    int y12 = Mth.floor((double)entityplayer.getY());
                    int z1 = Mth.floor((double)entityplayer.getZ());
                    double x22 = xt - (double)x12;
                    double y22 = yt - (double)y12;
                    double z22 = zt2 - (double)z1;
                    if (Mth.abs((int)((int)x22)) >= 32 || Mth.abs((int)((int)z22)) >= 32 || Mth.abs((int)((int)y22)) >= 24 || tchk == mchk) continue;
                    entity.getDeltaMovement();
                    entity.getDeltaMovement();
                    entity.getDeltaMovement();
                    entity.lerpMotion(0.0, 0.0, 0.0);
                    continue;
                }
                if (entity == null || tameFlag || entity == entityplayer || !(entity instanceof LivingEntity)) continue;
                double xt = entity.getX();
                double yt = entity.getY();
                zt = entity.getZ();
                x1 = Mth.floor((double)entityplayer.getX());
                y1 = Mth.floor((double)entityplayer.getY());
                int z1 = Mth.floor((double)entityplayer.getZ());
                x2 = xt - (double)x1;
                y2 = yt - (double)y1;
                z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 32 || Mth.abs((int)((int)z2)) >= 32 || Mth.abs((int)((int)y2)) >= 24) continue;
                entity.getDeltaMovement();
                entity.getDeltaMovement();
                entity.getDeltaMovement();
                entity.lerpMotion(0.0, 0.0, 0.0);
            }
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            for (int k2 = 0; k2 < 36; ++k2) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k2);
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
                    if (!(s3.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s3.getItem() != Blocks.OBSIDIAN.asItem() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
                    this.eatFlag = true;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() >= 3 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (int k3 = 0; k3 < 36; ++k3) {
                s2 = entityplayer.getInventory().getItem(k3);
                if (!this.customFood && !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.OBSIDIAN_NUGGET)) {
                        this.odFlag = true;
                    }
                }
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Blocks.OBSIDIAN.asItem() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
                this.complainFlag = false;
                this.eatFlag = true;
                break;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.time_warp.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                    this.complainFlag = true;
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
        if (((Boolean)InventoryPetsConfig.disableBlackHole.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 3) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.time_warp.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!(worldObj.isClientSide || entityplayer.isCrouching() || itemstack.getDamageValue() >= 3 && !entityplayer.isCreative())) {
            if (this.useDelay > 40) {
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.black_hole.get(), SoundSource.NEUTRAL, 1.1f, 1.0f);
                AABB range = new AABB(entityplayer.getX() - 25.0, entityplayer.getY() - 25.0, entityplayer.getZ() - 25.0, entityplayer.getX() + 25.0, entityplayer.getY() + 25.0, entityplayer.getZ() + 25.0);
                List entities = worldObj.getEntitiesOfClass(LivingEntity.class, range);
                int esize = entities.size();
                for (int k = 0; k <= esize - 1; ++k) {
                    TamableAnimal tameable;
                    Entity entity = (Entity)entities.get(k);
                    boolean tameFlag = false;
                    if (entity instanceof TamableAnimal && (tameable = (TamableAnimal)entity).isTame()) {
                        tameFlag = true;
                    }
                    if (entity != null && (entity instanceof Player || entity instanceof Player) && entity == entityplayer) continue;
                    if (entity != null && (entity instanceof Player || entity instanceof Player) && entity != entityplayer && ((Boolean)InventoryPetsConfig.enableBlackHoleAffectsPlayers.get()).booleanValue()) {
                        ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 450, 20));
                        this.slowFlag = true;
                        this.slowTimer = 900;
                        continue;
                    }
                    if (entity == null || tameFlag || !(entity instanceof LivingEntity) || entity instanceof Player || entity == entityplayer) continue;
                    ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 410, 20));
                    this.slowFlag = true;
                    this.slowTimer = 900;
                }
                this.useDelay = 0;
                if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
            }
        } else if (!worldObj.isClientSide && entityplayer.isCrouching() && (itemstack.getDamageValue() < 3 || entityplayer.isCreative())) {
            if (Boolean.TRUE.equals(itemstack.get(ModDataComponents.ATTRACT_ITEMS))) {
                itemstack.set(ModDataComponents.ATTRACT_ITEMS, (Object)false);
                entityplayer.sendSystemMessage((Component)Component.translatable((String)"item.blackhole.off", (Object[])new Object[0]));
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.STONE_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 0.7f, 1.1f);
            } else {
                itemstack.set(ModDataComponents.ATTRACT_ITEMS, (Object)true);
                entityplayer.sendSystemMessage((Component)Component.translatable((String)"item.blackhole.on", (Object[])new Object[0]));
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.PLAYERS, 0.7f, 1.3f);
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
        if (!stack.has(ModDataComponents.ATTRACT_ITEMS)) {
            stack.set(ModDataComponents.ATTRACT_ITEMS, (Object)true);
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petblackhole1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        if (Boolean.TRUE.equals(stack.get(ModDataComponents.ATTRACT_ITEMS))) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petblackhole2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.on", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petblackhole2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.off", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petblackhole3", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBlackHole.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.OBSIDIAN.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_OBSIDIAN.get()).getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.LIGHT_PURPLE) + I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableBlackHole.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

