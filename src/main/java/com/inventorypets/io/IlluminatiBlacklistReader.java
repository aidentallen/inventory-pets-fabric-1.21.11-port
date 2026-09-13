/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.stream.JsonReader
 *  net.minecraft.client.Minecraft
 *  net.neoforged.neoforge.server.ServerLifecycleHooks
 */
package com.inventorypets.io;

import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class IlluminatiBlacklistReader {
    public static String main(String k) {
        String itemname = null;
        boolean foundValue = false;
        try {
            File f = new File(IlluminatiBlacklistReader.getMcDir(), "config/inventory_pets_illuminati_blacklist.json");
            JsonReader reader = new JsonReader((Reader)new FileReader(f));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(k)) {
                    itemname = reader.nextString();
                    foundValue = true;
                    continue;
                }
                reader.skipValue();
            }
            reader.endObject();
            reader.close();
        }
        catch (FileNotFoundException fileNotFoundException) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (foundValue) {
            return itemname;
        }
        return "1";
    }

    public static File getMcDir() {
        if (ServerLifecycleHooks.getCurrentServer() != null && ServerLifecycleHooks.getCurrentServer().isDedicatedServer()) {
            return new File(".");
        }
        return Minecraft.getInstance().gameDirectory;
    }
}

