//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.block;

import com.defacto34.croparia.core.recipes.RitualRecipe;
import com.defacto34.croparia.core.recipes.rituals.FirstRitual;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RitualStand extends Block {
    protected final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.3, 0.0, 16.0, 6.0, 16.0);

    public RitualStand() {
        super(FabricBlockSettings.create().strength(1.0F, 1.0F).sounds(BlockSoundGroup.ANVIL).requiresTool());
    }

    public void onSteppedOn(World worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        if (entityIn instanceof ItemEntity && !worldIn.isClient) {
            if (((ItemEntity)entityIn).getStack().getItem() == Items.DRAGON_HEAD) {
                if (!FirstRitual.checkRitual((BlockState)null, worldIn, pos, true)) {
                    FirstRitual.placeRitual(worldIn, pos);
                }

                ((ItemEntity)entityIn).getStack().decrement(1);
            }

            ItemStack stack = ((ItemEntity)entityIn).getStack();
            RitualRecipe.craft(stack, worldIn, pos);
        }

    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }
}
