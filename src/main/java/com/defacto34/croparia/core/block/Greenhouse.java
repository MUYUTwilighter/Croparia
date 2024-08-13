//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.block;

import com.defacto34.croparia.core.blockEntity.GreenhouseBE;
import com.defacto34.croparia.init.BlockEntityInit;
import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Greenhouse extends BlockWithEntity {
    public static final MapCodec<Greenhouse> CODEC = createCodec(Greenhouse::new);
    public static List<Greenhouse> blockGreenhouse = new ArrayList();
    protected final VoxelShape SHAPE = Block.createCuboidShape(1.0, 1.0, 0.0, 15.0, 3.0, 15.0);

    public Greenhouse(AbstractBlock.Settings settings) {
        super(settings);
        blockGreenhouse.add(this);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos.down()).getBlock() instanceof CropBlock) {
            world.getBlockState(pos.down()).randomTick(world, pos.down(), random);
        }

    }

    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GreenhouseBE(pos, state);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, BlockEntityInit.GREENHOUSE_BE, (world1, pos, state1, be) -> {
            GreenhouseBE.tick(world1, pos, be);
        });
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GreenhouseBE) {
                ItemScatterer.spawn(world, pos, (GreenhouseBE)blockEntity);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }
}
