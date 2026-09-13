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
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.entities.MiniQuantumBlazeEntity;
import com.inventorypets.entities.MiniQuantumEndermanEntity;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petQuantumCrystalMonster
extends Item {
    private int spawnDelay = 299;
    private boolean eatFlag;
    private boolean complainFlag = false;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood;

    public petQuantumCrystalMonster(Item.Properties properties) {
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
        ++this.spawnDelay;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_LAPIS.get();
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_QCM.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodQuantumCrystalMonster.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() >= 2 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!this.customFood && !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                        this.odFlag = false;
                        if (s3.is(InventoryPets.LAPIS_NUGGET)) {
                            this.odFlag = true;
                        }
                    }
                    if (!(s3.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s3.getItem() != Items.LAPIS_LAZULI || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.4f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() >= 2 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (!this.customFood && !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.LAPIS_NUGGET)) {
                        this.odFlag = true;
                    }
                }
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Items.LAPIS_LAZULI || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.4f);
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
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.qcm_buzz.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
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
        if (((Boolean)InventoryPetsConfig.disableQuantumCrystalMonster.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 2) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.qcm_buzz.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        boolean minionFlag = false;
        if (!worldIn.isClientSide && itemstack.getDamageValue() < 2 && this.spawnDelay > 160) {
            this.spawnDelay = 0;
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.zap.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
            AABB range = new AABB(playerIn.getX() - 30.0, playerIn.getY() - 30.0, playerIn.getZ() - 30.0, playerIn.getX() + 30.0, playerIn.getY() + 30.0, playerIn.getZ() + 30.0);
            List entities = worldIn.getEntitiesOfClass(MiniQuantumEndermanEntity.class, range);
            int esize = entities.size();
            for (int k = 0; k <= esize - 1; ++k) {
                MiniQuantumEndermanEntity chkMinion;
                Entity entity = (Entity)entities.get(k);
                if (entity == null || (chkMinion = (MiniQuantumEndermanEntity)entity).getOwnerId() == null || chkMinion.getOwnerId() != playerIn.getUUID()) continue;
                minionFlag = true;
                chkMinion.discard();
            }
            List entities2 = worldIn.getEntitiesOfClass(MiniQuantumBlazeEntity.class, range);
            esize = entities2.size();
            for (int k = 0; k <= esize - 1; ++k) {
                MiniQuantumBlazeEntity chkMinion;
                Entity entity = (Entity)entities2.get(k);
                if (entity == null || (chkMinion = (MiniQuantumBlazeEntity)entity).getOwnerId() == null || chkMinion.getOwnerId() != playerIn.getUUID()) continue;
                minionFlag = true;
                chkMinion.discard();
            }
            if (!minionFlag) {
                BlockHitResult movingobjectposition = petQuantumCrystalMonster.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
                if (movingobjectposition != null && movingobjectposition.getType() == HitResult.Type.BLOCK) {
                    String randName;
                    int nameChk;
                    RandomPoolAlias rand;
                    String[] names;
                    int i = (int)movingobjectposition.getLocation().x;
                    int j = (int)movingobjectposition.getLocation().y;
                    int k = (int)movingobjectposition.getLocation().z;
                    if (!((Boolean)InventoryPetsConfig.reduceQuantumCrystalMonsterMinions.get()).booleanValue()) {
                        MiniQuantumBlazeEntity entityQB1 = new MiniQuantumBlazeEntity(InventoryPets.MINI_QB_ENTITY.get(), worldIn);
                        entityQB1.setPos(i, j + 1, k + 1);
                        worldIn.addFreshEntity((Entity)entityQB1);
                        names = new String[]{"Misty", "Quant", "Crypto Bro", "Binary", "Zero", "One", "Flipper", "Switch", "Firebrand", "Entangler", "Singeworthy", "Daft", "Flunky", "Funk", "Stain"};
                        rand = new RandomPoolAlias();
                        nameChk = rand.nextInt(15);
                        randName = names[nameChk];
                        entityQB1.setCustomName((Component)Component.literal((String)randName));
                        entityQB1.setCustomNameVisible(true);
                        entityQB1.setOwnerId(playerIn.getUUID());
                        MiniQuantumEndermanEntity entityQE1 = new MiniQuantumEndermanEntity(InventoryPets.MINI_QE_ENTITY.get(), worldIn);
                        entityQE1.setPos(i, j + 1, k);
                        worldIn.addFreshEntity((Entity)entityQE1);
                        String[] names2 = new String[]{"Esther", "Quandary", "Wanderlust", "Base 10", "Zero-One", "One-Zero", "Friendly", "Schrodinger", "Leaper", "Minnie", "Bertha", "10", "01", "11", "00"};
                        nameChk = rand.nextInt(15);
                        randName = names2[nameChk];
                        entityQE1.setCustomName((Component)Component.literal((String)randName));
                        entityQE1.setCustomNameVisible(true);
                        entityQE1.setOwnerId(playerIn.getUUID());
                    }
                    MiniQuantumEndermanEntity entityQE2 = new MiniQuantumEndermanEntity(InventoryPets.MINI_QE_ENTITY.get(), worldIn);
                    entityQE2.setPos(i + 1, j + 1, k);
                    worldIn.addFreshEntity((Entity)entityQE2);
                    names = new String[]{"Fripp", "Conundrum", "Willy", "Base 2", "Ozone", "101", "Angry", "Not a Pipe", "Hair-brained", "Feather", "Temp for Hire", "111", "011", "Dave", "000"};
                    rand = new RandomPoolAlias();
                    nameChk = rand.nextInt(15);
                    randName = names[nameChk];
                    entityQE2.setCustomName((Component)Component.literal((String)randName));
                    entityQE2.setCustomNameVisible(true);
                    entityQE2.setOwnerId(playerIn.getUUID());
                }
            } else if (this.spawnDelay <= 160) {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.qcm_buzz.get(), SoundSource.PLAYERS, 0.5f, 0.9f);
            }
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.spawnDelay == 0) {
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petqcm1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodQuantumCrystalMonster.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.LAPIS_LAZULI.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_LAPIS.get()).getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.BLUE) + I18n.get((String)"tooltip.ip.special", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableQuantumCrystalMonster.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

