//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cool.muyucloud.croparia.api.core.block;

import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.api.core.block.entity.GreenhouseBlockEntity;
import cool.muyucloud.croparia.api.repo.ProxyProvider;
import cool.muyucloud.croparia.registry.BlockEntities;
import cool.muyucloud.croparia.registry.CropariaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

public class Greenhouse extends BaseEntityBlock {
    public static final MapCodec<Greenhouse> CODEC = simpleCodec(Greenhouse::new);
    protected final VoxelShape SHAPE = Block.box(1.0, 1.0, 0.0, 15.0, 3.0, 15.0);

    public Greenhouse(Properties settings) {
        super(settings);
        ProxyProvider.registerItem((world, pos, state, be, direction) -> {
            if (be instanceof GreenhouseBlockEntity gbe) {
                return gbe.visitItem();
            } else {
                return null;
            }
        }, this);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!world.isClientSide) {
            MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
            if (screenHandlerFactory != null) {
                player.openMenu(screenHandlerFactory);
            }
        }

        return InteractionResult.SUCCESS;
    }

    public void randomTick(@Nullable BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getBlockState(pos.below()).getBlock() instanceof CropBlock) {
            world.getBlockState(pos.below()).randomTick(world, pos.below(), random);
        }

    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GreenhouseBlockEntity(pos, state);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(
            type, BlockEntities.GREENHOUSE_BE.get(),
            (world1, pos, state1, be) -> GreenhouseBlockEntity.tick(world1, pos, be)
        );
    }

    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GreenhouseBlockEntity) {
                Containers.dropContentsOnDestroy(state, newState, world, pos);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public @NotNull Item asItem() {
        return CropariaItems.GREENHOUSE.get();
    }
}
