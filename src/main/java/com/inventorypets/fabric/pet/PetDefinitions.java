package com.inventorypets.fabric.pet;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PetDefinitions {
    public static final Map<String, PetDefinition> ALL = new LinkedHashMap<>();

    static {
        pet("pet_blaze", "Blaze", "petblaze", 2, 20);
        pet("pet_creeper", "Creeper", "petcreeper", 2, 100);
        pet("pet_enderman", "Enderman", "petenderman", 2, 40);
        pet("pet_ghast", "Ghast", "petghast", 1, 20);
        pet("pet_iron_golem", "IronGolem", "petirongolem", 2, 0);
        pet("pet_magma_cube", "MagmaCube", "petmagmacube", 3, 0);
        pet("pet_snow_golem", "SnowGolem", "petsnowgolem", 2, 0);
        pet("pet_spider", "Spider", "petspider", 4, 0);
        pet("pet_chicken", "Chicken", "petchicken", 2, 0);
        pet("pet_cow", "Cow", "petcow", 2, 0);
        pet("pet_mooshroom", "Mooshroom", "petmooshroom", 2, 40);
        pet("pet_ocelot", "Ocelot", "petocelot", 3, 0);
        pet("pet_pig", "Pig", "petpig", 3, 0);
        pet("pet_sheep", "Sheep", "petsheep", 2, 0);
        pet("pet_squid", "Squid", "petsquid", 4, 0);
        pet("pet_anvil", "Anvil", "petanvil", 3, 40);
        pet("pet_bed", "Bed", "petbed", 2, 100);
        pet("pet_brewing_stand", "BrewingStand", "petbrewingstand", 1, 200);
        pet("pet_chest", "Chest", "petchest", 1, 0);
        pet("pet_crafting_table", "CraftingTable", "petcraftingtable", 1, 0);
        pet("pet_double_chest", "DoubleChest", "petchest", 1, 0);
        pet("pet_enchanting_table", "EnchantingTable", "petenchantingtable", 1, 0);
        pet("pet_ender_chest", "EnderChest", "petchest", 1, 0);
        pet("pet_furnace", "Furnace", "petfurnace", 2, 40);
        pet("pet_jukebox", "Jukebox", "petjukebox", 3, 0);
        pet("pet_lead", "Lead", "petlead", 1, 0);
        pet("pet_nether_portal", "NetherPortal", "petnetherportal", 2, 100);
        pet("pet_saddle", "Saddle", "petsaddle", 1, 0);
        pet("pet_end_portal", "EndPortal", "petendportal", 2, 100);
        pet("pet_sated_chest", "SatedChest", "petchest", 1, 0);
        pet("pet_sated_double_chest", "SatedDoubleChest", "petchest", 1, 0);
        pet("pet_banana", "Banana", "petbanana", 2, 10);
        pet("pet_biome", "Biome", "petbiome", 2, 20);
        pet("pet_flying_saddle", "FlyingSaddle", "petflyingsaddle", 3, 0);
        pet("pet_dingot", "Dingot", "petdingot", 2, 20);
        pet("pet_loot", "Loot", "petloot", 2, 0);
        pet("pet_meta", "Meta", "petmeta", 2, 20);
        pet("pet_mickerson", "Mickerson", "petmickerson", 2, 0);
        pet("pet_pingot", "Pingot", "petpingot", 2, 20);
        pet("pet_purplicious_cow", "PurpliciousCow", "petpurpliciouscow", 2, 0);
        pet("pet_qcm", "QuantumCrystalMonster", "petqcm", 1, 100);
        pet("pet_quiver", "Quiver", "petquiver", 2, 600);
        pet("pet_sponge", "Sponge", "petsponge", 4, 20);
        pet("pet_cobblestone", "Cobblestone", "petcobblestone", 3, 20);
        pet("pet_dirt", "Dirt", "petdirt", 3, 20);
        pet("pet_juggernaut", "Juggernaut", "petjuggernaut", 2, 1200);
        pet("pet_illuminati", "Illuminati", "petilluminati", 2, 1200);
        pet("pet_siamese", "Siamese", "petsiamese", 4, 100);
        pet("pet_apple", "Apple", "petapple", 2, 6);
        pet("pet_cheetah", "Cheetah", "petcheetah", 3, 100);
        pet("pet_house", "House", "pethouse", 2, 40);
        pet("pet_pacman", "PacMan", "petpacman", 2, 100);
        pet("pet_pixie", "Pixie", "petpixie", 1, 0);
        pet("pet_silverfish", "Silverfish", "petsilverfish", 2, 20);
        pet("pet_torch", "Torch", "pettorch", 2, 4);
        pet("pet_wolf", "Wolf", "petwolf", 3, 40);
        pet("pet_dubstep", "Dubstep", "petdubstep", 1, 100);
        pet("pet_heart", "Heart", "petheart", 1, 100);
        pet("pet_moon", "Moon", "petmoon", 1, 100);
        pet("pet_shield", "Shield", "petshield", 1, 100);
        meta("pet_meta_aoe");
        meta("pet_meta_fan");
        meta("pet_meta_mob");
        meta("pet_meta_peaceful");
        meta("pet_meta_special");
        meta("pet_meta_utility");
        meta("pet_meta_youtuber");
        meta("pet_meta_25");
        meta("pet_meta_50");
        meta("pet_meta_75");
        meta("pet_meta_100");
        pet("pet_april_fool", "AprilFool", "petaprilfool", 1, 100);
        pet("pet_christmas_tree", "ChristmasTree", "petmas", 1, 0);
        pet("pet_menorah", "Menorah", "petmas", 1, 0);
        pet("pet_mishumaa_saba", "MishumaaSaba", "petmas", 1, 0);
        pet("pet_politically_correct", "PoliticallyCorrect", "petmas", 1, 0);
        pet("pet_black_hole", "BlackHole", "petblackhole", 3, 100);
        pet("pet_cloud", "Cloud", "petcloud", 3, 40);
        pet("pet_pufferfish", "Pufferfish", "petpufferfish", 2, 60);
        pet("pet_slime", "Slime", "petslime", 2, 0);
        pet("pet_sun", "Sun", "petsun", 2, 100);
        pet("pet_wither", "Wither", "petwither", 4, 40);
    }

    private PetDefinitions() {
    }

    private static void pet(String id, String suffix, String tooltip, int lines, int cooldown) {
        ALL.put(id, new PetDefinition(id, suffix, tooltip, lines, cooldown));
    }

    private static void meta(String id) {
        ALL.put(id, new PetDefinition(id, "Meta", "petmeta", 2, 20));
    }
}
