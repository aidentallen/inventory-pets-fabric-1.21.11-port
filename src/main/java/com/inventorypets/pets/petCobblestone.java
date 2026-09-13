/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.TieredItem
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.gamerules.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.GrassBlock
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
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
import com.inventorypets.helper.ItemHelper;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import java.util.RandomPoolAlias;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petCobblestone
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag = false;
    private int useDelay;
    private boolean newFlag = true;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.DIRT;

    public petCobblestone(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player playerIn = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if ((Integer)InventoryPetsConfig.petEatTimerFactor.get() <= 0) {
            return;
        }
        if (this.newFlag && (Integer)InventoryPetsConfig.petEatTimerFactor.get() > 0 && !this.isDamaged(stack)) {
            this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
            this.newFlag = false;
        }
        ItemStack petchk = playerIn.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == this) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodCobblestone.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableCobblestone.get()).booleanValue()) {
            return;
        }
        if (this.useDelay < 100) {
            ++this.useDelay;
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = playerIn.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_COBBLESTONE.get()) continue;
            slotNo = i;
        }
        if (!(playerIn.isCreative() || petFood == Items.AIR || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10)) {
            this.eatFlag = false;
            ItemStack itemstack2 = stack;
            block1: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = playerIn.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Blocks.DIRT.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Blocks.GRASS_BLOCK.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(itemstack2, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.4f, 1.2f);
                    this.eatFlag = true;
                    this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block1;
                }
            }
        }
        if (!playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide) {
            int i;
            ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                hotbar[i] = playerIn.getInventory().getItem(i);
            }
            this.hbFlag = false;
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                if (hotbar[i] != ItemStack.EMPTY && hotbar[i].getItem() == InventoryPets.PET_COBBLESTONE.get() && this.chkEat > 40 && (this.ticktime <= 0 || hotbar[i].getDamageValue() > 0)) {
                    int dmg;
                    if (this.ticktime <= 0) {
                        this.hbFlag = true;
                    }
                    if (i >= slotNo) {
                        this.chkEat = 0;
                    }
                    this.eatFlag = false;
                    for (int k = 0; k < 36; ++k) {
                        ItemStack s2 = playerIn.getInventory().getItem(k);
                        if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Blocks.DIRT.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s2.getItem() != Blocks.GRASS_BLOCK.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                        s2.shrink(1);
                        if (s2.getCount() == 0) {
                            this.removeItem(playerIn, s2);
                        }
                        this.setDamage(hotbar[i], 0);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.4f, 1.2f);
                        this.eatFlag = true;
                    }
                    if (!this.eatFlag && hotbar[i] != ItemStack.EMPTY && (dmg = hotbar[i].getDamageValue()) == 0) {
                        hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                        if (hotbar[i].getDamageValue() == 1) {
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.stone.get(), SoundSource.PLAYERS, 0.2f, 1.0f);
                        }
                        this.eatFlag = true;
                    }
                }
                if (!this.hbFlag) continue;
                this.chkEat = 0;
                this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = playerIn.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_COBBLESTONE.get() || itemchk.getDamageValue() != 0 && !playerIn.isCreative()) continue;
                Block dirtChk1 = playerIn.level().getBlockState(new BlockPos(Mth.floor((double)playerIn.getX()), Mth.floor((double)playerIn.getY()) - 1, Mth.floor((double)playerIn.getZ()))).getBlock();
                boolean hasDirt = false;
                for (int k = 0; k <= ItemHelper.getHotbarSize() - k; ++k) {
                    ItemStack otherchk = playerIn.getInventory().getItem(k);
                    if (otherchk == ItemStack.EMPTY || ((Boolean)InventoryPetsConfig.disableDirt.get()).booleanValue() || otherchk.getItem() != InventoryPets.PET_DIRT.get() || otherchk.getDamageValue() != 0) continue;
                    hasDirt = true;
                    break;
                }
                if (!(dirtChk1 == null || hasDirt || dirtChk1 != Blocks.COBBLESTONE && dirtChk1 != Blocks.STONE)) {
                    if (playerIn.hasEffect(MobEffects.MOVEMENT_SPEED) || playerIn.hasEffect(MobEffects.DIG_SPEED)) continue;
                    playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4, 3, false, false));
                    playerIn.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 4, 2, false, false));
                    playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 4, 0, false, false));
                    if (playerIn.hasEffect(MobEffects.REGENERATION)) continue;
                    playerIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, false));
                    continue;
                }
                if (dirtChk1 == null || hasDirt || !(dirtChk1 instanceof GrassBlock) || playerIn.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || playerIn.hasEffect(MobEffects.DIG_SLOWDOWN)) continue;
                playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4, 2, false, false));
                playerIn.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 4, 2, false, false));
                playerIn.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 4, 0, false, false));
                if (playerIn.hasEffect(MobEffects.POISON)) continue;
                playerIn.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0, false, false));
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (((Boolean)InventoryPetsConfig.disableCobblestone.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.stone.get(), SoundSource.PLAYERS, 0.2f, 1.0f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (!(worldIn.isClientSide || playerIn.isCrouching() || itemstack.getDamageValue() != 0 && !playerIn.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue())) {
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk = playerIn.getInventory().getItem(i);
                if (itemchk != ItemStack.EMPTY && itemchk.getItem() == InventoryPets.PET_COBBLESTONE.get() && itemchk.getDamageValue() == 0) {
                    for (int m = 0; m < 50; ++m) {
                        BlockHitResult raytraceresult = petCobblestone.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
                        if (raytraceresult == null || raytraceresult.getType() != HitResult.Type.BLOCK) continue;
                        BlockHitResult blockraytraceresult = raytraceresult;
                        BlockPos blockpos = blockraytraceresult.getBlockPos();
                        Direction direction = blockraytraceresult.getDirection();
                        BlockPos blockpos1 = blockpos.offset(direction.getNormal());
                        if (!worldIn.mayInteract(playerIn, blockpos) || !playerIn.mayInteract(worldIn, blockpos1) || worldIn.getBlockState(blockpos).getBlock() == Blocks.AIR) continue;
                        int ii = blockpos.getX();
                        int j = blockpos.getY();
                        int k = blockpos.getZ();
                        if (worldIn.getBlockState(blockpos).getBlock() == Blocks.DIRT || worldIn.getBlockState(blockpos).getBlock() instanceof GrassBlock) {
                            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                            }
                            for (int i1 = -10; i1 < 10; ++i1) {
                                for (int j1 = -3; j1 < 3; ++j1) {
                                    for (int k1 = -10; k1 < 10; ++k1) {
                                        if (worldIn.getBlockState(new BlockPos(ii + i1, j + j1, k + k1)).getBlock() != Blocks.DIRT && !(worldIn.getBlockState(new BlockPos(ii + i1, j + j1, k + k1)).getBlock() instanceof GrassBlock)) continue;
                                        worldIn.setBlockAndUpdate(new BlockPos(ii + i1, j + j1, k + k1), Blocks.COBBLESTONE.defaultBlockState());
                                    }
                                }
                            }
                            RandomPoolAlias rand = new RandomPoolAlias();
                            Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(4));
                            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.dirt.get(), SoundSource.PLAYERS, 1.0f, 0.8f + pitchAdj.floatValue());
                            if (playerIn.isCreative()) {
                                return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                            }
                            m = 200;
                            return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                        }
                        i = 9;
                    }
                    continue;
                }
                if (itemstack.getDamageValue() == 0) continue;
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.stone.get(), SoundSource.PLAYERS, 1.0f, 0.4f);
            }
        } else if (!worldIn.isClientSide && playerIn.isCrouching() && this.useDelay > 50 && (itemstack.getDamageValue() == 0 || playerIn.isCreative() || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue())) {
            this.useDelay = 0;
            if (((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !playerIn.isCreative()) {
                itemstack.setDamageValue(itemstack.getDamageValue() + 1);
            }
            RandomPoolAlias rand = new RandomPoolAlias();
            int bob = rand.nextInt(11);
            AABB range = new AABB(playerIn.getX() - 96.0, playerIn.getY() - 96.0, playerIn.getZ() - 96.0, playerIn.getX() + 96.0, playerIn.getY() + 96.0, playerIn.getZ() + 96.0);
            List entities = worldIn.getEntitiesOfClass(Player.class, range);
            int esize = entities.size();
            boolean foundPlayer = false;
            for (int k = 0; k <= esize - 1; ++k) {
                int hSize;
                ItemStack hChk;
                int h;
                Entity entity = (Entity)entities.get(k);
                if (entity == null || !(entity instanceof Player)) continue;
                Player chkPlayer = (Player)entity;
                double xt = entity.getX();
                double yt = entity.getY();
                double zt = entity.getZ();
                int x1 = Mth.floor((double)playerIn.getX());
                int y1 = Mth.floor((double)playerIn.getY());
                int z1 = Mth.floor((double)playerIn.getZ());
                double x2 = xt - (double)x1;
                double y2 = yt - (double)y1;
                double z2 = zt - (double)z1;
                boolean hasCobble = false;
                boolean hasDirt = false;
                for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                    ItemStack itemchk = chkPlayer.getInventory().getItem(i);
                    if (itemchk != ItemStack.EMPTY && itemchk.getItem() == InventoryPets.PET_COBBLESTONE.get()) {
                        hasCobble = true;
                    }
                    if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_DIRT.get() || itemchk.getDamageValue() != 0) continue;
                    hasDirt = true;
                }
                if (hasCobble || !hasDirt) continue;
                foundPlayer = true;
                Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(4));
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (Holder)SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 1.4f, 1.0f + pitchAdj.floatValue());
                pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(4));
                worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (Holder)SoundEvents.NOTE_BLOCK_CHIME, SoundSource.PLAYERS, 1.4f, 1.0f + pitchAdj.floatValue());
                if (Mth.abs((int)((int)x2)) >= 50 || Mth.abs((int)((int)z2)) >= 50 || Mth.abs((int)((int)y2)) >= 50) continue;
                if (bob == 0) {
                    pitchAdj = Float.valueOf(rand.nextFloat());
                    int chooser = rand.nextInt(3);
                    if (chooser == 0) {
                        worldIn.playSound(null, chkPlayer.getX(), chkPlayer.getY(), chkPlayer.getZ(), ModSoundEvents.trololo.get(), SoundSource.PLAYERS, 1.2f, 1.0f + pitchAdj.floatValue());
                        continue;
                    }
                    if (chooser == 1) {
                        worldIn.playSound(null, chkPlayer.getX(), chkPlayer.getY(), chkPlayer.getZ(), ModSoundEvents.trololo1.get(), SoundSource.PLAYERS, 1.2f, 1.0f + pitchAdj.floatValue());
                        continue;
                    }
                    worldIn.playSound(null, chkPlayer.getX(), chkPlayer.getY(), chkPlayer.getZ(), ModSoundEvents.trololo2.get(), SoundSource.PLAYERS, 1.2f, 1.0f + pitchAdj.floatValue());
                    continue;
                }
                if (bob == 1) {
                    chkPlayer.setRemainingFireTicks(200);
                    continue;
                }
                if (bob == 2) {
                    worldIn.setBlockAndUpdate(chkPlayer.blockPosition().above(2), Blocks.WATER.defaultBlockState());
                    continue;
                }
                if (bob == 3) {
                    boolean hasWeapons = false;
                    boolean blockMade = false;
                    if (worldIn.getBlockState(chkPlayer.blockPosition().above(3)).getBlock() == Blocks.AIR || worldIn.getBlockState(chkPlayer.blockPosition().above(4)).getBlock() == Blocks.AIR) {
                        for (h = 0; h <= ItemHelper.getHotbarSize() - 1; ++h) {
                            ItemStack hChk2 = chkPlayer.getInventory().getItem(h);
                            if (hChk2 == ItemStack.EMPTY || !(hChk2.getItem() instanceof TieredItem) && !(hChk2.getItem() instanceof SwordItem)) continue;
                            if (!blockMade) {
                                worldIn.setBlockAndUpdate(chkPlayer.blockPosition().above(3), Blocks.COBBLESTONE.defaultBlockState());
                                blockMade = true;
                            }
                            ItemEntity entityitem = new ItemEntity(worldIn, chkPlayer.getX(), chkPlayer.getY() + 4.5, chkPlayer.getZ(), hChk2);
                            entityitem.lerpMotion(0.0, 0.0, 0.0);
                            worldIn.addFreshEntity((Entity)entityitem);
                            this.removeItem(chkPlayer, hChk2);
                            hasWeapons = true;
                        }
                    }
                    if (hasWeapons) continue;
                    entity.hurt(worldIn.damageSources().cactus(), 2.0f);
                    continue;
                }
                if (bob == 4) {
                    Block dirtChk1 = playerIn.level().getBlockState(new BlockPos(Mth.floor((double)playerIn.getX()), Mth.floor((double)playerIn.getY()) - 1, Mth.floor((double)playerIn.getZ()))).getBlock();
                    if (dirtChk1 != null && (dirtChk1 == Blocks.SMOOTH_STONE || dirtChk1 == Blocks.COBBLESTONE)) {
                        entity.hurt(worldIn.damageSources().cactus(), 8.0f);
                        continue;
                    }
                    entity.hurt(worldIn.damageSources().cactus(), 2.0f);
                    continue;
                }
                if (bob == 5) {
                    ItemStack[] armors = new ItemStack[4];
                    for (int j1 = 0; j1 < chkPlayer.getInventory().armor.size(); ++j1) {
                        armors[j1] = chkPlayer.getInventory().getArmor(j1);
                        chkPlayer.getInventory().armor.set(j1, (Object)ItemStack.EMPTY);
                        if (armors[j1] != null && armors[j1].getItem() != Blocks.COBBLESTONE.asItem()) {
                            ItemEntity entityitem3 = new ItemEntity(chkPlayer.level(), chkPlayer.getX() + 0.5, chkPlayer.getY() + 0.5, chkPlayer.getZ() + 0.5, armors[j1]);
                            worldIn.addFreshEntity((Entity)entityitem3);
                            continue;
                        }
                        if (armors[j1] == null || armors[j1].getItem() != Blocks.COBBLESTONE.asItem()) continue;
                        chkPlayer.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1, false, false));
                    }
                    ItemStack head = new ItemStack((ItemLike)Blocks.COBBLESTONE, 1);
                    chkPlayer.getInventory().armor.set(3, (Object)head);
                    continue;
                }
                if (bob == 6) {
                    hChk = ItemStack.EMPTY;
                    boolean hasWood = false;
                    chkPlayer.getInventory();
                    for (h = 0; h <= 35; ++h) {
                        hChk = chkPlayer.getInventory().getItem(h);
                        if (hChk == ItemStack.EMPTY || hChk.getItem() != Blocks.OAK_LOG.asItem() && hChk.getItem() != Blocks.OAK_PLANKS.asItem() && hChk.getItem() != Items.STICK) continue;
                        hSize = hChk.getCount();
                        this.removeItem(chkPlayer, hChk);
                        chkPlayer.getInventory().armor.set(h, (Object)new ItemStack((ItemLike)Blocks.OAK_BUTTON, hSize));
                        hasWood = true;
                    }
                    if (hasWood) continue;
                    chkPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1, false, false));
                    continue;
                }
                if (bob == 7) {
                    hChk = ItemStack.EMPTY;
                    boolean hasStone = false;
                    chkPlayer.getInventory();
                    for (h = 0; h <= 35; ++h) {
                        hChk = chkPlayer.getInventory().getItem(h);
                        if (hChk == ItemStack.EMPTY || hChk.getItem() != Blocks.COBBLESTONE.asItem() && hChk.getItem() != Blocks.STONE.asItem()) continue;
                        hSize = (int)Math.floor(hChk.getCount() / 2);
                        if (hSize == 0) {
                            this.removeItem(chkPlayer, hChk);
                        } else {
                            chkPlayer.getInventory().armor.set(h, (Object)new ItemStack((ItemLike)hChk.getItem(), hSize));
                        }
                        hasStone = true;
                    }
                    if (hasStone) continue;
                    chkPlayer.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 70, 1, false, false));
                    continue;
                }
                if (bob == 8) {
                    Level.ExplosionInteraction explosion$mode = Level.ExplosionInteraction.MOB;
                    if (worldIn.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        explosion$mode = Level.ExplosionInteraction.NONE;
                    }
                    worldIn.explode((Entity)playerIn, chkPlayer.getX(), chkPlayer.getY() + 1.0, chkPlayer.getZ(), 0.5f, worldIn.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING), explosion$mode);
                    continue;
                }
                if (bob == 9) {
                    chkPlayer.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 1, false, false));
                    chkPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 1, false, false));
                    continue;
                }
                if (bob != 10) continue;
                chkPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1, false, false));
            }
            if (!foundPlayer) {
                playerIn.sendSystemMessage((Component)Component.translatable((String)"info.dirt.notfound"));
                playerIn.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 120, 1, false, false));
                playerIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1, false, false));
                playerIn.setRemainingFireTicks(200);
            }
        } else if (!worldIn.isClientSide && playerIn.isCrouching() && this.useDelay <= 50 && (itemstack.getDamageValue() == 0 || playerIn.isCreative() || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue())) {
            RandomPoolAlias rand = new RandomPoolAlias();
            Float pitchAdj = Float.valueOf(rand.nextFloat() * (float)rand.nextInt(4));
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), (Holder)SoundEvents.NOTE_BLOCK_PLING, SoundSource.PLAYERS, 0.5f, 0.5f + pitchAdj.floatValue());
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

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcobblestone1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcobblestone2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petcobblestone3", (Object[])new Object[0]))));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodCobblestone.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.GRASS_BLOCK.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Blocks.DIRT.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.youtuber", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.approvedby", (Object[])new Object[0]) + " SSundee")));
        if (((Boolean)InventoryPetsConfig.disableCobblestone.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

