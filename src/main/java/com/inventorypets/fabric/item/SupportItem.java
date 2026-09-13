package com.inventorypets.fabric.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public final class SupportItem extends Item {
    private final String kind;

    public SupportItem(String kind, Properties properties) {
        super(properties);
        this.kind = kind;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        ItemStack stack = player.getItemInHand(hand);
        switch (kind) {
            case "holiday_gift" -> openHolidayGift(player);
            case "siamese_gift" -> openSiameseGift(player);
            case "banana" -> player.getFoodData().eat(4, 0.4f);
            case "holiday_cookie" -> {
                player.getFoodData().eat(4, 0.4f);
                player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 6000, 1));
            }
            case "candy_cane" -> {
                player.getFoodData().eat(3, 0.3f);
                player.addEffect(new MobEffectInstance(MobEffects.SPEED, 6000, 1));
                player.addEffect(new MobEffectInstance(MobEffects.HASTE, 6000, 1));
            }
            case "egg_nog" -> {
                player.getFoodData().eat(6, 0.8f);
                player.setHealth(player.getMaxHealth());
            }
            case "rock_candy" -> {
                player.getFoodData().eat(2, 0.2f);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1));
            }
            default -> throw new IllegalStateException("No support-item handler registered for " + kind);
        }
        if (!player.isCreative()) stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    private static void openHolidayGift(Player player) {
        ItemStack gift = switch (player.getRandom().nextInt(6)) {
            case 0 -> new ItemStack(Items.DIAMOND);
            case 1 -> new ItemStack(Items.EMERALD, 2);
            case 2 -> new ItemStack(Items.GOLD_INGOT, 3);
            case 3 -> new ItemStack(Items.COOKIE, 8);
            case 4 -> new ItemStack(Items.SNOWBALL, 16);
            default -> new ItemStack(Items.GOLDEN_APPLE);
        };
        if (!player.addItem(gift)) player.drop(gift, false);
    }

    private static void openSiameseGift(Player player) {
        ItemStack gift = switch (player.getRandom().nextInt(5)) {
            case 0 -> new ItemStack(Items.CAKE);
            case 1 -> new ItemStack(Items.GOLDEN_APPLE);
            case 2 -> new ItemStack(Items.EMERALD, 4);
            case 3 -> new ItemStack(Items.BREAD, 8);
            default -> new ItemStack(Items.DIAMOND);
        };
        if (!player.addItem(gift)) player.drop(gift, false);
    }
}
