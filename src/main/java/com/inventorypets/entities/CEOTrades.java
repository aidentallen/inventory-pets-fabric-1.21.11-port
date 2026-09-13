/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.trading.ItemCost
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.Nullable
 */
package com.inventorypets.entities;

import com.google.common.collect.ImmutableMap;
import com.inventorypets.InventoryPets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class CEOTrades {
    public static final Int2ObjectMap<ItemListing[]> field_221240_b = CEOTrades.gatAsIntMap((ImmutableMap<Integer, ItemListing[]>)ImmutableMap.of((Object)1, (Object)new ItemListing[]{new ItemsForEmeralds(Items.SLIME_BALL, 4, 1, 5, 1), new ItemsForEmeralds(Items.GLOWSTONE, 2, 1, 5, 1), new ItemsForEmeralds(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new ItemsForEmeralds(Items.FERN, 1, 1, 12, 1), new ItemsForEmeralds(Items.SUGAR_CANE, 1, 1, 8, 1), new ItemsForEmeralds(Items.PUMPKIN, 1, 1, 4, 1), new ItemsForEmeralds(Items.KELP, 3, 1, 12, 1), new ItemsForEmeralds(Items.CACTUS, 3, 1, 8, 1), new ItemsForEmeralds(Items.DANDELION, 1, 1, 12, 1), new ItemsForEmeralds(Items.POPPY, 1, 1, 12, 1), new ItemsForEmeralds(Items.BLUE_ORCHID, 1, 1, 8, 1), new ItemsForEmeralds(Items.ALLIUM, 1, 1, 12, 1), new ItemsForEmeralds(Items.AZURE_BLUET, 1, 1, 12, 1), new ItemsForEmeralds(Items.RED_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.ORANGE_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.WHITE_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.PINK_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.OXEYE_DAISY, 1, 1, 12, 1), new ItemsForEmeralds(Items.CORNFLOWER, 1, 1, 12, 1), new ItemsForEmeralds(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new ItemsForEmeralds(Items.WHEAT_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.MELON_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.ACACIA_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.BIRCH_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.DARK_OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.RED_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.WHITE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.PINK_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BLACK_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.GREEN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.MAGENTA_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.YELLOW_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.PURPLE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIME_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.ORANGE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BROWN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.CYAN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.VINE, 1, 1, 12, 1), new ItemsForEmeralds(Items.BROWN_MUSHROOM, 1, 1, 12, 1), new ItemsForEmeralds(Items.RED_MUSHROOM, 1, 1, 12, 1), new ItemsForEmeralds(Items.LILY_PAD, 1, 2, 5, 1), new ItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1)}, (Object)2, (Object)new ItemListing[]{new ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PACKED_ICE, 3, 1, 6, 1), new ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1), new ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1)}));
    public static final Int2ObjectMap<ItemListing[]> BILL_GATES = CEOTrades.gatAsIntMap((ImmutableMap<Integer, ItemListing[]>)ImmutableMap.of((Object)1, (Object)new ItemListing[]{new ItemsForEmeralds((Item)InventoryPets.WINDOWS_31.get(), 1, 1, 1, 1), new ItemsForEmeralds((Item)InventoryPets.BLUE_SCREEN.get(), 1, 1, 1, 1), new ItemsForEmeralds((Item)InventoryPets.WINDOWS_ME.get(), 1, 1, 1, 1), new ItemsForEmeralds((Item)InventoryPets.WINDOWS_XP.get(), 1, 1, 1, 1), new ItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1)}, (Object)2, (Object)new ItemListing[]{new ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PACKED_ICE, 3, 1, 6, 1), new ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1), new ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1)}));
    public static final Int2ObjectMap<ItemListing[]> STEVE_BALLMER = CEOTrades.gatAsIntMap((ImmutableMap<Integer, ItemListing[]>)ImmutableMap.of((Object)1, (Object)new ItemListing[]{new ItemsForEmeralds((Item)InventoryPets.WINDOWS_7.get(), 1, 1, 1, 1), new ItemsForEmeralds((Item)InventoryPets.WINDOWS_MOJAVE.get(), 1, 1, 1, 1), new ItemsForEmeralds((Item)InventoryPets.WINDOWS_8.get(), 1, 1, 1, 1), new ItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1)}, (Object)2, (Object)new ItemListing[]{new ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PACKED_ICE, 3, 1, 6, 1), new ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1), new ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1)}));
    public static final Int2ObjectMap<ItemListing[]> SATYA_NADELLA = CEOTrades.gatAsIntMap((ImmutableMap<Integer, ItemListing[]>)ImmutableMap.of((Object)1, (Object)new ItemListing[]{new ItemsForEmeralds((Item)InventoryPets.START_BUTTON.get(), 1, 1, 1, 1), new ItemsForEmeralds((Item)InventoryPets.PET_CLOUD.get(), 1, 1, 1, 1), new ItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1)}, (Object)2, (Object)new ItemListing[]{new ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PACKED_ICE, 3, 1, 6, 1), new ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1), new ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1)}));

    private static Int2ObjectMap<ItemListing[]> gatAsIntMap(ImmutableMap<Integer, ItemListing[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap(p_221238_0_);
    }

    public static interface ItemListing {
        @Nullable
        public MerchantOffer getOffer(Entity var1, RandomSource var2);
    }

    static class ItemsForEmeralds
    implements ItemListing {
        private final ItemStack field_221208_a;
        private final int emeraldCount;
        private final int field_221210_c;
        private final int field_221211_d;
        private final int field_221212_e;
        private final float field_221213_f;

        public ItemsForEmeralds(Block p_i50528_1_, int p_i50528_2_, int p_i50528_3_, int p_i50528_4_, int p_i50528_5_) {
            this(new ItemStack((ItemLike)p_i50528_1_), p_i50528_2_, p_i50528_3_, p_i50528_4_, p_i50528_5_);
        }

        public ItemsForEmeralds(Item p_i50529_1_, int p_i50529_2_, int p_i50529_3_, int p_i50529_4_) {
            this(new ItemStack((ItemLike)p_i50529_1_), p_i50529_2_, p_i50529_3_, 12, p_i50529_4_);
        }

        public ItemsForEmeralds(Item p_i50530_1_, int p_i50530_2_, int p_i50530_3_, int p_i50530_4_, int p_i50530_5_) {
            this(new ItemStack((ItemLike)p_i50530_1_), p_i50530_2_, p_i50530_3_, p_i50530_4_, p_i50530_5_);
        }

        public ItemsForEmeralds(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_) {
            this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05f);
        }

        public ItemsForEmeralds(ItemStack p_i50532_1_, int p_i50532_2_, int p_i50532_3_, int p_i50532_4_, int p_i50532_5_, float p_i50532_6_) {
            this.field_221208_a = p_i50532_1_;
            this.emeraldCount = p_i50532_2_;
            this.field_221210_c = p_i50532_3_;
            this.field_221211_d = p_i50532_4_;
            this.field_221212_e = p_i50532_5_;
            this.field_221213_f = p_i50532_6_;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            if (this.field_221208_a.getItem() == InventoryPets.WINDOWS_31.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.XEROX_PARC_GUI.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.BLUE_SCREEN.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.WINDOWS_31.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.WINDOWS_ME.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.BLUE_SCREEN.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.WINDOWS_XP.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.WINDOWS_ME.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.WINDOWS_7.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.WINDOWS_XP.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.WINDOWS_MOJAVE.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.WINDOWS_7.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.WINDOWS_8.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.WINDOWS_MOJAVE.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.START_BUTTON.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.WINDOWS_8.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            if (this.field_221208_a.getItem() == InventoryPets.PET_CLOUD.get()) {
                return new MerchantOffer(new ItemCost((ItemLike)InventoryPets.START_BUTTON.get(), this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
            }
            return new MerchantOffer(new ItemCost((ItemLike)Items.EMERALD, this.emeraldCount), new ItemStack((ItemLike)this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
        }
    }

    static class ItemsForEmeraldsAndItems
    implements ItemListing {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final int emeraldCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsAndItems(ItemLike p_i50533_1_, int p_i50533_2_, Item p_i50533_3_, int p_i50533_4_, int p_i50533_5_, int p_i50533_6_) {
            this(p_i50533_1_, p_i50533_2_, 1, p_i50533_3_, p_i50533_4_, p_i50533_5_, p_i50533_6_);
        }

        public ItemsForEmeraldsAndItems(ItemLike p_i50534_1_, int p_i50534_2_, int p_i50534_3_, Item p_i50534_4_, int p_i50534_5_, int p_i50534_6_, int p_i50534_7_) {
            this.buyingItem = new ItemStack(p_i50534_1_);
            this.buyingItemCount = p_i50534_2_;
            this.emeraldCount = p_i50534_3_;
            this.sellingItem = new ItemStack((ItemLike)p_i50534_4_);
            this.sellingItemCount = p_i50534_5_;
            this.maxUses = p_i50534_6_;
            this.xpValue = p_i50534_7_;
            this.priceMultiplier = 0.05f;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            return new MerchantOffer(new ItemCost((ItemLike)Items.EMERALD, this.emeraldCount), Optional.of(new ItemCost((ItemLike)this.buyingItem.getItem(), this.buyingItemCount)), new ItemStack((ItemLike)this.sellingItem.getItem(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class EmeraldForItems
    implements ItemListing {
        private final Item tradeItem;
        private final int count;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EmeraldForItems(ItemLike tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
            this.tradeItem = tradeItemIn.asItem();
            this.count = countIn;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
            this.priceMultiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource rand) {
            ItemCost itemstack = new ItemCost((ItemLike)this.tradeItem, this.count);
            if (itemstack.item() == InventoryPets.BLUE_SCREEN.get()) {
                return new MerchantOffer(itemstack, new ItemStack((ItemLike)InventoryPets.XEROX_PARC_GUI.get()), this.maxUses, this.xpValue, this.priceMultiplier);
            }
            return new MerchantOffer(itemstack, new ItemStack((ItemLike)Items.EMERALD), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }
}

