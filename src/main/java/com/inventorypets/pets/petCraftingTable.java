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
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.SimpleMenuProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ContainerLevelAccess
 *  net.minecraft.world.inventory.CraftingMenu
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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
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

public class petCraftingTable
extends Item {
    private static final Component CONTAINER_TITLE = Component.translatable((String)"item.inventorypets.pet_crafting_table");
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag = true;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.OAK_PLANKS;

    public petCraftingTable(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
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
            this.ticktime = 120 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 160 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
            this.newFlag = false;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_CRAFTING_TABLE.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodCraftingTable.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableCraftingTable.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_CRAFTING_TABLE.get()) continue;
            slotNo = i;
        }
        if (!(entityplayer.isCreative() || petFood == Items.AIR || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10)) {
            this.eatFlag = false;
            block1: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    this.odFlag = false;
                    if (!this.customFood) {
                        if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            if (s3.is(ItemTags.PLANKS)) {
                                this.odFlag = true;
                            }
                        } else if (s3.is(ItemTags.LOGS)) {
                            this.odFlag = true;
                        }
                    } else if (s3.getItem() == petFood) {
                        this.odFlag = true;
                    }
                    if (!this.odFlag) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 1.3f);
                    this.eatFlag = true;
                    this.ticktime = 120 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 160 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block1;
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
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_CRAFTING_TABLE.get() || this.chkEat <= 40 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                this.eatFlag = false;
                for (int k = 0; k < 36; ++k) {
                    ItemStack s2 = entityplayer.getInventory().getItem(k);
                    this.odFlag = false;
                    if (!this.customFood) {
                        if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            if (s2.is(InventoryPets.PLANKS)) {
                                this.odFlag = true;
                            }
                        } else if (s2.is(InventoryPets.LOGS)) {
                            this.odFlag = true;
                        }
                    }
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || this.odFlag && !this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() != false && s2.getItem() == Blocks.OAK_LOG.asItem() && !this.customFood) && (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Blocks.OAK_PLANKS.asItem() || this.customFood)) continue;
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(entityplayer, s2);
                    }
                    this.setDamage(hotbar[i], 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 1.3f);
                    this.eatFlag = true;
                }
                if (this.eatFlag || hotbar[i] == ItemStack.EMPTY || (dmg = hotbar[i].getDamageValue()) != 0) continue;
                hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                if (hotbar[i].getDamageValue() == 1) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.crafting.get(), SoundSource.PLAYERS, 0.6f, 1.5f);
                }
                this.eatFlag = true;
            }
            if (this.hbFlag) {
                this.chkEat = 0;
                this.ticktime = 120 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 160 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableCraftingTable.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.crafting.get(), SoundSource.PLAYERS, 0.6f, 1.5f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!worldIn.isClientSide && (itemstack.getDamageValue() < 1 || playerIn.isCreative())) {
            playerIn.openMenu(this.getContainer(worldIn, playerIn.blockPosition()));
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

    public MenuProvider getContainer(Level worldIn, BlockPos blockPos) {
        return new SimpleMenuProvider((windowId, playerInventory, player) -> new CraftingMenu(this, windowId, playerInventory, ContainerLevelAccess.create((Level)worldIn, (BlockPos)blockPos)){

            public boolean stillValid(Player playerIn) {
                return true;
            }
        }, CONTAINER_TITLE);
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcraftingtable1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodCraftingTable.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)"tooltip.ip.logs", (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)"tooltip.ip.planks", (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableCraftingTable.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

