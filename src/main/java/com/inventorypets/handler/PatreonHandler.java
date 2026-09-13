/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package com.inventorypets.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;

public class PatreonHandler {
    public static List<String> patreonUUIDS;

    public static void initList() {
        patreonUUIDS = new ArrayList<String>();
        patreonUUIDS.add("3507ad5c-d868-453c-90a0-3b8092999d22");
        patreonUUIDS.add("615d0847-0ddd-4bc9-a410-355a79cdd519");
        patreonUUIDS.add("04372b9e-4e31-4a69-9660-4ac1cc2dbdb4");
        patreonUUIDS.add("bd1a8633-8ca7-4b5d-9ef7-5d1dfde310f3");
        patreonUUIDS.add("9f6e90b5-cd29-49fd-8858-87e3fa4ca150");
        patreonUUIDS.add("9c8a434b-2adc-4385-bc58-a8f08db3ebb9");
        patreonUUIDS.add("1fbb6b43-af8f-40a1-9c26-c5128ff513ce");
        patreonUUIDS.add("fdd172ed-dcb5-49ed-93ae-7847f6e88bef");
        patreonUUIDS.add("b0bb3fff-ddbd-40c6-95d7-51dfcaece879");
        patreonUUIDS.add("1f471396-84d9-41a0-ad9b-52a722c12a6a");
        patreonUUIDS.add("6265251a-5dae-4eb4-a95e-dfd922ad8fd5");
        patreonUUIDS.add("407720e3-22a6-4a78-9e64-0f6e82e00609");
        patreonUUIDS.add("f865dbb5-552c-4f62-8d73-2360634f6edd");
        patreonUUIDS.add("2ea31456-53d4-4eee-b034-5126fc45e1c7");
        patreonUUIDS.add("eef69763-af9d-4ce6-bef7-cd9bc839004c");
        patreonUUIDS.add("de8fc838-7468-4c5f-8f48-012808e079e1");
        patreonUUIDS.add("0b667a5c-9c03-4b8b-8cf4-ba3b31e09934");
        patreonUUIDS.add("ad425147-a229-48a0-930b-ec58f9c5dd84");
        patreonUUIDS.add("9ba262f1-3b9d-4fac-ba53-86d73bdd33e4");
        patreonUUIDS.add("12bab952-3321-481c-abf6-7065cc046ded");
        patreonUUIDS.add("fef70d41-c47b-4aa1-872f-e6f9271d2803");
        patreonUUIDS.add("6d84bec0-b5c4-4467-8957-8458c3c18cb0");
    }

    public static boolean isPlayerPatreon(Player player) {
        UUID id = player.getUUID();
        String uuid = id.toString();
        return patreonUUIDS.contains(uuid);
    }
}

