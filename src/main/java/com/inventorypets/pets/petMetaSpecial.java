/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.AdvancementHelper;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.pets.petMeta;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class petMetaSpecial
extends petMeta {
    private int basePet = 60;
    RandomPoolAlias rand = new RandomPoolAlias();

    public petMetaSpecial(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (((Boolean)InventoryPetsConfig.disableMeta.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        ItemStack[] petList = new ItemStack[66];
        RandomPoolAlias rand = new RandomPoolAlias();
        if (!worldIn.isClientSide) {
            int dropLevel;
            petList[0] = new ItemStack((ItemLike)InventoryPets.PET_DUBSTEP.get(), 1);
            petList[1] = new ItemStack((ItemLike)InventoryPets.PET_HEART.get(), 1);
            petList[2] = new ItemStack((ItemLike)InventoryPets.PET_SHIELD.get(), 1);
            petList[3] = new ItemStack((ItemLike)InventoryPets.PET_MOON.get(), 1);
            petList[4] = new ItemStack((ItemLike)InventoryPets.PET_APPLE.get(), 1);
            petList[5] = new ItemStack((ItemLike)InventoryPets.PET_CHEETAH.get(), 1);
            petList[6] = new ItemStack((ItemLike)InventoryPets.PET_PIXIE.get(), 1);
            petList[7] = new ItemStack((ItemLike)InventoryPets.PET_PACMAN.get(), 1);
            petList[8] = new ItemStack((ItemLike)InventoryPets.PET_SILVERFISH.get(), 1);
            petList[9] = new ItemStack((ItemLike)InventoryPets.PET_WOLF.get(), 1);
            petList[10] = new ItemStack((ItemLike)InventoryPets.PET_TORCH.get(), 1);
            petList[11] = new ItemStack((ItemLike)InventoryPets.PET_HOUSE.get(), 1);
            petList[12] = new ItemStack((ItemLike)InventoryPets.PET_BLAZE.get(), 1);
            petList[13] = new ItemStack((ItemLike)InventoryPets.PET_CREEPER.get(), 1);
            petList[14] = new ItemStack((ItemLike)InventoryPets.PET_ENDERMAN.get(), 1);
            petList[15] = new ItemStack((ItemLike)InventoryPets.PET_GHAST.get(), 1);
            petList[16] = new ItemStack((ItemLike)InventoryPets.PET_IRON_GOLEM.get(), 1);
            petList[17] = new ItemStack((ItemLike)InventoryPets.PET_MAGMA_CUBE.get(), 1);
            petList[18] = new ItemStack((ItemLike)InventoryPets.PET_SNOW_GOLEM.get(), 1);
            petList[19] = new ItemStack((ItemLike)InventoryPets.PET_SPIDER.get(), 1);
            petList[20] = new ItemStack((ItemLike)InventoryPets.PET_CHICKEN.get(), 1);
            petList[21] = new ItemStack((ItemLike)InventoryPets.PET_COW.get(), 1);
            petList[22] = new ItemStack((ItemLike)InventoryPets.PET_MOOSHROOM.get(), 1);
            petList[23] = new ItemStack((ItemLike)InventoryPets.PET_OCELOT.get(), 1);
            petList[24] = new ItemStack((ItemLike)InventoryPets.PET_PIG.get(), 1);
            petList[25] = new ItemStack((ItemLike)InventoryPets.PET_SHEEP.get(), 1);
            petList[26] = new ItemStack((ItemLike)InventoryPets.PET_SQUID.get(), 1);
            petList[27] = new ItemStack((ItemLike)InventoryPets.PET_FLYING_SADDLE.get(), 1);
            petList[28] = new ItemStack((ItemLike)InventoryPets.PET_DINGOT.get(), 1);
            petList[29] = new ItemStack((ItemLike)InventoryPets.PET_BIOME.get(), 1);
            petList[30] = new ItemStack((ItemLike)InventoryPets.PET_LOOT.get(), 1);
            petList[31] = new ItemStack((ItemLike)InventoryPets.PET_META.get(), 1);
            petList[32] = new ItemStack((ItemLike)InventoryPets.PET_MICKERSON.get(), 1);
            petList[33] = new ItemStack((ItemLike)InventoryPets.PET_PINGOT.get(), 1);
            petList[34] = new ItemStack((ItemLike)InventoryPets.PET_PCOW.get(), 1);
            petList[35] = new ItemStack((ItemLike)InventoryPets.PET_QCM.get(), 1);
            petList[36] = new ItemStack((ItemLike)InventoryPets.PET_QUIVER.get(), 1);
            petList[37] = new ItemStack((ItemLike)InventoryPets.PET_SPONGE.get(), 1);
            petList[38] = new ItemStack((ItemLike)InventoryPets.PET_BANANA.get(), 1);
            petList[39] = new ItemStack((ItemLike)InventoryPets.PET_BED.get(), 1);
            petList[40] = new ItemStack((ItemLike)InventoryPets.PET_BREWING_STAND.get(), 1);
            petList[41] = new ItemStack((ItemLike)InventoryPets.PET_CHEST.get(), 1);
            petList[42] = new ItemStack((ItemLike)InventoryPets.PET_CRAFTING_TABLE.get(), 1);
            petList[43] = new ItemStack((ItemLike)InventoryPets.PET_DOUBLE_CHEST.get(), 1);
            petList[44] = new ItemStack((ItemLike)InventoryPets.PET_ENCHANTING_TABLE.get(), 1);
            petList[45] = new ItemStack((ItemLike)InventoryPets.PET_ENDER_CHEST.get(), 1);
            petList[46] = new ItemStack((ItemLike)InventoryPets.PET_FURNACE.get(), 1);
            petList[47] = new ItemStack((ItemLike)InventoryPets.PET_JUKEBOX.get(), 1);
            petList[48] = new ItemStack((ItemLike)InventoryPets.PET_LEAD.get(), 1);
            petList[49] = new ItemStack((ItemLike)InventoryPets.PET_NETHER_PORTAL.get(), 1);
            petList[50] = new ItemStack((ItemLike)InventoryPets.PET_SADDLE.get(), 1);
            petList[51] = new ItemStack((ItemLike)InventoryPets.PET_ANVIL.get(), 1);
            petList[52] = new ItemStack((ItemLike)InventoryPets.PET_JUGGERNAUT.get(), 1);
            petList[53] = new ItemStack((ItemLike)InventoryPets.PET_SIAMESE.get(), 1);
            petList[54] = new ItemStack((ItemLike)InventoryPets.PET_ILLUMINATI.get(), 1);
            petList[55] = new ItemStack((ItemLike)InventoryPets.PET_MENORAH.get(), 1);
            petList[56] = new ItemStack((ItemLike)InventoryPets.PET_MISHUMAA_SABA.get(), 1);
            petList[57] = new ItemStack((ItemLike)InventoryPets.PET_POLITICALLY_CORRECT.get(), 1);
            petList[58] = new ItemStack((ItemLike)InventoryPets.PET_APRIL_FOOL.get(), 1);
            petList[59] = new ItemStack((ItemLike)InventoryPets.PET_CHRISTMAS_TREE.get(), 1);
            petList[60] = new ItemStack((ItemLike)InventoryPets.PET_BLACK_HOLE.get(), 1);
            petList[61] = new ItemStack((ItemLike)InventoryPets.PET_CLOUD.get(), 1);
            petList[62] = new ItemStack((ItemLike)InventoryPets.PET_PUFFERFISH.get(), 1);
            petList[63] = new ItemStack((ItemLike)InventoryPets.PET_SLIME.get(), 1);
            petList[64] = new ItemStack((ItemLike)InventoryPets.PET_SUN.get(), 1);
            petList[65] = new ItemStack((ItemLike)InventoryPets.PET_WITHER.get(), 1);
            int i = 0;
            i = this.basePet == 20 ? rand.nextInt(3) : (this.basePet == 30 ? 3 + rand.nextInt(8) : (this.basePet == 40 ? 12 + rand.nextInt(8) : (this.basePet == 50 ? 20 + rand.nextInt(7) : (this.basePet == 60 ? 27 + rand.nextInt(12) : (this.basePet == 70 ? 39 + rand.nextInt(13) : (this.basePet == 80 ? 52 + rand.nextInt(3) : (this.basePet == 90 ? 55 + rand.nextInt(5) : (this.basePet == 100 ? (rand.nextInt(100) < 25 ? 60 + rand.nextInt(7) : rand.nextInt(59)) : (this.basePet == 101 ? (rand.nextInt(100) < 50 ? 59 + rand.nextInt(7) : rand.nextInt(59)) : (this.basePet == 102 ? (rand.nextInt(100) < 75 ? 59 + rand.nextInt(7) : rand.nextInt(59)) : (this.basePet == 103 ? 59 + rand.nextInt(7) : (rand.nextInt(100) == 1 ? 59 + rand.nextInt(7) : ((dropLevel = rand.nextInt(100)) >= 0 && dropLevel <= 20 ? 12 + rand.nextInt(8) : (dropLevel > 20 && dropLevel <= 45 ? 20 + rand.nextInt(7) : (dropLevel > 45 && dropLevel <= 70 ? 39 + rand.nextInt(13) : (dropLevel > 70 && dropLevel <= 75 ? 27 + rand.nextInt(12) : (dropLevel > 75 && dropLevel <= 80 ? 4 + rand.nextInt(8) : (dropLevel > 80 && dropLevel <= 87 ? 52 + rand.nextInt(3) : (dropLevel > 87 && dropLevel <= 94 ? 55 + rand.nextInt(5) : rand.nextInt(4))))))))))))))))))));
            ItemStack bob = petList[i].copy();
            if (handIn == InteractionHand.OFF_HAND) {
                this.removeOffHandItem(playerIn);
            } else {
                this.removeItem(playerIn, itemstack);
            }
            ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob);
            worldIn.addFreshEntity((Entity)entityitem);
            if (!worldIn.isClientSide) {
                if (i == 31 && !AdvancementHelper.hasAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"meta_meta"))) {
                    AdvancementHelper.unlockAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"meta_meta"));
                }
                if (!AdvancementHelper.hasAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"use_meta"))) {
                    AdvancementHelper.unlockAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"use_meta"));
                }
            }
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.meta_transform.get(), SoundSource.PLAYERS, 0.6f, 0.8f);
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        Object metaScore = I18n.get((String)"tooltip.ip.random", (Object[])new Object[0]);
        int meta = this.basePet;
        metaScore = meta == 20 ? I18n.get((String)"tooltip.ip.aoe", (Object[])new Object[0]) : (meta == 30 ? I18n.get((String)"tooltip.ip.fan", (Object[])new Object[0]) : (meta == 40 ? I18n.get((String)"tooltip.ip.mob", (Object[])new Object[0]) : (meta == 50 ? I18n.get((String)"tooltip.ip.peaceful", (Object[])new Object[0]) : (meta == 60 ? I18n.get((String)"tooltip.ip.special", (Object[])new Object[0]) : (meta == 70 ? I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]) : (meta == 80 ? I18n.get((String)"tooltip.ip.youtuber", (Object[])new Object[0]) : (meta == 90 ? I18n.get((String)"tooltip.ip.fan", (Object[])new Object[0]) : (meta == 100 ? I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]) + " 25%" : (meta == 101 ? I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]) + " 50%" : (meta == 102 ? I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]) + " 75%" : (meta == 103 ? I18n.get((String)"tooltip.ip.legendary", (Object[])new Object[0]) + " 100%" : I18n.get((String)"tooltip.ip.random", (Object[])new Object[0]))))))))))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmeta1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petmeta2", (Object[])new Object[0]) + " " + (String)metaScore)));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.BLUE) + I18n.get((String)"tooltip.ip.special", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableMeta.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

