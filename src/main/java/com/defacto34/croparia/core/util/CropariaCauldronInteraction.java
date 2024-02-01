//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.util;

import com.defacto34.croparia.init.ItemInit;
import java.util.Map;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;

public interface CropariaCauldronInteraction extends CauldronBehavior {
    Map<Item, CauldronBehavior> ELEMATILIUS = CauldronBehavior.createMap();
    Map<Item, CauldronBehavior> WATER_CAULDRON = CauldronBehavior.createMap();
    Map<Item, CauldronBehavior> FIRE_CAULDRON = CauldronBehavior.createMap();
    Map<Item, CauldronBehavior> EARTH_CAULDRON = CauldronBehavior.createMap();
    Map<Item, CauldronBehavior> AIR_CAULDRON = CauldronBehavior.createMap();

    static void bootStrap() {
        ELEMATILIUS.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_ELEMATILIUS.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ActionResult.success(level.isClient);
        });
        WATER_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_WATER.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ActionResult.success(level.isClient);
        });
        FIRE_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_FIRE.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ActionResult.success(level.isClient);
        });
        EARTH_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_EARTH.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ActionResult.success(level.isClient);
        });
        AIR_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_AIR.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ActionResult.success(level.isClient);
        });
    }
}
