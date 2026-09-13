package com.inventorypets.fabric;

import com.inventorypets.fabric.config.InventoryPetsJsonConfig;
import com.inventorypets.fabric.event.PetEvents;
import com.inventorypets.fabric.init.ModAttachments;
import com.inventorypets.fabric.init.ModDataComponents;
import com.inventorypets.fabric.init.ModSounds;
import com.inventorypets.fabric.item.ModItems;
import com.inventorypets.fabric.network.ModNetworking;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InventoryPetsFabric implements ModInitializer {
    public static final String MOD_ID = "inventorypets";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        InventoryPetsJsonConfig.load();
        ModAttachments.initialize();
        ModDataComponents.initialize();
        ModSounds.initialize();
        ModItems.initialize();
        ModNetworking.initialize();
        PetEvents.initialize();
        LOGGER.info("Initialized private Inventory Pets Fabric port with {} registered items", ModItems.ITEMS.size());
    }
}
