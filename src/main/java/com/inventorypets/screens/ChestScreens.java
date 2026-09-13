/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package com.inventorypets.screens;

import com.inventorypets.inventory.Chest;
import com.inventorypets.inventory.IPContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ChestScreens
extends AbstractContainerScreen<IPContainer> {
    private final Identifier GUI;

    public ChestScreens(IPContainer container, Inventory playerInventory, Component name) {
        super((AbstractContainerMenu)container, playerInventory, name);
        Chest tier = container.getChestType();
        this.GUI = tier.texture;
        this.imageWidth = tier.xSize;
        this.imageHeight = tier.ySize;
    }

    protected void init() {
        super.init();
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int i = (this.width - this.imageWidth + 88) / 2;
        int j = (this.height - this.imageHeight + 80) / 2;
        guiGraphics.blit(this.GUI, i, j, 0.0f, 0.0f, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.drawString(this.font, this.title.getString(), 51, 46, 0x404040, false);
    }

    public void render(GuiGraphics gg, int pMouseX, int pMouseY, float pPartialTicks) {
        super.render(gg, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(gg, pMouseX, pMouseY);
    }
}

