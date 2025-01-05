package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.gameevent.GameEvent;

public interface CropariaCauldronInteraction extends CauldronInteraction {
    InteractionMap ELEMATILIUS = CauldronInteraction.newInteractionMap("croparia:elematilius");
    InteractionMap WATER_CAULDRON = CauldronInteraction.newInteractionMap("croparia:water_cauldron");
    InteractionMap FIRE_CAULDRON = CauldronInteraction.newInteractionMap("croparia:fire_cauldron");
    InteractionMap EARTH_CAULDRON = CauldronInteraction.newInteractionMap("croparia:earth_cauldron");
    InteractionMap AIR_CAULDRON = CauldronInteraction.newInteractionMap("croparia:air_cauldron");

    static void bootStrap() {
        ELEMATILIUS.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide && CropariaIf.CONFIG.getCauldron()) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level, blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_ELEMATILIUS.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });
        WATER_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide && CropariaIf.CONFIG.getCauldron()) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level, blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_WATER.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });
        FIRE_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide && CropariaIf.CONFIG.getCauldron()) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level, blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_FIRE.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });
        EARTH_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide && CropariaIf.CONFIG.getCauldron()) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level, blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_EARTH.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });
        AIR_CAULDRON.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, hand, stack) -> {
            if (!level.isClientSide && CropariaIf.CONFIG.getCauldron()) {
                player.getItemInHand(hand).shrink(1);
                level.addFreshEntity(new ItemEntity(
                    level, blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D,
                    CropariaItems.POTION_AIR.get().getDefaultInstance()
                ));
                LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });
    }
}
