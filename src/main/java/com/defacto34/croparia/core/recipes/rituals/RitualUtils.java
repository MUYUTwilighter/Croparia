//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes.rituals;

import com.defacto34.croparia.Croparia;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualUtils extends Croparia {
    public RitualUtils() {
    }

    public static boolean getBlock(Block block, BlockPos pos, World worldIn) {
        return worldIn.getBlockState(pos) == block.getDefaultState();
    }

    public static void setBlock(Block block, BlockPos pos, World worldIn) {
        worldIn.setBlockState(pos, block.getDefaultState());
    }

    public static boolean getBlockState(BlockState block, BlockPos pos, World worldIn) {
        return worldIn.getBlockState(pos) == block;
    }

    public static void setBlockState(BlockState state, BlockPos pos, World worldIn) {
        worldIn.setBlockState(pos, state);
    }

    public static void endFirst(ItemStack out, ItemStack in, BlockPos pos, World worldIn) {
        in.decrement(1);
        ItemEntity entityOut = new ItemEntity(worldIn, (double)pos.getX() + 0.5, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5, out);
        worldIn.spawnEntity(entityOut);
        worldIn.setBlockState(pos.down().north(), Blocks.AIR.getDefaultState());
        worldIn.setBlockState(pos.down().east(), Blocks.AIR.getDefaultState());
        worldIn.setBlockState(pos.down().south(), Blocks.AIR.getDefaultState());
        worldIn.setBlockState(pos.down().west(), Blocks.AIR.getDefaultState());
        entityOut.addVelocity(0.0, 0.3, 0.0);
    }

    public static void bad(String error, World level, BlockPos pos) {
        PlayerEntity player = level.getClosestPlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 10.0, true);
        if (player != null) {
            Croparia.sendMessage(player, "It seems there is an error with the " + error);
        }

    }

    public static void bad(BlockState error, World level, BlockPos pos) {
        PlayerEntity player = level.getClosestPlayer((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 10.0, false);
        if (player != null) {
            Croparia.sendMessage(player, "It seems there is an error with the " + error);
        }

    }

    public static void fail(World world, ItemEntity entityIn) {
        int j = world.random.nextInt(1);
        if (j > 0) {
            j = -1;
        } else {
            j = 1;
        }

        int k = world.random.nextInt(1);
        if (k > 0) {
            k = -1;
        } else {
            k = 1;
        }

        entityIn.addVelocity(((double)world.random.nextFloat() + 0.5) * (double)j / 100.0, (double)world.random.nextFloat() + 0.1, ((double)world.random.nextFloat() + 0.5) * (double)k / 100.0);
    }
}
