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
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.LivingEntity
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
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  net.neoforged.neoforge.capabilities.Capabilities$ItemHandler
 *  net.neoforged.neoforge.items.IItemHandler
 */
package com.inventorypets.pets;

import com.inventorypets.InventoryPets;
import com.inventorypets.config.InventoryPetsConfig;
import com.inventorypets.entities.AnvilPetEntity;
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

public class petAnvil
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean odFlag = false;
    private boolean hbFlag = false;
    private int chkEat = 0;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.IRON_NUGGET;

    public petAnvil(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player entityplayer) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(entityplayer.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        Item petFood;
        if (!(entityIn instanceof Player)) {
            return;
        }
        Player entityplayer = (Player)entityIn;
        if (!Inventory.isHotbarSlot((int)slot)) {
            return;
        }
        if ((Integer)InventoryPetsConfig.petEatTimerFactor.get() <= 0) {
            return;
        }
        if (this.newFlag && (Integer)InventoryPetsConfig.petEatTimerFactor.get() > 0 && !this.isDamaged(stack)) {
            this.ticktime = 30 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 30 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
            this.newFlag = false;
        }
        if ((petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodAnvil.get())))) != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        ItemStack petchk = entityplayer.getInventory().getSelected();
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_ANVIL.get()) {
            this.petNamer(entityplayer);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        if (((Boolean)InventoryPetsConfig.disableAnvil.get()).booleanValue()) {
            return;
        }
        if (!entityplayer.level().isClientSide) {
            for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                ItemStack itemchk2;
                ItemStack itemchk = entityplayer.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_ANVIL.get() || itemchk.getDamageValue() != 0 || this.ticktime >= 10) continue;
                for (int j = 0; j < 36; ++j) {
                    itemchk2 = entityplayer.getInventory().getItem(j);
                    if (itemchk2 == ItemStack.EMPTY || !itemchk2.isRepairable() || !itemchk2.isDamaged() || itemchk2.getDescriptionId().contains("pet_") || itemchk2.getDescriptionId().contains("_canteen") || itemchk2.getDescriptionId().contains("chisel")) continue;
                    itemchk2.setDamageValue(itemchk2.getDamageValue() - 30);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.anvil.get(), SoundSource.PLAYERS, 0.1f, 1.2f);
                    if (entityplayer.isCreative()) continue;
                    this.eatFlag = false;
                }
                for (int j1 = 0; j1 <= 3; ++j1) {
                    itemchk2 = entityplayer.getInventory().getArmor(j1);
                    if (itemchk2 == ItemStack.EMPTY || !itemchk2.isRepairable() || !itemchk2.isDamaged() || itemchk2.getDescriptionId().contains("_pet")) continue;
                    itemchk2.setDamageValue(itemchk2.getDamageValue() - 30);
                    entityplayer.containerMenu.broadcastChanges();
                    ItemStack itemchk3 = itemchk2.copy();
                    this.removeItem(entityplayer, itemchk2);
                    entityplayer.getInventory().armor.set(j1, (Object)itemchk3);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.anvil.get(), SoundSource.PLAYERS, 0.1f, 1.2f);
                    if (entityplayer.isCreative()) continue;
                    this.eatFlag = false;
                }
            }
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
            ItemStack itemchk = entityplayer.getInventory().getItem(i);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_ANVIL.get()) continue;
            slotNo = i;
        }
        if (!(entityplayer.isCreative() || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 20 || slotNo >= 10 || worldIn.isClientSide || petFood == Items.AIR)) {
            this.eatFlag = false;
            block4: for (int k = 0; k < 36; ++k) {
                IItemHandler handler;
                ItemStack s2 = entityplayer.getInventory().getItem(k);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    this.odFlag = false;
                    if (!this.customFood) {
                        if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            if (s3.is(Items.IRON_INGOT)) {
                                this.odFlag = true;
                            }
                        } else if (s3.is(Items.IRON_NUGGET)) {
                            this.odFlag = true;
                        }
                    }
                    if ((s3.getItem() != petFood || !this.customFood) && (!this.odFlag || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                    this.eatFlag = true;
                    RandomPoolAlias rand = new RandomPoolAlias();
                    this.ticktime = rand.nextInt(30 * (Integer)InventoryPetsConfig.petEatTimerFactor.get()) + 30 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block4;
                }
            }
        }
        if (!entityplayer.isCreative() && ((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() && !worldIn.isClientSide) {
            int i;
            ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                hotbar[i] = entityplayer.getInventory().getItem(i);
            }
            this.hbFlag = false;
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                int dmg;
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_ANVIL.get() || this.chkEat <= 20 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                this.eatFlag = false;
                for (int k = 0; k < 36; ++k) {
                    ItemStack s2 = entityplayer.getInventory().getItem(k);
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR) continue;
                    this.odFlag = false;
                    if (!this.customFood) {
                        if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                            if (s2.is(Items.IRON_INGOT)) {
                                this.odFlag = true;
                            }
                        } else if (s2.is(Items.IRON_NUGGET)) {
                            this.odFlag = true;
                        }
                    }
                    if ((s2.getItem() != petFood || !this.customFood) && (!this.odFlag || this.customFood)) continue;
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(entityplayer, s2);
                    }
                    this.setDamage(hotbar[i], 0);
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.8f);
                    this.eatFlag = true;
                    break;
                }
                if (this.eatFlag || hotbar[i] == ItemStack.EMPTY || (dmg = hotbar[i].getDamageValue()) != 0) continue;
                hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                if (hotbar[i].getDamageValue() == 1) {
                    worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.anvil_break.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
                }
                this.eatFlag = true;
            }
            if (this.hbFlag) {
                this.chkEat = 0;
                RandomPoolAlias rand = new RandomPoolAlias();
                this.ticktime = rand.nextInt(30 * (Integer)InventoryPetsConfig.petEatTimerFactor.get()) + 30 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        block10: {
            int esize;
            List entities;
            boolean lpFlag;
            block9: {
                ItemStack stack = playerIn.getItemInHand(handIn);
                if (worldIn.isClientSide || ((Boolean)InventoryPetsConfig.disableAnvil.get()).booleanValue()) {
                    return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                }
                BlockHitResult movingobjectposition = petAnvil.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
                lpFlag = false;
                AABB range = new AABB(playerIn.getX() - 25.0, playerIn.getY() - 25.0, playerIn.getZ() - 25.0, playerIn.getX() + 25.0, playerIn.getY() + 25.0, playerIn.getZ() + 25.0);
                entities = worldIn.getEntitiesOfClass(AnvilPetEntity.class, range);
                esize = entities.size();
                for (int k = 0; k <= esize - 1; ++k) {
                    AnvilPetEntity chkLP;
                    Entity entity = (Entity)entities.get(k);
                    if (entity == null || !(entity instanceof AnvilPetEntity) || (chkLP = (AnvilPetEntity)entity).getOwnerId() != playerIn.getUUID()) continue;
                    lpFlag = true;
                    break;
                }
                if (movingobjectposition == null) {
                    return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                }
                BlockPos blockpos = new BlockPos((int)movingobjectposition.getLocation().x, (int)movingobjectposition.getLocation().y, (int)movingobjectposition.getLocation().z);
                if (movingobjectposition.getType() != HitResult.Type.BLOCK || lpFlag) break block9;
                if (!worldIn.mayInteract(playerIn, blockpos)) {
                    return InteractionResultHolder.pass((Object)playerIn.getItemInHand(handIn));
                }
                Player entityplayer = playerIn;
                AnvilPetEntity entityB1 = new AnvilPetEntity(InventoryPets.ANVIL_PET_ENTITY.get(), worldIn);
                entityB1.setPos(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                worldIn.addFreshEntity((Entity)entityB1);
                if (stack.has(DataComponents.CUSTOM_NAME)) {
                    entityB1.setCustomName(stack.getDisplayName());
                }
                entityB1.setOwnerId(entityplayer.getUUID());
                worldIn.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), ModSoundEvents.boing.get(), SoundSource.PLAYERS, 0.4f, 1.2f);
                if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
                    entityplayer.sendSystemMessage((Component)Component.translatable((String)"info.ip.livinganvilinteracthc", (Object[])new Object[0]));
                } else {
                    entityplayer.sendSystemMessage((Component)Component.translatable((String)"info.ip.livinganvilinteract", (Object[])new Object[0]));
                }
                if (entityB1 == null || !(entityB1 instanceof LivingEntity) || !stack.has(DataComponents.CUSTOM_NAME)) break block10;
                entityB1.setCustomName(stack.getDisplayName());
                break block10;
            }
            if (lpFlag) {
                for (int k = 0; k <= esize - 1; ++k) {
                    AnvilPetEntity chkLP;
                    Entity entity = (Entity)entities.get(k);
                    if (entity == null || !(entity instanceof AnvilPetEntity) || (chkLP = (AnvilPetEntity)entity).getOwnerId() != playerIn.getUUID()) continue;
                    chkLP.remove(Entity.RemovalReason.DISCARDED);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.boing.get(), SoundSource.PLAYERS, 0.4f, 0.5f);
                    break;
                }
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

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petanvil1", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petanvil2", (Object[])new Object[0]))));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petanvil3", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodAnvil.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.IRON_INGOT.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.IRON_NUGGET.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GOLD) + I18n.get((String)"tooltip.ip.utility", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.disableAnvil.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

