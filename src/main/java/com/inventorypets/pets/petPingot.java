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
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
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
 *  net.minecraft.world.level.block.DropExperienceBlock
 *  net.minecraft.world.level.block.RedStoneOreBlock
 *  net.minecraft.world.level.block.state.BlockState
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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petPingot
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag = false;
    private int chkMax;
    private int chkX;
    private int chkY;
    private int chkZ;
    private boolean chkGem;
    private boolean odFlag = false;
    private String oreName;
    private int chkEat = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood;

    public petPingot(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        Item petFood;
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
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_PINGOT.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_DIAMOND.get();
        }
        if ((petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodPingot.get())))) != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disablePingot.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = playerIn.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_PINGOT.get()) continue;
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
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                        this.odFlag = false;
                        if (s3.is(InventoryPets.DIAMOND_NUGGET)) {
                            this.odFlag = true;
                        }
                    }
                    if (!(s3.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s3.getItem() != Items.DIAMOND || this.customFood)) continue;
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
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_PINGOT.get() || this.chkEat <= 40 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                this.eatFlag = false;
                for (int k = 0; k < 36; ++k) {
                    ItemStack s2 = playerIn.getInventory().getItem(k);
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                        this.odFlag = false;
                        if (s2.is(InventoryPets.DIAMOND_NUGGET)) {
                            this.odFlag = true;
                        }
                    }
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && this.odFlag && !this.customFood) && (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || s2.getItem() != Items.DIAMOND || this.customFood)) continue;
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
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.1f, 0.5f);
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

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disablePingot.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!(worldIn.isClientSide || itemstack.getDamageValue() != 0 && !playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue())) {
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                BlockState oreChk2;
                Block oreChk;
                BlockPos blockpos;
                int v1;
                ItemStack itemchk = playerIn.getInventory().getItem(i);
                if (itemchk != ItemStack.EMPTY && itemchk.getItem() == InventoryPets.PET_PINGOT.get() && itemchk.getDamageValue() == 0 && !playerIn.isCrouching()) {
                    this.chkMax = 25;
                    if (!worldIn.isClientSide) {
                        for (v1 = -7; v1 < 8; ++v1) {
                            for (int v2 = -7; v2 < 8; ++v2) {
                                for (int v3 = -7; v3 < 8; ++v3) {
                                    blockpos = new BlockPos(Mth.floor((double)playerIn.getX()) + v1, Mth.floor((double)playerIn.getY()) + v2, Mth.floor((double)playerIn.getZ()) + v3);
                                    oreChk = worldIn.getBlockState(blockpos).getBlock();
                                    oreChk2 = worldIn.getBlockState(blockpos);
                                    this.odFlag = this.isOre(oreChk, worldIn, blockpos);
                                    if (!this.odFlag || oreChk == null || Math.abs(v1) + Math.abs(v2) + Math.abs(v3) >= this.chkMax) continue;
                                    this.chkMax = Math.abs(v1) + Math.abs(v2) + Math.abs(v3);
                                    this.chkX = v1;
                                    this.chkY = v2;
                                    this.chkZ = v3;
                                    this.chkGem = false;
                                    if (oreChk2.is(BlockTags.NEEDS_IRON_TOOL) || oreChk2.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
                                        this.chkGem = true;
                                        if (oreChk.getDescriptionId().toString().contains("redstone_ore")) {
                                            this.oreName = "[" + Component.translatable((String)oreChk.getDescriptionId()).getString() + "]";
                                            continue;
                                        }
                                        this.oreName = new ItemStack((ItemLike)oreChk).getDisplayName().getString();
                                        continue;
                                    }
                                    if (oreChk == Blocks.CHEST) {
                                        this.chkGem = true;
                                        this.oreName = Component.translatable((String)"info.pingot.treetop").getString();
                                        continue;
                                    }
                                    this.chkGem = false;
                                    this.oreName = new ItemStack((ItemLike)oreChk).getDisplayName().getString();
                                }
                            }
                        }
                    }
                    if (this.chkMax < 25) {
                        if (this.chkGem) {
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 2.0f);
                            String temp = Component.translatable((String)"info.pingot.hasfound").getString();
                            playerIn.sendSystemMessage((Component)Component.translatable((String)(temp + this.oreName)));
                        } else {
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
                            String temp = Component.translatable((String)"info.pingot.hasfound").getString();
                            playerIn.sendSystemMessage((Component)Component.translatable((String)(temp + this.oreName)));
                        }
                    }
                    i = 9;
                    continue;
                }
                if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_PINGOT.get() || itemchk.getDamageValue() != 0 || !playerIn.isCrouching() || ((Boolean)InventoryPetsConfig.disablePingotAutoExtract.get()).booleanValue()) continue;
                this.chkMax = 25;
                for (v1 = -7; v1 < 8; ++v1) {
                    for (int v2 = -7; v2 < 8; ++v2) {
                        for (int v3 = -7; v3 < 8; ++v3) {
                            blockpos = new BlockPos(Mth.floor((double)playerIn.getX()) + v1, Mth.floor((double)playerIn.getY()) + v2, Mth.floor((double)playerIn.getZ()) + v3);
                            oreChk = worldIn.getBlockState(blockpos).getBlock();
                            oreChk2 = worldIn.getBlockState(blockpos);
                            this.odFlag = this.isOre(oreChk, worldIn, blockpos);
                            if (!this.odFlag || oreChk == null || Math.abs(v1) + Math.abs(v2) + Math.abs(v3) >= this.chkMax) continue;
                            this.chkMax = Math.abs(v1) + Math.abs(v2) + Math.abs(v3);
                            this.chkX = v1;
                            this.chkY = v2;
                            this.chkZ = v3;
                            this.chkGem = false;
                            this.chkGem = oreChk2.is(BlockTags.NEEDS_IRON_TOOL) || oreChk2.is(BlockTags.NEEDS_DIAMOND_TOOL);
                        }
                    }
                }
                if (this.chkGem && !this.odFlag && this.chkMax == 25) {
                    String temp = Component.translatable((String)"info.pingot.cannotextract").getString();
                    playerIn.sendSystemMessage((Component)Component.translatable((String)(temp + this.oreName)));
                    worldIn.playSound(null, (double)(Mth.floor((double)playerIn.getX()) + this.chkX), (double)(Mth.floor((double)playerIn.getY()) + this.chkY), (double)(Mth.floor((double)playerIn.getZ()) + this.chkZ), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 2.0f);
                }
                if (this.chkMax < 25) {
                    ItemEntity entityitem;
                    if (this.chkX == 0 && this.chkY == 0 || this.chkX == 0 && this.chkY == 1 || this.chkX == 0 && this.chkZ == 0 || this.chkY == 1 && this.chkZ == 0 || this.chkY == 0 && this.chkZ == 0) {
                        worldIn.playSound(null, (double)(Mth.floor((double)playerIn.getX()) + this.chkX), (double)(Mth.floor((double)playerIn.getY()) + this.chkY), (double)(Mth.floor((double)playerIn.getZ()) + this.chkZ), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 1.2f);
                        BlockPos blockpos2 = new BlockPos(Mth.floor((double)playerIn.getX()) + this.chkX, Mth.floor((double)playerIn.getY()) + this.chkY, Mth.floor((double)playerIn.getZ()) + this.chkZ);
                        Block theBlock = worldIn.getBlockState(blockpos2).getBlock();
                        ItemStack bob2 = new ItemStack((ItemLike)theBlock, 1);
                        entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                        worldIn.addFreshEntity((Entity)entityitem);
                        worldIn.setBlockAndUpdate(blockpos2, Blocks.AIR.defaultBlockState());
                    }
                    if (this.chkGem) {
                        worldIn.playSound(null, (double)(Mth.floor((double)playerIn.getX()) + this.chkX), (double)(Mth.floor((double)playerIn.getY()) + this.chkY), (double)(Mth.floor((double)playerIn.getZ()) + this.chkZ), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 2.0f);
                        blockpos1 = new BlockPos(Mth.floor((double)playerIn.getX()) + this.chkX, Mth.floor((double)playerIn.getY()) + this.chkY, Mth.floor((double)playerIn.getZ()) + this.chkZ);
                        theBlock = worldIn.getBlockState(blockpos1).getBlock();
                        bob2 = new ItemStack((ItemLike)theBlock, 1);
                        entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                        worldIn.addFreshEntity((Entity)entityitem);
                        worldIn.setBlockAndUpdate(blockpos1, Blocks.AIR.defaultBlockState());
                    } else {
                        worldIn.playSound(null, (double)(Mth.floor((double)playerIn.getX()) + this.chkX), (double)(Mth.floor((double)playerIn.getY()) + this.chkY), (double)(Mth.floor((double)playerIn.getZ()) + this.chkZ), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
                        blockpos1 = new BlockPos(Mth.floor((double)playerIn.getX()) + this.chkX, Mth.floor((double)playerIn.getY()) + this.chkY, Mth.floor((double)playerIn.getZ()) + this.chkZ);
                        theBlock = worldIn.getBlockState(blockpos1).getBlock();
                        bob2 = new ItemStack((ItemLike)theBlock, 1);
                        entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                        worldIn.addFreshEntity((Entity)entityitem);
                        worldIn.setBlockAndUpdate(blockpos1, Blocks.AIR.defaultBlockState());
                    }
                    if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                        itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                    }
                }
                i = 9;
            }
        } else if (!worldIn.isClientSide && itemstack.getDamageValue() != 0) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.ping.get(), SoundSource.PLAYERS, 0.3f, 0.5f);
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public boolean isOre(Block block, Level world, BlockPos pos) {
        String name = block.getDescriptionId().toString();
        if (block instanceof DropExperienceBlock || block instanceof RedStoneOreBlock) {
            return true;
        }
        for (int i = 0; i < name.length() - 2; ++i) {
            if (name.toLowerCase().contains(".ore") || name.toLowerCase().contains("_ore") || name.toLowerCase().contains("ic2.resource")) {
                return true;
            }
            if (!name.toLowerCase().contains("netherquartz")) continue;
            return true;
        }
        return false;
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petpingot1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        if (!((Boolean)InventoryPetsConfig.disablePingotAutoExtract.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petpingot2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodPingot.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.DIAMOND.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_DIAMOND.get()).getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.BLUE) + I18n.get((String)"tooltip.ip.special", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disablePingot.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

