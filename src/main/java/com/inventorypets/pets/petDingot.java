/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderSet
 *  net.minecraft.core.Registry
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.networking.PacketStructureSender;
import com.inventorypets.screens.PetNamerScreen;
import com.inventorypets.screens.StructureFinderScreen;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;

public class petDingot
extends Item {
    private boolean eatFlag;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean screenLoading = false;
    private int structureChecker = 0;
    private int loadedChecker = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.COPPER_INGOT;

    public petDingot(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void structureFinder(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new StructureFinderScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean par5) {
        ItemStack s2;
        if (!(entity instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entity;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && world.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == this) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodDingot.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableDingot.get()).booleanValue()) {
            return;
        }
        if (this.structureChecker >= 40 && itemstack.has(ModDataComponents.STRUCTURE_CHECK) && !world.isClientSide) {
            this.structureChecker = 0;
            Boolean structureInChecker = (Boolean)itemstack.get(ModDataComponents.STRUCTURE_CHECK);
            if (structureInChecker != null && structureInChecker.booleanValue()) {
                String str;
                String xstr = str = (String)itemstack.get(ModDataComponents.STRUCTURE_TO_FIND);
                String zstr = "";
                int sepLoc = str.indexOf("|");
                if (sepLoc >= 0) {
                    str = str.substring(0, sepLoc).trim();
                    zstr = xstr = xstr.substring(sepLoc + 1, xstr.length());
                    int sepLoc2 = xstr.indexOf("|");
                    if (sepLoc2 >= 0) {
                        xstr = xstr.substring(0, sepLoc2);
                        zstr = zstr.substring(sepLoc2 + 1, zstr.length());
                    }
                }
                int structureX = Integer.parseInt(xstr);
                int structureZ = Integer.parseInt(zstr);
                int structureY = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, structureX, structureZ) + 8;
                if (str.contains("Sky Dungeon")) {
                    structureY = 206;
                } else if (str.contains("Space Dungeon")) {
                    structureY = 100;
                } else if (str.contains("Nether Dungeon")) {
                    structureY = 36;
                } else if (str.contains("Underground Dungeon")) {
                    structureY = 18;
                } else if (str.contains("Tree Top")) {
                    structureY = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, structureX, structureZ) + 1;
                } else if (str.contains("Sea Cave")) {
                    structureY = world.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, structureX, structureZ) + 1;
                } else if (str.contains("Stronghold")) {
                    structureY = 0;
                } else if (str.contains("Ancient City")) {
                    structureY = -51;
                }
                double diffX = Math.abs((double)structureX - entityplayer.getX());
                double diffY = Math.abs((double)structureY - entityplayer.getY());
                double diffZ = Math.abs((double)structureZ - entityplayer.getZ());
                int dist = (int)Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
                if (!str.isEmpty() && dist <= 15) {
                    Object tempString = Component.translatable((String)"info.dingot.nearby").getString();
                    if (str.contains("Stronghold")) {
                        tempString = (String)tempString + Component.translatable((String)"info.dingot.stronghold").getString();
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)("\u00a7l" + str + "\u00a7r" + (String)tempString)));
                    } else {
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)("\u00a7l" + str + "\u00a7r" + (String)tempString)));
                    }
                    world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.structure_found.get(), SoundSource.PLAYERS, 0.4f, 1.0f);
                    itemstack.set(ModDataComponents.STRUCTURE_CHECK, (Object)false);
                }
            }
        } else if (this.structureChecker < 40) {
            ++this.structureChecker;
        }
        if (this.loadedChecker >= 50) {
            if (world.isClientSide) {
                if ((Integer)entityplayer.getData(ModDataAttachments.STRUCTURE_FLAG) == 2) {
                    this.structureFinder(entityplayer);
                    entityplayer.setData(ModDataAttachments.STRUCTURE_FLAG, (Object)0);
                    this.loadedChecker = 0;
                    this.screenLoading = false;
                }
            } else {
                this.screenLoading = false;
                this.loadedChecker = 0;
            }
        } else {
            ++this.loadedChecker;
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && itemstack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !world.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Items.COPPER_INGOT && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.DIAMOND_HOE || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(itemstack, 0);
                    world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.biome.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !world.isClientSide && !entityplayer.isCreative() && itemstack.getDamageValue() >= 3 && this.chkEat > 40) {
            int dmg;
            this.chkEat = 0;
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                s2 = entityplayer.getInventory().getItem(k);
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Items.COPPER_INGOT && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.DIAMOND_HOE || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(entityplayer, s2);
                }
                this.setDamage(itemstack, 0);
                world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
                this.complainFlag = false;
                this.eatFlag = true;
            }
            if (!this.eatFlag && (dmg = itemstack.getDamageValue()) == 0) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                this.eatFlag = true;
                this.complainFlag = false;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableDingot.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (playerIn.isCrouching() && !this.screenLoading && (itemstack.getDamageValue() < 3 || playerIn.isCreative())) {
            if (!worldIn.isClientSide) {
                String pathClean;
                String nameClean;
                if (itemstack.get((DataComponentType)ModDataComponents.LOADED_FLAG.get()) != null) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.dingot_waiting.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"info.dingot.loading"));
                } else {
                    itemstack.set(ModDataComponents.LOADED_FLAG, (Object)true);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.dingot_waiting_long.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
                    playerIn.sendSystemMessage((Component)Component.translatable((String)"info.dingot.firsttime"));
                }
                ServerLevel serverWorld = (ServerLevel)worldIn;
                ServerPlayer serverPlayer = (ServerPlayer)playerIn;
                String dimension = serverWorld.dimension().location().getPath();
                int regSize = playerIn.getServer().registryAccess().registryOrThrow(Registries.STRUCTURE).size();
                Structure[] structures = new Structure[regSize];
                BlockPos[] structurePos = new BlockPos[regSize];
                Structure chkStructure = null;
                BlockPos chkStructurePos = null;
                Identifier chkStructureResourceLocation = null;
                for (int kk = 0; kk < regSize; ++kk) {
                    Registry registry = serverWorld.registryAccess().registryOrThrow(Registries.STRUCTURE);
                    chkStructure = (Structure)registry.byId(kk);
                    chkStructureResourceLocation = worldIn.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey((Object)chkStructure);
                    Optional holderOpt = registry.getHolder(chkStructureResourceLocation);
                    if (((Boolean)InventoryPetsConfig.dingotIPStruturesOnly.get()).booleanValue() && chkStructureResourceLocation.getNamespace().contains("inventorypets")) {
                        if (dimension.equals("the_nether")) {
                            if (chkStructureResourceLocation.getPath().equalsIgnoreCase("nether_dungeon") && !holderOpt.isEmpty()) {
                                holderSet = HolderSet.direct((Holder[])new Holder[]{(Holder)holderOpt.get()});
                                pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, (HolderSet)holderSet, playerIn.blockPosition(), ((Integer)InventoryPetsConfig.biomeSearchChunks.get()).intValue(), false);
                                if (pair != null) {
                                    chkStructurePos = (BlockPos)pair.getFirst();
                                    if (chkStructureResourceLocation.getPath().toLowerCase().contains("nether_dungeon")) {
                                        chkStructurePos = chkStructurePos.offset(8, 28, 8);
                                    }
                                }
                            }
                        } else if (dimension.equals("the_end")) {
                            if (chkStructureResourceLocation.getPath().equalsIgnoreCase("space_dungeon")) {
                                if (!holderOpt.isEmpty()) {
                                    holderSet = HolderSet.direct((Holder[])new Holder[]{(Holder)holderOpt.get()});
                                    pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, (HolderSet)holderSet, playerIn.blockPosition(), ((Integer)InventoryPetsConfig.biomeSearchChunks.get()).intValue(), false);
                                    if (pair != null) {
                                        chkStructurePos = (BlockPos)pair.getFirst();
                                        if (chkStructureResourceLocation.getPath().toLowerCase().contains("space_dungeon")) {
                                            chkStructurePos = chkStructurePos.offset(8, 108, 8);
                                        }
                                    }
                                }
                            } else {
                                chkStructurePos = null;
                            }
                        } else if (dimension.equals("overworld")) {
                            if (chkStructureResourceLocation.getPath().equalsIgnoreCase("sky_dungeon") || chkStructureResourceLocation.getPath().equalsIgnoreCase("tree_top") || chkStructureResourceLocation.getPath().equalsIgnoreCase("sea_cave") || chkStructureResourceLocation.getPath().equalsIgnoreCase("underground_dungeon")) {
                                if (!holderOpt.isEmpty()) {
                                    holderSet = HolderSet.direct((Holder[])new Holder[]{(Holder)holderOpt.get()});
                                    pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, (HolderSet)holderSet, playerIn.blockPosition(), ((Integer)InventoryPetsConfig.biomeSearchChunks.get()).intValue(), false);
                                    if (pair != null) {
                                        chkStructurePos = (BlockPos)pair.getFirst();
                                        if (chkStructureResourceLocation.getPath().toLowerCase().contains("sky_dungeon")) {
                                            chkStructurePos = chkStructurePos.offset(8, 206, 8);
                                        } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("underground_dungeon")) {
                                            chkStructurePos = chkStructurePos.offset(8, 18, 8);
                                        } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("sea_cave")) {
                                            chkStructurePos = chkStructurePos.offset(8, 0, 8);
                                        } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("tree_top")) {
                                            chkStructurePos = chkStructurePos.offset(8, 0, 8);
                                        } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("stronghold")) {
                                            chkStructurePos = chkStructurePos.offset(8, -50, 8);
                                        }
                                    }
                                }
                            } else {
                                chkStructurePos = null;
                            }
                        } else {
                            chkStructurePos = null;
                        }
                    } else if (!((Boolean)InventoryPetsConfig.dingotIPStruturesOnly.get()).booleanValue()) {
                        if (dimension.equals("the_nether")) {
                            if (chkStructureResourceLocation.getPath().equalsIgnoreCase("bastion_remnant") || chkStructureResourceLocation.getPath().equalsIgnoreCase("ruined_portal_nether") || chkStructureResourceLocation.getPath().equalsIgnoreCase("nether_fossil") || chkStructureResourceLocation.getPath().equalsIgnoreCase("fortress") || chkStructureResourceLocation.getPath().equalsIgnoreCase("nether_dungeon")) {
                                if (!holderOpt.isEmpty()) {
                                    holderSet = HolderSet.direct((Holder[])new Holder[]{(Holder)holderOpt.get()});
                                    pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, (HolderSet)holderSet, playerIn.blockPosition(), 5, false);
                                    if (pair != null) {
                                        chkStructurePos = (BlockPos)pair.getFirst();
                                        if (chkStructureResourceLocation.getPath().toLowerCase().contains("nether_dungeon")) {
                                            chkStructurePos = chkStructurePos.offset(8, 28, 8);
                                        }
                                    }
                                }
                            } else {
                                chkStructurePos = null;
                            }
                        } else if (dimension.equals("the_end")) {
                            if (chkStructureResourceLocation.getPath().equalsIgnoreCase("end_city") || chkStructureResourceLocation.getPath().equalsIgnoreCase("space_dungeon")) {
                                if (!holderOpt.isEmpty()) {
                                    holderSet = HolderSet.direct((Holder[])new Holder[]{(Holder)holderOpt.get()});
                                    pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, (HolderSet)holderSet, playerIn.blockPosition(), 5, false);
                                    if (pair != null) {
                                        chkStructurePos = (BlockPos)pair.getFirst();
                                        if (chkStructureResourceLocation.getPath().toLowerCase().contains("space_dungeon")) {
                                            chkStructurePos = chkStructurePos.offset(8, 108, 8);
                                        }
                                    }
                                }
                            } else {
                                chkStructurePos = null;
                            }
                        } else if (dimension.equals("overworld")) {
                            if (chkStructureResourceLocation.getPath().equalsIgnoreCase("bastion_remnant") || chkStructureResourceLocation.getPath().equalsIgnoreCase("ruined_portal_nether") || chkStructureResourceLocation.getPath().equalsIgnoreCase("nether_fossil") || chkStructureResourceLocation.getPath().equalsIgnoreCase("fortress") || chkStructureResourceLocation.getPath().equalsIgnoreCase("nether_dungeon") || chkStructureResourceLocation.getPath().equalsIgnoreCase("end_city") || chkStructureResourceLocation.getPath().equalsIgnoreCase("space_dungeon")) {
                                chkStructurePos = null;
                            } else if (!holderOpt.isEmpty()) {
                                holderSet = HolderSet.direct((Holder[])new Holder[]{(Holder)holderOpt.get()});
                                pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, (HolderSet)holderSet, playerIn.blockPosition(), 5, false);
                                if (pair != null) {
                                    chkStructurePos = (BlockPos)pair.getFirst();
                                    if (chkStructureResourceLocation.getPath().toLowerCase().contains("sky_dungeon")) {
                                        chkStructurePos = chkStructurePos.offset(8, 206, 8);
                                    } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("underground_dungeon")) {
                                        chkStructurePos = chkStructurePos.offset(8, 18, 8);
                                    } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("sea_cave")) {
                                        chkStructurePos = chkStructurePos.offset(8, 0, 8);
                                    } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("tree_top")) {
                                        chkStructurePos = chkStructurePos.offset(8, 0, 8);
                                    } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("stronghold")) {
                                        chkStructurePos = chkStructurePos.offset(8, -50, 8);
                                    } else if (chkStructureResourceLocation.getPath().toLowerCase().contains("ancient_city")) {
                                        chkStructurePos = chkStructurePos.offset(8, -51, 8);
                                    }
                                }
                            }
                        }
                    } else {
                        chkStructurePos = null;
                    }
                    if (chkStructurePos == null) continue;
                    structures[kk] = chkStructure;
                    structurePos[kk] = chkStructurePos;
                }
                for (int i = 0; i < structures.length; ++i) {
                    for (int j = i + 1; j < structures.length; ++j) {
                        Structure tmp = null;
                        BlockPos tmpPos = null;
                        Identifier chki = worldIn.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey((Object)structures[i]);
                        Identifier chkj = worldIn.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey((Object)structures[j]);
                        if (chki == null || chkj == null || chki.getPath().compareTo(chkj.getPath()) <= 0) continue;
                        tmp = structures[i];
                        tmpPos = structurePos[i];
                        structures[i] = structures[j];
                        structurePos[i] = structurePos[j];
                        structures[j] = tmp;
                        structurePos[j] = tmpPos;
                    }
                }
                int newSize = 0;
                for (int i = 0; i < structures.length; ++i) {
                    if (structures[i] == null) continue;
                    ++newSize;
                }
                int structureCount = 0;
                Structure[] cleanStructures = new Structure[newSize];
                BlockPos[] cleanStructurePos = new BlockPos[newSize];
                for (int i = 0; i < structures.length; ++i) {
                    if (structures[i] == null || structurePos[i] == null) continue;
                    cleanStructures[structureCount] = structures[i];
                    cleanStructurePos[structureCount] = structurePos[i];
                    ++structureCount;
                }
                Object packetString = "";
                for (int i = 0; i < cleanStructures.length && ((String)(packetString = (String)packetString + (nameClean = worldIn.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey((Object)cleanStructures[i]).getNamespace()) + ":" + (pathClean = worldIn.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey((Object)cleanStructures[i]).getPath()) + "|" + cleanStructurePos[i].getX() + "|" + cleanStructurePos[i].getY() + "|" + cleanStructurePos[i].getZ() + "^")).length() <= 49800; ++i) {
                }
                PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketStructureSender((String)packetString), (CustomPacketPayload[])new CustomPacketPayload[0]);
                this.screenLoading = true;
            }
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        } else if (!playerIn.isCrouching() && (itemstack.getDamageValue() < 3 || playerIn.isCreative())) {
            int structureX = 0;
            int structureZ = 0;
            boolean currentStructure = false;
            String str = "Plains";
            this.screenLoading = false;
            if (itemstack.has(ModDataComponents.STRUCTURE_TO_FIND)) {
                str = (String)itemstack.get(ModDataComponents.STRUCTURE_TO_FIND);
                assert (str != null);
                int sepLoc = str.indexOf("|");
                if (sepLoc >= 0) {
                    str = str.substring(0, sepLoc).trim();
                }
            }
            if (!worldIn.isClientSide) {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.biome.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
            }
            if (worldIn.isClientSide) {
                boolean foundStructure = false;
                if (itemstack.has(ModDataComponents.STRUCTURE_TO_FIND)) {
                    String structureString = (String)itemstack.get(ModDataComponents.STRUCTURE_TO_FIND);
                    String dimName = (String)itemstack.get(ModDataComponents.DIM_NAME);
                    int sepLoc = structureString.indexOf("|");
                    String structure = sepLoc >= 0 ? structureString.substring(0, sepLoc).trim() : "";
                    String structureCoords = structureString.substring(sepLoc + 1, structureString.length());
                    if ((sepLoc = structureCoords.indexOf("|")) >= 0) {
                        String structureCheck = structure;
                        structureX = Integer.parseInt(structureCoords.substring(0, sepLoc).trim());
                        structureZ = Integer.parseInt(structureCoords.substring(sepLoc + 1, structureCoords.length()).trim());
                        int structureY = worldIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, structureX, structureZ) + 8;
                        if (str.contains("Sky Dungeon")) {
                            structureY = 206;
                        } else if (str.contains("Space Dungeon")) {
                            structureY = 100;
                        } else if (str.contains("Nether Dungeon")) {
                            structureY = 36;
                        } else if (str.contains("Underground Dungeon")) {
                            structureY = 18;
                        } else if (str.contains("Tree Top")) {
                            structureY = worldIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, structureX, structureZ) + 1;
                        } else if (str.contains("Sea Cave")) {
                            structureY = worldIn.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, structureX, structureZ) + 1;
                        } else if (str.contains("Stronghold")) {
                            structureY = 0;
                        } else if (str.contains("Ancient City")) {
                            structureY = -51;
                        }
                        double diffX = Math.abs((double)structureX - playerIn.getX());
                        double diffY = Math.abs((double)structureY - playerIn.getY());
                        double diffZ = Math.abs((double)structureZ - playerIn.getZ());
                        int dist = (int)Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
                        String dimCheck = playerIn.level().dimension().location().getPath();
                        if (!dimCheck.equals(dimName) && dimName != null) {
                            playerIn.sendSystemMessage((Component)Component.translatable((String)I18n.get((String)("\u00a7l" + I18n.get((String)structureCheck, (Object[])new Object[0]) + "\u00a7r" + I18n.get((String)"info.biome.wrongdimension", (Object[])new Object[0])), (Object[])new Object[0])));
                            foundStructure = true;
                        } else {
                            playerIn.sendSystemMessage((Component)Component.translatable((String)I18n.get((String)("\u00a7l" + I18n.get((String)structureCheck, (Object[])new Object[0]) + "\u00a7r" + I18n.get((String)"info.dingot.about", (Object[])new Object[0]) + dist + I18n.get((String)"info.dingot.meters", (Object[])new Object[0])), (Object[])new Object[0])));
                            petDingot.LookAt(structureX, structureY, structureZ, playerIn, false);
                            foundStructure = true;
                        }
                    }
                }
                if (!foundStructure && !currentStructure && worldIn.isClientSide && !foundStructure) {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)I18n.get((String)"info.dingot.notfound", (Object[])new Object[0])));
                }
            }
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public static void LookAt(double px, double py, double pz, Player me, boolean isCave) {
        if (isCave) {
            me.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(px, 0.0, pz));
        } else {
            me.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(px, py, pz));
        }
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petdingot1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petdingot2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodDingot.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.COPPER_INGOT.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.DIAMOND_HOE.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.BLUE) + I18n.get((String)"tooltip.ip.special", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableDingot.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

