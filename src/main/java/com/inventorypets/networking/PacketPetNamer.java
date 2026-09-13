/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Blocks
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package com.inventorypets.networking;

import com.inventorypets.InventoryPets;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketPetNamer(String petNamer) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PacketPetNamer> TYPE = new CustomPacketPayload.Type(InventoryPets.prefix("petnamer"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketPetNamer> STREAM_CODEC = CustomPacketPayload.codec(PacketPetNamer::write, PacketPetNamer::new);

    public PacketPetNamer(FriendlyByteBuf buf) {
        this(buf.readUtf(100));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.petNamer);
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static boolean handle(PacketPetNamer message, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ItemStack pet = ctx.player().getInventory().getSelected();
            if (pet != null || pet.getItem() == Blocks.AIR.asItem()) {
                pet.set(DataComponents.CUSTOM_NAME, (Object)Component.literal((String)message.petNamer));
            }
        });
        return true;
    }
}

