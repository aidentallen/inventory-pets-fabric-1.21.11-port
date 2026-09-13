/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceKey
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
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.levelgen.Heightmap$Types
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
import com.inventorypets.networking.PacketBiomeSender;
import com.inventorypets.screens.BiomeFinderScreen;
import com.inventorypets.screens.PetNamerScreen;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;

public class petBiome
extends Item {
    private boolean eatFlag;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean screenLoading = false;
    private int biomeChecker = 0;
    private int loadedChecker = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.GRASS_BLOCK;

    public petBiome(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void biomeFinder(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new BiomeFinderScreen(entityplayer.getInventory()));
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
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBiome.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableBiome.get()).booleanValue()) {
            return;
        }
        if (this.biomeChecker >= 40 && itemstack.has(ModDataComponents.BIOME_CHECK) && !world.isClientSide) {
            this.biomeChecker = 0;
            Boolean biomeInChecker = (Boolean)itemstack.get(ModDataComponents.BIOME_CHECK);
            if (biomeInChecker != null) {
                String str = (String)itemstack.get(ModDataComponents.BIOME_TO_FIND);
                int sepLoc = str.indexOf("|");
                if (sepLoc >= 0) {
                    str = str.substring(0, sepLoc).trim();
                }
                Holder holder = entityplayer.level().getBiome(entityplayer.blockPosition());
                String biome = ((ResourceKey)holder.unwrapKey().get()).location().getPath();
                String biomeName = (String)itemstack.get(ModDataComponents.BIOME_NAME);
                if (biomeName != null && !biomeName.isEmpty() && biome.equalsIgnoreCase(str) && Boolean.TRUE.equals(itemstack.get(ModDataComponents.BIOME_CHECK))) {
                    String tempString = Component.translatable((String)"info.biome.nowin").getString();
                    entityplayer.sendSystemMessage((Component)Component.translatable((String)(tempString + "\u00a7l" + biomeName + "\u00a7r")));
                    world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.biome_found.get(), SoundSource.PLAYERS, 0.4f, 1.0f);
                    itemstack.set(ModDataComponents.BIOME_CHECK, (Object)false);
                }
            }
        } else if (this.biomeChecker < 40) {
            ++this.biomeChecker;
        }
        if (this.loadedChecker >= 50) {
            if (world.isClientSide) {
                if ((Integer)entityplayer.getData(ModDataAttachments.BIOME_FLAG) == 2) {
                    this.biomeFinder(entityplayer);
                    entityplayer.setData(ModDataAttachments.BIOME_FLAG, (Object)0);
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
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Blocks.GRASS_BLOCK.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.COMPASS || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(itemstack, 0);
                    world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.biome.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
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
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Blocks.GRASS_BLOCK.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.COMPASS || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
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
        if (((Boolean)InventoryPetsConfig.disableBiome.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (playerIn.isCrouching() && !this.screenLoading && (itemstack.getDamageValue() < 3 || playerIn.isCreative())) {
            if (!worldIn.isClientSide) {
                playerIn.sendSystemMessage((Component)Component.translatable((String)"info.biome.loading"));
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.biome_waiting.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
                ServerLevel serverWorld = (ServerLevel)worldIn;
                ServerPlayer serverPlayer = (ServerPlayer)playerIn;
                int regSize = playerIn.getServer().registryAccess().registryOrThrow(Registries.BIOME).size();
                Biome[] biomes = new Biome[regSize];
                BlockPos[] biomePos = new BlockPos[regSize];
                Biome chkBiome = null;
                BlockPos chkBiomePos = null;
                for (int kk = 0; kk < regSize; ++kk) {
                    chkBiome = (Biome)playerIn.getServer().registryAccess().registryOrThrow(Registries.BIOME).byId(kk);
                    chkBiomePos = this.findNearestBiome(serverWorld, chkBiome, playerIn.blockPosition(), (Integer)InventoryPetsConfig.biomeSearchChunks.get() * 16, 16);
                    if (chkBiomePos == null) continue;
                    biomes[kk] = chkBiome;
                    biomePos[kk] = chkBiomePos;
                }
                for (int i = 0; i < biomes.length; ++i) {
                    for (int j = i + 1; j < biomes.length; ++j) {
                        Biome tmp = null;
                        BlockPos tmpPos = null;
                        Identifier chki = worldIn.registryAccess().registryOrThrow(Registries.BIOME).getKey((Object)biomes[i]);
                        Identifier chkj = worldIn.registryAccess().registryOrThrow(Registries.BIOME).getKey((Object)biomes[j]);
                        if (chki == null || chkj == null || chki.getPath().compareTo(chkj.getPath()) <= 0) continue;
                        tmp = biomes[i];
                        tmpPos = biomePos[i];
                        biomes[i] = biomes[j];
                        biomePos[i] = biomePos[j];
                        biomes[j] = tmp;
                        biomePos[j] = tmpPos;
                    }
                }
                int newSize = 0;
                for (int i = 0; i < biomes.length; ++i) {
                    if (biomes[i] == null) continue;
                    ++newSize;
                }
                int biomeCount = 0;
                Biome[] cleanBiomes = new Biome[newSize];
                BlockPos[] cleanBiomePos = new BlockPos[newSize];
                for (int i = 0; i < biomes.length; ++i) {
                    if (biomes[i] == null || biomePos[i] == null) continue;
                    cleanBiomes[biomeCount] = biomes[i];
                    cleanBiomePos[biomeCount] = biomePos[i];
                    ++biomeCount;
                }
                Object packetString = "";
                for (int i = 0; i < cleanBiomes.length; ++i) {
                    String nameClean = worldIn.registryAccess().registryOrThrow(Registries.BIOME).getKey((Object)cleanBiomes[i]).getNamespace();
                    String pathClean = worldIn.registryAccess().registryOrThrow(Registries.BIOME).getKey((Object)cleanBiomes[i]).getPath();
                    packetString = (String)packetString + nameClean + ":" + pathClean + "|" + cleanBiomePos[i].getX() + "|" + cleanBiomePos[0].getY() + "|" + cleanBiomePos[i].getZ() + "^";
                }
                PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new PacketBiomeSender((String)packetString), (CustomPacketPayload[])new CustomPacketPayload[0]);
                this.screenLoading = true;
            }
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        } else if (!playerIn.isCrouching() && (itemstack.getDamageValue() < 3 || playerIn.isCreative())) {
            String str;
            int biomeX = 0;
            int biomeZ = 0;
            boolean currentBiome = false;
            if (worldIn.isClientSide) {
                Holder holder = playerIn.level().getBiome(playerIn.blockPosition());
                str = ((ResourceKey)holder.unwrapKey().get()).location().getPath();
            } else {
                str = "Plains";
            }
            this.screenLoading = false;
            itemstack.set(ModDataComponents.LOADED_FLAG, (Object)false);
            if (itemstack.has(ModDataComponents.BIOME_TO_FIND)) {
                str = (String)itemstack.get(ModDataComponents.BIOME_TO_FIND);
                assert (str != null);
                int sepLoc = str.indexOf("|");
                if (sepLoc >= 0) {
                    str = str.substring(0, sepLoc).trim();
                }
            }
            if (!worldIn.isClientSide) {
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.biome.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
            }
            if (worldIn.isClientSide) {
                String biome;
                Holder holder = playerIn.level().getBiome(playerIn.blockPosition());
                if (holder != null) {
                    String biomeCheck = "biome." + ((ResourceKey)holder.unwrapKey().get()).location().getNamespace() + "." + ((ResourceKey)holder.unwrapKey().get()).location().getPath();
                    biome = ((ResourceKey)holder.unwrapKey().get()).location().getPath();
                    if (!biome.equals("") && biome.toLowerCase().contains(str.toLowerCase())) {
                        playerIn.sendSystemMessage((Component)Component.translatable((String)(I18n.get((String)"pop.inventorypets.biomein", (Object[])new Object[0]) + "\u00a7l" + I18n.get((String)biomeCheck, (Object[])new Object[0]) + "\u00a7r")));
                        currentBiome = true;
                        boolean isCave = false;
                        if (biome.contains("cave")) {
                            isCave = true;
                        }
                        petBiome.LookAt(playerIn.getX(), playerIn.getY() - 2.0, playerIn.getZ(), playerIn, isCave);
                    }
                }
                boolean foundBiome = false;
                if (!currentBiome && itemstack.has(ModDataComponents.BIOME_TO_FIND)) {
                    String biomeString = (String)itemstack.get(ModDataComponents.BIOME_TO_FIND);
                    String dimName = (String)itemstack.get(ModDataComponents.DIM_NAME);
                    int sepLoc = biomeString.indexOf("|");
                    biome = sepLoc >= 0 ? biomeString.substring(0, sepLoc).trim() : "";
                    String biomeCoords = biomeString.substring(sepLoc + 1, biomeString.length());
                    if ((sepLoc = biomeCoords.indexOf("|")) >= 0) {
                        biomeX = Integer.parseInt(biomeCoords.substring(0, sepLoc).trim());
                        biomeZ = Integer.parseInt(biomeCoords.substring(sepLoc + 1, biomeCoords.length()).trim());
                        double diffX = Math.abs((double)biomeX - playerIn.getX());
                        double diffZ = Math.abs((double)biomeZ - playerIn.getZ());
                        int dist = (int)Math.sqrt(diffX * diffX + diffZ * diffZ);
                        String biomeCheck = "biome." + ((ResourceKey)holder.unwrapKey().get()).location().getNamespace() + "." + biome;
                        String dimCheck = playerIn.level().dimension().location().getPath();
                        if (!dimCheck.equals(dimName) && dimName != null) {
                            playerIn.sendSystemMessage((Component)Component.translatable((String)I18n.get((String)("\u00a7l" + I18n.get((String)biomeCheck, (Object[])new Object[0]) + "\u00a7r" + I18n.get((String)"info.biome.wrongdimension", (Object[])new Object[0])), (Object[])new Object[0])));
                            foundBiome = true;
                        } else {
                            playerIn.sendSystemMessage((Component)Component.translatable((String)(I18n.get((String)"info.biome.hasfound", (Object[])new Object[0]) + "\u00a7l" + I18n.get((String)biomeCheck, (Object[])new Object[0]) + "\u00a7r" + I18n.get((String)"info.biome.about", (Object[])new Object[0]) + dist + I18n.get((String)"info.biome.meters", (Object[])new Object[0]))));
                            boolean isCave = false;
                            if (biome.contains("cave")) {
                                isCave = true;
                            }
                            BlockPos heightCheck = new BlockPos(biomeX, 0, biomeZ);
                            BlockPos coordinates = worldIn.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, heightCheck);
                            petBiome.LookAt(biomeX, coordinates.getY(), biomeZ, playerIn, isCave);
                            foundBiome = true;
                        }
                    }
                }
                if (!foundBiome && !currentBiome && worldIn.isClientSide && !foundBiome) {
                    playerIn.sendSystemMessage((Component)Component.translatable((String)(str + I18n.get((String)"info.biome.notfound", (Object[])new Object[0]))));
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

    public BlockPos findNearestBiome(ServerLevel level, Biome biome, BlockPos pos, int i, int j) {
        Pair bb = level.getChunkSource().getGenerator().getBiomeSource().findBiomeHorizontal(pos.getX(), pos.getY(), pos.getZ(), i, j, b_val -> b_val.value() == biome, level.random, true, level.getChunkSource().randomState().sampler());
        return bb != null && bb.getFirst() != null ? (BlockPos)bb.getFirst() : null;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petbiome1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petbiome2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBiome.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.COMPASS.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.GRASS_BLOCK.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.BLUE) + I18n.get((String)"tooltip.ip.special", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableBiome.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

