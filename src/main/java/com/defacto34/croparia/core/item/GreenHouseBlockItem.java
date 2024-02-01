//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GreenHouseBlockItem extends BlockItem {
    public GreenHouseBlockItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        if (!world.isClient) {
            if (world.isAir(pos.up()) && (world.getBlockState(pos).getBlock() instanceof CropBlock || world.getBlockState(pos).getBlock() instanceof StemBlock || world.getBlockState(pos).getBlock() instanceof AttachedStemBlock)) {
                world.setBlockState(pos.up(), this.getBlock().getDefaultState());
                player.getMainHandStack().decrement(1);
                return ActionResult.SUCCESS;
            }

            if (world.isAir(pos.up(2)) && world.isAir(pos.up())) {
                world.setBlockState(pos.up(2), this.getBlock().getDefaultState());
                player.getMainHandStack().decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }
}
