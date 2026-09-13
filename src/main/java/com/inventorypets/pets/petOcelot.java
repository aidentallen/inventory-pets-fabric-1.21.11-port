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
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.entity.monster.Creeper
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
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
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
import java.util.RandomPoolAlias;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

public class petOcelot
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag = false;
    private int chkFish;
    private int chkEat = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.COOKED_COD;

    public petOcelot(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        int k;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
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
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_OCELOT.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodOcelot.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableOcelot.get()).booleanValue()) {
            return;
        }
        ItemStack chickchk = entityplayer.getInventory().getItem(slot);
        if (chickchk != null && chickchk.getItem() == InventoryPets.PET_OCELOT.get() && slot < 10 && chickchk.getDamageValue() == 0) {
            this.hbFlag = true;
        }
        if (this.hbFlag) {
            AABB range = new AABB(entityplayer.getX() - 24.0, entityplayer.getY() - 24.0, entityplayer.getZ() - 24.0, entityplayer.getX() + 24.0, entityplayer.getY() + 24.0, entityplayer.getZ() + 24.0);
            List entities = worldIn.getEntitiesOfClass(Creeper.class, range);
            int esize = entities.size();
            for (k = 0; k <= esize - 1; ++k) {
                Entity entity = (Entity)entities.get(k);
                if (!(entity instanceof Creeper)) continue;
                double xt = entity.getX();
                double yt = entity.getY();
                double zt = entity.getZ();
                int x1 = Mth.floor((double)entityplayer.getX());
                int y1 = Mth.floor((double)entityplayer.getY());
                int z1 = Mth.floor((double)entityplayer.getZ());
                double x2 = xt - (double)x1;
                double y2 = yt - (double)y1;
                double z2 = zt - (double)z1;
                if (Mth.abs((int)((int)x2)) >= 24 || Mth.abs((int)((int)z2)) >= 8 || Mth.abs((int)((int)y2)) >= 24) continue;
                Creeper entitycreeper = (Creeper)entity;
                entitycreeper.setAggressive(false);
                entitycreeper.setTarget(null);
                Vec3 vec3d = DefaultRandomPos.getPosAway((PathfinderMob)entitycreeper, (int)20, (int)7, (Vec3)new Vec3(entityplayer.getX(), entityplayer.getY(), entityplayer.getZ()));
                WorldBorder wb = entityplayer.level().getWorldBorder();
                if (vec3d == null || !wb.isWithinBounds(new BlockPos((int)entityplayer.getX(), (int)entityplayer.getY(), (int)entityplayer.getZ())) || !(entityplayer.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) >= entityplayer.distanceToSqr((Entity)entitycreeper))) continue;
                entitycreeper.getNavigation().moveTo(vec3d.x, vec3d.y, vec3d.z, 2.0);
            }
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_OCELOT.get()) continue;
            slotNo = i;
        }
        if (!(entityplayer.isCreative() || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10 || petFood == Items.AIR)) {
            this.eatFlag = false;
            block2: for (int k2 = 0; k2 < 36; ++k2) {
                IItemHandler handler;
                ItemStack s2 = entityplayer.getInventory().getItem(k2);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Items.COOKED_COD && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.SPIDER_EYE || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.8f);
                    this.eatFlag = true;
                    this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block2;
                }
            }
        }
        if (!entityplayer.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide) {
            int i;
            ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                hotbar[i] = entityplayer.getInventory().getItem(i);
            }
            this.hbFlag = false;
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                int dmg;
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_OCELOT.get() || this.chkEat <= 40 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                this.eatFlag = false;
                for (k = 0; k < 36; ++k) {
                    ItemStack s2 = entityplayer.getInventory().getItem(k);
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Items.COOKED_COD && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.SPIDER_EYE || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(entityplayer, s2);
                    }
                    this.setDamage(hotbar[i], 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.8f);
                    this.eatFlag = true;
                }
                if (this.eatFlag || hotbar[i] == ItemStack.EMPTY || (dmg = hotbar[i].getDamageValue()) != 0) continue;
                hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                if (hotbar[i].getDamageValue() == 1) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.meow.get(), SoundSource.PLAYERS, 0.5f, 1.5f);
                }
                this.eatFlag = true;
            }
            if (this.hbFlag) {
                this.chkEat = 0;
                this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_OCELOT.get() || itemchk.getDamageValue() != 0) continue;
            RandomPoolAlias rand = new RandomPoolAlias();
            ++this.chkFish;
            if (this.chkFish > 400) {
                int fishin;
                this.chkFish = 0;
                int chkx = Mth.floor((double)entityplayer.getX());
                int chky = Mth.floor((double)entityplayer.getY());
                int chkz = Mth.floor((double)entityplayer.getZ());
                Block oreChk1 = worldIn.getBlockState(new BlockPos(chkx, chky - 1, chkz)).getBlock();
                Block oreChk2 = worldIn.getBlockState(new BlockPos(chkx + 1, chky - 1, chkz + 1)).getBlock();
                Block oreChk3 = worldIn.getBlockState(new BlockPos(chkx - 1, chky - 1, chkz)).getBlock();
                Block oreChk4 = worldIn.getBlockState(new BlockPos(chkx - 1, chky - 1, chkz)).getBlock();
                Block oreChk5 = worldIn.getBlockState(new BlockPos(chkx - 1, chky - 1, chkz - 1)).getBlock();
                Block oreChk6 = worldIn.getBlockState(new BlockPos(chkx - 1, chky - 1, chkz + 1)).getBlock();
                Block oreChk7 = worldIn.getBlockState(new BlockPos(chkx + 1, chky - 1, chkz - 1)).getBlock();
                Block oreChk8 = worldIn.getBlockState(new BlockPos(chkx, chky, chkz)).getBlock();
                if ((oreChk1 == Blocks.WATER || oreChk2 == Blocks.WATER || oreChk3 == Blocks.WATER || oreChk4 == Blocks.WATER || oreChk5 == Blocks.WATER || oreChk6 == Blocks.WATER || oreChk7 == Blocks.WATER || oreChk8 == Blocks.WATER) && (fishin = rand.nextInt(100)) < 20) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.fishin.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    entityplayer.getInventory().add(new ItemStack((ItemLike)Items.COD, 1));
                }
            }
            i = 9;
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

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petocelot1", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petocelot2", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petocelot3", (Object[])new Object[0]))));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodOcelot.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.SPIDER_EYE.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.COOKED_COD.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GREEN) + I18n.get((String)"tooltip.ip.peaceful", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableOcelot.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

