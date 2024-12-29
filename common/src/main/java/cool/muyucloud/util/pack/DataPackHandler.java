package cool.muyucloud.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;

import java.nio.file.Path;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler(CropariaIf.CONFIG.getPackPath());

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        root, PackType.SERVER_DATA, PackSource.BUILT_IN
    );

    public DataPackHandler(Path path) {
        super(path);
    }

    @Override
    protected void clear() {
        boolean deleted = this.root.resolve("data").toFile().delete();
        if (!deleted) {
            CropariaIf.LOGGER.warn("Failed to delete data folder");
        }
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
}
