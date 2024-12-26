//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cool.muyucloud.block;

import cool.muyucloud.blockentity.GreenhouseBlockEntity;
import cool.muyucloud.registry.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Greenhouse extends BaseEntityBlock {
    public static List<Greenhouse> blockGreenhouse = new ArrayList<>();
    protected final VoxelShape SHAPE = Block.box(1.0, 1.0, 0.0, 15.0, 3.0, 15.0);

    public Greenhouse(Properties settings) {
        super(settings);
        blockGreenhouse.add(this);
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.SHAPE;
    }

    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }

        return InteractionResult.SUCCESS;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getBlockState(pos.below()).getBlock() instanceof CropBlock) {
            world.getBlockState(pos.below()).randomTick(world, pos.below(), random);
        }

    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GreenhouseBlockEntity(pos, state);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BlockEntities.GREENHOUSE_BE.get(), (world1, pos, state1, be) -> {
            GreenhouseBlockEntity.tick(world1, pos, be);
        });
    }

    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GreenhouseBlockEntity) {
                Containers.dropContents(world, pos, (GreenhouseBlockEntity) blockEntity);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }
}
