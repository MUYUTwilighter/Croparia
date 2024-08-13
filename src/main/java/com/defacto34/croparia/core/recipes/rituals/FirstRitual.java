//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes.rituals;

import com.defacto34.croparia.api.crop.CropariaCrops;
import com.defacto34.croparia.init.BlockInit;
import com.defacto34.croparia.init.CropInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FirstRitual extends RitualUtils {
    public FirstRitual() {
    }

    public static boolean checkRitual(BlockState blockNeed, World worldIn, BlockPos pos, boolean bypass) {
        if (!getBlock(BlockInit.ELEMENTAL_STONE, pos.down(), worldIn)) {
            bad("ELEMENTAL STONE", worldIn, pos);
        } else if (getBlockState(CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.north().east(), worldIn) && getBlockState((BlockState)CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.north().west(), worldIn) && getBlockState((BlockState)CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.south().east(), worldIn) && getBlockState((BlockState)CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.south().west(), worldIn)) {
            if (getBlockState(CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.north(3), worldIn) && getBlockState((BlockState)CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.south(3), worldIn) && getBlockState((BlockState)CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.east(3), worldIn) && getBlockState((BlockState)CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.west(3), worldIn)) {
                if (getBlock(Blocks.DIORITE, pos.north(4).east(), worldIn) && getBlock(Blocks.DIORITE, pos.north(4).west(), worldIn) && getBlock(Blocks.DIORITE, pos.south(4).east(), worldIn) && getBlock(Blocks.DIORITE, pos.south(4).west(), worldIn) && getBlock(Blocks.DIORITE, pos.east(4).north(), worldIn) && getBlock(Blocks.DIORITE, pos.east(4).south(), worldIn) && getBlock(Blocks.DIORITE, pos.west(4).north(), worldIn) && getBlock(Blocks.DIORITE, pos.west(4).south(), worldIn)) {
                    if (getBlock(Blocks.ANDESITE, pos.north(3).east(3), worldIn) && getBlock(Blocks.ANDESITE, pos.north(3).west(3), worldIn) && getBlock(Blocks.ANDESITE, pos.south(3).east(3), worldIn) && getBlock(Blocks.ANDESITE, pos.south(3).west(3), worldIn) && getBlock(Blocks.ANDESITE, pos.up().north(3).east(3), worldIn) && getBlock(Blocks.ANDESITE, pos.up().north(3).west(3), worldIn) && getBlock(Blocks.ANDESITE, pos.up().south(3).east(3), worldIn) && getBlock(Blocks.ANDESITE, pos.up().south(3).west(3), worldIn)) {
                        if (bypass || getBlockState(blockNeed, pos.down().north(), worldIn) && getBlockState(blockNeed, pos.down().east(), worldIn) && getBlockState(blockNeed, pos.down().south(), worldIn) && getBlockState(blockNeed, pos.down().west(), worldIn)) {
                            return true;
                        }

                        bad(blockNeed, worldIn, pos);
                    } else {
                        bad("ANDESITES", worldIn, pos);
                    }
                } else {
                    bad("DIORITES", worldIn, pos);
                }
            } else {
                bad("GOLD CROPS", worldIn, pos);
            }
        } else {
            bad("IRON CROPS", worldIn, pos);
        }

        return false;
    }

    public static void placeRitual(World worldIn, BlockPos pos) {
        setBlock(BlockInit.ELEMENTAL_STONE, pos.down(), worldIn);
        setBlockState(CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.north().east(), worldIn);
        setBlockState(CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.north().west(), worldIn);
        setBlockState(CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.south().east(), worldIn);
        setBlockState(CropInit.IRON.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.south().west(), worldIn);
        setBlockState(CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.north(3), worldIn);
        setBlockState(CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.south(3), worldIn);
        setBlockState(CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.east(3), worldIn);
        setBlockState(CropInit.GOLD.cropBlock.getDefaultState().with(CropariaCrops.AGE, 7), pos.west(3), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.north().east().down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.north().west().down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.south().east().down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.south().west().down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.north(3).down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.south(3).down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.east(3).down(), worldIn);
        setBlockState(Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), pos.west(3).down(), worldIn);
        setBlock(Blocks.WATER, pos.down().north(2).west(2), worldIn);
        setBlock(Blocks.WATER, pos.down().north(2).east(2), worldIn);
        setBlock(Blocks.WATER, pos.down().south(2).west(2), worldIn);
        setBlock(Blocks.WATER, pos.down().south(2).east(2), worldIn);
        setBlock(Blocks.DIORITE, pos.north(4).east(), worldIn);
        setBlock(Blocks.DIORITE, pos.north(4).west(), worldIn);
        setBlock(Blocks.DIORITE, pos.south(4).east(), worldIn);
        setBlock(Blocks.DIORITE, pos.south(4).west(), worldIn);
        setBlock(Blocks.DIORITE, pos.east(4).north(), worldIn);
        setBlock(Blocks.DIORITE, pos.east(4).south(), worldIn);
        setBlock(Blocks.DIORITE, pos.west(4).north(), worldIn);
        setBlock(Blocks.DIORITE, pos.west(4).south(), worldIn);
        setBlock(Blocks.ANDESITE, pos.north(3).east(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.north(3).west(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.south(3).east(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.south(3).west(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.up().north(3).east(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.up().north(3).west(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.up().south(3).east(3), worldIn);
        setBlock(Blocks.ANDESITE, pos.up().south(3).west(3), worldIn);
    }
}
