/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 */
package com.inventorypets.handler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import net.minecraft.ChatFormatting;

public class UpdateHandler {
    private static String currentVersion = "2.2.9";
    private static String newestVersion;
    public static String updateStatus;
    public static boolean show;

    public static void init() {
        UpdateHandler.getNewestVersion();
        if (newestVersion != null) {
            if (!newestVersion.equalsIgnoreCase(currentVersion)) {
                show = true;
                updateStatus = String.valueOf(ChatFormatting.WHITE) + "Version " + newestVersion + " of " + String.valueOf(ChatFormatting.GREEN) + "Inventory Pets" + String.valueOf(ChatFormatting.WHITE) + " is now available from CurseForge. ";
            }
        } else {
            show = false;
            updateStatus = "Failed to connect to see if an update to " + String.valueOf(ChatFormatting.GREEN) + "Inventory Pets" + String.valueOf(ChatFormatting.WHITE) + " is available";
        }
    }

    private static void getNewestVersion() {
        try {
            URL url = new URL("https://www.creeptech.net/inventorypets/1.21/versionchecker.txt");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            Scanner s = new Scanner(connection.getInputStream());
            if (s.hasNext()) {
                newestVersion = s.next();
            }
            s.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static {
        updateStatus = "NULL";
        show = false;
    }
}

