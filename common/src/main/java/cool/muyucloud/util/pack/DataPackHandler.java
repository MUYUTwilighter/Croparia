package cool.muyucloud.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler(CropariaIf.CONFIG.getPackPath());

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        root, PackType.SERVER_DATA, PackSource.BUILT_IN
    );
    private boolean generated = false;

    @Override
    public boolean beforeLoad() {
        super.beforeLoad();
        return generated;
    }

    @Override
    public boolean afterLoad() {
        if (generated) {
            generated = false;
        } else {
            this.generate();
            this.dump();
            generated = true;
        }
        return generated;
    }

    public DataPackHandler(Path path) {
        super(path);
    }

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    public void addRecipe(ResourceLocation location, JsonObject recipe) {
        String path = "data/%s/recipes/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, recipe);
    }

    public void addLootTable(ResourceLocation location, JsonObject lootTable) {
        String path = "data/%s/loot_tables/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, lootTable);
    }

    public void addTag(ResourceLocation location, JsonObject tag) {
        String path = "data/%s/tags/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, tag);
    }

    @Override
    public void clear() {
        Path path = this.root.resolve("data");
        File file = path.toFile();
        if (file.isDirectory()) {
            CropariaIf.LOGGER.info("Clearing data pack directory");
            try (Stream<Path> stream = Files.walk(path)) {
                stream.sorted(Comparator.reverseOrder()).forEach(f -> {
                    try {
                        Files.delete(f);
                    } catch (Exception e) {
                        CropariaIf.LOGGER.error("Failed to delete file", e);
                    }
                });
            } catch (Exception e) {
                CropariaIf.LOGGER.error("Failed to clear data pack directory", e);
            }
        }
    }
}
