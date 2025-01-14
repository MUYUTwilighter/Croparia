package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldOpenFlows.class)
public abstract class WorldOpenFlowMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onLoadLevel(Minecraft minecraft, LevelStorageSource levelStorageSource, CallbackInfo ci) {
        DataPackHandler.INSTANCE.onInitial();
    }
}
