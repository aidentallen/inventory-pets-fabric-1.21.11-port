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
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
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
 *  net.minecraft.world.level.levelgen.Heightmap$Types
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
import com.inventorypets.handler.TeleporterPositionHandler;
import com.inventorypets.helper.ProxyHelper;
import com.inventorypets.init.ModDataComponents;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petNetherPortal
extends Item {
    private String lastDimension = Level.OVERWORLD.location().toString();
    private boolean eatFlag;
    private boolean fallFlag;
    private int fallCount = 400;
    private int lastX;
    private int lastY;
    private int lastZ;
    private BlockPos lastPos;
    private int otherlastX;
    private int otherlastY;
    private int otherlastZ;
    private boolean complainFlag = false;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood;

    public petNetherPortal(Item.Properties properties) {
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
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_OBSIDIAN.get();
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_NETHER_PORTAL.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodNetherPortal.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableNetherPortal.get()).booleanValue()) {
            return;
        }
        if (this.fallCount == 400 && this.fallFlag) {
            ProxyHelper.isFalling(playerIn);
            --this.fallCount;
        }
        if (this.fallFlag && this.fallCount > 1) {
            ProxyHelper.isFalling(playerIn);
            --this.fallCount;
        } else if (this.fallFlag && this.fallCount <= 1) {
            playerIn.fallDistance = 0.0f;
            this.fallFlag = false;
        }
        if (!worldIn.dimensionType().natural() && playerIn.getY() < 34.0 && this.fallCount > 10) {
            --this.fallCount;
            int xx1 = Mth.floor((double)playerIn.getX());
            int zz1 = Mth.floor((double)playerIn.getZ());
            playerIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2520, 2, false, false));
            worldIn.setBlockAndUpdate(new BlockPos(xx1, 31, zz1), Blocks.NETHERRACK.defaultBlockState());
            ProxyHelper.isFalling(playerIn);
        }
        ++this.chkEat;
        if (!playerIn.isCreative() && stack.getDamageValue() >= 2 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = playerIn.getInventory().getItem(k);
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
                    if (s3.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood || s3.getItem() == Items.QUARTZ && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() != false && s3.getItem() == Blocks.OBSIDIAN.asItem() && this.customFood || ((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && s3.getItem() == Blocks.QUARTZ_BLOCK.asItem() && !this.customFood) {
                        handler.extractItem(l, 1, false);
                        if (s3.getCount() <= 0) {
                            handler.extractItem(l, 1, false);
                        }
                        this.setDamage(stack, 0);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.7f);
                        this.eatFlag = true;
                        l = invsize + 1;
                        k = 37;
                        continue;
                    }
                    if (this.eatFlag || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Blocks.OBSIDIAN.asItem() && s2.getItem() != Blocks.QUARTZ_BLOCK.asItem()) continue;
                    if (!this.eatFlag) {
                        handler.extractItem(l, 1, false);
                    }
                    if (s3.getCount() == 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.7f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative() && stack.getDamageValue() >= 2 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                ItemStack s2 = playerIn.getInventory().getItem(k);
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    this.odFlag = false;
                    if (s2.is(InventoryPets.OBSIDIAN_NUGGET)) {
                        this.odFlag = true;
                    }
                }
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR) continue;
                if (s2.getItem() == petFood && this.customFood || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && (this.odFlag || s2.getItem() == Items.QUARTZ) && !this.customFood) {
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(playerIn, s2);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                    this.complainFlag = false;
                    this.eatFlag = true;
                    continue;
                }
                if ((s2.getItem() != petFood || !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Blocks.OBSIDIAN.asItem() && s2.getItem() != Blocks.QUARTZ_BLOCK.asItem() || this.customFood)) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                }
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
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
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 0.4f);
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
        if (((Boolean)InventoryPetsConfig.disableNetherPortal.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 2) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.teleport.get(), SoundSource.PLAYERS, 1.0f, 0.4f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        int x1 = Mth.floor((double)playerIn.getX());
        int z1 = Mth.floor((double)playerIn.getZ());
        if (!worldIn.isClientSide && (itemstack.getDamageValue() < 2 || playerIn.isCreative())) {
            this.lastDimension = (String)itemstack.get(ModDataComponents.LAST_DIMENSION);
            if (this.lastDimension == null) {
                this.lastDimension = playerIn.level().dimension().location().toString();
            }
            if (itemstack.get(ModDataComponents.LAST_X) != null && itemstack.get(ModDataComponents.OTHER_LAST_X) != null) {
                this.lastX = (Integer)itemstack.get(ModDataComponents.LAST_X);
                this.lastY = (Integer)itemstack.get(ModDataComponents.LAST_Y);
                this.lastZ = (Integer)itemstack.get(ModDataComponents.LAST_Z);
                this.otherlastX = (Integer)itemstack.get(ModDataComponents.OTHER_LAST_X);
                this.otherlastY = (Integer)itemstack.get(ModDataComponents.OTHER_LAST_Y);
                this.otherlastZ = (Integer)itemstack.get(ModDataComponents.OTHER_LAST_Z);
            }
            ServerLevel serverlevel = (ServerLevel)worldIn;
            int sepLoc = this.lastDimension.indexOf(":");
            String dimName = this.lastDimension.substring(sepLoc + 1);
            ServerLevel dimensionWorld = dimName.equals("the_nether") ? worldIn.getServer().getLevel(Level.NETHER) : worldIn.getServer().getLevel(Level.OVERWORLD);
            if (serverlevel.dimension() != ServerLevel.NETHER) {
                itemstack.set(ModDataComponents.LAST_DIMENSION, (Object)serverlevel.getLevel().dimension().location().toString());
                itemstack.set(ModDataComponents.LAST_X, (Object)((int)playerIn.getX()));
                itemstack.set(ModDataComponents.LAST_Y, (Object)((int)playerIn.getY() + 1));
                itemstack.set(ModDataComponents.LAST_Z, (Object)((int)playerIn.getZ()));
                dimensionWorld = ((ServerLevel)worldIn).getServer().getLevel(Level.NETHER);
                if (this.otherlastX != 0 && this.otherlastY != 0 && this.otherlastZ != 0) {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.netherportal.lastnether", (Object[])new Object[0]));
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, this.otherlastX, this.otherlastY + 1, this.otherlastZ);
                } else {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.netherportal.newlocnether", (Object[])new Object[0]));
                    playerIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2520, 2, false, false));
                    BlockPos coordinates = new BlockPos(x1, 70, z1);
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, coordinates.getX(), coordinates.getY(), coordinates.getZ());
                }
                this.fallFlag = true;
                this.fallCount = 400;
                playerIn.fallDistance = 0.0f;
                if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
            } else if (dimensionWorld == serverlevel.getServer().getLevel(Level.OVERWORLD)) {
                this.lastX = (Integer)itemstack.get(ModDataComponents.LAST_X);
                this.lastY = (Integer)itemstack.get(ModDataComponents.LAST_Y);
                this.lastZ = (Integer)itemstack.get(ModDataComponents.LAST_Z);
                itemstack.set(ModDataComponents.LAST_DIMENSION, (Object)serverlevel.getLevel().dimension().location().toString());
                itemstack.set(ModDataComponents.OTHER_LAST_X, (Object)((int)playerIn.getX()));
                itemstack.set(ModDataComponents.OTHER_LAST_Y, (Object)((int)playerIn.getY() + 1));
                itemstack.set(ModDataComponents.OTHER_LAST_Z, (Object)((int)playerIn.getZ()));
                if (this.lastX == 0 && this.lastY == 0 && this.lastZ == 0) {
                    playerIn.removeEffect(MobEffects.FIRE_RESISTANCE);
                    playerIn.clearFire();
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.netherportal.noreturn", (Object[])new Object[0]));
                    this.lastPos = worldIn.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, serverlevel.getSharedSpawnPos());
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, this.lastPos.getX(), this.lastPos.getY() + 1, this.lastPos.getZ());
                } else {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.netherportal.lastoverworld", (Object[])new Object[0]));
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, this.lastX, this.lastY + 1, this.lastZ);
                    playerIn.moveTo((double)this.lastX, (double)this.lastY, (double)this.lastZ);
                }
                this.fallCount = 400;
                playerIn.fallDistance = 0.0f;
                if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
            } else {
                itemstack.set(ModDataComponents.OTHER_LAST_X, (Object)((int)playerIn.getX()));
                itemstack.set(ModDataComponents.OTHER_LAST_Y, (Object)((int)playerIn.getY()));
                itemstack.set(ModDataComponents.OTHER_LAST_Z, (Object)((int)playerIn.getZ()));
                playerIn.removeEffect(MobEffects.FIRE_RESISTANCE);
                playerIn.clearFire();
                this.otherlastX = (int)playerIn.getX();
                this.otherlastY = (int)playerIn.getY();
                this.otherlastZ = (int)playerIn.getZ();
                if (this.lastX == 0 && this.lastY == 0 && this.lastZ == 0) {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.netherportal.noreturn", (Object[])new Object[0]));
                    this.lastPos = worldIn.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, serverlevel.getSharedSpawnPos());
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, this.lastPos.getX(), this.lastPos.getY() + 1, this.lastPos.getZ());
                    playerIn.setPos((double)this.lastPos.getX(), (double)(this.lastPos.getY() + 1), (double)this.lastPos.getZ());
                } else {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"item.netherportal.lastcustom", (Object[])new Object[0]));
                    TeleporterPositionHandler.teleport(playerIn, dimensionWorld, this.lastX, this.lastY, this.lastZ);
                }
                this.fallCount = 400;
                playerIn.fallDistance = 0.0f;
                if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petnetherportal1", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petnetherportal2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodNetherPortal.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.QUARTZ_BLOCK.getDescriptionId(), (Object[])new Object[0]))));
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.or", (Object[])new Object[0]) + I18n.get((String)Items.OBSIDIAN.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_OBSIDIAN.get()).getDescriptionId(), (Object[])new Object[0]))));
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.or", (Object[])new Object[0]) + I18n.get((String)Items.QUARTZ.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableNetherPortal.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

