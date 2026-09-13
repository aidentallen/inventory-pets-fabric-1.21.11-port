/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.bus.api.IEventBus
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.NeoForge
 *  net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent
 */
package com.inventorypets.init;

import com.inventorypets.InventoryPets;
import com.inventorypets.entities.AnvilPetEntity;
import com.inventorypets.entities.BedPetEntity;
import com.inventorypets.entities.BillGatesEntity;
import com.inventorypets.entities.MiniQuantumBlazeEntity;
import com.inventorypets.entities.MiniQuantumEndermanEntity;
import com.inventorypets.entities.SatyaNadellaEntity;
import com.inventorypets.entities.SiamesePetEntity;
import com.inventorypets.entities.SteveBallmerEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid="inventorypets")
public class ModSetup {
    public static void setup() {
        IEventBus bus = NeoForge.EVENT_BUS;
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(InventoryPets.ANVIL_PET_ENTITY.get(), AnvilPetEntity.prepareAttributes().build());
        event.put(InventoryPets.BED_PET_ENTITY.get(), BedPetEntity.createAttributes().build());
        event.put(InventoryPets.SIAMESE_ENTITY.get(), SiamesePetEntity.createAttributes().build());
        event.put(InventoryPets.BILL_GATES_ENTITY.get(), BillGatesEntity.createAttributes().build());
        event.put(InventoryPets.SATYA_NADELLA_ENTITY.get(), SatyaNadellaEntity.createAttributes().build());
        event.put(InventoryPets.STEVE_BALLMER_ENTITY.get(), SteveBallmerEntity.createAttributes().build());
        event.put(InventoryPets.MINI_QB_ENTITY.get(), MiniQuantumBlazeEntity.createAttributes().build());
        event.put(InventoryPets.MINI_QE_ENTITY.get(), MiniQuantumEndermanEntity.createAttributes().build());
    }
}

