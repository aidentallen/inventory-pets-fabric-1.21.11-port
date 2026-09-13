/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.item.crafting.SmeltingRecipe
 *  net.minecraft.world.level.Level
 */
package com.inventorypets.helper;

import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

public class ItemHelper {
    public static int getHotbarSize() {
        return 9;
    }

    public static int getPetSlot(Player player, Item itemIn, int maxDamage) {
        int foundFlag = -1;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = player.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != itemIn || itemchk.getDamageValue() >= maxDamage) continue;
            return i;
        }
        return foundFlag;
    }

    public static ItemStack getFurnaceResult(Level worldIn, ItemStack stackIn) {
        List recipes = worldIn.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING);
        ItemStack result = ItemStack.EMPTY;
        for (int r = 0; r < recipes.size(); ++r) {
            ItemStack[] chkIngredients;
            SmeltingRecipe chkRecipe = (SmeltingRecipe)((RecipeHolder)recipes.get(r)).value();
            if (chkRecipe.getIngredients().size() != 1 || (chkIngredients = ((Ingredient)chkRecipe.getIngredients().get(0)).getItems())[0].getItem() != stackIn.getItem()) continue;
            result = chkRecipe.getResultItem((HolderLookup.Provider)RegistryAccess.EMPTY).copy();
        }
        return result;
    }

    public static String nameFixer(String currentName) {
        currentName = currentName.replace("[", "");
        currentName = currentName.replace("]", "");
        return currentName;
    }
}

