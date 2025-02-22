package cool.muyucloud.croparia.forge.mixin;

import cool.muyucloud.croparia.api.repo.forge.ProxyProviderImpl;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnstableApiUsage")
@Mixin(CapabilityProvider.class)
public abstract class CapabilityProviderMixin {
    @Inject(method = "getCapability", at = @At("RETURN"), cancellable = true, remap = false)
    public void onGetCapability(@NotNull Capability<?> cap, @Nullable Direction side, CallbackInfoReturnable<LazyOptional<Object>> cir) {
        //noinspection ConstantValue
        if ((Object) this instanceof BlockEntity be && !(cir.getReturnValue().isPresent())) {
            if (cap == ForgeCapabilities.ITEM_HANDLER) {
                ProxyProviderImpl.findItem(be.getLevel(), be.getBlockPos(), be.getBlockState(), be, side).ifPresent(
                    handler -> cir.setReturnValue(LazyOptional.of(() -> handler))
                );
            }
            if (cap == ForgeCapabilities.FLUID_HANDLER) {
                ProxyProviderImpl.findFluid(be.getLevel(), be.getBlockPos(), be.getBlockState(), be, side).ifPresent(
                    handler -> cir.setReturnValue(LazyOptional.of(() -> handler))
                );
            }
        }
    }
}
