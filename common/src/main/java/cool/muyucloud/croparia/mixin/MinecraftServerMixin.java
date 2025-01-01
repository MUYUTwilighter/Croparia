package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantBlockableEventLoop<TickTask> implements CommandSource, AutoCloseable {
    @Shadow
    public abstract CompletableFuture<Void> reloadResources(Collection<String> collection);

    @Shadow
    public abstract PackRepository getPackRepository();

    public MinecraftServerMixin(String string) {
        super(string);
    }

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getMillis()J", ordinal = 0))
    private void onServerInitialized(CallbackInfo ci) {
        DataPackHandler.INSTANCE.onSecondary();
        // No need to collect new data packs again, as they are added on initial reload
        Collection<String> collection = this.getPackRepository().getSelectedIds();
        this.reloadResources(collection).exceptionally(e -> {
            CropariaIf.LOGGER.warn("Failed to execute secondary reload", e);
            return null;
        });
    }
}
