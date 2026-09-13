/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package com.inventorypets.networking;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModDataAttachments;
import com.inventorypets.init.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketStructureSender(String structureToSend) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketStructureSender> TYPE = new CustomPacketPayload.Type(InventoryPets.prefix("structuretosend"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureSender> STREAM_CODEC = CustomPacketPayload.codec(PacketStructureSender::write, PacketStructureSender::new);

    public PacketStructureSender(FriendlyByteBuf buf) {
        this(buf.readUtf(50000));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.structureToSend);
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static boolean handle(PacketStructureSender message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ItemStack pet = Minecraft.getInstance().player.getInventory().getSelected();
            LocalPlayer player = Minecraft.getInstance().player;
            if (pet != null || pet.getItem() == Blocks.AIR.asItem()) {
                pet.set(ModDataComponents.STRUCTURE_TO_SEND, (Object)message.structureToSend);
                pet.set(ModDataComponents.STRUCTURE_OPEN_ME, (Object)true);
                if (player.clientLevel.isClientSide) {
                    player.setData(ModDataAttachments.STRUCTURE_FLAG, (Object)2);
                    player.setData(ModDataAttachments.STRUCTURE_TO_SEND, (Object)message.structureToSend());
                }
            }
        });
        return true;
    }
}

