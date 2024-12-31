package cool.muyucloud.mixin;

import cool.muyucloud.util.pack.DataPackHandler;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin class used to insert our datapack provided by {@link DataPackHandler},
 * so that the datapack can be loaded.
 * */
@Mixin(PackRepository.class)
public abstract class PackRepositoryMixin {
    @Inject(method = "reload", at = @At("HEAD"))
    private void onReload(CallbackInfo ci) {
        DataPackHandler.INSTANCE.onInitial();
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static RepositorySource[] insertProviders(RepositorySource... providers) {
        RepositorySource[] newProviders = new RepositorySource[providers.length + 1];
        RepositorySource provider = DataPackHandler.INSTANCE.getDatapack();
        newProviders[0] = provider;
        System.arraycopy(providers, 0, newProviders, 1, providers.length);
        return newProviders;
    }
}
