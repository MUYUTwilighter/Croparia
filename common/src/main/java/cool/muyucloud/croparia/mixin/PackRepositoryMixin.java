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
 */
@Mixin(PackRepository.class)
public abstract class PackRepositoryMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static RepositorySource[] insertProviders(RepositorySource... providers) {
//        OnLoadSupplier.LAST_DATA_LOAD = System.currentTimeMillis();
        RepositorySource[] newProviders = new RepositorySource[providers.length + DataPackHandler.REGISTRY.size()];
        int i = 0;
        for (DataPackHandler pack : DataPackHandler.REGISTRY.values()) {
            pack.onTriggered();
            newProviders[i] = pack.getDatapack();
            i++;
        }
        for (RepositorySource source : providers) {
            newProviders[i] = source;
            i++;
        }
        return newProviders;
    }
}
