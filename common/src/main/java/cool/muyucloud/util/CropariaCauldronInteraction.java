package cool.muyucloud.util;

import cool.muyucloud.registry.CropariaItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;

public interface CropariaCauldronInteraction extends CauldronInteraction {
    Map<Item, CauldronInteraction> ELEMATILIUS = CauldronInteraction.newInteractionMap();
    Map<Item, CauldronInteraction> WATER_CAULDRON = CauldronInteraction.newInteractionMap();
    Map<Item, CauldronInteraction> FIRE_CAULDRON = CauldronInteraction.newInteractionMap();
    Map<Item, CauldronInteraction> EARTH_CAULDRON = CauldronInteraction.newInteractionMap();
    Map<Item, CauldronInteraction> AIR_CAULDRON = CauldronInteraction.newInteractionMap();

    static void bootStrap() {
        ELEMATILIUS.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level,
                    blockPos.getX() + 0.5D,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_ELEMATILIUS.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        });
        WATER_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level,
                    blockPos.getX() + 0.5D,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_WATER.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        });
        FIRE_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level,
                    blockPos.getX() + 0.5D,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_FIRE.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        });
        EARTH_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level,
                    blockPos.getX() + 0.5D,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_EARTH.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        });
        AIR_CAULDRON.put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level,
                    blockPos.getX() + 0.5D,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_AIR.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        });
    }
}
