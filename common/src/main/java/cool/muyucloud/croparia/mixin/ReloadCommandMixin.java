package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.commands.ReloadCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ReloadCommand.class)
public class ReloadCommandMixin {
    @Inject(method = "reloadPacks", at = @At("TAIL"))
    private static void onReload(Collection<String> collection, CommandSourceStack commandSourceStack, CallbackInfo ci) {
        DataPackHandler.INSTANCE.onSecondary();
        commandSourceStack.getServer().reloadResources(collection).exceptionally((throwable) -> {
            CropariaIf.LOGGER.warn("Failed to perform secondary reload", throwable);
            commandSourceStack.sendFailure(new TranslatableComponent("commands.reload.failure"));
            return null;
        });
    }

    @Inject(method = "reloadPacks", at = @At("HEAD"))
    private static void beforeReload(Collection<String> collection, CommandSourceStack commandSourceStack, CallbackInfo ci) {
        DataPackHandler.INSTANCE.onInitial();
    }
}
