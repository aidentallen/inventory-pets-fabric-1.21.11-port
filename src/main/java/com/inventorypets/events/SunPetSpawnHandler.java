/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BedItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.EntityJoinLevelEvent
 */
package com.inventorypets.events;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.helper.ItemHelper;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid="inventorypets")
public class SunPetSpawnHandler {
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        block5: {
            Player entityplayer;
            ItemEntity item;
            Level worldIn;
            block7: {
                block6: {
                    worldIn = event.getLevel();
                    if (worldIn.isClientSide || worldIn.getServer().getPlayerList().getPlayerCount() <= 0 || !(event.getEntity() instanceof Monster) || !event.getEntity().isAlive()) break block6;
                    List entities = worldIn.getServer().getPlayerList().getPlayers();
                    int esize = entities.size();
                    if (esize <= 0) break block5;
                    block0: for (int k = 0; k <= esize - 1; ++k) {
                        Player chkentity = (Player)entities.get(k);
                        Entity entity = event.getEntity();
                        if (chkentity == null || !chkentity.isAlive()) continue;
                        Player playerIn = chkentity;
                        double xt = entity.getX();
                        double zt = entity.getZ();
                        int x1 = Mth.floor((double)playerIn.getX());
                        int z1 = Mth.floor((double)playerIn.getZ());
                        double x2 = xt - (double)x1;
                        double z2 = zt - (double)z1;
                        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                            ItemStack itemchk = playerIn.getInventory().getItem(i);
                            if (itemchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableSun.get()).booleanValue() || itemchk.getItem() != InventoryPets.PET_SUN.get() || itemchk.getDamageValue() != 0 && !playerIn.isCreative() || !playerIn.isAlive() || Mth.abs((int)((int)x2)) <= 2 || Mth.abs((int)((int)x2)) >= 48 || Mth.abs((int)((int)z2)) <= 2 || Mth.abs((int)((int)z2)) >= 48) continue;
                            event.setCanceled(true);
                            continue block0;
                        }
                    }
                    break block5;
                }
                if (worldIn.isClientSide || worldIn.getServer().getPlayerList().getPlayerCount() <= 0 || !(event.getEntity() instanceof ItemEntity)) break block5;
                item = (ItemEntity)event.getEntity();
                if (item.getItem().getItem() != Items.LEAD) break block7;
                Player entityplayer2 = worldIn.getNearestPlayer(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 15.0, false);
                if (entityplayer2 == null) break block5;
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer2.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_LEAD.get() || ((Boolean)InventoryPetsConfig.disableLead.get()).booleanValue() || itemchk.getDamageValue() >= 3 && !entityplayer2.isCreative() || !entityplayer2.isAlive()) continue;
                    event.setCanceled(true);
                    break block5;
                }
                break block5;
            }
            if (item.getItem().getItem() instanceof BedItem && (entityplayer = worldIn.getNearestPlayer(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 5.0, false)) != null) {
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = entityplayer.getInventory().getItem(i);
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.ITEM_PETRIFIER.get() || entityplayer.isCreative() || !entityplayer.isAlive()) continue;
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }
}

