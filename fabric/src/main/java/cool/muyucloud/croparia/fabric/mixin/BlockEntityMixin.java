package cool.muyucloud.croparia.fabric.mixin;

import cool.muyucloud.croparia.api.repo.fluid.FluidProxyProvider;
import cool.muyucloud.croparia.api.repo.item.ItemProxyProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @SuppressWarnings({"unchecked"})
    @Inject(method = "<init>", at = @At("RETURN"))
    public void onConstruct(
        BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, CallbackInfo ci
    ) {
        if (this instanceof ItemProxyProvider provider) {
            ItemStorage.SIDED.registerForBlockEntities((be, direction) -> (Storage<ItemVariant>) provider.visitItem(direction));
        }
        if (this instanceof FluidProxyProvider provider) {
            FluidStorage.SIDED.registerForBlockEntities((be, direction) -> (Storage<FluidVariant>) provider.visitFluid(direction));
        }
    }
}
