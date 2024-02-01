//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.item.elements;

import com.defacto34.croparia.init.BlockInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElementalFire extends Item {
    public ElementalFire() {
        super(new FabricItemSettings());
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof LeveledCauldronBlock) {
                LeveledCauldronBlock block = (LeveledCauldronBlock)state.getBlock();
                if (block.isFull(state)) {
                    world.setBlockState(pos, (BlockState)BlockInit.FIRE_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3));
                    context.getStack().decrement(1);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.FAIL;
    }
}
