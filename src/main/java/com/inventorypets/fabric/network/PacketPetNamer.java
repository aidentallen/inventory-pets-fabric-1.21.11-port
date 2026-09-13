package com.inventorypets.fabric.network;

import com.inventorypets.fabric.InventoryPetsFabric;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PacketPetNamer(String petNamer) implements CustomPacketPayload {
    public static final Type<PacketPetNamer> TYPE = new Type<>(Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, "petnamer"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketPetNamer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(100), PacketPetNamer::petNamer, PacketPetNamer::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
