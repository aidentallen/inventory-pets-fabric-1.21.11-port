/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.StructureType
 *  net.minecraft.world.level.material.MapColor
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.fml.ModContainer
 *  net.neoforged.fml.ModLoadingContext
 *  net.neoforged.fml.common.Mod
 *  net.neoforged.fml.config.IConfigSpec
 *  net.neoforged.fml.config.ModConfig$Type
 *  net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
 *  net.neoforged.neoforge.client.gui.ConfigurationScreen
 *  net.neoforged.neoforge.client.gui.IConfigScreenFactory
 *  net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
 *  net.neoforged.neoforge.network.registration.PayloadRegistrar
 *  net.neoforged.neoforge.registries.DeferredBlock
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredItem
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.DeferredRegister$Blocks
 *  net.neoforged.neoforge.registries.DeferredRegister$Items
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.inventorypets;

import com.inventorypets.blocks.cloudBlock;
import com.inventorypets.blocks.cloudSpawn;
import com.inventorypets.blocks.netherSpawn;
import com.inventorypets.blocks.sandBlock;
import com.inventorypets.blocks.sandSpawn;
import com.inventorypets.blocks.spaceSpawn;
import com.inventorypets.blocks.stoneBlock;
import com.inventorypets.blocks.stoneSpawn;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.entities.AnvilPetEntity;
import com.inventorypets.entities.AppleEntity;
import com.inventorypets.entities.BananaEntity;
import com.inventorypets.entities.BedPetEntity;
import com.inventorypets.entities.BillGatesEntity;
import com.inventorypets.entities.GoldenAppleEntity;
import com.inventorypets.entities.MiniQuantumBlazeEntity;
import com.inventorypets.entities.MiniQuantumEndermanEntity;
import com.inventorypets.entities.SatyaNadellaEntity;
import com.inventorypets.entities.SiamesePetEntity;
import com.inventorypets.entities.SteveBallmerEntity;
import com.inventorypets.events.StartupClientEvents;
import com.inventorypets.handler.PatreonHandler;
import com.inventorypets.handler.UpdateHandler;
import com.inventorypets.init.InventoryPetsCreativeModeTab;
import com.inventorypets.init.ModArmorMaterial;
import com.inventorypets.init.ModClientEvents;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModFoods;
import com.inventorypets.init.ModMenus;
import com.inventorypets.init.ModParticles;
import com.inventorypets.init.ModSetup;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.init.ModToolMaterial;
import com.inventorypets.inventory.ChestManager;
import com.inventorypets.io.IlluminatiBlacklistWriter;
import com.inventorypets.items.bananaItem;
import com.inventorypets.items.blueScreenofDeath;
import com.inventorypets.items.candyCane;
import com.inventorypets.items.easterEgg;
import com.inventorypets.items.feedBag;
import com.inventorypets.items.holidayCookie;
import com.inventorypets.items.itemGift;
import com.inventorypets.items.itemPetrifier;
import com.inventorypets.items.mugEggNog;
import com.inventorypets.items.nuggetCoal;
import com.inventorypets.items.nuggetDiamond;
import com.inventorypets.items.nuggetEmerald;
import com.inventorypets.items.nuggetEnder;
import com.inventorypets.items.nuggetLapis;
import com.inventorypets.items.nuggetNetherite;
import com.inventorypets.items.nuggetObsidian;
import com.inventorypets.items.patreonHead;
import com.inventorypets.items.patreonShirt;
import com.inventorypets.items.petAchieveItem1;
import com.inventorypets.items.petAchieveItem10;
import com.inventorypets.items.petAchieveItem20;
import com.inventorypets.items.petAchieveItem30;
import com.inventorypets.items.petAchieveItem40;
import com.inventorypets.items.petAchieveItem5;
import com.inventorypets.items.petAchieveItem50;
import com.inventorypets.items.petAchieveItem60;
import com.inventorypets.items.petAchieveItemAll;
import com.inventorypets.items.petAchieveItemGeneral;
import com.inventorypets.items.rockCandy;
import com.inventorypets.items.siameseGift;
import com.inventorypets.items.solsticeBoots;
import com.inventorypets.items.solsticeChestplate;
import com.inventorypets.items.solsticeHelmet;
import com.inventorypets.items.solsticeLeggings;
import com.inventorypets.items.solsticeSword;
import com.inventorypets.items.startButton;
import com.inventorypets.items.windows31;
import com.inventorypets.items.windows7;
import com.inventorypets.items.windows8;
import com.inventorypets.items.windowsMe;
import com.inventorypets.items.windowsMojave;
import com.inventorypets.items.windowsXP;
import com.inventorypets.items.xeroxParcGui;
import com.inventorypets.networking.PacketBiomeFinder;
import com.inventorypets.networking.PacketBiomeName;
import com.inventorypets.networking.PacketBiomeSender;
import com.inventorypets.networking.PacketDimensionName;
import com.inventorypets.networking.PacketKeyInput;
import com.inventorypets.networking.PacketPetNamer;
import com.inventorypets.networking.PacketStructureFinder;
import com.inventorypets.networking.PacketStructureName;
import com.inventorypets.networking.PacketStructureSender;
import com.inventorypets.networking.PacketTeleport;
import com.inventorypets.pets.petAnvil;
import com.inventorypets.pets.petApple;
import com.inventorypets.pets.petAprilFool;
import com.inventorypets.pets.petBanana;
import com.inventorypets.pets.petBed;
import com.inventorypets.pets.petBiome;
import com.inventorypets.pets.petBlackHole;
import com.inventorypets.pets.petBlaze;
import com.inventorypets.pets.petBrewingStand;
import com.inventorypets.pets.petCheetah;
import com.inventorypets.pets.petChest;
import com.inventorypets.pets.petChicken;
import com.inventorypets.pets.petChristmasTree;
import com.inventorypets.pets.petCloud;
import com.inventorypets.pets.petCobblestone;
import com.inventorypets.pets.petCow;
import com.inventorypets.pets.petCraftingTable;
import com.inventorypets.pets.petCreeper;
import com.inventorypets.pets.petDingot;
import com.inventorypets.pets.petDirt;
import com.inventorypets.pets.petDoubleChest;
import com.inventorypets.pets.petDubstep;
import com.inventorypets.pets.petEnchantingTable;
import com.inventorypets.pets.petEndPortal;
import com.inventorypets.pets.petEnderChest;
import com.inventorypets.pets.petEnderman;
import com.inventorypets.pets.petFlyingSaddle;
import com.inventorypets.pets.petFurnace;
import com.inventorypets.pets.petGhast;
import com.inventorypets.pets.petHeart;
import com.inventorypets.pets.petHouse;
import com.inventorypets.pets.petIlluminati;
import com.inventorypets.pets.petIronGolem;
import com.inventorypets.pets.petJuggernaut;
import com.inventorypets.pets.petJukebox;
import com.inventorypets.pets.petLead;
import com.inventorypets.pets.petLoot;
import com.inventorypets.pets.petMagmaCube;
import com.inventorypets.pets.petMenorah;
import com.inventorypets.pets.petMeta;
import com.inventorypets.pets.petMeta100;
import com.inventorypets.pets.petMeta25;
import com.inventorypets.pets.petMeta50;
import com.inventorypets.pets.petMeta75;
import com.inventorypets.pets.petMetaAOE;
import com.inventorypets.pets.petMetaFan;
import com.inventorypets.pets.petMetaMob;
import com.inventorypets.pets.petMetaPeaceful;
import com.inventorypets.pets.petMetaSpecial;
import com.inventorypets.pets.petMetaUtility;
import com.inventorypets.pets.petMetaYouTuber;
import com.inventorypets.pets.petMickerson;
import com.inventorypets.pets.petMishumaaSaba;
import com.inventorypets.pets.petMoon;
import com.inventorypets.pets.petMooshroom;
import com.inventorypets.pets.petNetherPortal;
import com.inventorypets.pets.petOcelot;
import com.inventorypets.pets.petPacMan;
import com.inventorypets.pets.petPig;
import com.inventorypets.pets.petPingot;
import com.inventorypets.pets.petPixie;
import com.inventorypets.pets.petPoliticallyCorrect;
import com.inventorypets.pets.petPufferfish;
import com.inventorypets.pets.petPurpliciousCow;
import com.inventorypets.pets.petQuantumCrystalMonster;
import com.inventorypets.pets.petQuiver;
import com.inventorypets.pets.petSaddle;
import com.inventorypets.pets.petSatedChest;
import com.inventorypets.pets.petSatedDoubleChest;
import com.inventorypets.pets.petSheep;
import com.inventorypets.pets.petShield;
import com.inventorypets.pets.petSiamese;
import com.inventorypets.pets.petSilverfish;
import com.inventorypets.pets.petSlime;
import com.inventorypets.pets.petSnowGolem;
import com.inventorypets.pets.petSpider;
import com.inventorypets.pets.petSponge;
import com.inventorypets.pets.petSquid;
import com.inventorypets.pets.petSun;
import com.inventorypets.pets.petTorch;
import com.inventorypets.pets.petWither;
import com.inventorypets.pets.petWolf;
import com.inventorypets.worldgen.NetherDungeonStructure;
import com.inventorypets.worldgen.SeaCaveStructure;
import com.inventorypets.worldgen.SkyDungeonStructure;
import com.inventorypets.worldgen.SpaceDungeonStructure;
import com.inventorypets.worldgen.TreeTopStructure;
import com.inventorypets.worldgen.UndergroundDungeonStructure;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value="inventorypets")
public class InventoryPets {
    public static final String MODID = "inventorypets";
    public static final String VERSION = "2.2.9";
    public static final Logger LOGGER = LogManager.getLogger((String)"inventorypets");
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create((ResourceKey)Registries.STRUCTURE_TYPE, (String)"inventorypets");
    public static final DeferredHolder<StructureType<?>, StructureType<SkyDungeonStructure>> SKY_DUNGEON = STRUCTURES.register("sky_dungeon", () -> () -> SkyDungeonStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<SpaceDungeonStructure>> SPACE_DUNGEON = STRUCTURES.register("space_dungeon", () -> () -> SpaceDungeonStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<SeaCaveStructure>> SEA_CAVE = STRUCTURES.register("sea_cave", () -> () -> SeaCaveStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<TreeTopStructure>> TREE_TOP = STRUCTURES.register("tree_top", () -> () -> TreeTopStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<UndergroundDungeonStructure>> UNDERGROUND_DUNGEON = STRUCTURES.register("underground_dungeon", () -> () -> UndergroundDungeonStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<NetherDungeonStructure>> NETHER_DUNGEON = STRUCTURES.register("nether_dungeon", () -> () -> NetherDungeonStructure.CODEC);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks((String)"inventorypets");
    public static final DeferredBlock<Block> CLOUD_BLOCK = BLOCKS.registerBlock("cloud_block", properties -> new cloudBlock(properties.mapColor(MapColor.NONE).friction(0.6f).strength(3.0f, 3.0f).isSuffocating(InventoryPets::never).isViewBlocking(InventoryPets::never)));
    public static final DeferredBlock<Block> CLOUD_SPAWN = BLOCKS.registerBlock("cloud_spawn", properties -> new cloudSpawn(properties.mapColor(MapColor.NONE).randomTicks().friction(0.6f).strength(-1.0f, 3600000.0f).speedFactor(0.4f).requiresCorrectToolForDrops().isSuffocating(InventoryPets::never).isViewBlocking(InventoryPets::never)));
    public static final DeferredBlock<Block> SAND_BLOCK = BLOCKS.registerBlock("sand_block", properties -> new sandBlock(properties.mapColor(MapColor.SAND).friction(0.6f).strength(2.0f, 2.0f)));
    public static final DeferredBlock<Block> SAND_SPAWN = BLOCKS.registerBlock("sand_spawn", properties -> new sandSpawn(properties.mapColor(MapColor.SAND).randomTicks().friction(0.6f).strength(50.0f, 1200.0f).requiresCorrectToolForDrops().speedFactor(0.4f)));
    public static final DeferredBlock<Block> STONE_BLOCK = BLOCKS.registerBlock("stone_block", properties -> new stoneBlock(properties.mapColor(MapColor.STONE).friction(0.6f).strength(2.0f, 2.0f)));
    public static final DeferredBlock<Block> STONE_SPAWN = BLOCKS.registerBlock("stone_spawn", properties -> new stoneSpawn(properties.mapColor(MapColor.STONE).randomTicks().friction(0.6f).strength(50.0f, 1200.0f).speedFactor(0.4f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SPACE_SPAWN = BLOCKS.registerBlock("space_spawn", properties -> new spaceSpawn(properties.mapColor(MapColor.NONE).randomTicks().friction(0.6f).strength(50.0f, 1200.0f).speedFactor(0.4f).requiresCorrectToolForDrops().noOcclusion().isSuffocating(InventoryPets::never).isViewBlocking(InventoryPets::never)));
    public static final DeferredBlock<Block> NETHER_SPAWN = BLOCKS.registerBlock("nether_spawn", properties -> new netherSpawn(properties.mapColor(MapColor.STONE).randomTicks().friction(0.6f).strength(50.0f, 1200.0f).speedFactor(0.4f).requiresCorrectToolForDrops()));
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems((String)"inventorypets");
    public static final DeferredItem<Item> PET_BLAZE = ITEMS.registerItem("pet_blaze", properties -> new petBlaze(properties.setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_CREEPER = ITEMS.registerItem("pet_creeper", properties -> new petCreeper(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_ENDERMAN = ITEMS.registerItem("pet_enderman", properties -> new petEnderman(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_GHAST = ITEMS.registerItem("pet_ghast", properties -> new petGhast(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_IRON_GOLEM = ITEMS.registerItem("pet_iron_golem", properties -> new petIronGolem(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_MAGMA_CUBE = ITEMS.registerItem("pet_magma_cube", properties -> new petMagmaCube(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SNOW_GOLEM = ITEMS.registerItem("pet_snow_golem", properties -> new petSnowGolem(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SPIDER = ITEMS.registerItem("pet_spider", properties -> new petSpider(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_CHICKEN = ITEMS.registerItem("pet_chicken", properties -> new petChicken(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_COW = ITEMS.registerItem("pet_cow", properties -> new petCow(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_MOOSHROOM = ITEMS.registerItem("pet_mooshroom", properties -> new petMooshroom(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_OCELOT = ITEMS.registerItem("pet_ocelot", properties -> new petOcelot(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_PIG = ITEMS.registerItem("pet_pig", properties -> new petPig(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SHEEP = ITEMS.registerItem("pet_sheep", properties -> new petSheep(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SQUID = ITEMS.registerItem("pet_squid", properties -> new petSquid(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_ANVIL = ITEMS.registerItem("pet_anvil", properties -> new petAnvil(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_BED = ITEMS.registerItem("pet_bed", properties -> new petBed(new Item.Properties().setNoRepair().stacksTo(1).durability(2)));
    public static final DeferredItem<Item> PET_BREWING_STAND = ITEMS.registerItem("pet_brewing_stand", properties -> new petBrewingStand(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_CHEST = ITEMS.registerItem("pet_chest", properties -> new petChest(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_CRAFTING_TABLE = ITEMS.registerItem("pet_crafting_table", properties -> new petCraftingTable(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_DOUBLE_CHEST = ITEMS.registerItem("pet_double_chest", properties -> new petDoubleChest(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_ENCHANTING_TABLE = ITEMS.registerItem("pet_enchanting_table", properties -> new petEnchantingTable(new Item.Properties().setNoRepair().stacksTo(1).durability(2)));
    public static final DeferredItem<Item> PET_ENDER_CHEST = ITEMS.registerItem("pet_ender_chest", properties -> new petEnderChest(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_FURNACE = ITEMS.registerItem("pet_furnace", properties -> new petFurnace(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_JUKEBOX = ITEMS.registerItem("pet_jukebox", properties -> new petJukebox(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_LEAD = ITEMS.registerItem("pet_lead", properties -> new petLead(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_NETHER_PORTAL = ITEMS.registerItem("pet_nether_portal", properties -> new petNetherPortal(new Item.Properties().setNoRepair().stacksTo(1).durability(2)));
    public static final DeferredItem<Item> PET_SADDLE = ITEMS.registerItem("pet_saddle", properties -> new petSaddle(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_END_PORTAL = ITEMS.registerItem("pet_end_portal", properties -> new petEndPortal(new Item.Properties().setNoRepair().stacksTo(1).durability(2)));
    public static final DeferredItem<Item> PET_SATED_CHEST = ITEMS.registerItem("pet_sated_chest", properties -> new petSatedChest(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SATED_DOUBLE_CHEST = ITEMS.registerItem("pet_sated_double_chest", properties -> new petSatedDoubleChest(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_BANANA = ITEMS.registerItem("pet_banana", properties -> new petBanana(new Item.Properties().setNoRepair().stacksTo(1).durability(10)));
    public static final DeferredItem<Item> PET_BIOME = ITEMS.registerItem("pet_biome", properties -> new petBiome(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_FLYING_SADDLE = ITEMS.registerItem("pet_flying_saddle", properties -> new petFlyingSaddle(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_DINGOT = ITEMS.registerItem("pet_dingot", properties -> new petDingot(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_LOOT = ITEMS.registerItem("pet_loot", properties -> new petLoot(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META = ITEMS.registerItem("pet_meta", properties -> new petMeta(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_MICKERSON = ITEMS.registerItem("pet_mickerson", properties -> new petMickerson(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_PINGOT = ITEMS.registerItem("pet_pingot", properties -> new petPingot(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_PCOW = ITEMS.registerItem("pet_purplicious_cow", properties -> new petPurpliciousCow(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_QCM = ITEMS.registerItem("pet_qcm", properties -> new petQuantumCrystalMonster(new Item.Properties().setNoRepair().stacksTo(1).durability(2)));
    public static final DeferredItem<Item> PET_QUIVER = ITEMS.registerItem("pet_quiver", properties -> new petQuiver(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SPONGE = ITEMS.registerItem("pet_sponge", properties -> new petSponge(new Item.Properties().setNoRepair().stacksTo(1).durability(2)));
    public static final DeferredItem<Item> PET_COBBLESTONE = ITEMS.registerItem("pet_cobblestone", properties -> new petCobblestone(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_DIRT = ITEMS.registerItem("pet_dirt", properties -> new petDirt(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_JUGGERNAUT = ITEMS.registerItem("pet_juggernaut", properties -> new petJuggernaut(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_ILLUMINATI = ITEMS.registerItem("pet_illuminati", properties -> new petIlluminati(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_SIAMESE = ITEMS.registerItem("pet_siamese", properties -> new petSiamese(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_APPLE = ITEMS.registerItem("pet_apple", properties -> new petApple(new Item.Properties().setNoRepair().stacksTo(1).durability(10)));
    public static final DeferredItem<Item> PET_CHEETAH = ITEMS.registerItem("pet_cheetah", properties -> new petCheetah(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_HOUSE = ITEMS.registerItem("pet_house", properties -> new petHouse(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_PACMAN = ITEMS.registerItem("pet_pacman", properties -> new petPacMan(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_PIXIE = ITEMS.registerItem("pet_pixie", properties -> new petPixie(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SILVERFISH = ITEMS.registerItem("pet_silverfish", properties -> new petSilverfish(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_TORCH = ITEMS.registerItem("pet_torch", properties -> new petTorch(new Item.Properties().setNoRepair().stacksTo(1).durability(4)));
    public static final DeferredItem<Item> PET_WOLF = ITEMS.registerItem("pet_wolf", properties -> new petWolf(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_DUBSTEP = ITEMS.registerItem("pet_dubstep", properties -> new petDubstep(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_HEART = ITEMS.registerItem("pet_heart", properties -> new petHeart(new Item.Properties().setNoRepair().stacksTo(1).durability(4)));
    public static final DeferredItem<Item> PET_MOON = ITEMS.registerItem("pet_moon", properties -> new petMoon(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_SHIELD = ITEMS.registerItem("pet_shield", properties -> new petShield(new Item.Properties().setNoRepair().stacksTo(1).durability(4)));
    public static final DeferredItem<Item> PET_META_AOE = ITEMS.registerItem("pet_meta_aoe", properties -> new petMetaAOE(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_FAN = ITEMS.registerItem("pet_meta_fan", properties -> new petMetaFan(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_MOB = ITEMS.registerItem("pet_meta_mob", properties -> new petMetaMob(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_PEACEFUL = ITEMS.registerItem("pet_meta_peaceful", properties -> new petMetaPeaceful(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_SPECIAL = ITEMS.registerItem("pet_meta_special", properties -> new petMetaSpecial(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_UTILITY = ITEMS.registerItem("pet_meta_utility", properties -> new petMetaUtility(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_YOUTUBER = ITEMS.registerItem("pet_meta_youtuber", properties -> new petMetaYouTuber(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_25 = ITEMS.registerItem("pet_meta_25", properties -> new petMeta25(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_50 = ITEMS.registerItem("pet_meta_50", properties -> new petMeta50(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_75 = ITEMS.registerItem("pet_meta_75", properties -> new petMeta75(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_META_100 = ITEMS.registerItem("pet_meta_100", properties -> new petMeta100(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_APRIL_FOOL = ITEMS.registerItem("pet_april_fool", properties -> new petAprilFool(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_CHRISTMAS_TREE = ITEMS.registerItem("pet_christmas_tree", properties -> new petChristmasTree(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_MENORAH = ITEMS.registerItem("pet_menorah", properties -> new petMenorah(new Item.Properties()));
    public static final DeferredItem<Item> PET_MISHUMAA_SABA = ITEMS.registerItem("pet_mishumaa_saba", properties -> new petMishumaaSaba(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_POLITICALLY_CORRECT = ITEMS.registerItem("pet_politically_correct", properties -> new petPoliticallyCorrect(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_BLACK_HOLE = ITEMS.registerItem("pet_black_hole", properties -> new petBlackHole(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_CLOUD = ITEMS.registerItem("pet_cloud", properties -> new petCloud(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_PUFFERFISH = ITEMS.registerItem("pet_pufferfish", properties -> new petPufferfish(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SLIME = ITEMS.registerItem("pet_slime", properties -> new petSlime(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> PET_SUN = ITEMS.registerItem("pet_sun", properties -> new petSun(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> PET_WITHER = ITEMS.registerItem("pet_wither", properties -> new petWither(new Item.Properties().setNoRepair().stacksTo(1).durability(3)));
    public static final DeferredItem<Item> NUGGET_DIAMOND = ITEMS.registerItem("nugget_diamond", properties -> new nuggetDiamond(new Item.Properties()));
    public static final DeferredItem<Item> NUGGET_OBSIDIAN = ITEMS.registerItem("nugget_obsidian", properties -> new nuggetObsidian(new Item.Properties()));
    public static final DeferredItem<Item> NUGGET_COAL = ITEMS.registerItem("nugget_coal", properties -> new nuggetCoal(new Item.Properties()));
    public static final DeferredItem<Item> NUGGET_LAPIS = ITEMS.registerItem("nugget_lapis", properties -> new nuggetLapis(new Item.Properties()));
    public static final DeferredItem<Item> NUGGET_ENDER = ITEMS.registerItem("nugget_ender", properties -> new nuggetEnder(new Item.Properties()));
    public static final DeferredItem<Item> NUGGET_EMERALD = ITEMS.registerItem("nugget_emerald", properties -> new nuggetEmerald(new Item.Properties()));
    public static final DeferredItem<Item> NUGGET_NETHERITE = ITEMS.registerItem("nugget_netherite", properties -> new nuggetNetherite(new Item.Properties()));
    public static final DeferredItem<Item> SIAMESE_GIFT = ITEMS.registerItem("siamese_gift", properties -> new siameseGift(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ITEM_PETRIFIER = ITEMS.registerItem("item_petrifier", properties -> new itemPetrifier(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PETACHIEVE_1 = ITEMS.registerItem("petachieve_1", properties -> new petAchieveItem1(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_5 = ITEMS.registerItem("petachieve_5", properties -> new petAchieveItem5(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_10 = ITEMS.registerItem("petachieve_10", properties -> new petAchieveItem10(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_20 = ITEMS.registerItem("petachieve_20", properties -> new petAchieveItem20(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_30 = ITEMS.registerItem("petachieve_30", properties -> new petAchieveItem30(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_40 = ITEMS.registerItem("petachieve_40", properties -> new petAchieveItem40(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_50 = ITEMS.registerItem("petachieve_50", properties -> new petAchieveItem50(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_60 = ITEMS.registerItem("petachieve_60", properties -> new petAchieveItem60(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_ALL = ITEMS.registerItem("petachieve_all", properties -> new petAchieveItemAll(new Item.Properties()));
    public static final DeferredItem<Item> PETACHIEVE_GENERAL = ITEMS.registerItem("petachieve_general", properties -> new petAchieveItemGeneral(new Item.Properties()));
    public static final DeferredItem<Item> START_BUTTON = ITEMS.registerItem("start_button", properties -> new startButton(new Item.Properties()));
    public static final DeferredItem<Item> WINDOWS_7 = ITEMS.registerItem("windows_7", properties -> new windows7(new Item.Properties()));
    public static final DeferredItem<Item> WINDOWS_8 = ITEMS.registerItem("windows_8", properties -> new windows8(new Item.Properties()));
    public static final DeferredItem<Item> WINDOWS_31 = ITEMS.registerItem("windows_31", properties -> new windows31(new Item.Properties()));
    public static final DeferredItem<Item> WINDOWS_ME = ITEMS.registerItem("windows_me", properties -> new windowsMe(new Item.Properties()));
    public static final DeferredItem<Item> WINDOWS_MOJAVE = ITEMS.registerItem("windows_mojave", properties -> new windowsMojave(new Item.Properties()));
    public static final DeferredItem<Item> WINDOWS_XP = ITEMS.registerItem("windows_xp", properties -> new windowsXP(new Item.Properties()));
    public static final DeferredItem<Item> XEROX_PARC_GUI = ITEMS.registerItem("xerox_parc_gui", properties -> new xeroxParcGui(new Item.Properties()));
    public static final DeferredItem<Item> BLUE_SCREEN = ITEMS.registerItem("blue_screen", properties -> new blueScreenofDeath(new Item.Properties()));
    public static final DeferredItem<Item> EASTER_EGG = ITEMS.registerItem("easter_egg", properties -> new easterEgg(new Item.Properties()));
    public static final DeferredItem<Item> HOLIDAY_GIFT = ITEMS.registerItem("holiday_gift", properties -> new itemGift(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BANANA = ITEMS.registerItem("banana", properties -> new bananaItem(new Item.Properties()));
    public static final DeferredItem<Item> FEED_BAG = ITEMS.registerItem("feed_bag", properties -> new feedBag(new Item.Properties().setNoRepair().stacksTo(1).durability(1)));
    public static final DeferredItem<Item> HOLIDAY_COOKIE = ITEMS.registerItem("holiday_cookie", properties -> new holidayCookie(new Item.Properties().food(ModFoods.HOLIDAY_COOKIE_FOOD)));
    public static final DeferredItem<Item> EGG_NOG = ITEMS.registerItem("egg_nog", properties -> new mugEggNog(new Item.Properties().food(ModFoods.EGG_NOG_FOOD)));
    public static final DeferredItem<Item> CANDY_CANE = ITEMS.registerItem("candy_cane", properties -> new candyCane(new Item.Properties().food(ModFoods.CANDY_CANE_FOOD)));
    public static final DeferredItem<Item> ROCK_CANDY = ITEMS.registerItem("rock_candy", properties -> new rockCandy(new Item.Properties().food(ModFoods.ROCK_CANDY_FOOD)));
    public static final DeferredItem<Item> SOLSTICE_HELMET = ITEMS.registerItem("solstice_helmet", properties -> new solsticeHelmet(ModArmorMaterial.SOLSTICE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<Item> SOLSTICE_CHESTPLATE = ITEMS.registerItem("solstice_chestplate", properties -> new solsticeChestplate(ModArmorMaterial.SOLSTICE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<Item> SOLSTICE_LEGGINGS = ITEMS.registerItem("solstice_leggings", properties -> new solsticeLeggings(ModArmorMaterial.SOLSTICE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<Item> SOLSTICE_BOOTS = ITEMS.registerItem("solstice_boots", properties -> new solsticeBoots(ModArmorMaterial.SOLSTICE, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final DeferredItem<Item> SOLSTICE_SWORD = ITEMS.registerItem("solstice_sword", properties -> new solsticeSword(ModToolMaterial.SOLSTICE_TOOL, new Item.Properties()));
    public static final DeferredItem<Item> PATREON_HELMET = ITEMS.registerItem("patreon_head", properties -> new patreonHead(ModArmorMaterial.PATREON, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<ArmorItem> PATREON_CHESTPLATE = ITEMS.registerItem("patreon_shirt", properties -> new patreonShirt(ModArmorMaterial.PATREON, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<Item> CLOUD_BLOCK_ITEM = ITEMS.registerItem("cloud_block", properties -> new BlockItem((Block)CLOUD_BLOCK.get(), properties));
    public static final DeferredItem<Item> CLOUD_SPAWN_ITEM = ITEMS.registerItem("cloud_spawn", properties -> new BlockItem((Block)CLOUD_SPAWN.get(), properties));
    public static final DeferredItem<Item> SAND_BLOCK_ITEM = ITEMS.registerItem("sand_block", properties -> new BlockItem((Block)SAND_BLOCK.get(), properties));
    public static final DeferredItem<Item> SAND_SPAWN_ITEM = ITEMS.registerItem("sand_spawn", properties -> new BlockItem((Block)SAND_SPAWN.get(), properties));
    public static final DeferredItem<Item> STONE_BLOCK_ITEM = ITEMS.registerItem("stone_block", properties -> new BlockItem((Block)STONE_BLOCK.get(), properties));
    public static final DeferredItem<Item> STONE_SPAWN_ITEM = ITEMS.registerItem("stone_spawn", properties -> new BlockItem((Block)STONE_SPAWN.get(), properties));
    public static final DeferredItem<Item> SPACE_SPAWN_ITEM = ITEMS.registerItem("space_spawn", properties -> new BlockItem((Block)SPACE_SPAWN.get(), properties));
    public static final DeferredItem<Item> NETHER_SPAWN_ITEM = ITEMS.registerItem("nether_spawn", properties -> new BlockItem((Block)NETHER_SPAWN.get(), properties));
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create((Registry)BuiltInRegistries.ENTITY_TYPE, (String)"inventorypets");
    public static ResourceKey<EntityType<?>> SIAMESE_ENTITY_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"siamese_entity"));
    public static final Supplier<EntityType<SiamesePetEntity>> SIAMESE_ENTITY = ENTITY_TYPES.register("siamese_entity", () -> EntityType.Builder.of(SiamesePetEntity::new, (MobCategory)MobCategory.CREATURE).sized(EntityType.CAT.getWidth(), EntityType.CAT.getHeight()).setShouldReceiveVelocityUpdates(false).build(SIAMESE_ENTITY_KEY.toString()));
    public static final ResourceKey<EntityType<?>> BILL_GATES_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"bill_gates_entity"));
    public static final Supplier<EntityType<BillGatesEntity>> BILL_GATES_ENTITY = ENTITY_TYPES.register("bill_gates_entity", () -> EntityType.Builder.of(BillGatesEntity::new, (MobCategory)MobCategory.CREATURE).sized(0.6f, 1.95f).build(BILL_GATES_KEY.toString()));
    public static final ResourceKey<EntityType<?>> STEVE_BALLMER_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"steve_ballmer_entity"));
    public static final Supplier<EntityType<SteveBallmerEntity>> STEVE_BALLMER_ENTITY = ENTITY_TYPES.register("steve_ballmer_entity", () -> EntityType.Builder.of(SteveBallmerEntity::new, (MobCategory)MobCategory.CREATURE).sized(0.6f, 1.95f).build(STEVE_BALLMER_KEY.toString()));
    public static final ResourceKey<EntityType<?>> SATYA_NADELLA_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"satya_nadella_entity"));
    public static final Supplier<EntityType<SatyaNadellaEntity>> SATYA_NADELLA_ENTITY = ENTITY_TYPES.register("saya_nadella_entity", () -> EntityType.Builder.of(SatyaNadellaEntity::new, (MobCategory)MobCategory.CREATURE).sized(0.6f, 1.95f).build(SATYA_NADELLA_KEY.toString()));
    public static final ResourceKey<EntityType<?>> ANVIL_PET_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"anvil_pet_entity"));
    public static final Supplier<EntityType<AnvilPetEntity>> ANVIL_PET_ENTITY = ENTITY_TYPES.register("anvil_pet_entity", () -> EntityType.Builder.of(AnvilPetEntity::new, (MobCategory)MobCategory.CREATURE).sized(2.0f, 2.0f).clientTrackingRange(8).setShouldReceiveVelocityUpdates(false).build(ANVIL_PET_KEY.toString()));
    public static final ResourceKey<EntityType<?>> BED_PET_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"bed_pet_entity"));
    public static final Supplier<EntityType<BedPetEntity>> BED_PET_ENTITY = ENTITY_TYPES.register("bed_pet_entity", () -> EntityType.Builder.of(BedPetEntity::new, (MobCategory)MobCategory.CREATURE).sized(3.0f, 1.5f).build(BED_PET_KEY.toString()));
    public static final ResourceKey<EntityType<?>> MINI_QB_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"mini_quantum_blaze_entity"));
    public static final Supplier<EntityType<MiniQuantumBlazeEntity>> MINI_QB_ENTITY = ENTITY_TYPES.register("mini_quantum_blaze_entity", () -> EntityType.Builder.of(MiniQuantumBlazeEntity::new, (MobCategory)MobCategory.CREATURE).sized(0.4f, 0.8f).build(MINI_QB_KEY.toString()));
    public static final ResourceKey<EntityType<?>> MINI_QE_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"mini_quantum_enderman_entity"));
    public static final Supplier<EntityType<MiniQuantumEndermanEntity>> MINI_QE_ENTITY = ENTITY_TYPES.register("mini_quantum_enderman_entity", () -> EntityType.Builder.of(MiniQuantumEndermanEntity::new, (MobCategory)MobCategory.CREATURE).sized(0.4f, 0.9f).build(MINI_QE_KEY.toString()));
    public static final ResourceKey<EntityType<?>> APPLE_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"apple_entity"));
    public static final Supplier<EntityType<AppleEntity>> APPLE_ENTITY = ENTITY_TYPES.register("apple_entity", () -> EntityType.Builder.of(AppleEntity::new, (MobCategory)MobCategory.MISC).sized(0.25f, 0.25f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(APPLE_KEY.toString()));
    public static final ResourceKey<EntityType<?>> GOLDEN_APPLE_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"golden_apple_entity"));
    public static final Supplier<EntityType<GoldenAppleEntity>> GOLDEN_APPLE_ENTITY = ENTITY_TYPES.register("golden_apple_entity", () -> EntityType.Builder.of(GoldenAppleEntity::new, (MobCategory)MobCategory.MISC).sized(0.25f, 0.25f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(GOLDEN_APPLE_KEY.toString()));
    public static final ResourceKey<EntityType<?>> BANANA_KEY = ResourceKey.create((ResourceKey)Registries.ENTITY_TYPE, (Identifier)Identifier.withDefaultNamespace((String)"banana_entity"));
    public static final Supplier<EntityType<BananaEntity>> BANANA_ENTITY = ENTITY_TYPES.register("banana_entity", () -> EntityType.Builder.of(BananaEntity::new, (MobCategory)MobCategory.MISC).sized(0.25f, 0.25f).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build(BANANA_KEY.toString()));
    public static final TagKey<Item> OBSIDIAN_NUGGET = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"obsidian_nugget"));
    public static final TagKey<Item> LAPIS_NUGGET = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"lapis_nugget"));
    public static final TagKey<Item> DIAMOND_NUGGET = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"diamond_nugget"));
    public static final TagKey<Item> EMERALD_NUGGET = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"emerald_nugget"));
    public static final TagKey<Item> COAL_NUGGET = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"coal_nugget"));
    public static final TagKey<Item> ENDER_NUGGET = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"ender_nugget"));
    public static final TagKey<Item> BREAD = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"bread"));
    public static final TagKey<Item> COAL = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"coal"));
    public static final TagKey<Item> RAW_MEATS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"raw_meats"));
    public static final TagKey<Item> RAW_FISH = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"raw_fishes"));
    public static final TagKey<Item> COOKED_FISH = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cooked_fishes"));
    public static final TagKey<Item> LEGENDARY_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"legendary_pets"));
    public static final TagKey<Item> AOE_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"aoe_pets"));
    public static final TagKey<Item> FAN_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"fan_pets"));
    public static final TagKey<Item> MOB_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mob_pets"));
    public static final TagKey<Item> PEACEFUL_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"peaceful_pets"));
    public static final TagKey<Item> SPECIAL_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"special_pets"));
    public static final TagKey<Item> UTILITY_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"utiity_pets"));
    public static final TagKey<Item> YOUTUBER_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"youtuber_pets"));
    public static final TagKey<Item> NONLEGENDARY_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"nonlegendary_pets"));
    public static final TagKey<Item> ALL_PETS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"all_pets"));
    public static final TagKey<Item> LOGS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"minecraft", (String)"logs"));
    public static final TagKey<Item> WOOL = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"minecraft", (String)"wool"));
    public static final TagKey<Item> PLANKS = TagKey.create((ResourceKey)Registries.ITEM, (Identifier)Identifier.fromNamespaceAndPath((String)"minecraft", (String)"planks"));

    public InventoryPets(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setupPackets);
        modEventBus.addListener(this::registerCapabilities);
        modContainer.registerConfig(ModConfig.Type.COMMON, (IConfigSpec)InventoryPetsConfig.SPEC, "inventorypets-common.toml");
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ConfigurationScreen::new);
        InventoryPetsCreativeModeTab.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        STRUCTURES.register(modEventBus);
        ModMenus.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSoundEvents.SOUNDS.register(modEventBus);
        ModDataComponents.COMPONENTS.register(modEventBus);
        ModDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModSetup.setup();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        IEventBus modbus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modbus.register(StartupClientEvents.class);
        ModClientEvents.RegisterEntityRenderers();
        IlluminatiBlacklistWriter.main();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PatreonHandler.initList();
        if (((Boolean)InventoryPetsConfig.showUpdateMessage.get()).booleanValue()) {
            UpdateHandler.init();
        }
    }

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    public void setupPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.playToServer(PacketBiomeFinder.TYPE, PacketBiomeFinder.STREAM_CODEC, PacketBiomeFinder::handle);
        registrar.playToClient(PacketTeleport.TYPE, PacketTeleport.STREAM_CODEC, PacketTeleport::handle);
        registrar.playToServer(PacketBiomeName.TYPE, PacketBiomeName.STREAM_CODEC, PacketBiomeName::handle);
        registrar.playToServer(PacketDimensionName.TYPE, PacketDimensionName.STREAM_CODEC, PacketDimensionName::handle);
        registrar.playToClient(PacketBiomeSender.TYPE, PacketBiomeSender.STREAM_CODEC, PacketBiomeSender::handle);
        registrar.playToServer(PacketKeyInput.TYPE, PacketKeyInput.STREAM_CODEC, PacketKeyInput::handle);
        registrar.playToServer(PacketPetNamer.TYPE, PacketPetNamer.STREAM_CODEC, PacketPetNamer::handle);
        registrar.playToServer(PacketStructureFinder.TYPE, PacketStructureFinder.STREAM_CODEC, PacketStructureFinder::handle);
        registrar.playToServer(PacketStructureName.TYPE, PacketStructureName.STREAM_CODEC, PacketStructureName::handle);
        registrar.playToClient(PacketStructureSender.TYPE, PacketStructureSender.STREAM_CODEC, PacketStructureSender::handle);
    }

    public static Identifier prefix(String name) {
        return Identifier.fromNamespaceAndPath((String)MODID, (String)name.toLowerCase(Locale.ROOT));
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, (stack, ctx) -> ChestManager.get().getCapability((ItemStack)stack), new ItemLike[]{FEED_BAG, PET_CHEST, PET_SATED_CHEST, PET_DOUBLE_CHEST, PET_SATED_DOUBLE_CHEST});
    }
}

