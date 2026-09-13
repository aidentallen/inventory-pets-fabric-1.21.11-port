/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.ChatFormatting
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionResult
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
 *  net.minecraft.world.item.component.BlockItemStateProperties
 *  net.minecraft.world.item.component.CustomData
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.BaseFireBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CampfireBlock
 *  net.minecraft.world.level.block.CandleBlock
 *  net.minecraft.world.level.block.CandleCakeBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 *  org.jetbrains.annotations.Nullable
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class petTorch
extends Item {
    private boolean eatFlag;
    private boolean odFlag;
    private boolean complainFlag = false;
    private int chkEat = 0;
    protected final Block wallBlock = Blocks.WALL_TORCH;
    protected final Block floorBlock = Blocks.TORCH;
    private boolean customFood = false;
    private Item defaultFood;

    public petTorch(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack itemstack, Level worldIn, Entity playerIn, int slot, boolean par5) {
        ItemStack s2;
        int k;
        Item petFood;
        if (!(playerIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)playerIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_TORCH.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_COAL.get();
        }
        if ((petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodTorch.get())))) != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableTorch.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && itemstack.getDamageValue() >= 4 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    this.odFlag = false;
                    if (!this.customFood) {
                        if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            if (s3.is(InventoryPets.COAL_NUGGET)) {
                                this.odFlag = true;
                            }
                        } else if (s3.is(InventoryPets.COAL)) {
                            this.odFlag = true;
                        }
                    }
                    if ((s3.getItem() != petFood || !this.customFood) && (!this.odFlag || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(itemstack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.6f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && itemstack.getDamageValue() >= 4 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (!this.customFood) {
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                        this.odFlag = false;
                        if (s2.is(InventoryPets.COAL_NUGGET)) {
                            this.odFlag = true;
                        }
                    } else {
                        this.odFlag = false;
                        if (s2.is(InventoryPets.COAL)) {
                            this.odFlag = true;
                        }
                    }
                }
                if (!s2.isEmpty() && !this.eatFlag && (s2.getItem() == petFood && this.customFood || this.odFlag && !this.customFood)) {
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(entityplayer, s2);
                    }
                    this.setDamage(itemstack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.6f);
                    this.complainFlag = false;
                    this.eatFlag = true;
                }
                if (this.eatFlag) continue;
                int dmg = itemstack.getDamageValue();
                if (dmg == 0) {
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (this.complainFlag) continue;
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.6f, 1.4f);
                this.complainFlag = true;
            }
        }
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.wallBlock.getStateForPlacement(context);
        BlockState blockstate1 = null;
        Level levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState blockstate2;
            if (direction == Direction.UP) continue;
            BlockState blockState = blockstate2 = direction == Direction.DOWN ? this.floorBlock.getStateForPlacement(context) : blockstate;
            if (blockstate2 == null || !blockstate2.canSurvive((LevelReader)levelreader, blockpos)) continue;
            blockstate1 = blockstate2;
            break;
        }
        return blockstate1 != null && levelreader.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
    }

    protected boolean checkPosition() {
        return true;
    }

    protected boolean placeBlock(BlockPlaceContext p_40578_, BlockState p_40579_) {
        return true;
    }

    private BlockState updateBlockStateFromTag(BlockPos pos, Level level, ItemStack stack, BlockState state) {
        BlockItemStateProperties blockitemstateproperties = (BlockItemStateProperties)stack.getOrDefault(DataComponents.BLOCK_STATE, (Object)BlockItemStateProperties.EMPTY);
        if (blockitemstateproperties.isEmpty()) {
            return state;
        }
        BlockState blockstate = blockitemstateproperties.apply(state);
        if (blockstate != state) {
            level.setBlock(pos, blockstate, 2);
        }
        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState p_40594_, Property<T> p_40595_, String p_40596_) {
        return p_40595_.getValue(p_40596_).map(p_40592_ -> (BlockState)p_40594_.setValue(p_40595_, p_40592_)).orElse(p_40594_);
    }

    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundType().getPlaceSound();
    }

    protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
        return state.getSoundType((LevelReader)world, pos, (Entity)entity).getPlaceSound();
    }

    protected boolean updateCustomBlockEntityTag(BlockPos p_40597_, Level p_40598_, @Nullable Player p_40599_, ItemStack p_40600_, BlockState p_40601_) {
        return petTorch.updateCustomBlockEntityTag(p_40598_, p_40599_, p_40597_, p_40600_);
    }

    public static boolean updateCustomBlockEntityTag(Level level, @javax.annotation.Nullable Player player, BlockPos pos, ItemStack stack) {
        BlockEntity blockentity;
        MinecraftServer minecraftserver = level.getServer();
        if (minecraftserver == null) {
            return false;
        }
        CustomData customdata = (CustomData)stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, (Object)CustomData.EMPTY);
        if (!customdata.isEmpty() && (blockentity = level.getBlockEntity(pos)) != null) {
            if (level.isClientSide || !blockentity.onlyOpCanSetNbt() || player != null && player.canUseGameMasterBlocks()) {
                return customdata.loadInto(blockentity, (HolderLookup.Provider)level.registryAccess());
            }
            return false;
        }
        return false;
    }

    public InteractionResult place(BlockPlaceContext context) {
        Level worldIn = context.getLevel();
        Player playerIn = context.getPlayer();
        BlockPlaceContext blockplacecontext = this.updatePlacementContext(context);
        ItemStack itemstack = blockplacecontext.getItemInHand();
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResult.FAIL;
        }
        if (((Boolean)InventoryPetsConfig.disableTorch.get()).booleanValue()) {
            return InteractionResult.FAIL;
        }
        if (!worldIn.isClientSide && !playerIn.isCreative() && itemstack.getDamageValue() >= 4) {
            this.eatFlag = false;
            ItemStack itemstack2 = itemstack;
            playerIn.getInventory();
            for (int k = 0; k < 36; ++k) {
                ItemStack s2 = playerIn.getInventory().getItem(k);
                this.odFlag = false;
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    if (s2.is(InventoryPets.COAL_NUGGET)) {
                        this.odFlag = true;
                    }
                } else if (s2.is(InventoryPets.COAL)) {
                    this.odFlag = true;
                }
                if (s2 == ItemStack.EMPTY || !this.odFlag || this.eatFlag) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                }
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(itemstack2, 0);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.6f);
                this.eatFlag = true;
                this.complainFlag = false;
            }
            if (!this.eatFlag && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                int dmg = itemstack.getDamageValue();
                if (dmg == 0) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.6f, 1.4f);
            }
        }
        if (!(worldIn.isClientSide || context.canPlace() && itemstack.getDamageValue() <= 3)) {
            return InteractionResult.FAIL;
        }
        if (!worldIn.isClientSide) {
            blockplacecontext = this.updatePlacementContext(context);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            }
            BlockState blockstate = this.getPlacementState(blockplacecontext);
            if (blockstate == null) {
                return InteractionResult.FAIL;
            }
            if (!this.placeBlock(blockplacecontext, blockstate)) {
                return InteractionResult.FAIL;
            }
            if (!playerIn.isCrouching()) {
                BlockPos blockpos = blockplacecontext.getClickedPos();
                Level world = blockplacecontext.getLevel();
                Player playerentity = blockplacecontext.getPlayer();
                BlockState blockstate1 = world.getBlockState(blockpos);
                BlockState blockstate2 = this.getPlacementState(context);
                blockstate1 = this.updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
                this.updateCustomBlockEntityTag(blockpos, world, playerentity, itemstack, blockstate1);
                blockstate1.getBlock().setPlacedBy(world, blockpos, blockstate1, (LivingEntity)playerentity, itemstack);
                worldIn.setBlock(blockpos, blockstate2, 11);
                if (playerentity instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerentity, blockpos, itemstack);
                }
                if (!playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
                world.gameEvent((Entity)playerentity, (Holder)GameEvent.BLOCK_PLACE, blockpos);
                SoundType soundtype = blockstate1.getSoundType((LevelReader)world, blockpos, (Entity)playerentity);
                world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, playerentity), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
                return InteractionResult.SUCCESS;
            }
            BlockPos blockpos1 = blockplacecontext.getClickedPos();
            worldIn.setBlock(blockpos1, (BlockState)blockstate.setValue((Property)BlockStateProperties.LIT, (Comparable)Boolean.valueOf(true)), 11);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public InteractionResult useOn(UseOnContext context) {
        Player playerIn = context.getPlayer();
        Level worldIn = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        ItemStack itemstack = context.getItemInHand();
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResult.FAIL;
        }
        if (((Boolean)InventoryPetsConfig.disableTorch.get()).booleanValue()) {
            return InteractionResult.FAIL;
        }
        if (!worldIn.isClientSide) {
            if (!playerIn.isCrouching()) {
                InteractionResult actionresulttype = this.place(new BlockPlaceContext(context));
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.CHERRY_WOOD_PLACE, SoundSource.PLAYERS, 0.7f, 1.0f);
                return actionresulttype;
            }
            if (!(CampfireBlock.canLight((BlockState)blockstate) || CandleBlock.canLight((BlockState)blockstate) || CandleCakeBlock.canLight((BlockState)blockstate))) {
                BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
                if (BaseFireBlock.canBePlacedAt((Level)worldIn, (BlockPos)blockpos1, (Direction)context.getHorizontalDirection())) {
                    worldIn.playSound(playerIn, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, worldIn.getRandom().nextFloat() * 0.4f + 0.8f);
                    BlockState blockstate1 = BaseFireBlock.getState((BlockGetter)worldIn, (BlockPos)blockpos1);
                    worldIn.setBlock(blockpos1, blockstate1, 11);
                    worldIn.gameEvent((Entity)playerIn, (Holder)GameEvent.BLOCK_PLACE, blockpos);
                    if (playerIn instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerIn, blockpos1, itemstack);
                        if (playerIn != null && !playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                    }
                    return InteractionResult.sidedSuccess((boolean)worldIn.isClientSide());
                }
                return InteractionResult.FAIL;
            }
            worldIn.playSound(playerIn, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, worldIn.getRandom().nextFloat() * 0.4f + 0.8f);
            worldIn.setBlock(blockpos, (BlockState)blockstate.setValue((Property)BlockStateProperties.LIT, (Comparable)Boolean.valueOf(true)), 11);
            worldIn.gameEvent((Entity)playerIn, (Holder)GameEvent.BLOCK_PLACE, blockpos);
            if (playerIn instanceof ServerPlayer && playerIn != null && !playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
            return InteractionResult.sidedSuccess((boolean)worldIn.isClientSide());
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext p_40609_) {
        return p_40609_;
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.pettorch1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.pettorch2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodTorch.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.COAL.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_COAL.get()).getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_AQUA) + I18n.get((String)"tooltip.ip.fan", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.showSuggestors.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.suggestedby", (Object[])new Object[0]) + " Gubbot")));
        }
        if (((Boolean)InventoryPetsConfig.disableTorch.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

