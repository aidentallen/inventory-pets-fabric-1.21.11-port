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
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
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
import com.inventorypets.networking.PacketDimensionName;
import com.inventorypets.networking.PacketStructureFinder;
import com.inventorypets.networking.PacketStructureName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
public class StructureFinderScreen
extends Screen {
    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;
    private static String title = I18n.get((String)"gui.structuretofind", (Object[])new Object[0]);
    private static final Identifier GUI = Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"textures/gui/container/pet_namer.png");
    private String searchStructure;
    private ItemStack currentItem;
    LocalPlayer clientPlayer;
    private String s;
    private int structureNum;
    private int arraySize;
    private String[] structures2;
    private String[] structurePath;
    private BlockPos[] structurePos;
    protected MutableComponent cancelText;
    protected MutableComponent acceptText;
    protected Component leftText;
    protected Component rightText;
    boolean closeFlag;

    public StructureFinderScreen(Inventory inventoryplayer) {
        super((Component)Component.translatable((String)title));
        this.clientPlayer = Minecraft.getInstance().player;
        this.structures2 = new String[2000];
        this.structurePath = new String[2000];
        this.structurePos = new BlockPos[2000];
        if (inventoryplayer.getSelected().has(ModDataComponents.STRUCTURE_TO_FIND)) {
            this.searchStructure = (String)inventoryplayer.getSelected().get(ModDataComponents.STRUCTURE_TO_FIND);
            this.searchStructure = this.cleanString(this.searchStructure);
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
        this.structureNum = 0;
        LocalPlayer player = Minecraft.getInstance().player;
        String structureString = (String)player.getData(ModDataAttachments.STRUCTURE_TO_SEND);
        char delimiter = '^';
        for (int i = 0; i < structureString.length(); ++i) {
            if (structureString.charAt(i) != delimiter) continue;
            ++this.arraySize;
        }
        String[] structures = new String[this.arraySize];
        int pathEnd = 0;
        int structureEnd = 0;
        int tempX = 0;
        int tempY = 0;
        int tempZ = 0;
        int tempEnd = 0;
        this.closeFlag = false;
        for (int i = 0; i < this.arraySize; ++i) {
            pathEnd = structureString.indexOf(":");
            this.structurePath[i] = structureString.substring(0, pathEnd);
            structureString = structureString.substring(pathEnd + 1, structureString.length());
            structureEnd = structureString.indexOf("|");
            structures[i] = structureString.substring(0, structureEnd).trim();
            this.structures2[i] = structures[i];
            this.structures2[i] = this.cleanString(this.structures2[i]);
            if (this.structures2[i].contains("_")) {
                this.structures2[i] = this.cleanString(this.structures2[i]);
            }
            structureString = structureString.substring(structureEnd + 1, structureString.length());
            tempEnd = structureString.indexOf("|");
            tempX = Integer.parseInt(structureString.substring(0, tempEnd));
            structureString = structureString.substring(tempEnd + 1, structureString.length());
            tempEnd = structureString.indexOf("|");
            tempY = Integer.parseInt(structureString.substring(0, tempEnd));
            structureString = structureString.substring(tempEnd + 1, structureString.length());
            tempEnd = structureString.indexOf("^");
            tempZ = Integer.parseInt(structureString.substring(0, tempEnd));
            structureString = structureString.substring(tempEnd + 1, structureString.length());
            this.structurePos[i] = new BlockPos(tempX, tempY, tempZ);
        }
        if (structures.length > 0) {
            this.searchStructure = structures[this.structureNum];
            this.searchStructure = this.structurePath[this.structureNum] + "." + this.searchStructure;
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.cancelText, builder -> this.close()).bounds(relX + 10, relY + 72, 72, 20).build());
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.acceptText, builder -> this.searchForStructure()).bounds(relX + 94, relY + 72, 72, 20).build());
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.leftText, builder -> this.navLeft()).bounds(relX + 10, relY + 44, 14, 20).build());
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.rightText, builder -> this.navRight()).bounds(relX + 152, relY + 44, 14, 20).build());
            Object tmpDisplayName = this.cleanString(this.searchStructure);
            if (((String)tmpDisplayName).length() > 19) {
                tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
            }
            this.addRenderableWidget((GuiEventListener)Button.builder((Component)Component.literal((String)tmpDisplayName), builder -> this.searchForStructure()).bounds(relX + 30, relY + 44, 116, 20).build());
        } else {
            InventoryPets.LOGGER.log(Level.DEBUG, "Error: no elements in structure array. Closing.");
            this.close();
        }
    }

    public String cleanString(String dirtyString) {
        if (((String)dirtyString).contains("minecraft.")) {
            dirtyString = ((String)dirtyString).replace("minecraft.", "");
        } else if (((String)dirtyString).contains("inventorypets.")) {
            dirtyString = ((String)dirtyString).replace("inventorypets.", "");
        }
        if (((String)dirtyString).contains("_")) {
            String tempStart = ((String)dirtyString).substring(0, 1).toUpperCase();
            int tempLoc = ((String)(dirtyString = tempStart + ((String)dirtyString).substring(1, ((String)dirtyString).length()))).indexOf("_");
            if (tempLoc > 0) {
                tempStart = ((String)dirtyString).substring(0, tempLoc);
                String tempCap = ((String)dirtyString).substring(tempLoc + 1, tempLoc + 2).toUpperCase();
                String tempBack = ((String)dirtyString).substring(tempLoc + 2, ((String)dirtyString).length());
                dirtyString = tempStart + " " + tempCap + tempBack;
            }
        } else if (((String)dirtyString).length() > 0) {
            String tempStart = ((String)dirtyString).substring(0, 1).toUpperCase();
            dirtyString = tempStart + ((String)dirtyString).substring(1, ((String)dirtyString).length());
        }
        return dirtyString;
    }

    public boolean isPauseScreen() {
        return false;
    }

    public Component getNarrationMessage() {
        return Component.translatable((String)I18n.get((String)"gui.structuretofind", (Object[])new Object[0]));
    }

    private void close() {
        this.minecraft.forceSetScreen(null);
    }

    private void navLeft() {
        if (this.structureNum - 1 >= 0) {
            String tempName = this.structures2[this.structureNum - 1];
            Button chk = (Button)this.renderables.get(4);
            Object tmpDisplayName = I18n.get((String)tempName, (Object[])new Object[0]);
            if (((String)tmpDisplayName).length() > 20) {
                tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
            }
            chk.setMessage((Component)Component.literal((String)tmpDisplayName));
            --this.structureNum;
            return;
        }
        String tempName = this.structures2[this.arraySize - 1];
        Button chk = (Button)this.renderables.get(4);
        Object tmpDisplayName = I18n.get((String)tempName, (Object[])new Object[0]);
        if (((String)tmpDisplayName).length() > 20) {
            tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
        }
        chk.setMessage((Component)Component.literal((String)tmpDisplayName));
        this.structureNum = this.arraySize - 1;
    }

    private void navRight() {
        if (this.structureNum + 1 < this.arraySize) {
            String tempName = this.structures2[this.structureNum + 1];
            Button chk = (Button)this.renderables.get(4);
            Object tmpDisplayName = I18n.get((String)tempName, (Object[])new Object[0]);
            if (((String)tmpDisplayName).length() > 20) {
                tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
            }
            chk.setMessage((Component)Component.literal((String)tmpDisplayName));
            ++this.structureNum;
            return;
        }
        String tempName = this.structures2[0];
        Button chk = (Button)this.renderables.get(4);
        Object tmpDisplayName = I18n.get((String)tempName, (Object[])new Object[0]);
        if (((String)tmpDisplayName).length() > 20) {
            tmpDisplayName = ((String)tmpDisplayName).substring(0, 20) + "..";
        }
        chk.setMessage((Component)Component.literal((String)tmpDisplayName));
        this.structureNum = 0;
    }

    private void searchForStructure() {
        ItemStack getPet2 = this.currentItem;
        this.s = this.structures2[this.structureNum] + "|" + this.structurePos[this.structureNum].getX() + "|" + this.structurePos[this.structureNum].getZ();
        getPet2.set(ModDataComponents.STRUCTURE_TO_FIND, (Object)this.s);
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        PacketDistributor.sendToServer((CustomPacketPayload)new PacketStructureFinder(this.s), (CustomPacketPayload[])new CustomPacketPayload[0]);
        int structureX = 0;
        int structureY = 0;
        int structureZ = 0;
        String structureString = this.s;
        int sepLoc = this.s.indexOf("|");
        String structure = sepLoc >= 0 ? structureString.substring(0, sepLoc).trim() : "";
        String structureCoords = structureString.substring(sepLoc + 1, structureString.length());
        sepLoc = structureCoords.indexOf("|");
        if (sepLoc >= 0) {
            structureX = Integer.parseInt(structureCoords.substring(0, sepLoc).trim());
            structureZ = Integer.parseInt(structureCoords.substring(sepLoc + 1, structureCoords.length()).trim());
            if (structure.contains("Sky Dungeon")) {
                structureY = 206;
            } else if (structure.contains("Space Dungeon")) {
                structureY = 100;
            } else if (structure.contains("Nether Dungeon")) {
                structureY = 36;
            } else if (structure.contains("Underground Dungeon")) {
                structureY = 18;
            } else if (structure.contains("Tree Top")) {
                structureY = clientPlayer.level().getHeight(Heightmap.Types.WORLD_SURFACE_WG, structureX, structureZ) + 8;
            } else if (structure.contains("Sea Cave")) {
                structureY = clientPlayer.level().getHeight(Heightmap.Types.OCEAN_FLOOR_WG, structureX, structureZ) + 1;
            }
            double diffX = Math.abs((double)structureX - clientPlayer.getX());
            double diffY = Math.abs((double)structureY - clientPlayer.getY());
            double diffZ = Math.abs((double)structureZ - clientPlayer.getZ());
            int dist = (int)Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
            String structureName = structure;
            String dimName = clientPlayer.level().dimension().location().getPath();
            getPet2.set(ModDataComponents.STRUCTURE_NAME, (Object)structureName);
            getPet2.set(ModDataComponents.DIM_NAME, (Object)dimName);
            PacketDistributor.sendToServer((CustomPacketPayload)new PacketStructureName(structureName), (CustomPacketPayload[])new CustomPacketPayload[0]);
            PacketDistributor.sendToServer((CustomPacketPayload)new PacketDimensionName(dimName), (CustomPacketPayload[])new CustomPacketPayload[0]);
            clientPlayer.sendSystemMessage((Component)Component.translatable((String)(I18n.get((String)"info.dingot.hasfound", (Object[])new Object[0]) + "\u00a7l" + structureName + "\u00a7r" + I18n.get((String)"info.dingot.about", (Object[])new Object[0]) + dist + I18n.get((String)"info.dingot.meters", (Object[])new Object[0]))));
            boolean isCave = false;
            if (structure.contains("cave")) {
                isCave = true;
            }
            StructureFinderScreen.LookAt(structureX, structureY, structureZ, (Player)clientPlayer, isCave);
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

