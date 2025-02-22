package cool.muyucloud.croparia.forge.mixin;

import cool.muyucloud.croparia.api.repo.forge.ProxyProviderImpl;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CapabilityProvider.class)
public abstract class CapabilityProviderMixin {
    @Inject(method = "getCapability", at = @At("HEAD"), cancellable = true, remap = false)
    public void onGetCapability(@NotNull Capability<?> cap, @Nullable Direction side, CallbackInfoReturnable<LazyOptional<Object>> cir) {
        //noinspection ConstantValue
        if ((Object) this instanceof BlockEntity be && !(cir.getReturnValue().isPresent())) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                ProxyProviderImpl.findItem(be.getLevel(), be.getBlockPos(), be.getBlockState(), be, side).ifPresent(
                    handler -> cir.setReturnValue(LazyOptional.of(() -> handler))
                );
            }
            if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                ProxyProviderImpl.findFluid(be.getLevel(), be.getBlockPos(), be.getBlockState(), be, side).ifPresent(
                    handler -> cir.setReturnValue(LazyOptional.of(() -> handler))
                );
            }
        }
    }
}
