package cool.muyucloud.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.nio.file.Path;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler(CropariaIf.CONFIG.getPackPath());
    private static final String RECIPE_PATH = "data/%s/recipes";
    private static final String LOOT_TABLE_PATH = "data/%s/loot_tables";

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        root, PackType.SERVER_DATA, PackSource.BUILT_IN
    );

    public DataPackHandler(Path path) {
        super(path);
    }

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    public void addRecipe(ResourceLocation location, JsonObject recipe) {
        File file = this.root.resolve(RECIPE_PATH.formatted(location.getNamespace())).
            resolve(location.getPath() + ".json").toFile();
        this.writeJson(recipe, file);
    }

    public void addLootTable(ResourceLocation location, JsonObject lootTable) {
        File file = this.root.resolve(LOOT_TABLE_PATH.formatted(location.getNamespace())).
            resolve(location.getPath() + ".json").toFile();
        this.writeJson(lootTable, file);
    }
}
