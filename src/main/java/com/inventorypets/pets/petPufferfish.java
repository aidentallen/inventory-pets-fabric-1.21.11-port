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
 *  net.minecraft.world.entity.LivingEntity
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
import com.inventorypets.helper.ItemHelper;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
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

public class petPufferfish
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag = false;
    private int chkEat = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.GLOWSTONE_DUST;

    public petPufferfish(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if ((Integer)InventoryPetsConfig.petEatTimerFactor.get() <= 0) {
            return;
        }
        if (this.newFlag && (Integer)InventoryPetsConfig.petEatTimerFactor.get() > 0 && !this.isDamaged(stack)) {
            this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
            this.newFlag = false;
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_PUFFERFISH.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodPufferfish.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disablePufferfish.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = playerIn.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_PUFFERFISH.get()) continue;
            slotNo = i;
        }
        if (!(playerIn.isCreative() || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10 || petFood == Items.AIR)) {
            this.eatFlag = false;
            block1: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Items.GLOWSTONE_DUST && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Blocks.GLOWSTONE.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
                    this.eatFlag = true;
                    this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block1;
                }
            }
        }
        if (!playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide) {
            int i;
            ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                hotbar[i] = playerIn.getInventory().getItem(i);
            }
            this.hbFlag = false;
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                int dmg;
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_PUFFERFISH.get() || this.chkEat <= 40 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                this.eatFlag = false;
                for (int k = 0; k < 36; ++k) {
                    ItemStack s2 = playerIn.getInventory().getItem(k);
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Items.GLOWSTONE_DUST && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Blocks.GLOWSTONE.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(playerIn, s2);
                    }
                    this.setDamage(hotbar[i], 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
                    this.eatFlag = true;
                }
                if (this.eatFlag || hotbar[i] == ItemStack.EMPTY || (dmg = hotbar[i].getDamageValue()) != 0) continue;
                hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                if (hotbar[i].getDamageValue() == 1) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.fizz.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
                }
                this.eatFlag = true;
            }
            if (this.hbFlag) {
                this.chkEat = 0;
                this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
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

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disablePufferfish.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.fizz.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        boolean hitFlag = false;
        if (!worldIn.isClientSide && (itemstack.getDamageValue() < 1 || playerIn.isCreative())) {
            AABB range = new AABB(playerIn.getX() - 8.0, playerIn.getY() - 8.0, playerIn.getZ() - 8.0, playerIn.getX() + 8.0, playerIn.getY() + 8.0, playerIn.getZ() + 8.0);
            List entities = worldIn.getEntitiesOfClass(LivingEntity.class, range);
            int e2size = entities.size();
            for (int k = 0; k <= e2size - 1; ++k) {
                Entity entity = (Entity)entities.get(k);
                if (entity != null && entity == playerIn) continue;
                if (entity != null && entity instanceof Player && entity != playerIn && ((Boolean)InventoryPetsConfig.enablePufferfishAffectsPlayers.get()).booleanValue()) {
                    Player entityally = (Player)entity;
                    PlayerTeam tchk = entityally.getTeam();
                    PlayerTeam mchk = playerIn.getTeam();
                    double xt = entity.getX();
                    double yt = entity.getY();
                    double zt = entity.getZ();
                    int x1 = Mth.floor((double)playerIn.getX());
                    int y1 = Mth.floor((double)playerIn.getY());
                    int z1 = Mth.floor((double)playerIn.getZ());
                    double x2 = xt - (double)x1;
                    double y2 = yt - (double)y1;
                    double z2 = zt - (double)z1;
                    if (Mth.abs((int)((int)x2)) >= 16 || Mth.abs((int)((int)z2)) >= 16 || Mth.abs((int)((int)y2)) >= 16 || tchk == mchk) continue;
                    entity.hurt(entity.level().damageSources().magic(), 2.0f);
                    ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.POISON, 600, 3));
                    hitFlag = true;
                    continue;
                }
                if (entity == null || !(entity instanceof LivingEntity) || entity == playerIn) continue;
                double xt = entity.getX();
                double yt = entity.getY();
                double zt = entity.getZ();
                int x1 = Mth.floor((double)playerIn.getX());
                int y1 = Mth.floor((double)playerIn.getY());
                int z1 = Mth.floor((double)playerIn.getZ());
                double x2 = xt - (double)x1;
                double y2 = yt - (double)y1;
                double z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 16 || Mth.abs((int)((int)z2)) >= 16 || Mth.abs((int)((int)y2)) >= 16) continue;
                int yaw = (int)playerIn.getYRot();
                if (yaw < 0) {
                    yaw += 360;
                }
                yaw += 22;
                int facing = (yaw %= 360) / 45;
                int dist = 2;
                if (facing == 0) {
                    entity.setPos(xt, yt + 1.0, zt + (double)dist);
                } else if (facing == 1) {
                    entity.setPos(xt - (double)dist, yt + 1.0, zt + (double)dist);
                } else if (facing == 2) {
                    entity.setPos(xt - (double)dist, yt + 1.0, zt);
                } else if (facing == 3) {
                    entity.setPos(xt - (double)dist, yt + 1.0, zt - (double)dist);
                } else if (facing == 4) {
                    entity.setPos(xt, yt + 1.0, zt - (double)dist);
                } else if (facing == 5) {
                    entity.setPos(xt + (double)dist, yt + 1.0, zt - (double)dist);
                } else if (facing == 6) {
                    entity.setPos(xt + (double)dist, yt + 1.0, zt);
                } else if (facing == 7) {
                    entity.setPos(xt + (double)dist, yt + 1.0, zt + (double)dist);
                }
                entity.level().playSound(null, entity.getX(), playerIn.getY(), entity.getZ(), ModSoundEvents.venom_inject.get(), SoundSource.PLAYERS, 1.0f, 1.1f);
                entity.hurt(entity.level().damageSources().magic(), 2.0f);
                ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.POISON, 600, 3));
                hitFlag = true;
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative() && hitFlag) {
            int dmg = itemstack.getDamageValue();
            if (dmg == 0) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        } else if (!hitFlag) {
            playerIn.level().playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BEEHIVE_DRIP, SoundSource.PLAYERS, 0.5f, 0.5f);
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petpufferfish1", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petpufferfish2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodPufferfish.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.GLOWSTONE.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.GLOWSTONE_DUST.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.LIGHT_PURPLE) + I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disablePufferfish.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

