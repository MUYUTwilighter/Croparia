package cool.muyucloud.croparia.mixin;

import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mixin class used to insert our datapack provided by {@link DataPackHandler},
 * so that the datapack can be loaded.
 * */
@Mixin(PackRepository.class)
public abstract class PackRepositoryMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static RepositorySource[] insertProviders(RepositorySource... providers) {
        RepositorySource[] newProviders = new RepositorySource[providers.length + 1];
        RepositorySource provider = DataPackHandler.INSTANCE.getDatapack();
        newProviders[0] = provider;
        System.arraycopy(providers, 0, newProviders, 1, providers.length);
        return newProviders;
    }
}
