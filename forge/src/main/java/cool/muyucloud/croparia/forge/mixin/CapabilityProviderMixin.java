package cool.muyucloud.croparia.forge.mixin;

import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.repo.fluid.FluidProxyProvider;
import cool.muyucloud.croparia.api.repo.item.ItemProxyProvider;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.minecraft.core.Direction;
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

import java.util.Objects;

@Mixin(CapabilityProvider.class)
public abstract class CapabilityProviderMixin {
    @Inject(method = "getCapability", at = @At("HEAD"), cancellable = true, remap = false)
    public void onGetCapability(@NotNull Capability<Object> cap, @Nullable Direction side, CallbackInfoReturnable<LazyOptional<Object>> cir) {
        if (this instanceof FluidProxyProvider provider && Objects.equals(cap, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
            RepoProxy<FluidSpec> agent = provider.visitFluid(side);
            if (agent == null) {
                cir.setReturnValue(LazyOptional.empty());
            } else {
                cir.setReturnValue(LazyOptional.of(() -> agent));
            }
            cir.cancel();
        } else if (this instanceof ItemProxyProvider provider && Objects.equals(cap, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            RepoProxy<ItemSpec> agent = provider.visitItem(side);
            if (agent == null) {
                cir.setReturnValue(LazyOptional.empty());
            } else {
                cir.setReturnValue(LazyOptional.of(() -> agent));
            }
            cir.cancel();
        }
    }
}
