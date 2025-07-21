package cool.muyucloud.croparia.mixin;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import net.minecraft.FileUtil;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.FileWriter;
import java.nio.file.Path;

/**
 * Mixin class used to insert our datapack provided by {@link DataPackHandler},
 * so that the datapack can be loaded.
 */
@Mixin(PackRepository.class)
public abstract class PackRepositoryMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static RepositorySource[] insertProviders(RepositorySource... providers) {
        Path packPath = CropariaIf.CONFIG.getPackPath();
        try {
            FileUtil.createDirectoriesSafe(packPath);
            JsonWriter writer = new JsonWriter(new FileWriter(packPath.resolve("pack.mcmeta").toFile()));
            new Gson().toJson(PackHandler.META, writer);
            writer.close();
        } catch (Throwable t) {
            CropariaIf.LOGGER.error("Failed to establish pack dir", t);
        }
        RepositorySource[] newProviders = new RepositorySource[providers.length + 1];
        RepositorySource provider = DataPackHandler.INSTANCE.getDatapack();
        newProviders[0] = provider;
        System.arraycopy(providers, 0, newProviders, 1, providers.length);
        return newProviders;
    }
}
