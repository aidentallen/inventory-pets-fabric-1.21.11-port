/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.ChestBlockEntity
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import java.util.RandomPoolAlias;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid="inventorypets")
public class DungeonChestLoot {
    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void onItemRightClick(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        Block block = world.getBlockState(event.getPos()).getBlock();
        BlockPos pos = event.getPos();
        if (!event.getLevel().isClientSide && block == Blocks.CHEST) {
            ChestBlockEntity techest = (ChestBlockEntity)world.getBlockEntity(pos);
            ItemStack chestStack = new ItemStack((ItemLike)block);
            if (techest.hasCustomName() && techest.getCustomName().getString().equals("Sky Dungeon")) {
                if (techest.getItem(4).getItem() == Items.POISONOUS_POTATO) {
                    chestStack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)Blocks.CHEST.getDescriptionId()));
                    techest.setItem(4, ItemStack.EMPTY);
                    DungeonChestLoot.fillChest(techest, 0);
                }
            } else if (techest.hasCustomName() && techest.getCustomName().getString().equals("Space Dungeon")) {
                if (techest.getItem(4).getItem() == Items.POISONOUS_POTATO) {
                    chestStack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)Blocks.CHEST.getDescriptionId()));
                    techest.setItem(4, ItemStack.EMPTY);
                    DungeonChestLoot.fillChest(techest, 1);
                }
            } else if (techest.hasCustomName() && techest.getCustomName().getString().equals("Sea Cave")) {
                if (techest.getItem(4).getItem() == Items.POISONOUS_POTATO) {
                    chestStack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)Blocks.CHEST.getDescriptionId()));
                    techest.setItem(4, ItemStack.EMPTY);
                    DungeonChestLoot.fillChest(techest, 2);
                }
            } else if (techest.hasCustomName() && techest.getCustomName().getString().equals("Tree Top")) {
                if (techest.getItem(4).getItem() == Items.POISONOUS_POTATO) {
                    chestStack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)Blocks.CHEST.getDescriptionId()));
                    techest.setChanged();
                    techest.setItem(4, ItemStack.EMPTY);
                    DungeonChestLoot.fillChest(techest, 3);
                }
            } else if (techest.hasCustomName() && techest.getCustomName().getString().equals("Underground Dungeon")) {
                if (techest.getItem(4).getItem() == Items.POISONOUS_POTATO) {
                    chestStack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)Blocks.CHEST.getDescriptionId()));
                    techest.setItem(4, ItemStack.EMPTY);
                    DungeonChestLoot.fillChest(techest, 4);
                }
            } else if (techest.hasCustomName() && techest.getCustomName().getString().contains("Nether Dungeon") && techest.getItem(4).getItem() == Items.POISONOUS_POTATO) {
                chestStack.set(DataComponents.CUSTOM_NAME, (Object)Component.translatable((String)Blocks.CHEST.getDescriptionId()));
                techest.setItem(4, ItemStack.EMPTY);
                DungeonChestLoot.fillChest(techest, 5);
            }
        }
    }

    private static void fillChest(ChestBlockEntity techest, int chestType) {
        RandomPoolAlias rand = new RandomPoolAlias();
        for (int g = 1; g < techest.getContainerSize(); ++g) {
            int r;
            int h;
            if (g == 4) {
                for (int loopy = 0; loopy <= 50; ++loopy) {
                    int k;
                    h = rand.nextInt(100);
                    if (h >= 0 && h <= 19) {
                        k = rand.nextInt(8);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disableGhast.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_GHAST.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableCreeper.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_CREEPER.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disableEnderman.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_ENDERMAN.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disableIronGolem.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_IRON_GOLEM.get(), 1));
                            loopy = 50;
                        } else if (k == 4 && !((Boolean)InventoryPetsConfig.disableSnowGolem.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SNOW_GOLEM.get(), 1));
                            loopy = 50;
                        } else if (k == 5 && !((Boolean)InventoryPetsConfig.disableSpider.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SPIDER.get(), 1));
                            loopy = 50;
                        } else if (k == 6 && !((Boolean)InventoryPetsConfig.disableMagmaCube.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_MAGMA_CUBE.get(), 1));
                            loopy = 50;
                        } else if (k == 7 && !((Boolean)InventoryPetsConfig.disableBlaze.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_BLAZE.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 20 && h <= 49) {
                        k = rand.nextInt(7);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disableSheep.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SHEEP.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableCow.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_COW.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disableChicken.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_CHICKEN.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disablePig.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_PIG.get(), 1));
                            loopy = 50;
                        } else if (k == 4 && !((Boolean)InventoryPetsConfig.disableSquid.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SQUID.get(), 1));
                            loopy = 50;
                        } else if (k == 5 && !((Boolean)InventoryPetsConfig.disableOcelot.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_OCELOT.get(), 1));
                            loopy = 50;
                        } else if (k == 6 && !((Boolean)InventoryPetsConfig.disableMooshroom.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_MOOSHROOM.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 50 && h <= 74) {
                        k = rand.nextInt(14);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disableFurnace.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_FURNACE.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableEnchantingTable.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_ENCHANTING_TABLE.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disableCraftingTable.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_CRAFTING_TABLE.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disableChest.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_CHEST.get(), 1));
                            loopy = 50;
                        } else if (k == 4 && !((Boolean)InventoryPetsConfig.disableDoubleChest.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_DOUBLE_CHEST.get(), 1));
                            loopy = 50;
                        } else if (k == 5 && !((Boolean)InventoryPetsConfig.disableBed.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_BED.get(), 1));
                            loopy = 50;
                        } else if (k == 6 && !((Boolean)InventoryPetsConfig.disableJukebox.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_JUKEBOX.get(), 1));
                            loopy = 50;
                        } else if (k == 7 && !((Boolean)InventoryPetsConfig.disableAnvil.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_ANVIL.get(), 1));
                            loopy = 50;
                        } else if (k == 8 && !((Boolean)InventoryPetsConfig.disableBrewingStand.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_BREWING_STAND.get(), 1));
                            loopy = 50;
                        } else if (k == 9 && !((Boolean)InventoryPetsConfig.disableNetherPortal.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_NETHER_PORTAL.get(), 1));
                            loopy = 50;
                        } else if (k == 10 && !((Boolean)InventoryPetsConfig.disableEnderChest.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_ENDER_CHEST.get(), 1));
                            loopy = 50;
                        } else if (k == 11 && !((Boolean)InventoryPetsConfig.disableSaddle.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SADDLE.get(), 1));
                            loopy = 50;
                        } else if (k == 12 && !((Boolean)InventoryPetsConfig.disableLead.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_LEAD.get(), 1));
                            loopy = 50;
                        } else if (k == 13 && !((Boolean)InventoryPetsConfig.disableEndPortal.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_END_PORTAL.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 75 && h <= 79) {
                        k = rand.nextInt(11);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disablePingot.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_PINGOT.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableMickerson.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_MICKERSON.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disablePurpliciousCow.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_PCOW.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disableQuantumCrystalMonster.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_QCM.get(), 1));
                            loopy = 50;
                        } else if (k == 4 && !((Boolean)InventoryPetsConfig.disableBanana.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_BANANA.get(), 1));
                            loopy = 50;
                        } else if (k == 5 && !((Boolean)InventoryPetsConfig.disableDingot.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_DINGOT.get(), 1));
                            loopy = 50;
                        } else if (k == 6 && !((Boolean)InventoryPetsConfig.disableQuiver.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_QUIVER.get(), 1));
                            loopy = 50;
                        } else if (k == 7 && !((Boolean)InventoryPetsConfig.disableSponge.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SPONGE.get(), 1));
                            loopy = 50;
                        } else if (k == 8 && !((Boolean)InventoryPetsConfig.disableBiome.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_BIOME.get(), 1));
                            loopy = 50;
                        } else if (k == 9 && !((Boolean)InventoryPetsConfig.disableLoot.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_LOOT.get(), 1));
                            loopy = 50;
                        } else if (k == 10 && !((Boolean)InventoryPetsConfig.disableFlyingSaddle.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_FLYING_SADDLE.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 80 && h <= 83) {
                        k = rand.nextInt(5);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disableJuggernaut.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_JUGGERNAUT.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableIlluminati.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_ILLUMINATI.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disableSiamese.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SIAMESE.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disableDirt.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_DIRT.get(), 1));
                            loopy = 50;
                        } else if (k == 4 && !((Boolean)InventoryPetsConfig.disableCobblestone.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_COBBLESTONE.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 84 && h <= 92) {
                        k = rand.nextInt(8);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disablePacMan.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_PACMAN.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableCheetah.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_CHEETAH.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disableHouse.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_HOUSE.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disableSiamese.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SILVERFISH.get(), 1));
                            loopy = 50;
                        } else if (k == 4 && !((Boolean)InventoryPetsConfig.disableWolf.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_WOLF.get(), 1));
                            loopy = 50;
                        } else if (k == 5 && !((Boolean)InventoryPetsConfig.disableApple.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_APPLE.get(), 1));
                            loopy = 50;
                        } else if (k == 6 && !((Boolean)InventoryPetsConfig.disableTorch.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_TORCH.get(), 1));
                            loopy = 50;
                        } else if (k == 7 && !((Boolean)InventoryPetsConfig.disablePixie.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_PIXIE.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 93 && h <= 98) {
                        k = rand.nextInt(4);
                        if (k == 0 && !((Boolean)InventoryPetsConfig.disableShield.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SHIELD.get(), 1));
                            loopy = 50;
                        } else if (k == 1 && !((Boolean)InventoryPetsConfig.disableDubstep.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_DUBSTEP.get(), 1));
                            loopy = 50;
                        } else if (k == 2 && !((Boolean)InventoryPetsConfig.disableHeart.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_HEART.get(), 1));
                            loopy = 50;
                        } else if (k == 3 && !((Boolean)InventoryPetsConfig.disableMoon.get()).booleanValue()) {
                            techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_MOON.get(), 1));
                            loopy = 50;
                        }
                    }
                    if (h >= 99 && chestType == 0) {
                        techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_CLOUD.get(), 1));
                        loopy = 50;
                        continue;
                    }
                    if (h >= 99 && chestType == 1) {
                        techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_BLACK_HOLE.get(), 1));
                        loopy = 50;
                        continue;
                    }
                    if (h >= 99 && chestType == 2) {
                        techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_PUFFERFISH.get(), 1));
                        loopy = 50;
                        continue;
                    }
                    if (h >= 99 && chestType == 3) {
                        techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SUN.get(), 1));
                        loopy = 50;
                        continue;
                    }
                    if (h >= 99 && chestType == 4) {
                        techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_SLIME.get(), 1));
                        loopy = 50;
                        continue;
                    }
                    if (h < 99 || chestType != 5) continue;
                    techest.setItem(g, new ItemStack((ItemLike)InventoryPets.PET_WITHER.get(), 1));
                    loopy = 50;
                }
                continue;
            }
            if (g == 3 || g == 5) continue;
            int chance = rand.nextInt(100);
            h = rand.nextInt(67);
            if (chance <= 35) continue;
            if (h <= 49) {
                if (h >= 0 && h <= 14) {
                    r = rand.nextInt(3) + 1;
                    if (chestType == 0) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.QUARTZ, r));
                    } else if (chestType == 1) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.AMETHYST_SHARD, r));
                    } else if (chestType == 2) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.SPONGE, r));
                    } else if (chestType == 3) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.WHEAT, r));
                    } else if (chestType == 4) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.COBWEB, r));
                    } else if (chestType == 5) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.BLAZE_POWDER, r));
                    }
                }
                if (h >= 15 && h <= 30) {
                    r = rand.nextInt(6) + 1;
                    if (chestType == 0) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.ICE, r));
                    } else if (chestType == 1) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.RAW_IRON, r));
                    } else if (chestType == 2) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.WATER_BUCKET, r));
                    } else if (chestType == 3) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.OAK_LEAVES, r));
                    } else if (chestType == 4) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.STONE_BRICKS, r));
                    } else if (chestType == 5) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.NETHER_BRICKS, r));
                    }
                }
                if (h >= 31 && h <= 42) {
                    r = rand.nextInt(10) + 1;
                    if (chestType == 0) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.SNOWBALL, r));
                    } else if (chestType == 1) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.FIREWORK_ROCKET, r));
                    } else if (chestType == 2) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.COD, r));
                    } else if (chestType == 3) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MELON, r));
                    } else if (chestType == 4) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.COAL, r));
                    } else if (chestType == 5) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.GLOWSTONE_DUST, r));
                    }
                }
                if (h >= 43 && h <= 43) {
                    r = rand.nextInt(50);
                    if (r == 0) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_11, 1));
                    } else if (r == 1) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_BLOCKS, 1));
                    } else if (r == 2) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_CHIRP, 1));
                    } else if (r == 3) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_FAR, 1));
                    } else if (r == 4) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_MELLOHI, 1));
                    } else if (r == 5) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_MALL, 1));
                    } else if (r == 6) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_STAL, 1));
                    } else if (r == 7) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_STRAD, 1));
                    } else if (r == 8) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_WAIT, 1));
                    } else if (r == 9) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_WARD, 1));
                    } else if (r == 10) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_OTHERSIDE, 1));
                    } else if (r == 11) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_PIGSTEP, 1));
                    } else if (r == 12) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_13, 1));
                    } else if (r == 13) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.MUSIC_DISC_CAT, 1));
                    }
                }
                if (h >= 44 && h <= 47) {
                    r = rand.nextInt(4) + 1;
                    if (chestType == 0) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.GOLD_INGOT, r));
                    } else if (chestType == 1) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.GOLD_BLOCK, r));
                    } else if (chestType == 2) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.LAPIS_LAZULI, r));
                    } else if (chestType == 3) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.EMERALD, r));
                    } else if (chestType == 4) {
                        techest.setItem(g, new ItemStack((ItemLike)Blocks.OBSIDIAN, r));
                    } else if (chestType == 5) {
                        techest.setItem(g, new ItemStack((ItemLike)Items.NETHERITE_INGOT, r));
                    }
                }
                if (h < 48 || h > 49) continue;
                r = rand.nextInt(1) + 1;
                if (chestType == 1) {
                    techest.setItem(g, new ItemStack((ItemLike)Items.NETHER_STAR, 1));
                    continue;
                }
                techest.setItem(g, new ItemStack((ItemLike)Items.DIAMOND, r));
                continue;
            }
            if (h >= 50 && h <= 50) {
                r = rand.nextInt(4) + 1;
                techest.setItem(g, new ItemStack((ItemLike)InventoryPets.NUGGET_EMERALD.get(), r));
            }
            if (h >= 51 && h <= 51) {
                r = rand.nextInt(4) + 1;
                techest.setItem(g, new ItemStack((ItemLike)InventoryPets.NUGGET_DIAMOND.get(), r));
            }
            if (h >= 52 && h <= 53) {
                r = rand.nextInt(4) + 1;
                techest.setItem(g, new ItemStack((ItemLike)InventoryPets.NUGGET_ENDER.get(), r));
            }
            if (h >= 54 && h <= 57) {
                r = rand.nextInt(4) + 1;
                techest.setItem(g, new ItemStack((ItemLike)InventoryPets.NUGGET_LAPIS.get(), r));
            }
            if (h >= 58 && h <= 61) {
                r = rand.nextInt(4) + 1;
                techest.setItem(g, new ItemStack((ItemLike)InventoryPets.NUGGET_OBSIDIAN.get(), r));
            }
            if (h < 62 || h > 66) continue;
            r = rand.nextInt(4) + 1;
            techest.setItem(g, new ItemStack((ItemLike)Items.IRON_NUGGET, r));
        }
    }
}

