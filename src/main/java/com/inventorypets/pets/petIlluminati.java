/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.fish.WaterAnimal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Blaze
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.Silverfish
 *  net.minecraft.world.entity.monster.spider.Spider
 *  net.minecraft.world.entity.monster.zombie.ZombifiedPiglin
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.hurtingprojectile.Fireball
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.RecipeHolder
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.common.util.FakePlayer
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.helper.AdvancementHelper;
import com.inventorypets.init.ModDataComponents;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.io.IlluminatiBlacklistReader;
import com.inventorypets.screens.PetNamerScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petIlluminati
extends Item {
    private boolean eatFlag;
    private boolean slowFlag;
    private int slowTimer;
    private boolean complainFlag = false;
    private long readyTime;
    private boolean stopFlag = false;
    private boolean odFlag = false;
    private int chkEat = 0;
    private boolean msgFlag = true;
    private boolean customFood = false;
    private Item defaultFood;

    public petIlluminati(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_ILLUMINATI.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodIlluminati.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (this.defaultFood == null) {
            this.defaultFood = (Item)InventoryPets.NUGGET_EMERALD.get();
        }
        if (((Boolean)InventoryPetsConfig.disableIlluminati.get()).booleanValue()) {
            return;
        }
        if (!worldIn.isClientSide) {
            if (!stack.has(ModDataComponents.SLOW_DELAY)) {
                readyTime = System.currentTimeMillis();
                readyTime = readyTime + (long)(60000 * (Integer)InventoryPetsConfig.illuminatiCooldown.get());
                stack.set(ModDataComponents.SLOW_DELAY, (Object)readyTime);
            } else {
                this.readyTime = (Long)stack.get(ModDataComponents.SLOW_DELAY);
                if (this.readyTime == 0L) {
                    readyTime = System.currentTimeMillis();
                    readyTime = readyTime + (long)(60000 * (Integer)InventoryPetsConfig.illuminatiCooldown.get());
                    stack.set(ModDataComponents.SLOW_DELAY, (Object)readyTime);
                }
            }
        }
        if (this.readyTime / 100L == Long.valueOf(System.currentTimeMillis()) / 100L && !worldIn.isClientSide && stack.getDamageValue() < 3 && !this.msgFlag) {
            String tmpString1 = Component.translatable((String)"tooltip.ip.illuminatiready").getString();
            playerIn.sendSystemMessage((Component)Component.literal((String)(stack.getDisplayName().getString() + tmpString1)));
            this.msgFlag = true;
            playerIn.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 0, 2, false, false));
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.visible.get(), SoundSource.PLAYERS, 0.2f, 1.2f);
        }
        if (!this.slowFlag && this.stopFlag) {
            playerIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 18, 0, false, false));
            this.stopFlag = false;
        } else if (this.slowFlag && !this.stopFlag) {
            playerIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 18, 0, false, false));
            playerIn.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, (Integer)InventoryPetsConfig.illuminatiInvisibleDuration.get() * 1260, 0, false, false));
            this.stopFlag = true;
            this.msgFlag = false;
        }
        if (this.slowFlag && this.slowTimer > 0 && !worldIn.isClientSide) {
            Monster entitymob;
            Entity entity;
            int k;
            List entities;
            AABB range;
            --this.slowTimer;
            if (this.slowTimer <= 0) {
                this.slowFlag = false;
                range = new AABB(playerIn.getX() - 48.0, playerIn.getY() - 48.0, playerIn.getZ() - 48.0, playerIn.getX() + 48.0, playerIn.getY() + 48.0, playerIn.getZ() + 48.0);
                entities = worldIn.getEntitiesOfClass(LivingEntity.class, range);
                int esize2 = entities.size();
                for (k = 0; k <= esize2 - 1; ++k) {
                    entity = (Entity)entities.get(k);
                    if (!(entity instanceof Monster) || playerIn.isCreative()) continue;
                    entitymob = (Monster)entity;
                    entitymob.getBrain().setActiveActivityIfPossible(Activity.PANIC);
                    entitymob.setTarget((LivingEntity)playerIn);
                    entitymob.hurt(worldIn.damageSources().magic(), 1.0f);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.visible.get(), SoundSource.PLAYERS, 0.1f, 1.1f);
                }
            }
            range = new AABB(playerIn.getX() - 48.0, playerIn.getY() - 48.0, playerIn.getZ() - 48.0, playerIn.getX() + 48.0, playerIn.getY() + 48.0, playerIn.getZ() + 48.0);
            entities = worldIn.getEntitiesOfClass(LivingEntity.class, range);
            int esize = entities.size();
            for (k = 0; k <= esize - 1; ++k) {
                Monster monster;
                entity = (Entity)entities.get(k);
                if (entity != null && (entity instanceof Spider || entity instanceof Creeper || entity instanceof Blaze || entity instanceof ZombifiedPiglin || entity instanceof Silverfish)) {
                    entitymob = (Monster)entity;
                    entitymob.setTarget(null);
                    entitymob.goalSelector.disableControlFlag(Goal.Flag.TARGET);
                    if (entity instanceof Blaze) {
                        Blaze entityblaze = (Blaze)entitymob;
                        entityblaze.clearFire();
                        entityblaze.setTarget(null);
                        continue;
                    }
                    if (!(entity instanceof Creeper)) continue;
                    Creeper creeper = (Creeper)entitymob;
                    creeper.setSwellDir(-100);
                    creeper.setAggressive(false);
                    creeper.setTarget(null);
                    creeper.targetSelector.disableControlFlag(Goal.Flag.TARGET);
                    continue;
                }
                if (entity instanceof Fireball) {
                    if (!(((Fireball)entity).getEffectSource() instanceof Blaze)) continue;
                    entity.clearFire();
                    entity.setInvisible(true);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    Fireball entityfireball = (Fireball)entity;
                    entityfireball.discard();
                    continue;
                }
                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;
                    mob.goalSelector.disableControlFlag(Goal.Flag.TARGET);
                    mob.setTarget(null);
                    continue;
                }
                if (entity instanceof Animal) {
                    Animal animal = (Animal)entity;
                    animal.goalSelector.disableControlFlag(Goal.Flag.TARGET);
                    animal.setTarget(null);
                    continue;
                }
                if (entity instanceof Monster) {
                    monster = (Monster)entity;
                    monster.goalSelector.disableControlFlag(Goal.Flag.TARGET);
                    monster.setTarget(null);
                    continue;
                }
                if (!(entity instanceof WaterAnimal)) continue;
                monster = (WaterAnimal)entity;
                monster.goalSelector.disableControlFlag(Goal.Flag.TARGET);
                monster.setTarget(null);
            }
        }
        ++this.chkEat;
        if (!playerIn.isCreative() && stack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block2: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    this.odFlag = false;
                    if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && s3.is(InventoryPets.EMERALD_NUGGET)) {
                        this.odFlag = true;
                    }
                    if (s3.getItem() != petFood && (!this.odFlag || ((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood) && (s3.getItem() != Items.NETHER_STAR || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 1.1f);
                    this.eatFlag = true;
                    continue block2;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !playerIn.isCreative() && stack.getDamageValue() >= 3 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                s2 = playerIn.getInventory().getItem(k);
                this.odFlag = false;
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && s2.is(InventoryPets.EMERALD_NUGGET)) {
                    this.odFlag = true;
                }
                if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || this.odFlag && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.NETHER_STAR || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                s2.shrink(1);
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(stack, 0);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 1.1f);
                this.complainFlag = false;
                this.eatFlag = true;
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminout.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    this.complainFlag = true;
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableIlluminati.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        ItemStack itemstack2 = itemstack;
        if (!worldIn.isClientSide) {
            if (!itemstack2.has(ModDataComponents.SLOW_DELAY)) {
                Long readyTime = System.currentTimeMillis();
                readyTime = readyTime + (long)(60000 * (Integer)InventoryPetsConfig.illuminatiCooldown.get());
                itemstack2.set(ModDataComponents.SLOW_DELAY, (Object)readyTime);
            } else {
                this.readyTime = (Long)itemstack2.get(ModDataComponents.SLOW_DELAY);
            }
            if (this.readyTime > Long.valueOf(System.currentTimeMillis()) && !worldIn.isClientSide && !playerIn.isCreative()) {
                Long cooldown = this.readyTime - Long.valueOf(System.currentTimeMillis());
                cooldown = cooldown / 60L;
                int secs = Math.round(cooldown * 10L / 166L);
                int mins = 0;
                if (secs > 60) {
                    mins = Math.round(secs / 60);
                    secs %= 60;
                }
                Object secString = secs < 10 && mins > 0 ? "0" + String.valueOf(secs) : String.valueOf(secs);
                if (itemstack.getDamageValue() < 3) {
                    String tmpString1 = Component.translatable((String)"tooltip.ip.illuminaticooldown").getString();
                    String tmpString2 = Component.translatable((String)"tooltip.ip.illuminatiremaining").getString();
                    String tmpString3 = Component.translatable((String)"tooltip.ip.illuminatiseconds").getString();
                    if (mins == 0) {
                        playerIn.sendSystemMessage((Component)Component.literal((String)(itemstack.getDisplayName().getString() + tmpString1 + (String)secString + tmpString3 + tmpString2)));
                    } else {
                        playerIn.sendSystemMessage((Component)Component.literal((String)(itemstack.getDisplayName().getString() + tmpString1 + mins + ":" + (String)secString + tmpString2)));
                    }
                }
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminout.get(), SoundSource.PLAYERS, 0.4f, 4.1f);
            }
        } else if (!worldIn.isClientSide && !playerIn.isCreative() && itemstack.getDamageValue() >= 3 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                ItemStack s2 = playerIn.getInventory().getItem(k);
                this.odFlag = false;
                if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && s2.is(InventoryPets.EMERALD_NUGGET)) {
                    this.odFlag = true;
                }
                if ((s2 == ItemStack.EMPTY || !this.odFlag || ((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) && (s2 == ItemStack.EMPTY || s2.getItem() != Items.NETHER_STAR || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue())) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                }
                if (s2.getCount() == 0) {
                    this.removeItem(playerIn, s2);
                }
                this.setDamage(itemstack2, 0);
                Long readyTime = System.currentTimeMillis();
                readyTime = readyTime + 60000L * (long)((Integer)InventoryPetsConfig.illuminatiCooldown.get()).intValue();
                itemstack2.set(ModDataComponents.SLOW_DELAY, (Object)readyTime);
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.6f, 1.1f);
                this.eatFlag = true;
            }
            if (!this.eatFlag && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue()) {
                int dmg = itemstack.getDamageValue();
                if (dmg == 0) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                    this.eatFlag = true;
                }
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminout.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
            }
        }
        if (!worldIn.isClientSide && (itemstack.getDamageValue() < 3 || playerIn.isCreative()) && playerIn.isCrouching()) {
            if (this.readyTime <= Long.valueOf(System.currentTimeMillis()) || playerIn.isCreative()) {
                Long readyTime = System.currentTimeMillis();
                readyTime = readyTime + (long)(60000 * (Integer)InventoryPetsConfig.illuminatiCooldown.get());
                itemstack2.set(ModDataComponents.SLOW_DELAY, (Object)readyTime);
                this.slowFlag = true;
                this.slowTimer = (Integer)InventoryPetsConfig.illuminatiCooldown.get() >= 1 && (Integer)InventoryPetsConfig.illuminatiCooldown.get() <= 10 ? (Integer)InventoryPetsConfig.illuminatiCooldown.get() * 1260 : 1260;
                if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminati.get(), SoundSource.PLAYERS, 0.2f, 1.1f);
            }
        } else if (!(worldIn.isClientSide || itemstack.getDamageValue() >= 3 && !playerIn.isCreative() || playerIn.isCrouching() || ((Boolean)InventoryPetsConfig.disableIlluminatiGiveItems.get()).booleanValue() || this.readyTime > Long.valueOf(System.currentTimeMillis()) && !playerIn.isCreative())) {
            Long readyTime = System.currentTimeMillis();
            readyTime = readyTime + (long)(60000 * (Integer)InventoryPetsConfig.illuminatiCooldown.get());
            itemstack2.set(ModDataComponents.SLOW_DELAY, (Object)readyTime);
            RandomPoolAlias chooser = new RandomPoolAlias();
            int loops = 0;
            block1: for (int m = 0; m < loops + 1; ++m) {
                int chChk = chooser.nextInt(100);
                if (chChk < 1) {
                    ItemEntity entityitem;
                    ItemStack bob2 = ItemStack.EMPTY;
                    int specialDrops = chooser.nextInt(9);
                    if (specialDrops == 0) {
                        bob2 = new ItemStack((ItemLike)InventoryPets.PET_BLACK_HOLE.get(), 1);
                    } else if (specialDrops == 1) {
                        bob2 = new ItemStack((ItemLike)InventoryPets.PET_SLIME.get(), 1);
                    } else if (specialDrops == 2) {
                        bob2 = new ItemStack((ItemLike)InventoryPets.PET_CLOUD.get(), 1);
                    } else if (specialDrops == 3) {
                        bob2 = new ItemStack((ItemLike)InventoryPets.PET_PUFFERFISH.get(), 1);
                    } else if (specialDrops == 4) {
                        bob2 = new ItemStack((ItemLike)Blocks.DIAMOND_BLOCK, 1);
                    } else if (specialDrops == 5) {
                        bob2 = new ItemStack((ItemLike)Blocks.EMERALD_BLOCK, 1);
                    } else if (specialDrops == 6) {
                        bob2 = new ItemStack((ItemLike)Blocks.BEACON, 1);
                    } else if (specialDrops == 7) {
                        bob2 = new ItemStack((ItemLike)Items.NETHER_STAR, 1);
                    } else if (specialDrops == 8) {
                        bob2 = new ItemStack((ItemLike)InventoryPets.PET_ILLUMINATI.get(), 1);
                    }
                    String blacklist = "";
                    String modBlacklist = "";
                    if (bob2 != null && bob2 != ItemStack.EMPTY && bob2.getItem() != null && bob2.getItem().getDescriptionId() != null) {
                        blacklist = IlluminatiBlacklistReader.main(bob2.getItem().getDescriptionId().toString());
                        modBlacklist = IlluminatiBlacklistReader.main((petIlluminati.getModId(bob2.getItem()) + ":").toString());
                    }
                    if (bob2.getItem() == InventoryPets.PET_ILLUMINATI.get()) {
                        entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                        worldIn.addFreshEntity((Entity)entityitem);
                        if (!worldIn.isClientSide && !AdvancementHelper.hasAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"illum_from_illum"))) {
                            AdvancementHelper.unlockAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"illum_from_illum"));
                        }
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminati_confirmed.get(), SoundSource.PLAYERS, 1.4f, 1.0f);
                        for (int k = 0; k < 64; ++k) {
                            ItemEntity entityitem2 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 10.0 + (double)(k * 10), playerIn.getZ() + 0.5, new ItemStack((ItemLike)Items.DIAMOND, 1));
                            worldIn.addFreshEntity((Entity)entityitem2);
                        }
                        ItemEntity entityitem3 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)Items.NETHER_STAR, 1));
                        worldIn.addFreshEntity((Entity)entityitem3);
                        ItemEntity entityitem4 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)Blocks.BEACON, 1));
                        worldIn.addFreshEntity((Entity)entityitem4);
                        continue;
                    }
                    if (bob2 == null || bob2 == ItemStack.EMPTY || bob2.getItem() == Items.AIR || blacklist.equals("0") || modBlacklist.equals("0")) continue;
                    if (m == 0) {
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminati.get(), SoundSource.PLAYERS, 0.2f, 1.1f);
                    }
                    playerIn.sendSystemMessage((Component)Component.literal((String)(bob2.getDisplayName().getString() + " Confirmed!!")));
                    System.out.println(playerIn.getDisplayName().getString() + " spawned " + bob2.getDisplayName().getString() + " from an Illuminati Pet");
                    entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                    worldIn.addFreshEntity((Entity)entityitem);
                    this.msgFlag = false;
                    break;
                }
                if (chChk > 50 || ((Boolean)InventoryPetsConfig.illuminatiCraftedOnly.get()).booleanValue()) {
                    List recipes = worldIn.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
                    ArrayList list = new ArrayList(recipes);
                    for (int i = 0; i < list.size(); ++i) {
                        RandomPoolAlias rand = new RandomPoolAlias();
                        int bob = rand.nextInt(recipes.size());
                        RecipeHolder bobber = (RecipeHolder)list.get(bob);
                        ItemStack bob2 = ((CraftingRecipe)bobber.value()).getResultItem((HolderLookup.Provider)RegistryAccess.EMPTY).copy();
                        if (((CraftingRecipe)bobber.value()).getIngredients().isEmpty()) {
                            bob2 = ItemStack.EMPTY;
                        }
                        bob2.setCount(1);
                        String blacklist = "";
                        String modBlacklist = "";
                        if (bob2 != null && bob2 != ItemStack.EMPTY && bob2.getItem() != null && bob2.getItem().getDescriptionId() != null) {
                            blacklist = IlluminatiBlacklistReader.main(bob2.getItem().getDescriptionId().toString());
                            modBlacklist = IlluminatiBlacklistReader.main((petIlluminati.getModId(bob2.getItem()) + ":").toString());
                        }
                        if (bob2 == null || bob2.getItem() == null || bob2.getItem() == Items.AIR || bob2 == ItemStack.EMPTY || blacklist.equals("0") || modBlacklist.equals("0") || bob2.getItem().getDescriptionId().toString().toLowerCase().contains("lantern") || bob2.getItem().getDescriptionId().toString().contains("lamp") || bob2.getItem().getDescriptionId().toString().toLowerCase().contains("trowel")) continue;
                        bob2.setCount(1);
                        playerIn.sendSystemMessage((Component)Component.literal((String)(bob2.getDisplayName().getString() + " Confirmed!!")));
                        System.out.println(playerIn.getName().getString() + " spawned " + bob2.getDisplayName().getString() + " from an Illuminati Pet");
                        ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                        worldIn.addFreshEntity((Entity)entityitem);
                        this.msgFlag = false;
                        if (bob2.getItem() == InventoryPets.PET_ILLUMINATI.get()) {
                            if (!worldIn.isClientSide && !AdvancementHelper.hasAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"illum_from_illum"))) {
                                AdvancementHelper.unlockAdvancement((ServerPlayer)playerIn, Identifier.fromNamespaceAndPath((String)"inventorypets", (String)"illum_from_illum"));
                            }
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminati_confirmed.get(), SoundSource.PLAYERS, 1.4f, 1.0f);
                            for (int k = 0; k < 64; ++k) {
                                ItemEntity entityitem2 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 10.0 + (double)(k * 10), playerIn.getZ() + 0.5, new ItemStack((ItemLike)Items.DIAMOND, 1));
                                worldIn.addFreshEntity((Entity)entityitem2);
                            }
                            ItemEntity entityitem3 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)Items.NETHER_STAR, 1));
                            worldIn.addFreshEntity((Entity)entityitem3);
                            ItemEntity entityitem4 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)Blocks.BEACON, 1));
                            worldIn.addFreshEntity((Entity)entityitem4);
                            continue block1;
                        }
                        if (m != 0) continue block1;
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminati.get(), SoundSource.PLAYERS, 0.2f, 1.1f);
                        continue block1;
                    }
                    continue;
                }
                for (int i = 0; i < (Integer)InventoryPetsConfig.illuminatiItemCapLimit.get(); ++i) {
                    RandomPoolAlias rand2 = new RandomPoolAlias();
                    int bob = rand2.nextInt((Integer)InventoryPetsConfig.illuminatiItemCapLimit.get());
                    ItemStack bob2 = new ItemStack((ItemLike)Item.byId((int)bob), 1);
                    String blacklist = "";
                    String modBlacklist = "";
                    if (bob2 != null && bob2 != ItemStack.EMPTY && bob2.getItem() != null && bob2.getItem().getDescriptionId() != null) {
                        blacklist = IlluminatiBlacklistReader.main(bob2.getItem().getDescriptionId().toString());
                        modBlacklist = IlluminatiBlacklistReader.main((petIlluminati.getModId(bob2.getItem()) + ":").toString());
                    }
                    if (bob2 == null || bob2 == ItemStack.EMPTY || bob2.getItem() == null || bob2.getItem() == Items.AIR || blacklist.equals("0") || modBlacklist.equals("0") || bob2.getItem().getDescriptionId().toLowerCase().contains("lantern") || bob2.getItem().getDescriptionId().contains("Lamp") || bob2.getItem().getDescriptionId().toLowerCase().contains("trowel")) continue;
                    bob2.setCount(1);
                    if (m == 0) {
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.illuminati.get(), SoundSource.PLAYERS, 0.2f, 1.1f);
                    }
                    playerIn.sendSystemMessage((Component)Component.literal((String)(bob2.getDisplayName().getString() + " Confirmed!!")));
                    System.out.println(playerIn.getDisplayName().getString() + " spawned " + bob2.getDisplayName().getString() + " from an Illuminati Pet");
                    ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, bob2);
                    worldIn.addFreshEntity((Entity)entityitem);
                    continue block1;
                }
            }
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    public void removeItem(Player ep, ItemStack removeitem) {
        Inventory inventoryPlayer = ep.getInventory();
        for (int i = 0; i < 36; ++i) {
            ItemStack j;
            if (inventoryPlayer.getItem(i) == ItemStack.EMPTY || (j = inventoryPlayer.getItem(i)) == ItemStack.EMPTY || j.getItem() != removeitem.getItem()) continue;
            inventoryPlayer.setItem(i, ItemStack.EMPTY);
            break;
        }
    }

    public static String getModId(Item item) {
        if (item != null) {
            String id = ((ResourceKey)item.builtInRegistryHolder().unwrapKey().get()).location().getNamespace();
            return id == null || id.equals("") ? "minecraft" : id;
        }
        return null;
    }

    public static String getModId(ItemStack key) {
        return petIlluminati.getModId(key.getItem());
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        if (!((Boolean)InventoryPetsConfig.disableIlluminatiGiveItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petilluminati1", (Object[])new Object[0]) + " " + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        }
        if ((Integer)InventoryPetsConfig.illuminatiInvisibleDuration.get() > 1) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petilluminati2", (Object[])new Object[0]) + " (" + String.valueOf(InventoryPetsConfig.illuminatiInvisibleDuration.get()) + " " + I18n.get((String)"tooltip.ip.petminutes", (Object[])new Object[0]) + ") " + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petilluminati2", (Object[])new Object[0]) + " (" + String.valueOf(InventoryPetsConfig.illuminatiInvisibleDuration.get()) + " " + I18n.get((String)"tooltip.ip.petminute", (Object[])new Object[0]) + ") " + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + "(" + String.valueOf(InventoryPetsConfig.illuminatiCooldown.get()) + " " + I18n.get((String)"tooltip.ip.petminutecooldown", (Object[])new Object[0]) + ")")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodIlluminati.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)((Item)InventoryPets.NUGGET_EMERALD.get()).getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.NETHER_STAR.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.youtuber", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.designedby", (Object[])new Object[0]) + " Lachlan")));
        if (((Boolean)InventoryPetsConfig.disableIlluminati.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

