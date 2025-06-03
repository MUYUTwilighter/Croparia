package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "reloadResources", at = @At("HEAD"))
    public void onReloadResources(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        DataPackHandler.INSTANCE.beforeReload();
        CropariaIf.LOGGER.info("Data pack generation performed");
    }
}
