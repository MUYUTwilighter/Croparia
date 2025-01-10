package cool.muyucloud.croparia.mixin;

import com.mojang.serialization.Dynamic;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {
    @Inject(method = "openWorldLoadLevelStem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/ServerPacksSource;createPackRepository(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;)Lnet/minecraft/server/packs/repository/PackRepository;", ordinal = 0))
    private void onOpenWorldLoadLevelStem(
        LevelStorageSource.LevelStorageAccess levelStorageAccess,
        Dynamic<?> dynamic, boolean bl, Runnable runnable, CallbackInfo ci
    ) {
        DataPackHandler.INSTANCE.onInitial();
    }
}
