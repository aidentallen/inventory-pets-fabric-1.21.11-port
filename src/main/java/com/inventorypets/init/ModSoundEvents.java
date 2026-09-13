/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvent
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package com.inventorypets.init;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create((Registry)BuiltInRegistries.SOUND_EVENT, (String)"inventorypets");
    public static final Supplier<SoundEvent> nomnom = ModSoundEvents.createEvent("nomnom");
    public static final Supplier<SoundEvent> moo = ModSoundEvents.createEvent("moo");
    public static final Supplier<SoundEvent> baa = ModSoundEvents.createEvent("baa");
    public static final Supplier<SoundEvent> jet = ModSoundEvents.createEvent("jet");
    public static final Supplier<SoundEvent> squawk = ModSoundEvents.createEvent("squawk");
    public static final Supplier<SoundEvent> oink = ModSoundEvents.createEvent("oink");
    public static final Supplier<SoundEvent> ghast_scream = ModSoundEvents.createEvent("ghast_scream");
    public static final Supplier<SoundEvent> pc_moo = ModSoundEvents.createEvent("pc_moo");
    public static final Supplier<SoundEvent> spider_screech = ModSoundEvents.createEvent("spider_screech");
    public static final Supplier<SoundEvent> iron_golem = ModSoundEvents.createEvent("iron_golem");
    public static final Supplier<SoundEvent> fizz = ModSoundEvents.createEvent("fizz");
    public static final Supplier<SoundEvent> hey = ModSoundEvents.createEvent("hey");
    public static final Supplier<SoundEvent> teleport = ModSoundEvents.createEvent("teleport");
    public static final Supplier<SoundEvent> boing = ModSoundEvents.createEvent("boing");
    public static final Supplier<SoundEvent> knockback = ModSoundEvents.createEvent("knockback");
    public static final Supplier<SoundEvent> creeper = ModSoundEvents.createEvent("creeper");
    public static final Supplier<SoundEvent> lifesteal = ModSoundEvents.createEvent("lifesteal");
    public static final Supplier<SoundEvent> bed_yawn = ModSoundEvents.createEvent("bed_yawn");
    public static final Supplier<SoundEvent> anvil = ModSoundEvents.createEvent("anvil");
    public static final Supplier<SoundEvent> anvil_break = ModSoundEvents.createEvent("anvil_break");
    public static final Supplier<SoundEvent> bolt_thunder = ModSoundEvents.createEvent("bolt_thunder");
    public static final Supplier<SoundEvent> bolt_explode = ModSoundEvents.createEvent("bolt_explode");
    public static final Supplier<SoundEvent> cloud_rumble = ModSoundEvents.createEvent("cloud_rumble");
    public static final Supplier<SoundEvent> venom_inject = ModSoundEvents.createEvent("venom_inject");
    public static final Supplier<SoundEvent> black_hole = ModSoundEvents.createEvent("black_hole");
    public static final Supplier<SoundEvent> time_warp = ModSoundEvents.createEvent("time_warp");
    public static final Supplier<SoundEvent> ping = ModSoundEvents.createEvent("ping");
    public static final Supplier<SoundEvent> magmacube = ModSoundEvents.createEvent("magmacube");
    public static final Supplier<SoundEvent> crafting = ModSoundEvents.createEvent("crafting");
    public static final Supplier<SoundEvent> chest = ModSoundEvents.createEvent("chest");
    public static final Supplier<SoundEvent> mqbbreathe = ModSoundEvents.createEvent("mqbbreathe");
    public static final Supplier<SoundEvent> mqbhit = ModSoundEvents.createEvent("mqbhit");
    public static final Supplier<SoundEvent> mqbdeath = ModSoundEvents.createEvent("mqbdeath");
    public static final Supplier<SoundEvent> mqeidle = ModSoundEvents.createEvent("mqeidle");
    public static final Supplier<SoundEvent> mqehit = ModSoundEvents.createEvent("mqehit");
    public static final Supplier<SoundEvent> mqedeath = ModSoundEvents.createEvent("mqedeath");
    public static final Supplier<SoundEvent> mqeportal = ModSoundEvents.createEvent("mqeportal");
    public static final Supplier<SoundEvent> mqestare = ModSoundEvents.createEvent("mqestare");
    public static final Supplier<SoundEvent> zap = ModSoundEvents.createEvent("zap");
    public static final Supplier<SoundEvent> qcm_buzz = ModSoundEvents.createEvent("qcm_buzz");
    public static final Supplier<SoundEvent> meow = ModSoundEvents.createEvent("meow");
    public static final Supplier<SoundEvent> meow1 = ModSoundEvents.createEvent("meow1");
    public static final Supplier<SoundEvent> brew = ModSoundEvents.createEvent("brew");
    public static final Supplier<SoundEvent> brewpot = ModSoundEvents.createEvent("brewpot");
    public static final Supplier<SoundEvent> nether = ModSoundEvents.createEvent("nether");
    public static final Supplier<SoundEvent> smelt = ModSoundEvents.createEvent("smelt");
    public static final Supplier<SoundEvent> furnace = ModSoundEvents.createEvent("furnace");
    public static final Supplier<SoundEvent> pageflip = ModSoundEvents.createEvent("pageflip");
    public static final Supplier<SoundEvent> slime_revive = ModSoundEvents.createEvent("slime_revive");
    public static final Supplier<SoundEvent> fireball = ModSoundEvents.createEvent("fireball");
    public static final Supplier<SoundEvent> moon = ModSoundEvents.createEvent("moon");
    public static final Supplier<SoundEvent> splat = ModSoundEvents.createEvent("splat");
    public static final Supplier<SoundEvent> raspberry = ModSoundEvents.createEvent("raspberry");
    public static final Supplier<SoundEvent> shield = ModSoundEvents.createEvent("shield");
    public static final Supplier<SoundEvent> blaze = ModSoundEvents.createEvent("blaze");
    public static final Supplier<SoundEvent> heart = ModSoundEvents.createEvent("heart");
    public static final Supplier<SoundEvent> sponge_dry = ModSoundEvents.createEvent("sponge_dry");
    public static final Supplier<SoundEvent> sponge_wet = ModSoundEvents.createEvent("sponge_wet");
    public static final Supplier<SoundEvent> sponge_slurp = ModSoundEvents.createEvent("sponge_slurp");
    public static final Supplier<SoundEvent> click = ModSoundEvents.createEvent("click");
    public static final Supplier<SoundEvent> dubstep = ModSoundEvents.createEvent("dubstep");
    public static final Supplier<SoundEvent> clang = ModSoundEvents.createEvent("clang");
    public static final Supplier<SoundEvent> shorted = ModSoundEvents.createEvent("shorted");
    public static final Supplier<SoundEvent> fade = ModSoundEvents.createEvent("fade");
    public static final Supplier<SoundEvent> coin = ModSoundEvents.createEvent("coin");
    public static final Supplier<SoundEvent> wither = ModSoundEvents.createEvent("wither");
    public static final Supplier<SoundEvent> shields_up = ModSoundEvents.createEvent("shields_up");
    public static final Supplier<SoundEvent> squeak = ModSoundEvents.createEvent("squeak");
    public static final Supplier<SoundEvent> illuminati = ModSoundEvents.createEvent("illuminati");
    public static final Supplier<SoundEvent> visible = ModSoundEvents.createEvent("visible");
    public static final Supplier<SoundEvent> slam = ModSoundEvents.createEvent("slam");
    public static final Supplier<SoundEvent> illuminout = ModSoundEvents.createEvent("illuminout");
    public static final Supplier<SoundEvent> slamstop = ModSoundEvents.createEvent("slamstop");
    public static final Supplier<SoundEvent> juggerout = ModSoundEvents.createEvent("juggerout");
    public static final Supplier<SoundEvent> illuminati_confirmed = ModSoundEvents.createEvent("illuminati_confirmed");
    public static final Supplier<SoundEvent> slamready = ModSoundEvents.createEvent("slamready");
    public static final Supplier<SoundEvent> grave = ModSoundEvents.createEvent("grave");
    public static final Supplier<SoundEvent> blech = ModSoundEvents.createEvent("blech");
    public static final Supplier<SoundEvent> unwrap = ModSoundEvents.createEvent("unwrap");
    public static final Supplier<SoundEvent> sleigh = ModSoundEvents.createEvent("sleigh");
    public static final Supplier<SoundEvent> combo = ModSoundEvents.createEvent("combo");
    public static final Supplier<SoundEvent> fishin = ModSoundEvents.createEvent("fishin");
    public static final Supplier<SoundEvent> pm_blue = ModSoundEvents.createEvent("pm_blue");
    public static final Supplier<SoundEvent> pm_eat4u = ModSoundEvents.createEvent("pm_eat4u");
    public static final Supplier<SoundEvent> pm_eatghost = ModSoundEvents.createEvent("pm_eatghost");
    public static final Supplier<SoundEvent> pm_wokka = ModSoundEvents.createEvent("pm_wokka");
    public static final Supplier<SoundEvent> pm_die = ModSoundEvents.createEvent("pm_die");
    public static final Supplier<SoundEvent> medieval = ModSoundEvents.createEvent("medieval");
    public static final Supplier<SoundEvent> stringsnap = ModSoundEvents.createEvent("stringsnap");
    public static final Supplier<SoundEvent> april_fool = ModSoundEvents.createEvent("april_fool");
    public static final Supplier<SoundEvent> rickroll = ModSoundEvents.createEvent("rickroll");
    public static final Supplier<SoundEvent> horn_fade = ModSoundEvents.createEvent("horn_fade");
    public static final Supplier<SoundEvent> cheetah_go = ModSoundEvents.createEvent("cheetah_go");
    public static final Supplier<SoundEvent> doorbell = ModSoundEvents.createEvent("doorbell");
    public static final Supplier<SoundEvent> doubledoorbell = ModSoundEvents.createEvent("doubledoorbell");
    public static final Supplier<SoundEvent> overbell = ModSoundEvents.createEvent("overbell");
    public static final Supplier<SoundEvent> silverhunger = ModSoundEvents.createEvent("silverhunger");
    public static final Supplier<SoundEvent> silver = ModSoundEvents.createEvent("silver");
    public static final Supplier<SoundEvent> howl = ModSoundEvents.createEvent("howl");
    public static final Supplier<SoundEvent> howl2 = ModSoundEvents.createEvent("howl2");
    public static final Supplier<SoundEvent> bark1 = ModSoundEvents.createEvent("bark1");
    public static final Supplier<SoundEvent> apple_eat = ModSoundEvents.createEvent("apple_eat");
    public static final Supplier<SoundEvent> bgsay = ModSoundEvents.createEvent("bgsay");
    public static final Supplier<SoundEvent> sbsay = ModSoundEvents.createEvent("sbsay");
    public static final Supplier<SoundEvent> snsay = ModSoundEvents.createEvent("snsay");
    public static final Supplier<SoundEvent> siamese_gift = ModSoundEvents.createEvent("siamese_gift");
    public static final Supplier<SoundEvent> meta_transform = ModSoundEvents.createEvent("meta_transform");
    public static final Supplier<SoundEvent> sun = ModSoundEvents.createEvent("sun");
    public static final Supplier<SoundEvent> petrifier = ModSoundEvents.createEvent("petrifier");
    public static final Supplier<SoundEvent> dirt = ModSoundEvents.createEvent("dirt");
    public static final Supplier<SoundEvent> stone = ModSoundEvents.createEvent("stone");
    public static final Supplier<SoundEvent> trololo = ModSoundEvents.createEvent("trololo");
    public static final Supplier<SoundEvent> trololo1 = ModSoundEvents.createEvent("trololo1");
    public static final Supplier<SoundEvent> trololo2 = ModSoundEvents.createEvent("trololo2");
    public static final Supplier<SoundEvent> biome = ModSoundEvents.createEvent("biome");
    public static final Supplier<SoundEvent> humburp = ModSoundEvents.createEvent("humburp");
    public static final Supplier<SoundEvent> biome_found = ModSoundEvents.createEvent("biome_found");
    public static final Supplier<SoundEvent> biome_waiting = ModSoundEvents.createEvent("biome_waiting");
    public static final Supplier<SoundEvent> structure_found = ModSoundEvents.createEvent("ding");
    public static final Supplier<SoundEvent> dingot_waiting = ModSoundEvents.createEvent("dingot");
    public static final Supplier<SoundEvent> dingot_waiting_long = ModSoundEvents.createEvent("dingot_long");

    private static Supplier<SoundEvent> createEvent(String sound) {
        return SOUNDS.register(sound, () -> SoundEvent.createVariableRangeEvent((Identifier)Identifier.fromNamespaceAndPath((String)"inventorypets", (String)sound)));
    }
}

