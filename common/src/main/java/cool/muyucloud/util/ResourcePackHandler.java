package cool.muyucloud.util;

import com.google.gson.JsonObject;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;

import java.nio.file.Path;

public class ResourcePackHandler extends PackHandler {
    public static final ResourcePackHandler INSTANCE = new ResourcePackHandler();

    private final Path blockStateModelPath = PACKS_DIR.resolve("assets/croparia/blockstates");
    private final Path itemModelPath = PACKS_DIR.resolve("assets/croparia/models/item");
    private final Path langEnUsPath = PACKS_DIR.resolve("assets/croparia/lang/en_us.json");
    private final PathPackResources resourcePack = new PathPackResources("croparia", PACKS_DIR, true);

    public PackResources getResourcePack() {
        return this.resourcePack;
    }

    public void addBlockStateModel(String name, JsonObject json) {
        Path path = this.blockStateModelPath.resolve(name + ".json");
        this.writeJson(json, path.toFile());
    }

    public void addItemModel(String name, JsonObject json) {
        Path path = this.itemModelPath.resolve(name + ".json");
        this.writeJson(json, path.toFile());
    }

    public void addLangEnUs(JsonObject json) {
        this.writeJson(json, this.langEnUsPath.toFile());
    }
}
