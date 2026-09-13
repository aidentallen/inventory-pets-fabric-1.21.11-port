/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.entity.animal.wolf.Wolf
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.skeleton.Skeleton
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
 *  net.minecraft.world.phys.Vec3
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
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.items.IItemHandler;

public class petWolf
extends Item {
    private int ticktime;
    private boolean eatFlag = false;
    private boolean hbFlag;
    private int chkEat = 0;
    private int useDelay = 60;
    private boolean wwFlag;
    private boolean wolfAlarm = false;
    private int barkCounter = 0;
    private String bob;
    private boolean foundFlag = false;
    private boolean newFlag = true;
    private boolean customFood = false;
    private Item defaultFood = Items.BONE;

    public petWolf(Item.Properties properties) {
        super(properties);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void petNamer(Player playerIn) {
        Minecraft.getInstance().setScreen((Screen)new PetNamerScreen(playerIn.getInventory()));
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int slot, boolean par5) {
        int i;
        int k;
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
        if (KeyHandler.nflag && worldIn.isClientSide && petchk != ItemStack.EMPTY && petchk.getItem() == InventoryPets.PET_WOLF.get()) {
            this.petNamer(playerIn);
            KeyHandler.nflag = false;
        } else if (KeyHandler.nflag && petchk == ItemStack.EMPTY) {
            KeyHandler.nflag = false;
        }
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodWolf.get())));
        if (petFood != this.defaultFood && petFood != Items.AIR) {
            this.customFood = true;
        }
        if (((Boolean)InventoryPetsConfig.disableWolf.get()).booleanValue()) {
            return;
        }
        ++this.useDelay;
        ItemStack chickchk = playerIn.getInventory().getItem(slot);
        if (chickchk.getItem() == InventoryPets.PET_WOLF.get() && slot < 10 && chickchk.getDamageValue() == 0) {
            this.hbFlag = true;
        }
        if (this.hbFlag) {
            AABB range = new AABB(playerIn.getX() - 25.0, playerIn.getY() - 25.0, playerIn.getZ() - 25.0, playerIn.getX() + 25.0, playerIn.getY() + 25.0, playerIn.getZ() + 25.0);
            List entities = worldIn.getEntitiesOfClass(Skeleton.class, range);
            int esize = entities.size();
            for (k = 0; k <= esize - 1; ++k) {
                Entity entity = (Entity)entities.get(k);
                if (!(entity instanceof Skeleton)) continue;
                Skeleton entityskeleton = (Skeleton)entity;
                entityskeleton.setTarget(null);
                Vec3 vec3d = DefaultRandomPos.getPosAway((PathfinderMob)entityskeleton, (int)20, (int)7, (Vec3)new Vec3(playerIn.getX(), playerIn.getY(), playerIn.getZ()));
                if (vec3d == null || !(playerIn.distanceToSqr(vec3d.x, vec3d.y, vec3d.z) >= playerIn.distanceToSqr((Entity)entityskeleton))) continue;
                entityskeleton.getNavigation().moveTo(vec3d.x, vec3d.y, vec3d.z, 2.0);
            }
        }
        ++this.chkEat;
        --this.ticktime;
        int slotNo = 10;
        for (int i2 = 0; i2 <= ItemHelper.getHotbarSize() - 1; ++i2) {
            ItemStack itemchk = playerIn.getInventory().getItem(i2);
            if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_WOLF.get()) continue;
            slotNo = i2;
        }
        if (!(playerIn.isCreative() || stack.getDamageValue() <= 0 && this.ticktime > 0 || !((Boolean)InventoryPetsConfig.petsMustEat.get()).booleanValue() || this.chkEat <= 40 || worldIn.isClientSide || slotNo >= 10 || petFood == Items.AIR)) {
            this.eatFlag = false;
            block2: for (int k2 = 0; k2 < 36; ++k2) {
                IItemHandler handler;
                ItemStack s2 = playerIn.getInventory().getItem(k2);
                if (s2 == ItemStack.EMPTY || s2.getItem() != InventoryPets.FEED_BAG.get() || this.eatFlag || (handler = (IItemHandler)s2.getCapability(Capabilities.ItemHandler.ITEM)) == null) continue;
                int invsize = 18;
                for (int l = 0; l < invsize; ++l) {
                    ItemStack s3 = handler.getStackInSlot(l);
                    if (!(s3.getItem() == petFood && this.customFood || s3.getItem() == Items.BONE && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false && !this.customFood) && (s3.getItem() != Items.DIAMOND || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue() || this.customFood)) continue;
                    handler.extractItem(l, 1, false);
                    if (s3.getCount() <= 0) {
                        handler.extractItem(l, 1, false);
                    }
                    this.setDamage(stack, 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.9f);
                    this.eatFlag = true;
                    this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                    continue block2;
                }
            }
        }
        if (!playerIn.isCreative() && (Boolean)InventoryPetsConfig.petsMustEat.get() & !worldIn.isClientSide) {
            ItemStack[] hotbar = new ItemStack[ItemHelper.getHotbarSize() + 1];
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                hotbar[i] = playerIn.getInventory().getItem(i);
            }
            this.hbFlag = false;
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                int dmg;
                if (hotbar[i] == ItemStack.EMPTY || hotbar[i].getItem() != InventoryPets.PET_WOLF.get() || this.chkEat <= 40 || this.ticktime > 0 && hotbar[i].getDamageValue() <= 0) continue;
                if (this.ticktime <= 0) {
                    this.hbFlag = true;
                }
                if (i >= slotNo) {
                    this.chkEat = 0;
                }
                this.eatFlag = false;
                for (k = 0; k < 36; ++k) {
                    ItemStack s2 = playerIn.getInventory().getItem(k);
                    if (s2.isEmpty() || this.eatFlag || petFood == Items.AIR || !(s2.getItem() == petFood && this.customFood || s2.getItem() == Items.BONE && (Boolean)InventoryPetsConfig.petsEatWholeItems.get() == false & !this.customFood) && (s2 == ItemStack.EMPTY || s2.getItem() != Items.DIAMOND || !((Boolean)InventoryPetsConfig.petsEatWholeItems.get() & !this.customFood))) continue;
                    s2.shrink(1);
                    if (s2.getCount() == 0) {
                        this.removeItem(playerIn, s2);
                    }
                    this.setDamage(hotbar[i], 0);
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.nomnom.get(), SoundSource.PLAYERS, 0.5f, 0.9f);
                    this.eatFlag = true;
                }
                if (this.eatFlag || hotbar[i] == ItemStack.EMPTY || (dmg = hotbar[i].getDamageValue()) != 0) continue;
                hotbar[i].setDamageValue(hotbar[i].getDamageValue() + 1);
                if (hotbar[i].getDamageValue() == 1) {
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.howl.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                }
                this.eatFlag = true;
            }
            if (this.hbFlag) {
                this.chkEat = 0;
                this.ticktime = 60 * (Integer)InventoryPetsConfig.petEatTimerFactor.get() + 80 * (Integer)InventoryPetsConfig.petEatTimerFactor.get();
                this.hbFlag = false;
            }
        }
        --this.barkCounter;
        if (this.wwFlag && this.barkCounter <= 0) {
            this.barkCounter = 300;
            String wolfName = "";
            for (i = 0; i <= ItemHelper.getHotbarSize() - 1; ++i) {
                Entity entity;
                int k3;
                ItemStack itemchk = playerIn.getInventory().getItem(i);
                if (itemchk == ItemStack.EMPTY || itemchk.getItem() != InventoryPets.PET_WOLF.get() || itemchk.getDamageValue() != 0 || !this.wolfAlarm || !this.wwFlag) continue;
                this.wwFlag = false;
                AABB range = new AABB(playerIn.getX() - 25.0, playerIn.getY() - 25.0, playerIn.getZ() - 25.0, playerIn.getX() + 25.0, playerIn.getY() + 25.0, playerIn.getZ() + 25.0);
                List entities = worldIn.getEntitiesOfClass(Entity.class, range);
                int esize = entities.size();
                for (k3 = 0; k3 <= esize - 1; ++k3) {
                    Wolf chkWolf;
                    entity = (Entity)entities.get(k3);
                    if (entity == null || !(entity instanceof Wolf) || (chkWolf = (Wolf)entity).getOwner() != playerIn) continue;
                    if (chkWolf.hasCustomName()) {
                        wolfName = ItemHelper.nameFixer(chkWolf.getCustomName().getString());
                    }
                    this.wwFlag = true;
                }
                if (!this.wwFlag) continue;
                this.foundFlag = false;
                for (k3 = 0; k3 <= esize - 1; ++k3) {
                    entity = (Entity)entities.get(k3);
                    int xdist = (int)Math.abs(entity.getX() - playerIn.getX());
                    int ydist = (int)Math.abs(entity.getY() - playerIn.getY());
                    int zdist = (int)Math.abs(entity.getZ() - playerIn.getZ());
                    int entDist = xdist + zdist;
                    int closeDist = 40;
                    if (entity == null || !(entity instanceof Monster) || entDist > closeDist || ydist >= 4) continue;
                    closeDist = entDist;
                    worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.bark1.get(), SoundSource.PLAYERS, 0.5f, 1.4f);
                    if (entity.getDisplayName() != null) {
                        this.bob = entity.getDisplayName().getString();
                    }
                    this.foundFlag = true;
                }
                if (!this.foundFlag) break;
                String tmpString1 = Component.translatable((String)"tooltip.ip.wolfsays1").getString();
                String tmpString2 = Component.translatable((String)"tooltip.ip.wolfsays2").getString();
                playerIn.sendSystemMessage((Component)Component.literal((String)(wolfName + tmpString1 + this.bob + tmpString2)));
                this.foundFlag = false;
                break;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof FakePlayer && ((Boolean)InventoryPetsConfig.disableRightClickMachines.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (((Boolean)InventoryPetsConfig.disableWolf.get()).booleanValue()) {
            return InteractionResultHolder.fail((Object)itemstack);
        }
        if (itemstack.getDamageValue() >= 1) {
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.howl.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
            return InteractionResultHolder.fail((Object)itemstack);
        }
        Wolf foundWolf = null;
        boolean wolfFlag = false;
        AABB range = new AABB(playerIn.getX() - 20.0, playerIn.getY() - 20.0, playerIn.getZ() - 20.0, playerIn.getX() + 20.0, playerIn.getY() + 20.0, playerIn.getZ() + 20.0);
        List entities = worldIn.getEntitiesOfClass(Wolf.class, range);
        int esize = entities.size();
        for (int k = 0; k <= esize - 1; ++k) {
            Wolf chkWolf;
            Entity entity = (Entity)entities.get(k);
            if (entity == null || !(entity instanceof Wolf) || (chkWolf = (Wolf)entity).getOwner() != playerIn || !chkWolf.hasCustomName()) continue;
            wolfFlag = true;
            foundWolf = chkWolf;
        }
        if (!(worldIn.isClientSide || itemstack.getDamageValue() != 0 || wolfFlag || playerIn.isCrouching())) {
            if (this.useDelay > 60) {
                this.useDelay = 0;
                Wolf entityW1 = (Wolf)EntityType.WOLF.create(worldIn);
                entityW1.setOwnerUUID(playerIn.getUUID());
                entityW1.setTame(true, true);
                entityW1.setInSittingPose(false);
                entityW1.setHealth(30.0f);
                if (!itemstack.has(DataComponents.CUSTOM_NAME)) {
                    String[] wolfnames = new String[]{"Wolfgang", "Woofless", "Rufus", "Barkley", "Dog", "Notch", "Waggy", "Spot", "Furball", "Woofer", "Baxter", "Sparky", "Rover", "Benji", "Cujo"};
                    RandomPoolAlias rand = new RandomPoolAlias();
                    int nameChk = rand.nextInt(15);
                    String randName = wolfnames[nameChk];
                    entityW1.setCustomName((Component)Component.literal((String)randName));
                } else {
                    entityW1.setCustomName((Component)Component.literal((String)ItemHelper.nameFixer(itemstack.getDisplayName().getString())));
                }
                entityW1.setCustomNameVisible(true);
                BlockHitResult movingobjectposition = petWolf.getPlayerPOVHitResult((Level)worldIn, (Player)playerIn, (ClipContext.Fluid)ClipContext.Fluid.ANY);
                if (movingobjectposition != null) {
                    if (movingobjectposition.getType() == HitResult.Type.BLOCK) {
                        int i = (int)movingobjectposition.getLocation().x;
                        int j = (int)movingobjectposition.getLocation().y;
                        int k = (int)movingobjectposition.getLocation().z;
                        entityW1.setPos((double)i, (double)(j + 1), (double)k);
                        worldIn.addFreshEntity((Entity)entityW1);
                        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.howl2.get(), SoundSource.PLAYERS, 0.5f, 1.0f);
                    }
                    this.wwFlag = true;
                }
            }
        } else if (!worldIn.isClientSide && itemstack.getDamageValue() == 0 && playerIn.isCrouching()) {
            if (this.wolfAlarm) {
                this.wolfAlarm = false;
                playerIn.sendSystemMessage((Component)Component.translatable((String)"item.wolf.false", (Object[])new Object[0]));
            } else {
                this.wolfAlarm = true;
                playerIn.sendSystemMessage((Component)Component.translatable((String)"item.wolf.true", (Object[])new Object[0]));
                this.barkCounter = 0;
            }
        } else if (wolfFlag && !playerIn.isCrouching() && foundWolf != null) {
            foundWolf.remove(Entity.RemovalReason.DISCARDED);
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), ModSoundEvents.howl.get(), SoundSource.PLAYERS, 0.5f, 1.3f);
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
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petwolf1", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.rightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GREEN) + I18n.get((String)"tooltip.ip.petwolf2", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + " [" + String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.sneakrightclick", (Object[])new Object[0]) + String.valueOf(ChatFormatting.DARK_GREEN) + "]")));
        Item petFood = (Item)BuiltInRegistries.ITEM.get(Identifier.parse((String)((String)InventoryPetsConfig.foodWolf.get())));
        if (this.customFood || petFood == Items.AIR) {
            if (!petFood.getDescriptionId().contains("minecraft.air")) {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)petFood.getDescriptionId(), (Object[])new Object[0]))));
            } else {
                list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.foodonetimeuse", (Object[])new Object[0]))));
            }
        } else if (!((Boolean)InventoryPetsConfig.petsEatWholeItems.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.BONE.getDescriptionId(), (Object[])new Object[0]))));
        } else {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.GRAY) + I18n.get((String)"tooltip.ip.favoritefood", (Object[])new Object[0]) + " " + I18n.get((String)Items.DIAMOND.getDescriptionId(), (Object[])new Object[0]))));
        }
        list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_AQUA) + I18n.get((String)"tooltip.ip.fan", (Object[])new Object[0]))));
        if (((Boolean)InventoryPetsConfig.showSuggestors.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_GRAY) + I18n.get((String)"tooltip.ip.suggestedby", (Object[])new Object[0]) + " Twingemios")));
        }
        if (((Boolean)InventoryPetsConfig.disableWolf.get()).booleanValue()) {
            list.add((Component)Component.translatable((String)(String.valueOf(ChatFormatting.DARK_RED) + I18n.get((String)"tooltip.ip.disabled", (Object[])new Object[0]))));
        }
    }
}

