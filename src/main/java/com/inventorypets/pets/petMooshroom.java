/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Plane
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
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
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraft.world.level.block.BaseCoralWallFanBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.event.EventHooks
 *  net.neoforged.neoforge.event.entity.player.BonemealEvent
 *  net.neoforged.neoforge.items.IItemHandler
 *  org.jetbrains.annotations.Nullable
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
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class petMooshroom
extends Item {
    RandomPoolAlias rand = new RandomPoolAlias();
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag = false;
    private int chkEat = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.RED_MUSHROOM;

    public petMooshroom(Item.Properties properties) {
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
            this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
            this.newFlag = false;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_MOOSHROOM.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodMooshroom.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableMooshroom.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_MOOSHROOM.get()) continue;
            slotNo = i;
        }
        if (!(entityplayer.isCreative() || stack.getDamageValue() <= 2 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10 || petFood == Items.AIR)) {
            this.eatFlag = false;
            block1: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Blocks.RED_MUSHROOM.asItem() && !this.customFood) && (s3.getItem() != Blocks.BROWN_MUSHROOM.asItem() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.7f, 1.4f);
                    this.eatFlag = true;
                    this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
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
                if (hotbar[i] != ItemStack.EMPTY && hotbar[i].getItem() == InventoryPets.PET_MOOSHROOM.get() && this.chkEat > 40 && (this.ticktime <= 0 || hotbar[i].getDamageValue() > 2)) {
                    int dmg;
                    if (this.ticktime <= 0) {
                        this.hbFlag = true;
                    }
                    if (i >= slotNo) {
                        this.chkEat = 0;
                    }
                    this.eatFlag = false;
                    for (int k = 0; k < 36; ++k) {
                        ItemStack s2 = entityplayer.getInventory().getItem(k);
                        if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Items.RED_MUSHROOM && !this.customFood) && (s2.getItem() != Items.BROWN_MUSHROOM || this.customFood)) continue;
                        s2.shrink(1);
                        if (s2.getCount() == 0) {
                            this.removeItem(entityplayer, s2);
                        }
                        this.setDamage(hotbar[i], 0);
                        worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.7f, 1.4f);
                        this.eatFlag = true;
                    }
                    if (!this.eatFlag && hotbar[i] != ItemStack.EMPTY && (dmg = hotbar[i].getDamageValue()) >= 0 && dmg < 3) {
                        hotbar[i].setDamageValue(3);
                        if (hotbar[i].getDamageValue() == 3) {
                            worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.moo.get(), SoundSource.PLAYERS, 0.6f, 1.9f);
                        }
                        this.eatFlag = true;
                    }
                }
                if (!this.hbFlag) continue;
                this.chkEat = 0;
                this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = entityplayer.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_MOOSHROOM.get() || itemchk.getDamageValue() >= 3) continue;
                if (this.ticktime == 7000 || this.ticktime == 10) {
                    for (int j = 0; j < 36; ++j) {
                        ItemStack itemchk2 = entityplayer.getInventory().getItem(j);
                        if (itemchk2 == ItemStack.EMPTY || itemchk2.getItem() != Items.BOWL) continue;
                        entityplayer.getInventory().setItem(j, new ItemStack((ItemLike)Items.MUSHROOM_STEW, itemchk2.getCount()));
                        j = 36;
                    }
                }
                i = 9;
            }
        }
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        Player playerIn = context.getPlayer();
        BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResult.FAIL;
        }
        if (((Boolean)InventoryPetsConfig.disableMooshroom.get()).booleanValue()) {
            return InteractionResult.FAIL;
        }
        if (stack.getDamageValue() < 3) {
            if (petMooshroom.applyBonemeal(context.getItemInHand(), level, blockpos, context.getPlayer())) {
                if (!level.isClientSide) {
                    level.levelEvent(1505, blockpos, 0);
                }
                return InteractionResult.sidedSuccess((boolean)level.isClientSide);
            }
            BlockState blockstate = level.getBlockState(blockpos);
            boolean flag = blockstate.isFaceSturdy((BlockGetter)level, blockpos, context.getClickedFace());
            if (flag && petMooshroom.growWaterPlant(context.getItemInHand(), level, blockpos1, context.getClickedFace(), playerIn)) {
                if (!level.isClientSide) {
                    level.levelEvent(1505, blockpos1, 0);
                }
                return InteractionResult.sidedSuccess((boolean)level.isClientSide);
            }
            return InteractionResult.FAIL;
        }
        level.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.moo.get(), SoundSource.PLAYERS, 1.0f, 1.9f);
        return InteractionResult.FAIL;
    }

    public static boolean growWaterPlant(ItemStack p_40632_, Level p_40633_, BlockPos p_40634_, @Nullable Direction p_40635_, Player playerIn) {
        if (p_40633_.getBlockState(p_40634_).is(Blocks.WATER) && p_40633_.getFluidState(p_40634_).getAmount() == 8) {
            if (!(p_40633_ instanceof ServerLevel)) {
                return true;
            }
            RandomSource random = p_40633_.getRandom();
            block0: for (int i = 0; i < 128; ++i) {
                BlockPos blockpos = p_40634_;
                BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();
                for (int j = 0; j < i / 16; ++j) {
                    if (p_40633_.getBlockState(blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1)).isCollisionShapeFullBlock((BlockGetter)p_40633_, blockpos)) continue block0;
                }
                Holder holder = p_40633_.getBiome(blockpos);
                if (holder.is(Biomes.WARM_OCEAN)) {
                    if (i == 0 && p_40635_ != null && p_40635_.getAxis().isHorizontal()) {
                        blockstate = BuiltInRegistries.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap(p_204098_ -> p_204098_.getRandomElement(p_40633_.random)).map(p_204100_ -> ((Block)p_204100_.value()).defaultBlockState()).orElse(blockstate);
                        if (blockstate.hasProperty((Property)BaseCoralWallFanBlock.FACING)) {
                            blockstate = (BlockState)blockstate.setValue((Property)BaseCoralWallFanBlock.FACING, (Comparable)p_40635_);
                        }
                    } else if (random.nextInt(4) == 0) {
                        blockstate = BuiltInRegistries.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap(p_204091_ -> p_204091_.getRandomElement(p_40633_.random)).map(p_204095_ -> ((Block)p_204095_.value()).defaultBlockState()).orElse(blockstate);
                    }
                }
                if (blockstate.is(BlockTags.WALL_CORALS, p_204093_ -> p_204093_.hasProperty((Property)BaseCoralWallFanBlock.FACING))) {
                    for (int k = 0; !blockstate.canSurvive((LevelReader)p_40633_, blockpos) && k < 4; ++k) {
                        blockstate = (BlockState)blockstate.setValue((Property)BaseCoralWallFanBlock.FACING, (Comparable)Direction.Plane.HORIZONTAL.getRandomDirection(random));
                    }
                }
                if (!blockstate.canSurvive((LevelReader)p_40633_, blockpos)) continue;
                BlockState blockstate1 = p_40633_.getBlockState(blockpos);
                if (blockstate1.is(Blocks.WATER) && p_40633_.getFluidState(blockpos).getAmount() == 8) {
                    p_40633_.setBlock(blockpos, blockstate, 3);
                    continue;
                }
                if (!blockstate1.is(Blocks.SEAGRASS) || random.nextInt(10) != 0) continue;
                ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)p_40633_, random, blockpos, blockstate1);
            }
            if (!playerIn.isCreative()) {
                p_40632_.setDamageValue(p_40632_.getDamageValue() + 1);
            }
            return true;
        }
        return true;
    }

    public static boolean applyBonemeal(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_, @javax.annotation.Nullable Player player) {
        BonemealableBlock bonemealableblock;
        BlockState blockstate = p_40629_.getBlockState(p_40630_);
        BonemealEvent event = EventHooks.fireBonemealEvent((Player)player, (Level)p_40629_, (BlockPos)p_40630_, (BlockState)blockstate, (ItemStack)p_40628_);
        if (event.isCanceled()) {
            return event.isSuccessful();
        }
        Block block = blockstate.getBlock();
        if (block instanceof BonemealableBlock && (bonemealableblock = (BonemealableBlock)block).isValidBonemealTarget((LevelReader)p_40629_, p_40630_, blockstate)) {
            if (p_40629_ instanceof ServerLevel) {
                if (bonemealableblock.isBonemealSuccess(p_40629_, p_40629_.random, p_40630_, blockstate)) {
                    bonemealableblock.performBonemeal((ServerLevel)p_40629_, p_40629_.random, p_40630_, blockstate);
                }
                if (!player.isCreative()) {
                    p_40628_.setDamageValue(p_40628_.getDamageValue() + 1);
                }
            }
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

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        int dmg;
        ItemStack s2;
        int k;
        ItemStack itemstack2;
        Level worldObj = worldIn;
        Player entityplayer = playerIn;
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!worldObj.isClientSide && !entityplayer.isCreative() && itemstack.getDamageValue() >= 6) {
            this.eatFlag = false;
            itemstack2 = itemstack;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != Blocks.RED_MUSHROOM.asItem() && s2.getItem() != Blocks.BROWN_MUSHROOM.asItem() || this.eatFlag || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                }
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(itemstack2, 0);
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.7f);
                this.eatFlag = true;
            }
            if (!this.eatFlag && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                dmg = itemstack.getDamageValue();
                if (dmg >= 0 && dmg < 6) {
                    itemstack.setDamageValue(6);
                    this.eatFlag = true;
                }
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.moo.get(), SoundSource.PLAYERS, 1.0f, 1.9f);
            }
        }
        if (!worldObj.isClientSide && !entityplayer.isCreative() && itemstack.getDamageValue() >= 6) {
            this.eatFlag = false;
            itemstack2 = itemstack;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != Blocks.RED_MUSHROOM.asItem() && s2.getItem() != Blocks.BROWN_MUSHROOM.asItem() || this.eatFlag || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                }
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(itemstack2, 0);
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.7f, 1.4f);
                this.eatFlag = true;
            }
            if (!this.eatFlag && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                dmg = itemstack.getDamageValue();
                if (dmg >= 0 && dmg < 6) {
                    itemstack.setDamageValue(6);
                    this.eatFlag = true;
                }
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.moo.get(), SoundSource.PLAYERS, 1.0f, 1.9f);
            }
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmooshroom1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmooshroom2", (Object[])new Object[0]))));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodMooshroom.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.RED_MUSHROOM.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GREEN) + I18n.get((String)"tooltip.ip.peaceful", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableMooshroom.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

