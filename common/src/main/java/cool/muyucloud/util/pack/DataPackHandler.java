package cool.muyucloud.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.nio.file.Path;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler(CropariaIf.CONFIG.getPackPath());

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        root, PackType.SERVER_DATA, PackSource.BUILT_IN
    );
    private boolean generated = false;

    @Override
    public boolean beforeLoad() {
        super.beforeLoad();
        if (!generated && CropariaIf.CONFIG.getOverride()) {
            this.clear();
        }
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
            try {
                Util.deleteDir(file);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to clear data pack directory", e);
            }
        }
    }
}
