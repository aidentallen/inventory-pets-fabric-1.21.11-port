/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package com.inventorypets.screens;

import com.inventorypets.networking.PacketPetNamer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

@OnlyIn(value=Dist.CLIENT)
public class PetNamerScreen
extends Screen {
    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;
    private static final MutableComponent TITLE = Component.translatable((String)"gui.nameyourpet");
    private EditBox petName;
    private String currentName;
    private ItemStack currentItem;
    private String s;
    protected MutableComponent cancelText;
    protected MutableComponent acceptText;
    protected MutableComponent guiText;

    public PetNamerScreen(Inventory inventoryplayer) {
        super((Component)TITLE);
        this.currentName = inventoryplayer.getSelected().getDisplayName().getString();
        this.currentItem = inventoryplayer.getSelected();
        this.cancelText = Component.translatable((String)"gui.cancel");
        this.acceptText = Component.translatable((String)"gui.accept");
        this.guiText = Component.translatable((String)"gui.nameyourpet");
    }

    protected void init() {
        int relX = (this.width - 179) / 2;
        int relY = (this.height - 151) / 2;
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.cancelText, builder -> this.close()).bounds(relX + 10, relY + 72, 72, 20).build());
        this.addRenderableWidget((GuiEventListener)Button.builder((Component)this.acceptText, builder -> this.rename()).bounds(relX + 94, relY + 72, 72, 20).build());
        this.petName = new EditBox(this.font, relX + 10, relY + 44, 120, 16, (Component)this.guiText);
        this.petName.setMaxLength(40);
        this.petName.setCanLoseFocus(false);
        this.petName.setFocused(true);
        this.petName.setTextColor(-1);
        this.petName.setTextColorUneditable(-1);
        this.setFocused((GuiEventListener)this.petName);
        this.currentName = this.currentName.replace("[", "");
        this.currentName = this.currentName.replace("]", "");
        this.petName.insertText(this.currentName);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public MutableComponent getNarrationMessage() {
        return Component.translatable((String)"gui.nameyourpet");
    }

    private void close() {
        this.minecraft.forceSetScreen(null);
    }

    private void rename() {
        this.s = this.petName.getValue();
        ItemStack getPet = this.currentItem;
        if (getPet != null && !getPet.has(DataComponents.CUSTOM_NAME) && this.s.equals(getPet.getDisplayName().getString())) {
            this.s = "";
        }
        PacketDistributor.sendToServer((CustomPacketPayload)new PacketPetNamer(this.s), (CustomPacketPayload[])new CustomPacketPayload[0]);
        this.minecraft.forceSetScreen(null);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int relX = (this.width - 179) / 2;
        int relY = (this.height - 151) / 2;
        guiGraphics.drawString(this.font, (Component)TITLE, this.width / 2 - 80, relY + 20, 0xFFFFFF);
        this.petName.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}

