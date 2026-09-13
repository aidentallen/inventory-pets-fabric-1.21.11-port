package com.inventorypets.fabric.network;

import com.inventorypets.fabric.InventoryPetsFabric;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PacketBiomeFinder(String searchBiome) implements CustomPacketPayload {
    public static final Type<PacketBiomeFinder> TYPE = new Type<>(Identifier.fromNamespaceAndPath(InventoryPetsFabric.MOD_ID, "searchbiome"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketBiomeFinder> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(200), PacketBiomeFinder::searchBiome, PacketBiomeFinder::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
