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

public record PacketBiomeSender(String biomeToSend) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketBiomeSender> TYPE = new CustomPacketPayload.Type(InventoryPets.prefix("biometosend"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketBiomeSender> STREAM_CODEC = CustomPacketPayload.codec(PacketBiomeSender::write, PacketBiomeSender::new);

    public PacketBiomeSender(FriendlyByteBuf buf) {
        this(buf.readUtf(15000));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.biomeToSend);
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static boolean handle(PacketBiomeSender message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ItemStack pet = Minecraft.getInstance().player.getInventory().getSelected();
            LocalPlayer player = Minecraft.getInstance().player;
            if (pet != null || pet.getItem() == Blocks.AIR.asItem()) {
                pet.set(ModDataComponents.BIOME_TO_SEND, (Object)message.biomeToSend);
                pet.set(ModDataComponents.BIOME_OPEN_ME, (Object)true);
                if (player.clientLevel.isClientSide) {
                    player.setData(ModDataAttachments.BIOME_FLAG, (Object)2);
                    player.setData(ModDataAttachments.BIOME_TO_SEND, (Object)message.biomeToSend());
                }
            }
        });
        return true;
    }
}

