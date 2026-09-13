/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.npc.villager.Villager
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.Item$TooltipContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
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
import com.inventorypets.entities.BedPetEntity;
import com.inventorypets.entities.MiniQuantumBlazeEntity;
import com.inventorypets.entities.MiniQuantumEndermanEntity;
import com.inventorypets.events.KeyHandler;
import com.inventorypets.helper.AdvancementHelper;
import com.inventorypets.helper.ProxyHelper;
import com.inventorypets.init.ModSoundEvents;
import com.inventorypets.screens.PetNamerScreen;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petBed
extends Item {
    private int useDelay = 0;
    private boolean eatFlag;
    private boolean complainFlag = false;
    private int chkEat = 0;
    private boolean customFood = false;
    private Item defaultFood = Items.WHITE_WOOL;

    public petBed(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        ItemStack s2;
        int k;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_BED.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBed.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableBed.get()).booleanValue()) {
            return;
        }
        ++this.useDelay;
        ++this.chkEat;
        if (!entityplayer.isCreative() && stack.getDamageValue() >= 2 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40 && !worldIn.isClientSide && petFood != Items.AIR) {
            this.eatFlag = false;
            block0: for (k = 0; k < 36; ++k) {
                IItemHandler handler;
                s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Blocks.WHITE_WOOL.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Blocks.OAK_LOG.asItem() || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    if (this.customFood && !this.eatFlag) {
                        handler.extractItem(l, 1, false);
                    } else if (!this.eatFlag) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                    this.eatFlag = true;
                    continue block0;
                }
            }
        }
        if (!worldIn.isClientSide && !entityplayer.isCreative() && stack.getDamageValue() >= 2 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && this.chkEat > 40) {
            this.chkEat = 0;
            this.eatFlag = false;
            for (k = 0; k < 36; ++k) {
                int dmg;
                s2 = entityplayer.getInventory().getItem(k);
                if (!s2.isEmpty() && !this.eatFlag && petFood != Items.AIR && (s2.getItem() == petFood && this.customFood || s2.getItem() == Blocks.WHITE_WOOL.asItem() && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood || s2.getItem() == Blocks.OAK_LOG.asItem() && ((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() && !this.customFood)) {
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(entityplayer, s2);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                    this.complainFlag = false;
                    this.eatFlag = true;
                }
                if (this.eatFlag || (dmg = stack.getDamageValue()) != 0) continue;
                stack.setDamageValue(stack.getDamageValue() + 1);
                this.eatFlag = true;
                this.complainFlag = false;
            }
            if (!this.complainFlag && this.chkEat > 40) {
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.bed_yawn.get(), SoundSource.PLAYERS, 0.5f, 1.6f);
                this.complainFlag = true;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack;
        block26: {
            int esize;
            List entities;
            boolean lpFlag;
            Player entityplayer;
            Level worldObj;
            block27: {
                BlockHitResult movingobjectposition;
                block25: {
                    worldObj = worldIn;
                    entityplayer = playerIn;
                    itemstack = playerIn.getItemInHand(handIn);
                    if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
                        return InteractionResultHolder.fail((Object)itemstack);
                    }
                    if (((Boolean)InventoryPetsConfig.disableBed.get()).booleanValue()) {
                        return InteractionResultHolder.fail((Object)itemstack);
                    }
                    if (worldObj.isClientSide || itemstack.getDamageValue() >= 2 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || entityplayer.isCrouching()) break block25;
                    if (this.useDelay <= 20) break block26;
                    boolean sleepFlag = true;
                    boolean mobFlag = false;
                    BlockPos pos = entityplayer.blockPosition();
                    AABB range = new AABB(entityplayer.getX() - 16.0, entityplayer.getY() - 8.0, entityplayer.getZ() - 16.0, entityplayer.getX() + 16.0, entityplayer.getY() + 8.0, entityplayer.getZ() + 16.0);
                    List entities2 = worldIn.getEntitiesOfClass(Monster.class, range);
                    if (!entities2.isEmpty()) {
                        mobFlag = true;
                        int mobtotals = entities2.size();
                        List mqbs = worldIn.getEntitiesOfClass(MiniQuantumBlazeEntity.class, range);
                        List mqes = worldIn.getEntitiesOfClass(MiniQuantumEndermanEntity.class, range);
                        int friendtotals = mqbs.size() + mqes.size();
                        if (mobtotals == friendtotals) {
                            mobFlag = false;
                        }
                    }
                    if (!worldIn.dimensionType().natural()) {
                        sleepFlag = false;
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"block.bed.nope", (Object[])new Object[0]));
                    } else if (worldIn.isDay()) {
                        sleepFlag = false;
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"block.minecraft.bed.no_sleep", (Object[])new Object[0]));
                    } else if (mobFlag) {
                        sleepFlag = false;
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"block.minecraft.bed.not_safe", (Object[])new Object[0]));
                    } else if (this.checkPlayersSleeping(worldObj, pos)) {
                        sleepFlag = false;
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"tile.bed.allPlayersNotSleeping", (Object[])new Object[0]));
                    } else if (!sleepFlag) {
                        entityplayer.sendSystemMessage((Component)Component.translatable((String)"block.bed.nope", (Object[])new Object[0]));
                    }
                    if (sleepFlag) {
                        ServerPlayer serverEntity = (ServerPlayer)entityplayer;
                        ServerLevel ServerLevel2 = (ServerLevel)worldIn;
                        serverEntity.setRespawnPosition(worldIn.dimension(), pos, entityplayer.getYRot(), false, true);
                        ServerLevel2.updateSleepingPlayerList();
                        entityplayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 2, false, false));
                        ProxyHelper.Sleep(entityplayer);
                        if (!entityplayer.isCreative()) {
                            itemstack.setDamageValue(itemstack.getDamageValue() + 1);
                        }
                        if (!AdvancementHelper.hasAdvancement(serverEntity, Identifier.fromNamespaceAndPath((String)"minecraft", (String)"adventure/sleep_in_bed"))) {
                            AdvancementHelper.unlockAdvancement(serverEntity, Identifier.fromNamespaceAndPath((String)"minecraft", (String)"adventure/sleep_in_bed"));
                        }
                    }
                    break block26;
                }
                if (worldObj.isClientSide || itemstack.getDamageValue() >= 2 && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || !entityplayer.isCrouching() || itemstack.getDamageValue() != 0) break block26;
                lpFlag = false;
                AABB range = new AABB(entityplayer.getX() - 25.0, entityplayer.getY() - 25.0, entityplayer.getZ() - 25.0, entityplayer.getX() + 25.0, entityplayer.getY() + 25.0, entityplayer.getZ() + 25.0);
                entities = worldObj.getEntitiesOfClass(BedPetEntity.class, range);
                esize = entities.size();
                for (int k = 0; k <= esize - 1; ++k) {
                    BedPetEntity chkLP;
                    Entity entity = (Entity)entities.get(k);
                    if (entity == null || !(entity instanceof BedPetEntity) || (chkLP = (BedPetEntity)entity).getOwnerId() != entityplayer.getUUID()) continue;
                    lpFlag = true;
                    break;
                }
                if ((movingobjectposition = petBed.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY)) == null) {
                    return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                }
                BlockPos blockpos = new BlockPos((int)movingobjectposition.getLocation().x, (int)movingobjectposition.getLocation().y, (int)movingobjectposition.getLocation().z);
                if (movingobjectposition.getType() != HitResult.Type.BLOCK || lpFlag) break block27;
                if (!worldObj.mayInteract(entityplayer, blockpos)) {
                    return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                }
                BedPetEntity entityB1 = new BedPetEntity(InventoryPets.BED_PET_ENTITY.get(), worldObj);
                entityB1.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                worldObj.addFreshEntity((Entity)entityB1);
                if (itemstack.has(DataComponents.CUSTOM_NAME)) {
                    entityB1.setCustomName(itemstack.getDisplayName());
                }
                entityB1.setOwnerId(entityplayer.getUUID());
                if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    entityplayer.sendSystemMessage((Component)Component.translatable((String)"info.ip.livingbedinteracthc", (Object[])new Object[0]));
                } else {
                    entityplayer.sendSystemMessage((Component)Component.translatable((String)"info.ip.livingbedinteract", (Object[])new Object[0]));
                }
                worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.4f, 1.2f);
                if (entityB1 == null || !(entityB1 instanceof LivingEntity) || !itemstack.has(DataComponents.CUSTOM_NAME)) break block26;
                entityB1.setCustomName(itemstack.getDisplayName());
                break block26;
            }
            if (lpFlag) {
                for (int k = 0; k <= esize - 1; ++k) {
                    BedPetEntity chkLP;
                    Entity entity = (Entity)entities.get(k);
                    if (entity == null || !(entity instanceof BedPetEntity) || (chkLP = (BedPetEntity)entity).getOwnerId() != entityplayer.getUUID()) continue;
                    chkLP.discard();
                    worldObj.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.4f, 0.5f);
                    break;
                }
            }
        }
        if (itemstack.getDamageValue() >= 2) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.bed_yawn.get(), SoundSource.PLAYERS, 0.5f, 1.6f);
        }
        return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
    }

    private boolean checkPlayersSleeping(Level world, BlockPos blockPos) {
        List list = world.getEntitiesOfClass(Villager.class, new AABB(blockPos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        }
        ((Villager)list.get(0)).stopSleeping();
        return true;
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petbed1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petbed2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodBed.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + Blocks.WHITE_WOOL.getName().getString())));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + Blocks.OAK_LOG.getName().getString())));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableBed.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

