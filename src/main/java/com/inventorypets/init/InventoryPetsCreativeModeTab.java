/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package com.inventorypets.init;

import com.inventorypets.InventoryPets;
import java.util.RandomPoolAlias;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InventoryPetsCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create((ResourceKey)Registries.CREATIVE_MODE_TAB, (String)"inventorypets");
    public static final Supplier<CreativeModeTab> COURSE_TAB = CREATIVE_MODE_TABS.register("inventorypets", () -> CreativeModeTab.builder().icon(() -> InventoryPetsCreativeModeTab.randomPetGet()).title((Component)Component.translatable((String)"itemGroup.inventorypets")).displayItems((displayParameters, output) -> {
        output.accept((ItemLike)InventoryPets.PET_BLAZE.get());
        output.accept((ItemLike)InventoryPets.PET_CREEPER.get());
        output.accept((ItemLike)InventoryPets.PET_ENDERMAN.get());
        output.accept((ItemLike)InventoryPets.PET_GHAST.get());
        output.accept((ItemLike)InventoryPets.PET_IRON_GOLEM.get());
        output.accept((ItemLike)InventoryPets.PET_MAGMA_CUBE.get());
        output.accept((ItemLike)InventoryPets.PET_SNOW_GOLEM.get());
        output.accept((ItemLike)InventoryPets.PET_SPIDER.get());
        output.accept((ItemLike)InventoryPets.PET_CHICKEN.get());
        output.accept((ItemLike)InventoryPets.PET_COW.get());
        output.accept((ItemLike)InventoryPets.PET_MOOSHROOM.get());
        output.accept((ItemLike)InventoryPets.PET_OCELOT.get());
        output.accept((ItemLike)InventoryPets.PET_PIG.get());
        output.accept((ItemLike)InventoryPets.PET_SHEEP.get());
        output.accept((ItemLike)InventoryPets.PET_SQUID.get());
        output.accept((ItemLike)InventoryPets.PET_ANVIL.get());
        output.accept((ItemLike)InventoryPets.PET_BED.get());
        output.accept((ItemLike)InventoryPets.PET_BREWING_STAND.get());
        output.accept((ItemLike)InventoryPets.PET_CHEST.get());
        output.accept((ItemLike)InventoryPets.PET_CRAFTING_TABLE.get());
        output.accept((ItemLike)InventoryPets.PET_DOUBLE_CHEST.get());
        output.accept((ItemLike)InventoryPets.PET_ENCHANTING_TABLE.get());
        output.accept((ItemLike)InventoryPets.PET_ENDER_CHEST.get());
        output.accept((ItemLike)InventoryPets.PET_FURNACE.get());
        output.accept((ItemLike)InventoryPets.PET_JUKEBOX.get());
        output.accept((ItemLike)InventoryPets.PET_LEAD.get());
        output.accept((ItemLike)InventoryPets.PET_NETHER_PORTAL.get());
        output.accept((ItemLike)InventoryPets.PET_SADDLE.get());
        output.accept((ItemLike)InventoryPets.PET_END_PORTAL.get());
        output.accept((ItemLike)InventoryPets.PET_SATED_CHEST.get());
        output.accept((ItemLike)InventoryPets.PET_SATED_DOUBLE_CHEST.get());
        output.accept((ItemLike)InventoryPets.PET_BANANA.get());
        output.accept((ItemLike)InventoryPets.PET_BIOME.get());
        output.accept((ItemLike)InventoryPets.PET_FLYING_SADDLE.get());
        output.accept((ItemLike)InventoryPets.PET_DINGOT.get());
        output.accept((ItemLike)InventoryPets.PET_LOOT.get());
        output.accept((ItemLike)InventoryPets.PET_META.get());
        output.accept((ItemLike)InventoryPets.PET_MICKERSON.get());
        output.accept((ItemLike)InventoryPets.PET_PINGOT.get());
        output.accept((ItemLike)InventoryPets.PET_PCOW.get());
        output.accept((ItemLike)InventoryPets.PET_QCM.get());
        output.accept((ItemLike)InventoryPets.PET_QUIVER.get());
        output.accept((ItemLike)InventoryPets.PET_SPONGE.get());
        output.accept((ItemLike)InventoryPets.PET_COBBLESTONE.get());
        output.accept((ItemLike)InventoryPets.PET_DIRT.get());
        output.accept((ItemLike)InventoryPets.PET_JUGGERNAUT.get());
        output.accept((ItemLike)InventoryPets.PET_ILLUMINATI.get());
        output.accept((ItemLike)InventoryPets.PET_SIAMESE.get());
        output.accept((ItemLike)InventoryPets.PET_APPLE.get());
        output.accept((ItemLike)InventoryPets.PET_CHEETAH.get());
        output.accept((ItemLike)InventoryPets.PET_HOUSE.get());
        output.accept((ItemLike)InventoryPets.PET_PACMAN.get());
        output.accept((ItemLike)InventoryPets.PET_PIXIE.get());
        output.accept((ItemLike)InventoryPets.PET_SILVERFISH.get());
        output.accept((ItemLike)InventoryPets.PET_TORCH.get());
        output.accept((ItemLike)InventoryPets.PET_WOLF.get());
        output.accept((ItemLike)InventoryPets.PET_DUBSTEP.get());
        output.accept((ItemLike)InventoryPets.PET_HEART.get());
        output.accept((ItemLike)InventoryPets.PET_MOON.get());
        output.accept((ItemLike)InventoryPets.PET_SHIELD.get());
        output.accept((ItemLike)InventoryPets.PET_APRIL_FOOL.get());
        output.accept((ItemLike)InventoryPets.PET_CHRISTMAS_TREE.get());
        output.accept((ItemLike)InventoryPets.PET_MENORAH.get());
        output.accept((ItemLike)InventoryPets.PET_MISHUMAA_SABA.get());
        output.accept((ItemLike)InventoryPets.PET_POLITICALLY_CORRECT.get());
        output.accept((ItemLike)InventoryPets.PET_BLACK_HOLE.get());
        output.accept((ItemLike)InventoryPets.PET_CLOUD.get());
        output.accept((ItemLike)InventoryPets.PET_PUFFERFISH.get());
        output.accept((ItemLike)InventoryPets.PET_SLIME.get());
        output.accept((ItemLike)InventoryPets.PET_SUN.get());
        output.accept((ItemLike)InventoryPets.PET_WITHER.get());
        output.accept((ItemLike)InventoryPets.NUGGET_COAL.get());
        output.accept((ItemLike)InventoryPets.NUGGET_LAPIS.get());
        output.accept((ItemLike)InventoryPets.NUGGET_ENDER.get());
        output.accept((ItemLike)InventoryPets.NUGGET_DIAMOND.get());
        output.accept((ItemLike)InventoryPets.NUGGET_OBSIDIAN.get());
        output.accept((ItemLike)InventoryPets.NUGGET_EMERALD.get());
        output.accept((ItemLike)InventoryPets.NUGGET_NETHERITE.get());
        output.accept((ItemLike)InventoryPets.SIAMESE_GIFT.get());
        output.accept((ItemLike)InventoryPets.ITEM_PETRIFIER.get());
        output.accept((ItemLike)InventoryPets.START_BUTTON.get());
        output.accept((ItemLike)InventoryPets.WINDOWS_7.get());
        output.accept((ItemLike)InventoryPets.WINDOWS_8.get());
        output.accept((ItemLike)InventoryPets.WINDOWS_31.get());
        output.accept((ItemLike)InventoryPets.WINDOWS_ME.get());
        output.accept((ItemLike)InventoryPets.WINDOWS_MOJAVE.get());
        output.accept((ItemLike)InventoryPets.WINDOWS_XP.get());
        output.accept((ItemLike)InventoryPets.XEROX_PARC_GUI.get());
        output.accept((ItemLike)InventoryPets.BLUE_SCREEN.get());
        output.accept((ItemLike)InventoryPets.EASTER_EGG.get());
        output.accept((ItemLike)InventoryPets.HOLIDAY_GIFT.get());
        output.accept((ItemLike)InventoryPets.FEED_BAG.get());
        output.accept((ItemLike)InventoryPets.HOLIDAY_COOKIE.get());
        output.accept((ItemLike)InventoryPets.EGG_NOG.get());
        output.accept((ItemLike)InventoryPets.CANDY_CANE.get());
        output.accept((ItemLike)InventoryPets.ROCK_CANDY.get());
        output.accept((ItemLike)InventoryPets.SOLSTICE_HELMET.get());
        output.accept((ItemLike)InventoryPets.SOLSTICE_CHESTPLATE.get());
        output.accept((ItemLike)InventoryPets.SOLSTICE_LEGGINGS.get());
        output.accept((ItemLike)InventoryPets.SOLSTICE_BOOTS.get());
        output.accept((ItemLike)InventoryPets.SOLSTICE_SWORD.get());
        output.accept((ItemLike)InventoryPets.PATREON_HELMET.get());
        output.accept((ItemLike)InventoryPets.PATREON_CHESTPLATE.get());
        output.accept((ItemLike)InventoryPets.CLOUD_BLOCK_ITEM.get());
        output.accept((ItemLike)InventoryPets.CLOUD_SPAWN_ITEM.get());
        output.accept((ItemLike)InventoryPets.SAND_BLOCK_ITEM.get());
        output.accept((ItemLike)InventoryPets.SAND_SPAWN_ITEM.get());
        output.accept((ItemLike)InventoryPets.STONE_BLOCK_ITEM.get());
        output.accept((ItemLike)InventoryPets.STONE_SPAWN_ITEM.get());
        output.accept((ItemLike)InventoryPets.SPACE_SPAWN_ITEM.get());
        output.accept((ItemLike)InventoryPets.NETHER_SPAWN_ITEM.get());
    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static ItemStack randomPetGet() {
        RandomPoolAlias rand = new RandomPoolAlias();
        int k = rand.nextInt(71);
        if (k == 0) {
            return new ItemStack((ItemLike)InventoryPets.PET_GHAST.get(), 1);
        }
        if (k == 1) {
            return new ItemStack((ItemLike)InventoryPets.PET_WITHER.get(), 1);
        }
        if (k == 2) {
            return new ItemStack((ItemLike)InventoryPets.PET_CREEPER.get(), 1);
        }
        if (k == 3) {
            return new ItemStack((ItemLike)InventoryPets.PET_ENDERMAN.get(), 1);
        }
        if (k == 4) {
            return new ItemStack((ItemLike)InventoryPets.PET_IRON_GOLEM.get(), 1);
        }
        if (k == 5) {
            return new ItemStack((ItemLike)InventoryPets.PET_SNOW_GOLEM.get(), 1);
        }
        if (k == 6) {
            return new ItemStack((ItemLike)InventoryPets.PET_SPIDER.get(), 1);
        }
        if (k == 7) {
            return new ItemStack((ItemLike)InventoryPets.PET_MAGMA_CUBE.get(), 1);
        }
        if (k == 8) {
            return new ItemStack((ItemLike)InventoryPets.PET_SHEEP.get(), 1);
        }
        if (k == 9) {
            return new ItemStack((ItemLike)InventoryPets.PET_COW.get(), 1);
        }
        if (k == 10) {
            return new ItemStack((ItemLike)InventoryPets.PET_CHICKEN.get(), 1);
        }
        if (k == 11) {
            return new ItemStack((ItemLike)InventoryPets.PET_PIG.get(), 1);
        }
        if (k == 12) {
            return new ItemStack((ItemLike)InventoryPets.PET_SQUID.get(), 1);
        }
        if (k == 13) {
            return new ItemStack((ItemLike)InventoryPets.PET_OCELOT.get(), 1);
        }
        if (k == 14) {
            return new ItemStack((ItemLike)InventoryPets.PET_FURNACE.get(), 1);
        }
        if (k == 15) {
            return new ItemStack((ItemLike)InventoryPets.PET_ENCHANTING_TABLE.get(), 1);
        }
        if (k == 16) {
            return new ItemStack((ItemLike)InventoryPets.PET_OCELOT.get(), 1);
        }
        if (k == 17) {
            return new ItemStack((ItemLike)InventoryPets.PET_CHEST.get(), 1);
        }
        if (k == 18) {
            return new ItemStack((ItemLike)InventoryPets.PET_DOUBLE_CHEST.get(), 1);
        }
        if (k == 19) {
            return new ItemStack((ItemLike)InventoryPets.PET_BED.get(), 1);
        }
        if (k == 20) {
            return new ItemStack((ItemLike)InventoryPets.PET_JUKEBOX.get(), 1);
        }
        if (k == 21) {
            return new ItemStack((ItemLike)InventoryPets.PET_ANVIL.get(), 1);
        }
        if (k == 22) {
            return new ItemStack((ItemLike)InventoryPets.PET_BREWING_STAND.get(), 1);
        }
        if (k == 23) {
            return new ItemStack((ItemLike)InventoryPets.PET_NETHER_PORTAL.get(), 1);
        }
        if (k == 24) {
            return new ItemStack((ItemLike)InventoryPets.PET_PINGOT.get(), 1);
        }
        if (k == 25) {
            return new ItemStack((ItemLike)InventoryPets.PET_MICKERSON.get(), 1);
        }
        if (k == 26) {
            return new ItemStack((ItemLike)InventoryPets.PET_PCOW.get(), 1);
        }
        if (k == 27) {
            return new ItemStack((ItemLike)InventoryPets.PET_QCM.get(), 1);
        }
        if (k == 28) {
            return new ItemStack((ItemLike)InventoryPets.PET_SLIME.get(), 1);
        }
        if (k == 29) {
            return new ItemStack((ItemLike)InventoryPets.PET_BLACK_HOLE.get(), 1);
        }
        if (k == 30) {
            return new ItemStack((ItemLike)InventoryPets.PET_PUFFERFISH.get(), 1);
        }
        if (k == 31) {
            return new ItemStack((ItemLike)InventoryPets.PET_CLOUD.get(), 1);
        }
        if (k == 32) {
            return new ItemStack((ItemLike)InventoryPets.PET_SHIELD.get(), 1);
        }
        if (k == 33) {
            return new ItemStack((ItemLike)InventoryPets.PET_MOON.get(), 1);
        }
        if (k == 34) {
            return new ItemStack((ItemLike)InventoryPets.PET_DUBSTEP.get(), 1);
        }
        if (k == 35) {
            return new ItemStack((ItemLike)InventoryPets.PET_HEART.get(), 1);
        }
        if (k == 36) {
            return new ItemStack((ItemLike)InventoryPets.PET_SPONGE.get(), 1);
        }
        if (k == 37) {
            return new ItemStack((ItemLike)InventoryPets.PET_BANANA.get(), 1);
        }
        if (k == 38) {
            return new ItemStack((ItemLike)InventoryPets.PET_BLAZE.get(), 1);
        }
        if (k == 39) {
            return new ItemStack((ItemLike)InventoryPets.PET_ENDER_CHEST.get(), 1);
        }
        if (k == 40) {
            return new ItemStack((ItemLike)InventoryPets.PET_MOOSHROOM.get(), 1);
        }
        if (k == 41) {
            return new ItemStack((ItemLike)InventoryPets.PET_LOOT.get(), 1);
        }
        if (k == 42) {
            return new ItemStack((ItemLike)InventoryPets.PET_ILLUMINATI.get(), 1);
        }
        if (k == 43) {
            return new ItemStack((ItemLike)InventoryPets.PET_JUGGERNAUT.get(), 1);
        }
        if (k == 44) {
            return new ItemStack((ItemLike)InventoryPets.PET_APRIL_FOOL.get(), 1);
        }
        if (k == 45) {
            return new ItemStack((ItemLike)InventoryPets.PET_PACMAN.get(), 1);
        }
        if (k == 46) {
            return new ItemStack((ItemLike)InventoryPets.PET_CHEETAH.get(), 1);
        }
        if (k == 47) {
            return new ItemStack((ItemLike)InventoryPets.PET_HOUSE.get(), 1);
        }
        if (k == 48) {
            return new ItemStack((ItemLike)InventoryPets.PET_SILVERFISH.get(), 1);
        }
        if (k == 49) {
            return new ItemStack((ItemLike)InventoryPets.PET_WOLF.get(), 1);
        }
        if (k == 50) {
            return new ItemStack((ItemLike)InventoryPets.PET_SUN.get(), 1);
        }
        if (k == 51) {
            return new ItemStack((ItemLike)InventoryPets.PET_TORCH.get(), 1);
        }
        if (k == 52) {
            return new ItemStack((ItemLike)InventoryPets.PET_SIAMESE.get(), 1);
        }
        if (k == 53) {
            return new ItemStack((ItemLike)InventoryPets.PET_BIOME.get(), 1);
        }
        if (k == 54) {
            return new ItemStack((ItemLike)InventoryPets.PET_SADDLE.get(), 1);
        }
        if (k == 55) {
            return new ItemStack((ItemLike)InventoryPets.PET_FLYING_SADDLE.get(), 1);
        }
        if (k == 56) {
            return new ItemStack((ItemLike)InventoryPets.PET_LEAD.get(), 1);
        }
        if (k == 57) {
            return new ItemStack((ItemLike)InventoryPets.PET_PIXIE.get(), 1);
        }
        if (k == 58) {
            return new ItemStack((ItemLike)InventoryPets.PET_META.get(), 1);
        }
        if (k == 59) {
            return new ItemStack((ItemLike)InventoryPets.PET_COBBLESTONE.get(), 1);
        }
        if (k == 60) {
            return new ItemStack((ItemLike)InventoryPets.PET_DIRT.get(), 1);
        }
        if (k == 61) {
            return new ItemStack((ItemLike)InventoryPets.PET_CHRISTMAS_TREE.get(), 1);
        }
        if (k == 62) {
            return new ItemStack((ItemLike)InventoryPets.PET_MENORAH.get(), 1);
        }
        if (k == 63) {
            return new ItemStack((ItemLike)InventoryPets.PET_MISHUMAA_SABA.get(), 1);
        }
        if (k == 64) {
            return new ItemStack((ItemLike)InventoryPets.PET_POLITICALLY_CORRECT.get(), 1);
        }
        if (k == 65) {
            return new ItemStack((ItemLike)InventoryPets.PET_QUIVER.get(), 1);
        }
        if (k == 66) {
            return new ItemStack((ItemLike)InventoryPets.PET_DINGOT.get(), 1);
        }
        if (k == 67) {
            return new ItemStack((ItemLike)InventoryPets.PET_APPLE.get(), 1);
        }
        if (k == 68) {
            return new ItemStack((ItemLike)InventoryPets.PET_SATED_CHEST.get(), 1);
        }
        if (k == 69) {
            return new ItemStack((ItemLike)InventoryPets.PET_SATED_DOUBLE_CHEST.get(), 1);
        }
        if (k == 70) {
            return new ItemStack((ItemLike)InventoryPets.PET_END_PORTAL.get(), 1);
        }
        return new ItemStack((ItemLike)InventoryPets.PET_BLAZE.get(), 1);
    }
}

