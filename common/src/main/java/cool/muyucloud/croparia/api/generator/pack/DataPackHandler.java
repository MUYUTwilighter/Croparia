package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.util.AlwaysEnabledFileResourcePackProvider;
import cool.muyucloud.croparia.util.FileUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DataPackHandler extends PackHandler {
    public static final Map<ResourceLocation, DataPackHandler> REGISTRY = new HashMap<>();

    public static <P extends DataPackHandler> P register(P pack) {
        REGISTRY.put(pack.getId(), pack);
        return pack;
    }

    public static DataPackHandler register(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        return register(new DataPackHandler(id, path, meta, override));
    }

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        this.getId().toString(), getRoot(), PackType.SERVER_DATA, PackSource.BUILT_IN
    );

    public DataPackHandler(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        super(id, path, meta, override);
    }

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    @Override
    public void clear() {
        Path path = this.getRoot().resolve("data");
        File file = path.toFile();
        if (file.isDirectory()) {
            try {
                FileUtil.deleteUnder(file);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to clear datapack directory", e);
            }
        }
    }

    @Override
    public void addFile(String relative, String content) {
        super.addFile("data/" + relative, content);
    }
}