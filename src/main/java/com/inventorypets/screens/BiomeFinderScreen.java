/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.network.PacketDistributor
 *  org.apache.logging.log4j.Level
 */
package com.inventorypets.screens;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.networking.PacketBiomeFinder;
import com.inventorypets.networking.PacketBiomeName;
import com.inventorypets.networking.PacketDimensionName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.logging.log4j.Level;

@OnlyIn(value=Dist.CLIENT)
public class BiomeFinderScreen
extends Screen {
    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;
    private static String title = I18n.get((String)"gui.biometofind", (Object[])new Object[0]);
    private static final Identifier GUI = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/gui/container/pet_namer.png");
    private String searchBiome;
    private ItemStack currentItem;
    LocalPlayer clientPlayer;
    private String s;
    private int biomeNum;
    private int arraySize;
    private String[] biomes2;
    private String[] biomePath;
    private BlockPos[] biomePos;
    protected MutableComponent cancelText;
    protected MutableComponent acceptText;
    protected Component leftText;
    protected Component rightText;
    boolean closeFlag;

    public BiomeFinderScreen(Inventory inventoryplayer) {
        super((Component)Component.translatable((String)title));
        this.clientPlayer = Minecraft.getInstance().player;
        this.biomes2 = new String[2000];
        this.biomePath = new String[2000];
        this.biomePos = new BlockPos[2000];
        if (inventoryplayer.getSelected().has(ModDataComponents.BIOME_TO_FIND)) {
            this.searchBiome = (String)inventoryplayer.getSelected().get(ModDataComponents.BIOME_TO_FIND);
        }
        this.currentItem = inventoryplayer.getSelected();
        this.cancelText = Component.translatable((String)"gui.cancel");
        this.acceptText = Component.translatable((String)"gui.inventorypets.find");
        this.leftText = Component.literal((String)"<");
        this.rightText = Component.literal((String)">");
        this.arraySize = 0;
    }

    protected void init() {
        int relX = (this.width - 179) / 2;
        int relY = (this.height - 151) / 2;
        this.biomeNum = 0;
        LocalPlayer player = Minecraft.getInstance().player;
        String biomeString = (String)player.getData(ModDataAttachments.BIOME_TO_SEND);
        char delimiter = '^';
        for (int i = 0; i < biomeString.length(); ++i) {
            if (biomeString.charAt(i) != delimiter) continue;
            ++this.arraySize;
        }
        String[] biomes = new String[this.arraySize];
        int pathEnd = 0;
        int biomeEnd = 0;
        int tempX = 0;
        int tempY = 0;
        int tempZ = 0;
        int tempEnd = 0;
        this.closeFlag = false;
        for (int i = 0; i < this.arraySize; ++i) {
            pathEnd = biomeString.indexOf(":");
            this.biomePath[i] = biomeString.substring(0, pathEnd);
            biomeString = biomeString.substring(pathEnd + 1, biomeString.length());
            biomeEnd = biomeString.indexOf("|");
            biomes[i] = biomeString.substring(0, biomeEnd).trim();
            this.biomes2[i] = biomes[i];
            biomeString = biomeString.substring(biomeEnd + 1, biomeString.length());
            tempEnd = biomeString.indexOf("|");
            tempX = Integer.parseInt(biomeString.substring(0, tempEnd));
            biomeString = biomeString.substring(tempEnd + 1, biomeString.length());
            tempEnd = biomeString.indexOf("|");
            tempY = Integer.parseInt(biomeString.substring(0, tempEnd));
            biomeString = biomeString.substring(tempEnd + 1, biomeString.length());
            tempEnd = biomeString.indexOf("^");
            tempZ = Integer.parseInt(biomeString.substring(0, tempEnd));
            biomeString = biomeString.substring(tempEnd + 1, biomeString.length());
            this.biomePos[i] = new BlockPos(tempX, tempY, tempZ);
        }
        if (biomes.length > 0) {
            this.searchBiome = biomes[this.biomeNum];
            this.searchBiome = "biome." + this.biomePath[this.biomeNum] + "." + this.searchBiome;
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.cancelText, builder -> this.close()).bounds(relX + 10, relY + 72, 72, 20).build());
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.acceptText, builder -> this.searchForBiome()).bounds(relX + 94, relY + 72, 72, 20).build());
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.leftText, builder -> this.navLeft()).bounds(relX + 10, relY + 44, 14, 20).build());
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.rightText, builder -> this.navRight()).bounds(relX + 152, relY + 44, 14, 20).build());
            Object tmpDisplayName = I18n.get((String)this.searchBiome, (Object[])new Object[0]);
            if (((String)tmpDisplayName).length() > 20) {
                tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
            }
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.literal((String)tmpDisplayName), builder -> this.searchForBiome()).bounds(relX + 30, relY + 44, 116, 20).build());
        } else {
            InventoryPets.LOGGER.log(Level.DEBUG, "Error: no elements in biome array. Closing.");
            this.close();
        }
    }

    public boolean isPauseScreen() {
        return false;
    }

    public Component getNarrationMessage() {
        return Component.translatable((String)I18n.get((String)"gui.biometofind", (Object[])new Object[0]));
    }

    private void close() {
        this.minecraft.forceSetScreen(null);
    }

    private void navLeft() {
        if (this.biomeNum - 1 >= 0) {
            Object tempName = this.biomes2[this.biomeNum - 1];
            Button chk = (Button)this.renderables.get(4);
            Object tmpDisplayName = I18n.get((String)(tempName = "biome." + this.biomePath[this.biomeNum - 1] + "." + (String)tempName), (Object[])new Object[0]);
            if (((String)tmpDisplayName).length() > 20) {
                tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
            }
            chk.setMessage((Component)Component.literal((String)tmpDisplayName));
            --this.biomeNum;
            return;
        }
        Object tempName = this.biomes2[this.arraySize - 1];
        Button chk = (Button)this.renderables.get(4);
        Object tmpDisplayName = I18n.get((String)(tempName = "biome." + this.biomePath[this.arraySize - 1] + "." + (String)tempName), (Object[])new Object[0]);
        if (((String)tmpDisplayName).length() > 20) {
            tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
        }
        chk.setMessage((Component)Component.literal((String)tmpDisplayName));
        this.biomeNum = this.arraySize - 1;
    }

    private void navRight() {
        if (this.biomeNum + 1 < this.arraySize) {
            Object tempName = this.biomes2[this.biomeNum + 1];
            Button chk = (Button)this.renderables.get(4);
            Object tmpDisplayName = I18n.get((String)(tempName = "biome." + this.biomePath[this.biomeNum + 1] + "." + (String)tempName), (Object[])new Object[0]);
            if (((String)tmpDisplayName).length() > 20) {
                tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
            }
            chk.setMessage((Component)Component.literal((String)tmpDisplayName));
            ++this.biomeNum;
            return;
        }
        Object tempName = this.biomes2[0];
        Button chk = (Button)this.renderables.get(4);
        Object tmpDisplayName = I18n.get((String)(tempName = "biome." + this.biomePath[0] + "." + (String)tempName), (Object[])new Object[0]);
        if (((String)tmpDisplayName).length() > 20) {
            tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
        }
        chk.setMessage((Component)Component.literal((String)tmpDisplayName));
        this.biomeNum = 0;
    }

    private void searchForBiome() {
        ItemStack getPet2 = this.currentItem;
        this.s = this.biomes2[this.biomeNum] + "|" + this.biomePos[this.biomeNum].getX() + "|" + this.biomePos[this.biomeNum].getZ();
        getPet2.set(ModDataComponents.BIOME_TO_FIND, (Object)this.s);
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        PacketDistributor.sendToServer((CustomPacketPayload)new PacketBiomeFinder(this.s), (CustomPacketPayload[])new CustomPacketPayload[0]);
        int biomeX = 0;
        int biomeZ = 0;
        String biomeString = this.s;
        int sepLoc = this.s.indexOf("|");
        String biome = sepLoc >= 0 ? biomeString.substring(0, sepLoc).trim() : "";
        String biomeCoords = biomeString.substring(sepLoc + 1, biomeString.length());
        sepLoc = biomeCoords.indexOf("|");
        if (sepLoc >= 0) {
            biomeX = Integer.parseInt(biomeCoords.substring(0, sepLoc).trim());
            biomeZ = Integer.parseInt(biomeCoords.substring(sepLoc + 1, biomeCoords.length()).trim());
            double diffX = Math.abs((double)biomeX - clientPlayer.getX());
            double diffZ = Math.abs((double)biomeZ - clientPlayer.getZ());
            int dist = (int)Math.sqrt(diffX * diffX + diffZ * diffZ);
            Holder holder = clientPlayer.level().getBiome(new BlockPos(biomeX, 0, biomeZ));
            String biomeCheck = "biome." + ((ResourceKey)holder.unwrapKey().get()).location().getNamespace() + "." + biome;
            BlockPos heightCheck = new BlockPos(biomeX, 0, biomeZ);
            BlockPos coordinates = clientPlayer.clientLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, heightCheck);
            String biomeName = I18n.get((String)biomeCheck, (Object[])new Object[0]);
            String dimName = clientPlayer.level().dimension().location().getPath();
            getPet2.set(ModDataComponents.BIOME_NAME, (Object)biomeName);
            getPet2.set(ModDataComponents.DIM_NAME, (Object)dimName);
            PacketDistributor.sendToServer((CustomPacketPayload)new PacketBiomeName(biomeName), (CustomPacketPayload[])new CustomPacketPayload[0]);
            PacketDistributor.sendToServer((CustomPacketPayload)new PacketDimensionName(dimName), (CustomPacketPayload[])new CustomPacketPayload[0]);
            clientPlayer.sendSystemMessage((Component)Component.translatable((String)(I18n.get((String)"info.biome.hasfound", (Object[])new Object[0]) + "\u00a7l" + I18n.get((String)biomeCheck, (Object[])new Object[0]) + "\u00a7r" + I18n.get((String)"info.biome.about", (Object[])new Object[0]) + dist + I18n.get((String)"info.biome.meters", (Object[])new Object[0]))));
            boolean isCave = false;
            if (biome.contains("cave")) {
                isCave = true;
            }
            BiomeFinderScreen.LookAt(biomeX, coordinates.getY(), biomeZ, (Player)clientPlayer, isCave);
        }
        this.minecraft.forceSetScreen(null);
    }

    public static void LookAt(double px, double py, double pz, Player me, boolean isCave) {
        if (isCave) {
            me.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(px, 0.0, pz));
        } else {
            me.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(px, py, pz));
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int relY = (this.height - 151) / 2;
        guiGraphics.drawString(this.font, title, this.width / 2 - 80, relY + 20, 0xFFFFFF);
    }
}

