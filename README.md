# Inventory Pets — Fabric 1.21.11 Port

A community port of [Inventory Pets](https://www.curseforge.com/minecraft/mc-mods/inventory-pets) (originally by Purplicious_Cow and cy4n) from NeoForge 1.21.1 to Fabric Loader for Minecraft 1.21.11.

## Overview

Inventory Pets are tiny animated companions that live in your inventory and give you powerful perks, abilities, and bonuses. Each pet has a passive effect, an active ability (right-click), and a favorite food to keep it happy.

This port covers **97 registered items** across **82 pet definitions**, with all original gameplay features reimplemented on the Fabric API event system.

## Features

### Passive Pets
Constant buffs while the pet is in your inventory — Speed, Haste, Strength, Absorption, Night Vision, Water Breathing, Regeneration, Jump Boost, Slow Falling, Fire Resistance, Resistance, and more.

### Active Pets
Right-click to trigger abilities — teleport, explosions, fireballs, wither volleys, gravity reversal, rapid arrows, smelting, structure/biome locating, dimension travel, time skip, and more.

### Utility Pets
- **Chest / Double Chest / Ender Chest** — portable storage
- **Crafting Table** — portable crafting
- **Enchanting Table** — portable enchanting
- **Furnace** — portable smelting
- **Anvil** — inventory repair
- **Bed** — set day
- **Brewing Stand** — random potion dispenser

### Special Pets
- **Black Hole** — attracts nearby item drops (toggleable)
- **Cloud** — float above the ground
- **Sponge** — clears nearby water
- **Sun / Moon / Shield / Heart** — area buffs and effects
- **Meta Pets** — transform into random pets by category

### Support Items
Nuggets (diamond, obsidian, coal, lapis, ender, emerald, netherite) for feeding pets, plus the Feed Bag, Banana, Siamese Gift, and holiday items.

## Technical Details

| | |
|---|---|
| Minecraft | 1.21.11 |
| Mod Loader | Fabric Loader 0.19.5+ |
| Fabric API | 0.141.6+1.21.11 |
| Java | 21 |
| Build System | Gradle 9.5.1 + Fabric Loom 1.17.20 |
| License | All Rights Reserved |

## Building

```bash
./gradlew build
```

The output jar will be in `build/libs/`.

## Running (Dev)

```bash
./gradlew runServer
```

## Project Structure

```
src/main/java/com/inventorypets/fabric/
├── InventoryPetsFabric.java      # Main entry point
├── client/                       # Client-side rendering and screens
├── config/                       # JSON config loader
├── event/                        # Fabric API event handlers (attack, block break, entity use)
├── init/                         # Registry initialization (attachments, data components, sounds)
├── item/                         # Item classes (pet item, feed bag, support items)
├── network/                      # C2S network packets
└── pet/                          # Pet definition catalog
```

## Credits

- **Purplicious_Cow** — Original mod author
- **cy4n** — Co-author
- Community — Testing and feedback
