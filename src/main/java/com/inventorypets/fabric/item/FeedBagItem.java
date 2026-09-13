package com.inventorypets.fabric.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class FeedBagItem extends Item {
    public FeedBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            StackBackedContainer container = new StackBackedContainer(stack, 27);
            player.openMenu(new SimpleMenuProvider(
                    (syncId, inventory, ignored) -> ChestMenu.threeRows(syncId, inventory, container),
                    Component.translatable("item.inventorypets.feed_bag")));
        }
        return InteractionResult.SUCCESS;
    }
}
