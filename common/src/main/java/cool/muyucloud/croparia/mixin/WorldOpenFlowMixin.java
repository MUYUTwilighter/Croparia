package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowMixin {
    @Inject(method = "doLoadLevel", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/packs/repository/ServerPacksSource;createPackRepository(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;)Lnet/minecraft/server/packs/repository/PackRepository;",
        ordinal = 0
    ))
    private void onLoadLevel(Screen screen, String string, boolean bl, boolean bl2, CallbackInfo ci) {
        DataPackHandler.INSTANCE.onInitial();
    }
}
