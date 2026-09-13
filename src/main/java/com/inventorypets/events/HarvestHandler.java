/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.HolderLookup$RegistryLookup
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.item.enchantment.ItemEnchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DropExperienceBlock
 *  net.minecraft.world.level.block.RedStoneOreBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.ModList
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.Tags$Blocks
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$BreakSpeed
 *  net.neoforged.neoforge.event.level.BlockDropsEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

@EventBusSubscriber(modid="inventorypets")
public class HarvestHandler {
    @SubscribeEvent
    public static void notifyHarvest(BlockDropsEvent event) {
        boolean OreMessage = false;
        boolean lootchk = false;
        if (event.getBreaker() instanceof Player) {
            Player entityplayer = (Player)event.getBreaker();
            Level worldIn = entityplayer.level();
            if (entityplayer != null && !entityplayer.level().isClientSide) {
                ItemEnchantments enchants;
                ItemStack itemchk;
                int i;
                ItemStack lootstack = entityplayer.getItemInHand(entityplayer.getUsedItemHand());
                for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disablePixie.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_PIXIE.get() || itemchk.getDamageValue() != 0) continue;
                    event.setDroppedExperience(event.getDroppedExperience() * 2);
                }
                if (lootstack.isEnchanted() && (enchants = (ItemEnchantments)lootstack.get(DataComponents.ENCHANTMENTS)) != null && !enchants.isEmpty()) {
                    RegistryAccess lookupProvider = entityplayer.level().registryAccess();
                    Holder.Reference silkTouch = ((HolderLookup.RegistryLookup)lookupProvider.lookup(Registries.ENCHANTMENT).get()).getOrThrow(Enchantments.SILK_TOUCH);
                    Holder.Reference fortune = ((HolderLookup.RegistryLookup)lookupProvider.lookup(Registries.ENCHANTMENT).get()).getOrThrow(Enchantments.FORTUNE);
                    Holder.Reference looting = ((HolderLookup.RegistryLookup)lookupProvider.lookup(Registries.ENCHANTMENT).get()).getOrThrow(Enchantments.LOOTING);
                    if (enchants.getLevel((Holder)silkTouch) > 0 || enchants.getLevel((Holder)fortune) > 0 || enchants.getLevel((Holder)looting) > 0) {
                        lootchk = true;
                    }
                }
                block1: for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableLoot.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_LOOT.get() || itemchk.getDamageValue() != 0) continue;
                    if (ModList.get().isLoaded("ftbultimine") && !((Boolean)InventoryPetsConfig.enableLootWithFTBUltimine.get()).booleanValue()) {
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"item.loot.ftbultimine", (Object[])new Object[0]));
                        return;
                    }
                    if (lootchk && !OreMessage) {
                        OreMessage = true;
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"item.loot.noenchants", (Object[])new Object[0]));
                        continue;
                    }
                    Block chkBlock = event.getState().getBlock();
                    Level world = entityplayer.level();
                    if (Block.getDrops((BlockState)event.getState(), (ServerLevel)((ServerLevel)world), (BlockPos)event.getPos(), null) == null) continue;
                    List stacks = Block.getDrops((BlockState)event.getState(), (ServerLevel)((ServerLevel)world), (BlockPos)event.getPos(), null);
                    for (int k = 0; k < stacks.size(); ++k) {
                        ItemStack itemstack2 = ((ItemStack)stacks.get(k)).copy();
                        if (chkBlock.getDescriptionId() == null || chkBlock.getDescriptionId().contains("ore_shade") || chkBlock.getDescriptionId().contains("ore_light") || !event.getState().getBlock().getDescriptionId().contains("ore") && !event.getState().getBlock().getDescriptionId().contains("netherquartz") && !(event.getState().getBlock() instanceof DropExperienceBlock | event.getState().getBlock() instanceof RedStoneOreBlock)) continue;
                        if (itemstack2.getDescriptionId() != null && (event.getState().is(Tags.Blocks.ORES) || itemstack2.getDescriptionId().contains("ore") || event.getState().getBlock() instanceof DropExperienceBlock || event.getState().getBlock() instanceof RedStoneOreBlock)) {
                            ExperienceOrb xp;
                            ItemEntity entityitem;
                            ItemStack itemstack4;
                            ItemStack result = ItemHelper.getFurnaceResult(worldIn, itemstack2);
                            if (result != ItemStack.EMPTY && !event.getState().getBlock().getDescriptionId().contains("coal")) {
                                itemstack4 = result.copy();
                                itemstack4.setCount(itemstack2.getCount() * 2);
                                entityitem = new ItemEntity(world, (double)event.getPos().getX() + 0.5, (double)event.getPos().getY() + 0.5, (double)event.getPos().getZ() + 0.5, itemstack4);
                                world.addFreshEntity((Entity)entityitem);
                                world.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
                                xp = new ExperienceOrb(world, (double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getZ(), event.getDroppedExperience());
                                world.addFreshEntity((Entity)xp);
                                event.setCanceled(true);
                                continue block1;
                            }
                            if (result != ItemStack.EMPTY) {
                                itemstack4 = result.copy();
                                itemstack4.setCount(itemstack2.getCount() * 2);
                                entityitem = new ItemEntity(world, (double)event.getPos().getX() + 0.5, (double)event.getPos().getY() + 0.5, (double)event.getPos().getZ() + 0.5, itemstack4);
                                world.addFreshEntity((Entity)entityitem);
                                world.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
                                xp = new ExperienceOrb(world, (double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getZ(), event.getDroppedExperience());
                                world.addFreshEntity((Entity)xp);
                                event.setCanceled(true);
                                continue block1;
                            }
                            if (result != ItemStack.EMPTY || !(event.getState().getBlock() instanceof DropExperienceBlock)) continue;
                            itemstack4 = (ItemStack)Block.getDrops((BlockState)event.getState(), (ServerLevel)((ServerLevel)world), (BlockPos)event.getPos(), null).get(0);
                            itemstack4.setCount(itemstack2.getCount() * 2);
                            entityitem = new ItemEntity(world, (double)event.getPos().getX() + 0.5, (double)event.getPos().getY() + 0.5, (double)event.getPos().getZ() + 0.5, itemstack4);
                            world.addFreshEntity((Entity)entityitem);
                            world.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
                            xp = new ExperienceOrb(world, (double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getZ(), event.getDroppedExperience());
                            world.addFreshEntity((Entity)xp);
                            event.setCanceled(true);
                            continue;
                        }
                        if (itemstack2 == ItemStack.EMPTY) continue;
                        itemstack2.setCount(itemstack2.getCount() * 2);
                        ItemEntity entityitem = new ItemEntity(world, (double)event.getPos().getX() + 0.5, (double)event.getPos().getY() + 0.5, (double)event.getPos().getZ() + 0.5, itemstack2);
                        world.addFreshEntity((Entity)entityitem);
                        world.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
                        ExperienceOrb xp = new ExperienceOrb(world, (double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getZ(), event.getDroppedExperience());
                        world.addFreshEntity((Entity)xp);
                        event.setCanceled(true);
                        continue block1;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerBreaking(PlayerEvent.BreakSpeed event) {
        if (!(event.getEntity() instanceof FakePlayer)) {
            ItemStack itemchk;
            int i;
            boolean chkFlag1 = false;
            boolean chkFlag2 = false;
            boolean chkFlag3 = false;
            int invSize = ItemHelper.getHotbarSize() - 1;
            for (i = 0; i <= invSize; ++i) {
                itemchk = event.getEntity().getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableCloud.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_CLOUD.get() || itemchk.getDamageValue() >= 3) continue;
                chkFlag1 = true;
                break;
            }
            for (i = 0; i <= invSize; ++i) {
                itemchk = event.getEntity().getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableSquid.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_SQUID.get() || itemchk.getDamageValue() >= 1) continue;
                chkFlag2 = true;
                break;
            }
            for (i = 0; i <= invSize; ++i) {
                itemchk = event.getEntity().getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableMagmaCube.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_MAGMA_CUBE.get() || itemchk.getDamageValue() >= 1) continue;
                chkFlag3 = true;
                break;
            }
            if (chkFlag1 || chkFlag2 || chkFlag3) {
                float hardness = event.getEntity().level().getBlockState((BlockPos)event.getPosition().get()).getBlock().defaultDestroyTime();
                if (!event.getEntity().onGround() && chkFlag1) {
                    event.setNewSpeed(event.getNewSpeed() * 5.0f);
                    if (hardness > 50.0f) {
                        event.setNewSpeed(1.0f + hardness);
                    }
                }
                if (event.getEntity().isInWater() && chkFlag2) {
                    event.setNewSpeed(event.getNewSpeed() * 10.0f);
                    if (hardness > 50.0f) {
                        event.setNewSpeed(1.0f + hardness);
                    }
                }
                if (event.getEntity().isInLava() && chkFlag3) {
                    event.setNewSpeed(event.getNewSpeed() * 8.0f);
                    if (hardness > 50.0f) {
                        event.setNewSpeed(1.0f + hardness);
                    }
                }
            }
        }
    }
}

