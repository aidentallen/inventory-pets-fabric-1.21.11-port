/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.item.trading.MerchantOffers
 *  net.minecraft.world.level.Level
 */
package com.inventorypets.entities;

import com.google.common.collect.Sets;
import com.inventorypets.InventoryPets;
import com.inventorypets.entities.CEOTrades;
import com.inventorypets.helper.AdvancementHelper;
import com.inventorypets.init.ModSoundEvents;
import java.util.HashSet;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class SatyaNadellaEntity
extends WanderingTrader {
    public SatyaNadellaEntity(EntityType<? extends SatyaNadellaEntity> entitySatyaNadella, Level worldIn) {
        super(entitySatyaNadella, worldIn);
        this.getNavigation().setCanFloat(true);
        this.setCanPickUpLoot(true);
    }

    protected void updateTrades() {
        CEOTrades.ItemListing[] avillagertrades$itrade = (CEOTrades.ItemListing[])CEOTrades.SATYA_NADELLA.get(1);
        CEOTrades.ItemListing[] avillagertrades$itrade1 = (CEOTrades.ItemListing[])CEOTrades.SATYA_NADELLA.get(2);
        if (avillagertrades$itrade != null && avillagertrades$itrade1 != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addTrades(merchantoffers, avillagertrades$itrade, 5);
            int i = this.random.nextInt(avillagertrades$itrade1.length);
            CEOTrades.ItemListing villagertrades$itrade = avillagertrades$itrade1[i];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer((Entity)this, this.random);
            if (merchantoffer != null) {
                merchantoffers.add((Object)merchantoffer);
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, (double)0.6f).add(Attributes.FOLLOW_RANGE, 48.0).add(Attributes.ATTACK_DAMAGE, 0.0);
    }

    protected void addTrades(MerchantOffers givenMerchantOffers, CEOTrades.ItemListing[] newTrades, int maxNumbers) {
        HashSet set = Sets.newHashSet();
        set.add(0);
        set.add(1);
        for (Integer integer : set) {
            CEOTrades.ItemListing villagertrades$itrade = newTrades[integer];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer((Entity)this, this.random);
            if (merchantoffer == null) continue;
            givenMerchantOffers.add((Object)merchantoffer);
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : ModSoundEvents.snsay.get();
    }

    public float getVoicePitch() {
        return 1.0f;
    }

    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        this.rewardTradeXp(offer);
        if (offer.getResult().getItem() == InventoryPets.PET_CLOUD.get() && this.getTradingPlayer() instanceof ServerPlayer && !AdvancementHelper.hasAdvancement((ServerPlayer)this.getTradingPlayer(), Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"scavenger"))) {
            AdvancementHelper.unlockAdvancement((ServerPlayer)this.getTradingPlayer(), Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"scavenger"));
        }
    }
}

