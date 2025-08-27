package cool.muyucloud.croparia.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import cool.muyucloud.croparia.util.supplier.OnLoadSupplier;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "reloadResources", at = @At("RETURN"))
    public void onReload(Collection<String> selectedIds, CallbackInfoReturnable<CompletableFuture<Void>> cir, @Local CompletableFuture<Void> completableFuture) {
        completableFuture.whenComplete((v, t) -> OnLoadSupplier.LAST_DATA_LOAD = System.currentTimeMillis());
    }
}
