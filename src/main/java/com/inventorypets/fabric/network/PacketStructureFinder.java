package com.inventorypets.fabric.network;

import com.inventorypets.fabric.InventoryPetsFabric;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PacketStructureFinder(String searchStructure) implements CustomPacketPayload {
    public static final Type<PacketStructureFinder> TYPE = new Type<>(Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, "searchstructure"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureFinder> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(400), PacketStructureFinder::searchStructure, PacketStructureFinder::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
