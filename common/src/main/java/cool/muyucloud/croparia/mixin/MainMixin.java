package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {
    @Inject(method = "main", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/packs/repository/ServerPacksSource;createPackRepository(Ljava/nio/file/Path;)Lnet/minecraft/server/packs/repository/PackRepository;",
        ordinal = 0
    ))
    private static void onMain(String[] strings, CallbackInfo ci) {
        DataPackHandler.INSTANCE.onInitial();
    }
}
