package cool.muyucloud.croparia.neoforge.mixin;

import cool.muyucloud.croparia.api.repo.fluid.FluidProxyProvider;
import cool.muyucloud.croparia.api.repo.item.ItemProxyProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BlockCapability.class)
public abstract class BlockCapabilityMixin<T> extends BaseCapability<T, Direction> {
    protected BlockCapabilityMixin(ResourceLocation name, Class<T> typeClass, Class<Direction> contextClass) {
        super(name, typeClass, contextClass);
    }

    @SuppressWarnings({"unchecked", "EqualsBetweenInconvertibleTypes"})
    @Inject(method = "getCapability", at = @At("RETURN"), cancellable = true)
    public void onGetCapability(Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, Object context, CallbackInfoReturnable<T> cir) {
        if (cir.getReturnValue() != null || blockEntity == null || !(context instanceof Direction direction)) {
            return;
        }
        if (blockEntity instanceof ItemProxyProvider provider && Objects.equals(this, Capabilities.ItemHandler.BLOCK)) {
            cir.setReturnValue((T) provider.visitItem(direction));
            return;
        }
        if (blockEntity instanceof FluidProxyProvider provider && Objects.equals(this, Capabilities.FluidHandler.BLOCK)) {
            cir.setReturnValue((T) provider.visitFluid(direction));
        }
    }
}
