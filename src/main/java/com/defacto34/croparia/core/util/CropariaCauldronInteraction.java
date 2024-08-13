//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.util;

import com.defacto34.croparia.init.ItemInit;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemActionResult;
import net.minecraft.world.event.GameEvent;

public interface CropariaCauldronInteraction extends CauldronBehavior {
    CauldronBehaviorMap ELEMATILIUS = CauldronBehavior.createMap("elematilius");
    CauldronBehaviorMap WATER_CAULDRON = CauldronBehavior.createMap("water_cauldron");
    CauldronBehaviorMap FIRE_CAULDRON = CauldronBehavior.createMap("fire_cauldron");
    CauldronBehaviorMap EARTH_CAULDRON = CauldronBehavior.createMap("earth_cauldron");
    CauldronBehaviorMap AIR_CAULDRON = CauldronBehavior.createMap("air_cauldron");

    static void bootStrap() {
        ELEMATILIUS.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_ELEMATILIUS.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ItemActionResult.success(level.isClient);
        });
        WATER_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_WATER.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ItemActionResult.success(level.isClient);
        });
        FIRE_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_FIRE.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ItemActionResult.success(level.isClient);
        });
        EARTH_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_EARTH.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ItemActionResult.success(level.isClient);
        });
        AIR_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClient) {
                player.getStackInHand(hand).decrement(1);
                level.spawnEntity(new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5, ItemInit.POTION_AIR.getDefaultStack()));
                LeveledCauldronBlock.decrementFluidLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                level.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return ItemActionResult.success(level.isClient);
        });
    }
}
