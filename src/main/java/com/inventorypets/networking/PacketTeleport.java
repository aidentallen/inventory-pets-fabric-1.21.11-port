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
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package com.inventorypets.networking;

import com.inventorypets.InventoryPets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketTeleport(double x, double y, double z) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketTeleport> TYPE = new CustomPacketPayload.Type(InventoryPets.prefix("teleportplayer"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTeleport> STREAM_CODEC = CustomPacketPayload.codec(PacketTeleport::write, PacketTeleport::new);

    public PacketTeleport(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static boolean handle(PacketTeleport message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null && player.clientLevel.isClientSide) {
                player.moveTo(message.x, message.y, message.z);
                player.resetPos();
            }
        });
        return true;
    }
}

