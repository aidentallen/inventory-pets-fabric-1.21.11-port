/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ambient.Bat
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Silverfish
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.enchantment.Enchantments
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
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.Calendar;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petAprilFool
extends Item {
    private int spawnDelay = 399;
    private boolean eatFlag;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.TRIPWIRE_HOOK;

    public petAprilFool(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_APRIL_FOOL.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodAprilFool.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableAprilFool.get()).booleanValue()) {
            return;
        }
        ++this.spawnDelay;
        if (worldIn.isClientSide && this.checkSinglePlayer()) {
            ++this.spawnDelay;
        }
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() >= 1 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == Items.TRIPWIRE_HOOK && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood || s3.getItem() == petFood && this.customFood) && (this.customFood || this.eatFlag || s3.getItem() != Items.TRAPPED_CHEST || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue())) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.4f);
                    this.eatFlag = true;
                    l = invsize + 1;
                    k = 37;
                }
            }
        }
        if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() >= 1 && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            ItemStack itemstack2 = stack;
            for (int k = 0; k < 36; ++k) {
                ItemStack s2 = entityplayer.getInventory().getItem(k);
                if (s2.isEmpty() || petFood == Items.AIR || !(this.customFood && s2.getItem() == petFood || s2.getItem() == Items.TRIPWIRE_HOOK && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Items.TRAPPED_CHEST || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                if (!this.eatFlag) {
                    s2.shrink(1);
                    this.setDamage(itemstack2, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 1.4f);
                    this.complainFlag = false;
                    this.eatFlag = true;
                    this.setDamage(itemstack2, 0);
                }
                if (s2.getCount() != 0) continue;
                this.removeItem(entityplayer, s2);
            }
            if (!this.eatFlag) {
                int dmg = stack.getDamageValue();
                if (dmg == 0) {
                    stack.setDamageValue(stack.getDamageValue() + 1);
                    this.eatFlag = true;
                    this.complainFlag = false;
                }
                if (!this.complainFlag) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.horn_fade.get(), SoundSource.PLAYERS, 0.4f, 1.0f);
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
        if (((Boolean)InventoryPetsConfig.disableAprilFool.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.horn_fade.get(), SoundSource.PLAYERS, 0.4f, 1.0f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        Long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.get(1);
        int mMonth = calendar.get(2);
        if (((Boolean)InventoryPetsConfig.yearroundAprilFool.get()).booleanValue()) {
            mMonth = 3;
        }
        if (mMonth == 3 && !((Boolean)InventoryPetsConfig.disableAprilFool.get()).booleanValue()) {
            if (!worldIn.isClientSide && itemstack.getDamageValue() == 0) {
                if (this.spawnDelay > 400) {
                    Entity entity;
                    int k;
                    this.spawnDelay = 0;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.april_fool.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    RandomPoolAlias rand = new RandomPoolAlias();
                    int bob = 0;
                    bob = (Boolean)InventoryPetsConfig.enableAprilFoolGriefPranks.get() != false ? rand.nextInt(8) : rand.nextInt(7);
                    int totPlayers = 0;
                    boolean multiPlayers = false;
                    AABB range = new AABB(playerIn.getX() - 48.0, playerIn.getY() - 48.0, playerIn.getZ() - 48.0, playerIn.getX() + 48.0, playerIn.getY() + 48.0, playerIn.getZ() + 48.0);
                    List entities = worldIn.getEntitiesOfClass(Entity.class, range);
                    int esize = entities.size();
                    for (k = 0; k <= esize - 1; ++k) {
                        entity = (Entity)entities.get(k);
                        if (entity == null || !(entity instanceof Player)) continue;
                        ++totPlayers;
                    }
                    if (totPlayers > 1) {
                        multiPlayers = false;
                    }
                    if (bob == 0) {
                        for (k = 0; k <= esize - 1; ++k) {
                            entity = (Entity)entities.get(k);
                            if (entity == null || !(entity instanceof Player)) continue;
                            Player entityfool = (Player)entity;
                            if (multiPlayers && playerIn == entityfool) continue;
                            entityfool.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, -1, false, false));
                            for (int m = 0; m < 20; ++m) {
                                Bat entityB1 = (Bat)EntityType.BAT.create(worldIn);
                                entityB1.setTarget((LivingEntity)entityfool);
                                entityB1.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 127, false, false));
                                entityB1.moveTo(entityfool.getX(), entityfool.getY(), entityfool.getZ());
                                worldIn.addFreshEntity((Entity)entityB1);
                            }
                        }
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.rickroll.get(), SoundSource.PLAYERS, 1.7f, 1.2f);
                    } else if (bob == 1) {
                        int bob2 = rand.nextInt(5);
                        if (bob2 == 0) {
                            ItemEntity entityitem3 = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)InventoryPets.XEROX_PARC_GUI.get(), 1));
                            worldIn.addFreshEntity((Entity)entityitem3);
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.april_fool.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.combo.get(), SoundSource.PLAYERS, 0.5f, 1.9f);
                        } else {
                            ItemStack ep = ItemStack.EMPTY;
                            if (bob2 < 3) {
                                ep = new ItemStack((ItemLike)Items.POISONOUS_POTATO);
                                reg = worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
                                Holder unbreaking = reg.getHolder(Enchantments.UNBREAKING).orElse(null);
                                ep.enchant(unbreaking, 1);
                                String[] potnames = new String[]{"Serious Starch", "Mashed Lightning", "Spud of Doom", "Fried Vengeance", "Frites", "Dirt Apple", "Couchy", "Drumpf", "Tuber", "YouTuber", "Yamtastic", "Sweetie", "Roota Carbo", "Super Spud"};
                                rand = new RandomPoolAlias();
                                int nameChk = rand.nextInt(14);
                                String randName = potnames[nameChk];
                                MutableComponent randName2 = Component.translatable((String)randName);
                                ep.set(DataComponents.CUSTOM_NAME, (Object)randName2);
                            } else {
                                ep = new ItemStack((ItemLike)Blocks.DEAD_BUSH);
                                reg = worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
                                Holder unbreaking = reg.getHolder(Enchantments.UNBREAKING).orElse(null);
                                ep.enchant(unbreaking, 1);
                                String[] bushnames = new String[]{"Chaff", "Shrubbery", "Styx", "Kate", "Kindling", "Chuff", "Compost", "Sticky", "Plush", "Ichibod", "Leavy", "Deserted One", "Tragic Ted", "Hedge"};
                                rand = new RandomPoolAlias();
                                int nameChk = rand.nextInt(14);
                                String randName = bushnames[nameChk];
                                MutableComponent randName2 = Component.translatable((String)randName);
                                ep.set(DataComponents.CUSTOM_NAME, (Object)randName2);
                            }
                            ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, ep);
                            worldIn.addFreshEntity((Entity)entityitem);
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.combo.get(), SoundSource.PLAYERS, 0.5f, 1.9f);
                        }
                    } else if (bob == 2) {
                        for (k = 0; k <= esize - 1; ++k) {
                            entity = (Entity)entities.get(k);
                            if (entity == null || !(entity instanceof Player)) continue;
                            Player entityfool = (Player)entity;
                            if (multiPlayers && playerIn == entityfool) continue;
                            entityfool.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, -1, false, false));
                            entityfool.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 2, false, false));
                            entityfool.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 4, false, false));
                            Silverfish entityB1 = (Silverfish)EntityType.SILVERFISH.create(worldIn);
                            entityB1.setTarget((LivingEntity)entityfool);
                            entityB1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2, false, false));
                            entityB1.moveTo(entityfool.getX() + 0.5, entityfool.getY() + 0.5, entityfool.getZ() + 0.5);
                            Silverfish entityB2 = (Silverfish)EntityType.SILVERFISH.create(worldIn);
                            entityB2.setTarget((LivingEntity)entityfool);
                            entityB1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2, false, false));
                            entityB2.moveTo(entityfool.getX() - 0.5, entityfool.getY() + 0.5, entityfool.getZ() + 0.5);
                            Silverfish entityB3 = (Silverfish)EntityType.SILVERFISH.create(worldIn);
                            entityB3.setTarget((LivingEntity)entityfool);
                            entityB3.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2, false, false));
                            entityB3.moveTo(entityfool.getX() + 0.5, entityfool.getY() + 0.5, entityfool.getZ() - 0.5);
                            Silverfish entityB4 = (Silverfish)EntityType.SILVERFISH.create(worldIn);
                            entityB4.setTarget((LivingEntity)entityfool);
                            entityB4.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2, false, false));
                            entityB4.moveTo(entityfool.getX() - 0.5, entityfool.getY() + 0.5, entityfool.getZ() - 0.5);
                            Silverfish entityB5 = (Silverfish)EntityType.SILVERFISH.create(worldIn);
                            entityB5.setTarget((LivingEntity)entityfool);
                            entityB5.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2, false, false));
                            entityB5.moveTo(entityfool.getX() + 0.5, entityfool.getY() + 1.0, entityfool.getZ() + 0.5);
                            worldIn.addFreshEntity((Entity)entityB1);
                            worldIn.addFreshEntity((Entity)entityB2);
                            worldIn.addFreshEntity((Entity)entityB3);
                            worldIn.addFreshEntity((Entity)entityB4);
                            worldIn.addFreshEntity((Entity)entityB5);
                        }
                    } else if (bob == 3) {
                        ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 0.5, playerIn.getZ() + 0.5, new ItemStack((ItemLike)InventoryPets.EASTER_EGG.get(), 1));
                        worldIn.addFreshEntity((Entity)entityitem);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.combo.get(), SoundSource.PLAYERS, 0.5f, 1.9f);
                    } else if (bob == 4) {
                        if (!worldIn.isClientSide) {
                            for (int f = 0; f < 5; ++f) {
                                ItemEntity entityitem = new ItemEntity(playerIn.level(), playerIn.getX() + 0.5, playerIn.getY() + 5.0 + (double)(f * 10), playerIn.getZ() + 0.5, new ItemStack((ItemLike)InventoryPets.ROCK_CANDY.get(), 1));
                                worldIn.addFreshEntity((Entity)entityitem);
                            }
                        }
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.combo.get(), SoundSource.PLAYERS, 0.5f, 1.9f);
                    } else if (bob == 5) {
                        for (k = 0; k <= esize - 1; ++k) {
                            entity = (Entity)entities.get(k);
                            if (entity == null || !(entity instanceof Player)) continue;
                            Player entityfool = (Player)entity;
                            if (multiPlayers && playerIn == entityfool) continue;
                            ItemStack[] armors = new ItemStack[4];
                            entityfool.getInventory();
                            for (int j1 = 0; j1 < entityfool.getInventory().armor.size(); ++j1) {
                                armors[j1] = entityfool.getInventory().getArmor(j1);
                                entityfool.getInventory().armor.set(j1, (Object)new ItemStack((ItemLike)Items.ROTTEN_FLESH, 1));
                                if (armors[j1] == null) continue;
                                ItemEntity entityitem3 = new ItemEntity(entityfool.level(), entityfool.getX() + 0.5, entityfool.getY() + 0.5, entityfool.getZ() + 0.5, armors[j1]);
                                worldIn.addFreshEntity((Entity)entityitem3);
                            }
                            if (worldIn.isClientSide) continue;
                            for (int f = 0; f < 8; ++f) {
                                ItemEntity entityitem2 = new ItemEntity(entityfool.level(), entityfool.getX() + 0.5, entityfool.getY() + 5.0 + (double)(f * 10), entityfool.getZ() + 0.5, new ItemStack((ItemLike)Items.ROTTEN_FLESH, 1));
                                worldIn.addFreshEntity((Entity)entityitem2);
                            }
                        }
                    } else if (bob == 6) {
                        for (k = 0; k <= esize - 1; ++k) {
                            entity = (Entity)entities.get(k);
                            if (entity == null || !(entity instanceof Player)) continue;
                            Player entityfool = (Player)entity;
                            if (multiPlayers && playerIn == entityfool) continue;
                            String[] playnames = new String[]{entityfool.getName().getString() + "'s Lackey", "I'm With Stupid", "I Love Broccoli", "Pity Party", "Snappy Combeback", "Zany Ziggy", "Underwhelmed", "Musky Melon", "Husky Felon", "Toopid", "Harley David", "Misunderstood", entityfool.getName().getString() + " is the real fool", "Electoral College"};
                            int nameChk = rand.nextInt(14);
                            String randName = playnames[nameChk];
                            MutableComponent randName2 = Component.literal((String)randName);
                            playerIn.getItemInHand(handIn).set(DataComponents.CUSTOM_NAME, (Object)randName2);
                            playerIn.sendSystemMessage((Component)Component.literal((String)("My new name is [" + randName2.getString() + "]!")));
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.trololo1.get(), SoundSource.PLAYERS, 1.0f, 1.4f);
                        }
                    } else if (bob == 7) {
                        for (k = 0; k <= esize - 1; ++k) {
                            entity = (Entity)entities.get(k);
                            if (entity == null || !(entity instanceof Player)) continue;
                            Player entityfool = (Player)entity;
                            if (multiPlayers && playerIn == entityfool) continue;
                            int blockX = Mth.floor((double)entityfool.getX());
                            int blockY = Mth.floor((double)(entityfool.getY() - 0.2 - (double)entityfool.getEyeHeight()));
                            int blockZ = Mth.floor((double)entityfool.getZ());
                            BlockPos blockpos = null;
                            for (int g = 0; g < 50; ++g) {
                                if (blockY - g > 1) {
                                    blockpos = new BlockPos(blockX, blockY - g, blockZ);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX + 1, blockY - g, blockZ);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX + 1, blockY - g, blockZ + 1);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX, blockY - g, blockZ + 1);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX - 1, blockY - g, blockZ);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX - 1, blockY - g, blockZ - 1);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX, blockY - g, blockZ - 1);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX + 1, blockY - g, blockZ - 1);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    blockpos = new BlockPos(blockX - 1, blockY - g, blockZ + 1);
                                    worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                    continue;
                                }
                                blockpos = new BlockPos(blockX, blockY - g, blockZ);
                                worldIn.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
                                g = 50;
                            }
                            worldIn.setBlockAndUpdate(new BlockPos(blockX, blockY - 49, blockZ), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX + 1, blockY - 49, blockZ), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX + 1, blockY - 49, blockZ + 1), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX, blockY - 49, blockZ + 1), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX - 1, blockY - 49, blockZ), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX - 1, blockY - 49, blockZ - 1), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX, blockY - 49, blockZ - 1), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX + 1, blockY - 49, blockZ - 1), Blocks.WATER.defaultBlockState());
                            worldIn.setBlockAndUpdate(new BlockPos(blockX - 1, blockY - 49, blockZ + 1), Blocks.WATER.defaultBlockState());
                        }
                    }
                } else if (this.spawnDelay <= 400 && !worldIn.isClientSide) {
                    Float cooldown = Float.valueOf((float)((double)(400 - this.spawnDelay) / 2400.0));
                    Object coolstring = cooldown.toString();
                    coolstring = ((String)coolstring).substring(0, ((String)coolstring).length()) + "00";
                    String minstring = ((String)coolstring).substring(0, 1);
                    String secstring = ((String)coolstring).substring(1, 4);
                    float secs = Float.parseFloat(secstring) * 60.0f;
                    int secs2 = Math.round(secs);
                    Object secs3 = String.valueOf(secs2);
                    if (((String)secs3).length() < 2) {
                        secs3 = "0" + (String)secs3;
                    }
                    if (itemstack.getDamageValue() < 3) {
                        MutableComponent msg = Component.translatable((String)"tooltip.ip.foolishnesscooldown");
                        playerIn.sendSystemMessage((Component)Component.literal((String)(msg.getString() + " " + minstring + ":" + (String)secs3 + ".")));
                    }
                }
                if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.spawnDelay == 0 && !playerIn.isCreative()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                }
            }
        } else if (!worldIn.isClientSide) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.horn_fade.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
            playerIn.sendSystemMessage((Component)Component.translatable((String)"pet.fool.dates", (Object[])new Object[0]));
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

    @OnlyIn(value=Dist.CLIENT)
    public boolean checkSinglePlayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petaprilfool1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodAprilFool.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.TRIPWIRE_HOOK.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.TRAPPED_CHEST.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.holiday", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableAprilFool.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

