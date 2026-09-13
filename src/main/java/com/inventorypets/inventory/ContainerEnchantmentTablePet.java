/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Util
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.HolderSet$Named
 *  net.minecraft.core.IdMap
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.tags.EnchantmentTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ContainerLevelAccess
 *  net.minecraft.world.inventory.DataSlot
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.EnchantmentInstance
 *  net.neoforged.neoforge.common.CommonHooks
 *  net.neoforged.neoforge.common.Tags$Items
 */
package com.inventorypets.inventory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IdMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.Tags;

public class ContainerEnchantmentTablePet
extends AbstractContainerMenu {
    private final Container enchantSlots = new SimpleContainer(2){

        public void setChanged() {
            super.setChanged();
            ContainerEnchantmentTablePet.this.slotsChanged((Container)this);
        }
    };
    private final ContainerLevelAccess access;
    private final RandomSource random = RandomSource.create();
    private final DataSlot enchantmentSeed = DataSlot.standalone();
    public final int[] costs = new int[3];
    public final int[] enchantClue = new int[]{-1, -1, -1};
    public final int[] levelClue = new int[]{-1, -1, -1};

    public ContainerEnchantmentTablePet(int p_39454_, Inventory p_39455_) {
        this(p_39454_, p_39455_, ContainerLevelAccess.NULL);
    }

    public ContainerEnchantmentTablePet(int p_39457_, Inventory p_39458_, ContainerLevelAccess p_39459_) {
        super(MenuType.ENCHANTMENT, p_39457_);
        this.access = p_39459_;
        this.addSlot(new Slot(this, this.enchantSlots, 0, 15, 47){

            public boolean mayPlace(ItemStack p_39508_) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this, this.enchantSlots, 1, 35, 47){

            public boolean mayPlace(ItemStack p_39517_) {
                return p_39517_.is(Tags.Items.ENCHANTING_FUELS);
            }
        });
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot((Container)p_39458_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot((Container)p_39458_, k, 8 + k * 18, 142));
        }
        this.addDataSlot(DataSlot.shared((int[])this.costs, (int)0));
        this.addDataSlot(DataSlot.shared((int[])this.costs, (int)1));
        this.addDataSlot(DataSlot.shared((int[])this.costs, (int)2));
        this.addDataSlot(this.enchantmentSeed).set(p_39458_.player.getEnchantmentSeed());
        this.addDataSlot(DataSlot.shared((int[])this.enchantClue, (int)0));
        this.addDataSlot(DataSlot.shared((int[])this.enchantClue, (int)1));
        this.addDataSlot(DataSlot.shared((int[])this.enchantClue, (int)2));
        this.addDataSlot(DataSlot.shared((int[])this.levelClue, (int)0));
        this.addDataSlot(DataSlot.shared((int[])this.levelClue, (int)1));
        this.addDataSlot(DataSlot.shared((int[])this.levelClue, (int)2));
    }

    private float getPower() {
        return 100.0f;
    }

    public void slotsChanged(Container p_39461_) {
        if (p_39461_ == this.enchantSlots) {
            ItemStack itemstack = p_39461_.getItem(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {
                this.access.execute((level, blockPos) -> {
                    IdMap idmap = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).asHolderIdMap();
                    float j = 0.0f;
                    j += this.getPower();
                    this.random.setSeed((long)this.enchantmentSeed.get());
                    for (int k = 0; k < 3; ++k) {
                        this.costs[k] = EnchantmentHelper.getEnchantmentCost((RandomSource)this.random, (int)k, (int)((int)j), (ItemStack)itemstack);
                        this.enchantClue[k] = -1;
                        this.levelClue[k] = -1;
                        if (this.costs[k] < k + 1) {
                            this.costs[k] = 0;
                        }
                        this.costs[k] = EnchantmentHelper.getEnchantmentCost((RandomSource)this.random, (int)k, (int)((int)j), (ItemStack)itemstack);
                    }
                    for (int l = 0; l < 3; ++l) {
                        List<EnchantmentInstance> list;
                        if (this.costs[l] <= 0 || (list = this.getEnchantmentList(level.registryAccess(), itemstack, l, this.costs[l])) == null || list.isEmpty()) continue;
                        EnchantmentInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
                        this.enchantClue[l] = idmap.getId((Object)enchantmentinstance.enchantment);
                        this.levelClue[l] = enchantmentinstance.level;
                    }
                    this.broadcastChanges();
                });
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }
    }

    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            ItemStack itemstack1 = this.enchantSlots.getItem(1);
            int i = id + 1;
            if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !player.hasInfiniteMaterials()) {
                return false;
            }
            if (this.costs[id] <= 0 || itemstack.isEmpty() || (player.experienceLevel < i || player.experienceLevel < this.costs[id]) && !player.getAbilities().instabuild) {
                return false;
            }
            this.access.execute((p_347276_, p_347277_) -> {
                ItemStack itemstack2 = itemstack;
                List<EnchantmentInstance> list = this.getEnchantmentList(p_347276_.registryAccess(), itemstack, id, this.costs[id]);
                if (!list.isEmpty()) {
                    player.onEnchantmentPerformed(itemstack, i);
                    itemstack2 = itemstack.getItem().applyEnchantments(itemstack, list);
                    this.enchantSlots.setItem(0, itemstack2);
                    CommonHooks.onPlayerEnchantItem((Player)player, (ItemStack)itemstack2, list);
                    itemstack1.consume(i, (LivingEntity)player);
                    if (itemstack1.isEmpty()) {
                        this.enchantSlots.setItem(1, ItemStack.EMPTY);
                    }
                    player.awardStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, itemstack2, i);
                    }
                    this.enchantSlots.setChanged();
                    this.enchantmentSeed.set(player.getEnchantmentSeed());
                    this.slotsChanged(this.enchantSlots);
                    p_347276_.playSound(null, p_347277_, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0f, p_347276_.random.nextFloat() * 0.1f + 0.9f);
                }
            });
            return true;
        }
        Util.logAndPauseIfInIde((String)(String.valueOf(player.getName()) + " pressed invalid button id: " + id));
        return false;
    }

    private List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost) {
        this.random.setSeed((long)(this.enchantmentSeed.get() + slot));
        Optional optional = registryAccess.registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return List.of();
        }
        List list = EnchantmentHelper.selectEnchantment((RandomSource)this.random, (ItemStack)stack, (int)cost, (Stream)((HolderSet.Named)optional.get()).stream());
        if (stack.is(Items.BOOK) && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }
        return list;
    }

    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    public int getEnchantmentSeed() {
        return this.enchantmentSeed.get();
    }

    public void removed(Player p_39488_) {
        super.removed(p_39488_);
        this.access.execute((p_39469_, p_39470_) -> this.clearContainer(p_39488_, this.enchantSlots));
    }

    public boolean stillValid(Player p_39463_) {
        return true;
    }

    public ItemStack quickMoveStack(Player p_39490_, int p_39491_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(p_39491_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39491_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39491_ == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.is(Items.LAPIS_LAZULI)) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasItem() || !((Slot)this.slots.get(0)).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }
                ItemStack itemstack2 = itemstack1.copy();
                itemstack2.setCount(1);
                itemstack1.shrink(1);
                ((Slot)this.slots.get(0)).set(itemstack2);
            }
            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(p_39490_, itemstack1);
        }
        return itemstack;
    }
}

