package cool.muyucloud.croparia.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.SharedConstants;
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

    @Override
    public void onInitial() {
        super.onInitial();
        if (CropariaIf.CONFIG.getOverride()) {
            this.clear();
        }
    }

    @Override
    public void onSecondary() {
        super.onInitial();
        CropariaIf.LOGGER.info("Performing extra data pack reload");
        this.dump();
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

    public void addItemTag(ResourceLocation location, JsonObject tag) {
        String path = "data/%s/tags/items/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, tag);
    }

    public void addBlockTag(ResourceLocation location, JsonObject tag) {
        String path = "data/%s/tags/blocks/%s.json".formatted(location.getNamespace(), location.getPath());
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

    @Override
    protected int getPackVersion() {
        return SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA);
    }
}
