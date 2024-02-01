//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.block;

import com.defacto34.croparia.core.recipes.InfusorRecipe;
import com.defacto34.croparia.core.util.ElementsEnum;
import com.defacto34.croparia.init.ItemInit;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Infusor extends Block {
    protected final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final EnumProperty<ElementsEnum> TYPE = EnumProperty.of("infusor_type", ElementsEnum.class);

    public Infusor() {
        super(FabricBlockSettings.create().strength(1.0F, 1.0F).requiresTool());
        this.setDefaultState((BlockState)this.getDefaultState().with(TYPE, ElementsEnum.EMPTY));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.FAIL;
        } else {
            if (state.get(TYPE) == ElementsEnum.EMPTY && ItemInit.getElementFromPotion(player.getMainHandStack().getItem()) != ElementsEnum.EMPTY) {
                world.setBlockState(pos, (BlockState)this.getDefaultState().with(TYPE, ItemInit.getElementFromPotion(player.getMainHandStack().getItem())));
                player.getMainHandStack().decrement(1);
                world.spawnEntity(new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, new ItemStack(Items.GLASS_BOTTLE)));
            } else if (state.get(TYPE) != ElementsEnum.EMPTY && player.getMainHandStack().getItem() == Items.GLASS_BOTTLE) {
                world.setBlockState(pos, (BlockState)this.getDefaultState().with(TYPE, ElementsEnum.EMPTY));
                player.getMainHandStack().decrement(1);
                world.spawnEntity(new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, new ItemStack(ItemInit.getPotionFromElement((ElementsEnum)state.get(TYPE)))));
            }

            return ActionResult.PASS;
        }
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof ItemEntity itemEntity && !world.isClient) {
            InfusorRecipe.craft(itemEntity.getStack(), world, pos);
        }

    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{TYPE});
    }
}
