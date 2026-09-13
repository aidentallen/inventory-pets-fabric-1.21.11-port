/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.stream.JsonWriter
 *  net.minecraft.client.Minecraft
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.server.ServerLifecycleHooks
 */
package com.inventorypets.io;

import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@OnlyIn(value=Dist.CLIENT)
public class IlluminatiBlacklistWriter {
    public static void main() {
        try {
            File file = new File(IlluminatiBlacklistWriter.getMcDir(), "config/inventory_pets_illuminati_blacklist.json");
            if (!file.exists()) {
                JsonWriter writer = new JsonWriter((Writer)new FileWriter(file));
                writer.setIndent(" ");
                writer.beginObject();
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:command_block").value(0L);
                writer.name("bibliocraft:").value(0L);
                writer.name("inventorypets:april_fool_pet").value(0L);
                writer.name("inventorypets:item_gift").value(0L);
                writer.name("inventorypets:patreon_shirt").value(0L);
                writer.name("minecraft:air").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.name("minecraft:dirt").value(0L);
                writer.endObject();
                writer.close();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static File getMcDir() {
        if (ServerLifecycleHooks.getCurrentServer() != null && ServerLifecycleHooks.getCurrentServer().isDedicatedServer()) {
            return new File(".");
        }
        return Minecraft.getInstance().gameDirectory;
    }
}

