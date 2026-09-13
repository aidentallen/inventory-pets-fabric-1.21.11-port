/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.entity.monster.skeleton.Skeleton
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petCheetah
extends Item {
    private boolean odFlag = false;
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag;
    private int chkEat = 0;
    private int spawnDelay = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.BEEF;

    public petCheetah(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        int k;
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
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_CHEETAH.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodCheetah.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableCheetah.get()).booleanValue()) {
            return;
        }
        ++this.spawnDelay;
        ItemStack chickchk = playerIn.getInventory().getItem(slot);
        if (chickchk.getItem() == InventoryPets.PET_CHEETAH.get() && slot < 10 && chickchk.getDamageValue() == 0) {
            this.hbFlag = true;
        }
        if (this.hbFlag) {
            AABB range = new AABB(playerIn.getX() - 24.0, playerIn.getY() - 24.0, playerIn.getZ() - 24.0, playerIn.getX() + 24.0, playerIn.getY() + 24.0, playerIn.getZ() + 24.0);
            List entities = worldIn.getEntitiesOfClass(Skeleton.class, range);
            int esize = entities.size();
            for (k = 0; k <= esize - 1; ++k) {
                Entity entity = (Entity)entities.get(k);
                if (entity == null || !(entity instanceof Skeleton)) continue;
                double xt = entity.getX();
                double yt = entity.getY();
                double zt = entity.getZ();
                int x1 = Mth.floor((double)playerIn.getX());
                int y1 = Mth.floor((double)playerIn.getY());
                int z1 = Mth.floor((double)playerIn.getZ());
                double x2 = xt - (double)x1;
                double y2 = yt - (double)y1;
                double z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 8 || Mth.abs((int)((int)y2)) >= 24) continue;
                Skeleton entitymob = (Skeleton)entity;
                entitymob.setTarget(null);
                Vec3 vec3d = DefaultRandomPos.getPosAway((PathfinderMob)entitymob, (int)20, (int)7, (Vec3)new Vec3(playerIn.getX(), playerIn.getY(), playerIn.getZ()));
                WorldBorder wb = playerIn.level().getWorldBorder();
                if (vec3d == null || !wb.isWithinBounds(new BlockPos((int)playerIn.getX(), (int)playerIn.getY(), (int)playerIn.getZ())) || !(playerIn.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) >= playerIn.distanceToSqr((Entity)entitymob))) continue;
                entitymob.getNavigation().moveTo(vec3d.x, vec3d.y, vec3d.z, 2.0);
            }
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = playerIn.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_CHEETAH.get()) continue;
            slotNo = i;
        }
        if (!(playerIn.isCreative() || petFood == Items.AIR || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10)) {
            this.eatFlag = false;
            block2: for (int k2 = 0; k2 < 36; ++k2) {
                IItemHandler handler;
                ItemStack s2 = playerIn.getInventory().getItem(k2);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!this.customFood) {
                        if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            this.odFlag = false;
                            if (s3.is(InventoryPets.RAW_MEATS)) {
                                this.odFlag = true;
                            }
                        } else {
                            this.odFlag = false;
                            if (s3.is(InventoryPets.RAW_FISH)) {
                                this.odFlag = true;
                            }
                        }
                    }
                    if ((s3.getItem() != petFood || !this.customFood) && (!this.odFlag || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    playerIn.level().playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.4f);
                    this.eatFlag = true;
                    this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block2;
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
                if (hotbar[i] != ItemStack.EMPTY && hotbar[i].getItem() == InventoryPets.PET_CHEETAH.get() && this.chkEat > 40 && (this.ticktime <= 0 || hotbar[i].getDamageValue() > 0)) {
                    int dmg;
                    if (this.ticktime <= 0) {
                        this.hbFlag = true;
                    }
                    if (i >= slotNo) {
                        this.chkEat = 0;
                    }
                    this.eatFlag = false;
                    for (k = 0; k < 36; ++k) {
                        ItemStack s2 = playerIn.getInventory().getItem(k);
                        if (!this.customFood) {
                            if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                                this.odFlag = false;
                                if (s2.is(InventoryPets.RAW_MEATS)) {
                                    this.odFlag = true;
                                }
                            } else {
                                this.odFlag = false;
                                if (s2.is(InventoryPets.RAW_FISH)) {
                                    this.odFlag = true;
                                }
                            }
                        }
                        if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || (s2.getItem() != petFood || !this.customFood) && (s2 == ItemStack.EMPTY || !this.odFlag || this.customFood)) continue;
                        s2.shrink(1);
                        if (s2.getCount() == 0) {
                            this.removeItem(playerIn, s2);
                        }
                        this.setDamage(hotbar[i], 0);
                        playerIn.level().playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.4f);
                        this.eatFlag = true;
                    }
                    if (!this.eatFlag && hotbar[i] != ItemStack.EMPTY && (dmg = hotbar[i].getDamageValue()) == 0) {
                        hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                        if (hotbar[i].getDamageValue() == 1) {
                            playerIn.level().playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.meow.get(), SoundSource.PLAYERS, 0.6f, 0.7f);
                        }
                        this.eatFlag = true;
                    }
                }
                if (!this.hbFlag) continue;
                this.chkEat = 0;
                this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = playerIn.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_CHEETAH.get() || itemchk.getDamageValue() != 0) continue;
            playerIn.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 6, 3, false, false));
            i = 9;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        Level worldObj = worldIn;
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableCheetah.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.meow.get(), SoundSource.PLAYERS, 0.6f, 0.7f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldObj.isClientSide && itemstack.getDamageValue() == 0) {
            if (this.spawnDelay > 60) {
                this.spawnDelay = 0;
                worldObj.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.cheetah_go.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 450, 20, false, false));
            }
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.spawnDelay == 0 && !playerIn.isCreative()) {
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcheetah1", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcheetah2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcheetah3", (Object[])new Object[0]))));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodCheetah.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (petFood != Items.AIR) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)"tooltip.ip.rawmeat", (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)"tooltip.ip.rawfish", (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_AQUA) + I18n.get((String)"tooltip.ip.fan", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.showSuggestors.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.suggestedby", (Object[])new Object[0]) + " DalekCheetah")));
        }
        if (((Boolean)InventoryPetsConfig.disableCheetah.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

