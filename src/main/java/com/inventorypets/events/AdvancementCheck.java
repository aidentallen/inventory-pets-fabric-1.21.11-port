/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.player.AdvancementEvent$AdvancementProgressEvent
 */
package com.inventorypets.events;

import com.inventorypets.helper.AdvancementHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

@EventBusSubscriber
public class AdvancementCheck {
    private static int delay = 0;

    @SubscribeEvent
    public static void onEvent(AdvancementEvent.AdvancementProgressEvent event) {
        ++delay;
        if (event.getEntity() instanceof ServerPlayer) {
            boolean ad11;
            boolean ad10;
            boolean ad9;
            boolean ad8;
            boolean ad7;
            boolean ad6;
            boolean ad5;
            boolean ad4;
            boolean ad3;
            boolean ad2;
            boolean ad1;
            delay = 0;
            ServerPlayer player = (ServerPlayer)event.getEntity();
            int heart = 0;
            int shield = 0;
            int dubstep = 0;
            int moon = 0;
            int cheetah = 0;
            int house = 0;
            int silverfish = 0;
            int wolf = 0;
            int apple = 0;
            int pixie = 0;
            int pacman = 0;
            int torch = 0;
            int aprilfool = 0;
            int christmastree = 0;
            int menorah = 0;
            int mishumaasaba = 0;
            int politicallycorrect = 0;
            int pufferfish = 0;
            int blackhole = 0;
            int slime = 0;
            int cloud = 0;
            int wither = 0;
            int sun = 0;
            int ghast = 0;
            int spider = 0;
            int irongolem = 0;
            int snowgolem = 0;
            int enderman = 0;
            int creeper = 0;
            int magmacube = 0;
            int blaze = 0;
            int chicken = 0;
            int cow = 0;
            int ocelot = 0;
            int pig = 0;
            int sheep = 0;
            int squid = 0;
            int mooshroom = 0;
            int sponge = 0;
            int pcow = 0;
            int mickerson = 0;
            int pingot = 0;
            int qcm = 0;
            int banana = 0;
            int loot = 0;
            int dingot = 0;
            int quiver = 0;
            int flyingsaddle = 0;
            int biome = 0;
            int illuminati = 0;
            int juggernaut = 0;
            int siamese = 0;
            int dirt = 0;
            int cobblestone = 0;
            int chest = 0;
            int doublechest = 0;
            int enderchest = 0;
            int furnace = 0;
            int craftingtable = 0;
            int enchantingtable = 0;
            int jukebox = 0;
            int anvil = 0;
            int brewingstand = 0;
            int netherportal = 0;
            int bed = 0;
            int saddle = 0;
            int lead = 0;
            int endportal = 0;
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"heart"))) {
                heart = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"shield"))) {
                shield = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dubstep"))) {
                dubstep = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dubstep"))) {
                moon = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"aoe"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"heart"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"shield"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dubstep"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"moon"));
                if (ad1 && ad2 && ad3 && ad4) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"aoe"));
                }
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cats"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"ocelot"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"siamese"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cheetah"));
                if (ad1 && ad2 && ad3) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cats"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cheetah"))) {
                cheetah = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"house"))) {
                house = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"silverfish"))) {
                silverfish = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"wolf"))) {
                wolf = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"apple"))) {
                apple = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pixie"))) {
                pixie = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pacman"))) {
                pacman = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"torch"))) {
                torch = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"fan"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cheetah"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"house"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"silverfish"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"wolf"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"apple"));
                ad6 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pacman"));
                ad7 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pixie"));
                ad8 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"torch"));
                if (ad1 && ad2 && ad3 && ad4 && ad5 && ad6 && ad7 && ad8) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"fan"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"aprilfool"))) {
                aprilfool = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"christmastree"))) {
                christmastree = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"menorah"))) {
                menorah = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mishumaasaba"))) {
                mishumaasaba = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"politicallycorrect"))) {
                politicallycorrect = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"holiday"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"aprilfool"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"christmastree"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"menorah"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mishumaasaba"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"politicallycorrect"));
                if (ad1 && ad2 && ad3 && ad4 && ad5) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"holiday"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pufferfish"))) {
                pufferfish = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"blackhole"))) {
                blackhole = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"slime"))) {
                slime = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cloud"))) {
                cloud = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"wither"))) {
                wither = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"sun"))) {
                sun = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"legend"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pufferfish"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"blackhole"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"slime"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cloud"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"wither"));
                ad6 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"sun"));
                if (ad1 && ad2 && ad3 && ad4 && ad5 && ad6) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"legend"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"ghast"))) {
                ghast = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"spider"))) {
                spider = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"irongolem"))) {
                irongolem = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"snowgolem"))) {
                snowgolem = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"enderman"))) {
                enderman = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"creeper"))) {
                creeper = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"magmacube"))) {
                magmacube = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"blaze"))) {
                blaze = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mob"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"ghast"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"spider"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"irongolem"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"snowgolem"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"enderman"));
                ad6 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"creeper"));
                ad7 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"magmacube"));
                ad8 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"blaze"));
                if (ad1 && ad2 && ad3 && ad4 && ad5 && ad6 && ad7 && ad8) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mob"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"chicken"))) {
                chicken = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cow"))) {
                cow = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"ocelot"))) {
                ocelot = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pig"))) {
                pig = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"sheep"))) {
                sheep = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"squid"))) {
                squid = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mooshroom"))) {
                mooshroom = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"peaceful"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"chicken"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cow"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"ocelot"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pig"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"sheep"));
                ad6 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"squid"));
                ad7 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mooshroom"));
                if (ad1 && ad2 && ad3 && ad4 && ad5 && ad6 && ad7) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"peaceful"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"sponge"))) {
                sponge = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"purpliciouscow"))) {
                pcow = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mickerson"))) {
                mickerson = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pingot"))) {
                pingot = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"qcm"))) {
                qcm = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"banana"))) {
                banana = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"loot"))) {
                loot = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dingot"))) {
                dingot = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"quiver"))) {
                quiver = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"flyingsaddle"))) {
                flyingsaddle = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"biome"))) {
                biome = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"specialist"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"sponge"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"purpliciouscow"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"mickerson"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"pingot"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"qcm"));
                ad6 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"banana"));
                ad7 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"loot"));
                ad8 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dingot"));
                ad9 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"quiver"));
                ad10 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"flyingsaddle"));
                ad11 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"biome"));
                if (ad1 && ad2 && ad3 && ad4 && ad5 && ad6 && ad7 && ad8 && ad9 && ad10 && ad11) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"specialist"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"chest"))) {
                chest = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"doublechest"))) {
                doublechest = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"enderchest"))) {
                enderchest = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"furnace"))) {
                furnace = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"craftingtable"))) {
                craftingtable = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"enchantingtable"))) {
                enchantingtable = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"jukebox"))) {
                jukebox = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"anvil"))) {
                anvil = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"brewingstand"))) {
                brewingstand = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"netherportal"))) {
                netherportal = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"bed"))) {
                bed = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"saddle"))) {
                saddle = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"lead"))) {
                lead = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"endportal"))) {
                endportal = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"utilitarian"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"chest"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"doublechest"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"enderchest"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"furnace"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"craftingtable"));
                ad6 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"enchantingtable"));
                ad7 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"jukebox"));
                ad8 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"anvil"));
                ad9 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"brewingstand"));
                ad10 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"netherportal"));
                ad11 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"bed"));
                boolean ad12 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"saddle"));
                boolean ad13 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"lead"));
                boolean ad14 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"endportal"));
                if (ad1 && ad2 && ad3 && ad4 && ad5 && ad6 && ad7 && ad8 && ad9 && ad10 && ad11 && ad12 && ad13 && ad14) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"utilitarian"));
                }
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"illuminati"))) {
                illuminati = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"juggernaut"))) {
                juggernaut = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"siamese"))) {
                siamese = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cobblestone"))) {
                cobblestone = 1;
            }
            if (AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dirt"))) {
                dirt = 1;
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"youtuber"))) {
                ad1 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"illuminati"));
                ad2 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"juggernaut"));
                ad3 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"siamese"));
                ad4 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"cobblestone"));
                ad5 = AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"dirt"));
                if (ad1 && ad2 && ad3 && ad4 && ad5) {
                    AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"youtuber"));
                }
            }
            int totalAOE = shield + dubstep + heart;
            int totalFan = pacman + cheetah + house + silverfish + wolf + apple + torch + pixie;
            int totalHoliday = aprilfool + christmastree + menorah + mishumaasaba + politicallycorrect;
            int totalLegend = blackhole + cloud + pufferfish + slime + wither + moon + sun;
            int totalMob = creeper + enderman + ghast + irongolem + magmacube + snowgolem + spider + blaze;
            int totalPeaceful = chicken + cow + ocelot + pig + sheep + squid + mooshroom;
            int totalSpecial = mickerson + pingot + pcow + qcm + banana + loot + sponge + dingot + quiver + flyingsaddle + biome;
            int totalUtilitarian = anvil + bed + brewingstand + chest + craftingtable + doublechest + enchantingtable + furnace + jukebox + netherportal + enderchest + saddle + lead + endportal;
            int totalYT = illuminati + juggernaut + siamese + dirt + cobblestone;
            int totals = totalLegend + totalMob + totalPeaceful + totalSpecial + totalUtilitarian + totalAOE + totalFan + totalYT + totalHoliday;
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"generalist")) && totalAOE > 0 && totalFan > 0 && totalHoliday > 0 && totalLegend > 0 && totalMob > 0 && totalPeaceful > 0 && totalSpecial > 0 && totalUtilitarian > 0 && totalYT > 0) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"generalist"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"1")) && totals >= 1) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"1"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"5")) && totals >= 5) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"5"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"10")) && totals >= 10) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"10"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"20")) && totals >= 20) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"20"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"30")) && totals >= 30) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"30"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"40")) && totals >= 40) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"40"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"50")) && totals >= 50) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"50"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"60")) && totals >= 60) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"60"));
            }
            if (!AdvancementHelper.hasAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"all")) && totals >= 68) {
                AdvancementHelper.unlockAdvancement(player, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"all"));
            }
        }
    }
}

