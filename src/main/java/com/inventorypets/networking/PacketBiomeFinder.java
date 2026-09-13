/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package com.inventorypets.networking;

import com.inventorypets.InventoryPets;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModSoundEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketBiomeFinder(String searchBiome) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketBiomeFinder> TYPE = new CustomPacketPayload.Type(InventoryPets.prefix("searchbiome"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketBiomeFinder> STREAM_CODEC = CustomPacketPayload.codec(PacketBiomeFinder::write, PacketBiomeFinder::new);

    public PacketBiomeFinder(FriendlyByteBuf buf) {
        this(buf.readUtf(200));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.searchBiome);
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static boolean handle(PacketBiomeFinder message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ItemStack pet = ctx.player().getInventory().getSelected();
            if (pet != null || pet.getItem() == Blocks.AIR.asItem()) {
                pet.set(ModDataComponents.BIOME_TO_FIND, (Object)message.searchBiome);
                pet.set(ModDataComponents.BIOME_CHECK, (Object)true);
            }
        });
        ServerPlayer player = (ServerPlayer)ctx.player();
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.biome.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
        return true;
    }
}

