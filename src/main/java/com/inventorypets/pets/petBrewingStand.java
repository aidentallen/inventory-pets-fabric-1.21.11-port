/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundSource
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
 *  net.minecraft.world.item.alchemy.PotionContents
 *  net.minecraft.world.item.alchemy.Potions
 *  net.minecraft.world.level.Level
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
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petBrewingStand
extends Item {
    private boolean eatFlag = false;
    private ItemStack[] potionList;
    private boolean complainFlag = false;
    private boolean customFood = false;
    private Item defaultFood;
    private int chkEat = 0;
    RandomPoolAlias rand = new RandomPoolAlias();

    public petBrewingStand(Item.Properties properties) {
        super(properties);
        this.potionList = new ItemStack[93];
        this.defaultFood = Items.NETHER_WART;
        this.potionList[0] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.FIRE_RESISTANCE);
        this.potionList[1] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.HARMING);
        this.potionList[2] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.HEALING);
        this.potionList[3] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.INVISIBILITY);
        this.potionList[4] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LEAPING);
        this.potionList[5] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.NIGHT_VISION);
        this.potionList[6] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.POISON);
        this.potionList[7] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.REGENERATION);
        this.potionList[8] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.SLOWNESS);
        this.potionList[9] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRENGTH);
        this.potionList[10] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.SWIFTNESS);
        this.potionList[11] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_FIRE_RESISTANCE);
        this.potionList[12] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_INVISIBILITY);
        this.potionList[13] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_LEAPING);
        this.potionList[14] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_NIGHT_VISION);
        this.potionList[15] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_POISON);
        this.potionList[16] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_REGENERATION);
        this.potionList[17] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_SLOWNESS);
        this.potionList[18] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_SWIFTNESS);
        this.potionList[19] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_WATER_BREATHING);
        this.potionList[20] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.LONG_WEAKNESS);
        this.potionList[21] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.WATER_BREATHING);
        this.potionList[22] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.WEAKNESS);
        this.potionList[23] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_HARMING);
        this.potionList[24] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_HEALING);
        this.potionList[25] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_HEALING);
        this.potionList[26] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_LEAPING);
        this.potionList[27] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_POISON);
        this.potionList[28] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_REGENERATION);
        this.potionList[29] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_STRENGTH);
        this.potionList[30] = PotionContents.createItemStack((Item)Items.POTION, (Holder)Potions.STRONG_SWIFTNESS);
        this.potionList[31] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.FIRE_RESISTANCE);
        this.potionList[32] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.HARMING);
        this.potionList[33] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.HEALING);
        this.potionList[34] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.INVISIBILITY);
        this.potionList[35] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LEAPING);
        this.potionList[36] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.NIGHT_VISION);
        this.potionList[37] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.POISON);
        this.potionList[38] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.REGENERATION);
        this.potionList[39] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.SLOWNESS);
        this.potionList[40] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRENGTH);
        this.potionList[41] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.SWIFTNESS);
        this.potionList[42] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_FIRE_RESISTANCE);
        this.potionList[43] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_INVISIBILITY);
        this.potionList[44] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_LEAPING);
        this.potionList[45] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_NIGHT_VISION);
        this.potionList[46] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_POISON);
        this.potionList[47] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_REGENERATION);
        this.potionList[48] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_SLOWNESS);
        this.potionList[49] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_SWIFTNESS);
        this.potionList[50] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_WATER_BREATHING);
        this.potionList[51] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.LONG_WEAKNESS);
        this.potionList[52] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.WATER_BREATHING);
        this.potionList[53] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.WEAKNESS);
        this.potionList[54] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_HARMING);
        this.potionList[55] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_HEALING);
        this.potionList[56] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_HEALING);
        this.potionList[57] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_LEAPING);
        this.potionList[58] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_POISON);
        this.potionList[59] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_REGENERATION);
        this.potionList[60] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_STRENGTH);
        this.potionList[61] = PotionContents.createItemStack((Item)Items.SPLASH_POTION, (Holder)Potions.STRONG_SWIFTNESS);
        this.potionList[62] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.FIRE_RESISTANCE);
        this.potionList[63] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.HARMING);
        this.potionList[64] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.HEALING);
        this.potionList[65] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.INVISIBILITY);
        this.potionList[66] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LEAPING);
        this.potionList[67] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.NIGHT_VISION);
        this.potionList[68] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.POISON);
        this.potionList[69] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.REGENERATION);
        this.potionList[70] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.SLOWNESS);
        this.potionList[71] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRENGTH);
        this.potionList[72] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.SWIFTNESS);
        this.potionList[73] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_FIRE_RESISTANCE);
        this.potionList[74] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_INVISIBILITY);
        this.potionList[75] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_LEAPING);
        this.potionList[76] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_NIGHT_VISION);
        this.potionList[77] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_POISON);
        this.potionList[78] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_REGENERATION);
        this.potionList[79] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_SLOWNESS);
        this.potionList[80] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_SWIFTNESS);
        this.potionList[81] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_WATER_BREATHING);
        this.potionList[82] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.LONG_WEAKNESS);
        this.potionList[83] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.WATER_BREATHING);
        this.potionList[84] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.WEAKNESS);
        this.potionList[85] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_HARMING);
        this.potionList[86] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_HEALING);
        this.potionList[87] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_HEALING);
        this.potionList[88] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_LEAPING);
        this.potionList[89] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_POISON);
        this.potionList[90] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_REGENERATION);
        this.potionList[91] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_STRENGTH);
        this.potionList[92] = PotionContents.createItemStack((Item)Items.LINGERING_POTION, (Holder)Potions.STRONG_SWIFTNESS);
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
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_BREWING_STAND.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBrewingStand.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableBrewingStand.get()).booleanValue()) {
            return;
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() > 0 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Items.NETHER_WART && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.BLAZE_ROD || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (!entityplayer.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && stack.getDamageValue() > 0 && this.chkEat > 40 && !worldIn.isClientSide) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Items.NETHER_WART && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.BLAZE_ROD || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
                this.complainFlag = false;
                this.eatFlag = true;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.complainFlag = false;
                    this.eatFlag = true;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.brew.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                    this.complainFlag = true;
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemEntity entityitem;
        ItemStack bob2;
        int pChk;
        RandomPoolAlias rand;
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableBrewingStand.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.brew.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!(!playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || itemstack.getDamageValue() != 0 || worldIn.isClientSide)) {
            rand = new RandomPoolAlias();
            pChk = rand.nextInt(93);
            bob2 = this.potionList[pChk].copy();
            entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
            worldIn.addFreshEntity((Entity)entityitem);
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.brewpot.get(), SoundSource.PLAYERS, 1.0f, 1.2f);
        }
        if (!playerIn.isCreative() && itemstack.getDamageValue() == 0 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide) {
            rand = new RandomPoolAlias();
            pChk = rand.nextInt(93);
            bob2 = this.potionList[pChk].copy();
            entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
            worldIn.addFreshEntity((Entity)entityitem);
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.brewpot.get(), SoundSource.PLAYERS, 1.0f, 1.2f);
            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petbrewingstand1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBrewingStand.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.NETHER_WART.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.BLAZE_ROD.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableBrewingStand.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

