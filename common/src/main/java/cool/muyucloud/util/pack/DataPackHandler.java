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

    @Override
    public void clear() {
        File file = this.root.resolve("data").toFile();
        if (file.isDirectory()) {
            file.delete();
        }
    }
}
