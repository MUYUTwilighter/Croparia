package cool.muyucloud.mixin;

import cool.muyucloud.util.pack.DataPackHandler;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantBlockableEventLoop<TickTask> implements CommandSource, AutoCloseable {
    public MinecraftServerMixin(String string) {
        super(string);
    }

    @Shadow public abstract Commands getCommands();

    @Shadow public abstract CommandSourceStack createCommandSourceStack();

    @Inject(method = "reloadResources", at = @At("RETURN"))
    private void onDatapackReloaded(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        this.croparia_if$tryDataPackReload();
    }

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getMillis()J", ordinal = 0))
    private void onServerInitialized(CallbackInfo ci) {
        this.croparia_if$tryDataPackReload();
    }

    @Unique
    private void croparia_if$tryDataPackReload() {
        if (DataPackHandler.INSTANCE.afterLoad()) {
            this.getCommands().performPrefixedCommand(this.createCommandSourceStack(), "reload");
        }
    }
}
