package cool.muyucloud.croparia.api.core.block;

import cool.muyucloud.croparia.access.BlockAccess;
import cool.muyucloud.croparia.api.core.block.entity.ActivatedShriekerBlockEntity;
import cool.muyucloud.croparia.registry.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.Nullable;

public class ActivatedShrieker extends SculkShriekerBlock {
    public ActivatedShrieker(BlockBehaviour.Properties properties) {
        super(properties);
        ((BlockAccess) this).cif$modifyDefaultState(this.defaultBlockState().setValue(CAN_SUMMON, true));
    }

    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (level instanceof ServerLevel serverLevel) {
            ServerPlayer serverPlayer = SculkShriekerBlockEntity.tryGetPlayer(entity);
            if (serverPlayer != null) {
                serverLevel.getBlockEntity(blockPos, BlockEntities.ACTIVATED_SHRIEKER.get()).ifPresent(
                    sculkShriekerBlockEntity -> sculkShriekerBlockEntity.tryShriek(serverLevel, serverPlayer)
                );
            }
        }
        super.stepOn(level, blockPos, blockState, entity);
    }

    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (level instanceof ServerLevel serverLevel) {
            if (blockState.getValue(SHRIEKING) && !blockState.is(blockState2.getBlock())) {
                serverLevel.getBlockEntity(blockPos, BlockEntities.ACTIVATED_SHRIEKER.get()).ifPresent(
                    sculkShriekerBlockEntity -> sculkShriekerBlockEntity.tryRespond(serverLevel)
                );
            }
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ActivatedShriekerBlockEntity(blockPos, blockState);
    }

    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(SHRIEKING)) {
            serverLevel.setBlock(blockPos, blockState.setValue(SHRIEKING, false), 3);
            serverLevel.getBlockEntity(blockPos, BlockEntities.ACTIVATED_SHRIEKER.get()).ifPresent(
                sculkShriekerBlockEntity -> sculkShriekerBlockEntity.tryRespond(serverLevel)
            );
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ? BaseEntityBlock.createTickerHelper(
            blockEntityType, BlockEntities.ACTIVATED_SHRIEKER.get(),
            (levelx, blockPos, blockStatex, sculkShriekerBlockEntity) -> VibrationSystem.Ticker.tick(
                levelx, sculkShriekerBlockEntity.getVibrationData(), sculkShriekerBlockEntity.getVibrationUser()
            )
        ) : null;
    }

    @Override
    protected void tryDropExperience(ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, IntProvider intProvider) {
    }

    @Override
    protected void popExperience(ServerLevel serverLevel, BlockPos blockPos, int i) {
    }
}
