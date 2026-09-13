/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import com.inventorypets.init.ModDataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketBiomeName(String biomeName) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketBiomeName> TYPE = new CustomPacketPayload.Type(InventoryPets.prefix("biomename"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketBiomeName> STREAM_CODEC = CustomPacketPayload.codec(PacketBiomeName::write, PacketBiomeName::new);

    public PacketBiomeName(FriendlyByteBuf buf) {
        this(buf.readUtf(200));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.biomeName);
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static boolean handle(PacketBiomeName message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ItemStack pet = ctx.player().getInventory().getSelected();
            if (pet != null || pet.getItem() == Blocks.AIR.asItem()) {
                pet.set(ModDataComponents.BIOME_NAME, (Object)message.biomeName);
            }
        });
        return true;
    }
}

