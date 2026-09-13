/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.living.LivingDeathEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.AdvancementHelper;
import com.inventorypets.helper.ItemHelper;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid="inventorypets")
public class CobbleDirtAchieveHandler {
    @SubscribeEvent
    public static void notifyAttack(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player entityplayer = (Player)event.getEntity();
            Level world = entityplayer.level();
            if (!entityplayer.level().isClientSide) {
                boolean hasCobble = false;
                boolean hasDirt = false;
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk != ItemStack.EMPTY && !((Boolean)InventoryPetsConfig.disableCobblestone.get()).booleanValue() && itemchk.getItem() == InventoryPets.PET_COBBLESTONE.get()) {
                        hasCobble = true;
                        continue;
                    }
                    if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableDirt.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_DIRT.get()) continue;
                    hasDirt = true;
                }
                if (hasCobble || hasDirt) {
                    AABB range = new AABB(entityplayer.getX() - 48.0, entityplayer.getY() - 48.0, entityplayer.getZ() - 48.0, entityplayer.getX() + 48.0, entityplayer.getY() + 48.0, entityplayer.getZ() + 48.0);
                    List entities = world.getEntities((Entity)entityplayer, range);
                    int esize = entities.size();
                    int numPlayers = 0;
                    for (int k = 0; k <= esize - 1; ++k) {
                        Entity entity = (Entity)entities.get(k);
                        if (entity == null || !(entity instanceof ServerPlayer)) continue;
                        ++numPlayers;
                        double xt = entity.getX();
                        double yt = entity.getY();
                        double zt = entity.getZ();
                        int x1 = Mth.floor((double)entityplayer.getX());
                        int y1 = Mth.floor((double)entityplayer.getY());
                        int z1 = Mth.floor((double)entityplayer.getZ());
                        double x2 = xt - (double)x1;
                        double y2 = yt - (double)y1;
                        double z2 = zt - (double)z1;
                        ServerPlayer killingEntity = (ServerPlayer)entity;
                        if (Mth.abs((int)((int)x2)) >= 48 || Mth.abs((int)((int)z2)) >= 48 || Mth.abs((int)((int)y2)) >= 48) continue;
                        if (hasCobble && !AdvancementHelper.hasAdvancement(killingEntity, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_cobble"))) {
                            AdvancementHelper.unlockAdvancement(killingEntity, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_cobble"));
                        }
                        if (!hasDirt || AdvancementHelper.hasAdvancement(killingEntity, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_dirt"))) continue;
                        AdvancementHelper.unlockAdvancement(killingEntity, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_dirt"));
                    }
                    if (numPlayers == 0 && entityplayer instanceof ServerPlayer) {
                        ServerPlayer serverplayer = (ServerPlayer)entityplayer;
                        if (hasCobble && !AdvancementHelper.hasAdvancement(serverplayer, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_cobble"))) {
                            AdvancementHelper.unlockAdvancement(serverplayer, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_cobble"));
                        }
                        if (hasDirt && !AdvancementHelper.hasAdvancement(serverplayer, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_dirt"))) {
                            AdvancementHelper.unlockAdvancement(serverplayer, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"kill_dirt"));
                        }
                    }
                }
            }
        }
    }
}

