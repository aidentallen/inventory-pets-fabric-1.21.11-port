package com.inventorypets.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class InventoryPetsJsonConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, JsonElement> DEFAULTS = new LinkedHashMap<>();
    private static JsonObject values;

    private InventoryPetsJsonConfig() {
    }

    public static void load() {
        installDefaults();
        Path path = FabricLoader.getInstance().getConfigDir().resolve("inventorypets.json");
        JsonObject loaded = new JsonObject();
        if (Files.isRegularFile(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                JsonElement root = JsonParser.parseReader(reader);
                if (root.isJsonObject()) loaded = root.getAsJsonObject();
            } catch (Exception exception) {
                throw new IllegalStateException("Failed to read Inventory Pets config: " + path, exception);
            }
        }
        for (Map.Entry<String, JsonElement> entry : DEFAULTS.entrySet()) {
            if (!loaded.has(entry.getKey())) loaded.add(entry.getKey(), entry.getValue().deepCopy());
        }
        values = loaded;
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(values, writer);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to write Inventory Pets config: " + path, exception);
        }
    }

    public static boolean bool(String key) {
        return required(key).getAsBoolean();
    }

    public static int integer(String key) {
        return required(key).getAsInt();
    }

    public static String string(String key) {
        return required(key).getAsString();
    }

    private static JsonElement required(String key) {
        if (values == null) throw new IllegalStateException("Inventory Pets config was accessed before load");
        JsonElement value = values.get(key);
        if (value == null) throw new IllegalArgumentException("Unknown Inventory Pets config option: " + key);
        return value;
    }

    private static void installDefaults() {
        if (!DEFAULTS.isEmpty()) return;
        put("disableHolidayPets", false);
        put("disableAprilFoolHoliday", false);
        put("yearroundAprilFool", false);
        put("enableAprilFoolGriefPranks", false);
        put("showHolidayMessages", true);
        put("holidayPetMonth", 12);
        put("enableBlackHoleAffectsPlayers", false);
        put("enablePufferfishAffectsPlayers", false);
        put("enableMoonAffectsPlayers", false);
        put("enableDubstepAffectsPlayers", false);
        put("enableSunAffectsPlayers", false);
        put("allowPetrifierLegendaries", false);
        put("disablePetrifier", false);
        put("enableLootWithFTBUltimine", false);
        put("disableRightClickMachines", false);
        put("showSuggestors", true);
        put("showUpdateMessage", true);
        put("allPlayersMustBeInBed", false);
        put("biomePetChunks", 96);
        put("chickenEffectLevel", 3);
        put("cloudFlySpeed", 3);
        put("disableCloudFly", false);
        put("disableCloudLightning", false);
        put("disableCloudSound", false);
        put("cowPetRemovesLevitation", false);
        put("disableCreeperExplosion", false);
        put("dingotIPStructuresOnly", false);
        put("disableEndermanAutoTeleport", false);
        put("disableSpongeWaterAbsorb", false);
        put("disableIlluminatiGiveItems", false);
        put("illuminatiCraftedOnly", true);
        put("illuminatiCooldown", 1);
        put("illuminatiInvisibleDuration", 1);
        put("illuminatiItemCapLimit", 5000);
        put("juggernautCooldown", 1);
        put("juggernautShieldWallDuration", 1);
        put("pacManEatLevel", 8);
        put("pacManPowerupFactor", 3);
        put("disablePingotAutoExtract", false);
        put("reduceQuantumCrystalMonsterMinions", false);
        put("disableSlimeReviveSound", false);
        put("disableSpiderJump", false);
        put("disableSquidSpeed", false);
        put("spongeAbsorbBlockRadius", 8);
        put("disablePetsGiveItems", false);
        put("petsMustEat", true);
        put("petsEatWholeItems", false);
        put("petEatTimerFactor", 120);

        food("Anvil", "minecraft:iron_nugget");
        food("Apple", "minecraft:gold_ingot");
        food("AprilFool", "minecraft:tripwire_hook");
        food("Banana", "minecraft:glowstone_dust");
        food("Bed", "minecraft:white_wool");
        food("Biome", "minecraft:grass_block");
        food("BlackHole", "inventorypets:nugget_obsidian");
        food("Blaze", "minecraft:quartz");
        food("BrewingStand", "minecraft:nether_wart");
        food("Cheetah", "minecraft:beef");
        food("Chest", "minecraft:oak_planks");
        food("Chicken", "minecraft:wheat_seeds");
        food("Cloud", "minecraft:quartz");
        food("Cobblestone", "minecraft:dirt");
        food("Cow", "minecraft:wheat");
        food("CraftingTable", "minecraft:oak_planks");
        food("Creeper", "inventorypets:nugget_emerald");
        food("Dingot", "minecraft:copper_ingot");
        food("Dirt", "minecraft:cobblestone");
        food("DoubleChest", "minecraft:oak_log");
        food("Dubstep", "minecraft:note_block");
        food("EnchantingTable", "inventorypets:nugget_lapis");
        food("EnderChest", "inventorypets:nugget_ender");
        food("Enderman", "inventorypets:nugget_obsidian");
        food("EndPortal", "inventorypets:nugget_ender");
        food("FlyingSaddle", "inventorypets:nugget_diamond");
        food("Furnace", "inventorypets:nugget_coal");
        food("Ghast", "minecraft:blaze_powder");
        food("Heart", "minecraft:red_tulip");
        food("House", "minecraft:iron_nugget");
        food("IronGolem", "minecraft:iron_ingot");
        food("Illuminati", "inventorypets:nugget_emerald");
        food("Juggernaut", "inventorypets:nugget_obsidian");
        food("Jukebox", "minecraft:redstone");
        food("Lead", "minecraft:string");
        food("Loot", "minecraft:gold_nugget");
        food("MagmaCube", "minecraft:quartz");
        food("Mickerson", "inventorypets:nugget_diamond");
        food("Moon", "inventorypets:nugget_emerald");
        food("Mooshroom", "minecraft:red_mushroom");
        food("NetherPortal", "inventorypets:nugget_obsidian");
        food("Ocelot", "minecraft:cooked_cod");
        food("PacMan", "minecraft:cookie");
        food("Pig", "minecraft:carrot");
        food("Pingot", "inventorypets:nugget_diamond");
        food("Pixie", "inventorypets:nugget_emerald");
        food("Pufferfish", "minecraft:glowstone_dust");
        food("PurpliciousCow", "inventorypets:nugget_diamond");
        food("QuantumCrystalMonster", "inventorypets:nugget_lapis");
        food("Quiver", "minecraft:feather");
        food("Saddle", "minecraft:iron_nugget");
        food("Sheep", "minecraft:wheat");
        food("Shield", "minecraft:iron_nugget");
        food("Siamese", "minecraft:cooked_chicken");
        food("Silverfish", "minecraft:cobblestone");
        food("Slime", "minecraft:golden_apple");
        food("SnowGolem", "minecraft:pumpkin");
        food("Spider", "minecraft:beef");
        food("Sponge", "minecraft:lily_pad");
        food("Squid", "minecraft:cod");
        food("Sun", "minecraft:gold_ingot");
        food("Torch", "inventorypets:nugget_coal");
        food("Wither", "minecraft:soul_sand");
        food("Wolf", "minecraft:bone");
        food("Meta", "");
        food("ChristmasTree", "");
        food("Menorah", "");
        food("MishumaaSaba", "");
        food("PoliticallyCorrect", "");
        food("SatedChest", "");
        food("SatedDoubleChest", "");

        String[] toggles = {
                "Anvil", "Apple", "AprilFool", "Banana", "Bed", "Biome", "BlackHole", "Blaze",
                "BrewingStand", "Cheetah", "Chest", "Chicken", "ChristmasTree", "Cloud", "Cobblestone",
                "Cow", "CraftingTable", "Creeper", "Dingot", "Dirt", "DoubleChest", "Dubstep",
                "EnchantingTable", "EnderChest", "Enderman", "EndPortal", "FlyingSaddle", "Furnace",
                "Ghast", "Heart", "House", "Illuminati", "IronGolem", "Juggernaut", "Jukebox", "Lead",
                "Loot", "MagmaCube", "Menorah", "Meta", "Mickerson", "MishumaaSaba", "Moon", "Mooshroom",
                "NetherPortal", "Ocelot", "PacMan", "Pig", "Pingot", "Pixie", "PoliticallyCorrect",
                "Pufferfish", "PurpliciousCow", "QuantumCrystalMonster", "Quiver", "Saddle", "SatedChest",
                "SatedDoubleChest", "Sheep", "Shield", "Siamese", "Silverfish", "Slime", "SnowGolem",
                "Spider", "Sponge", "Squid", "Sun", "Torch", "Wither", "Wolf"
        };
        for (String toggle : toggles) put("disable" + toggle, false);
    }

    private static void food(String suffix, String id) {
        put("food" + suffix, id);
    }

    private static void put(String key, boolean value) {
        DEFAULTS.put(key, GSON.toJsonTree(value));
    }

    private static void put(String key, int value) {
        DEFAULTS.put(key, GSON.toJsonTree(value));
    }

    private static void put(String key, String value) {
        DEFAULTS.put(key, GSON.toJsonTree(value));
    }
}
