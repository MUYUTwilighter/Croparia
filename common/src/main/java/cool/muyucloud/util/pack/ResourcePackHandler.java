package cool.muyucloud.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;

import java.io.File;
import java.nio.file.Path;

public class ResourcePackHandler extends PackHandler {
    public static final ResourcePackHandler INSTANCE = new ResourcePackHandler(CropariaIf.CONFIG.getPackPath());

    private static final String BLOCK_STATE_MODEL_PATH = "assets/%s/blockstates";
    private static final String ITEM_MODEL_PATH = "assets/%s/models/item";
    private static final String LANG_PATH = "assets/%s/lang";

    private final PathPackResources resourcePack = new PathPackResources("croparia", root, true);

    public ResourcePackHandler(Path path) {
        super(path);
    }

    public PackResources getResourcePack() {
        return this.resourcePack;
    }

    public void addBlockStateModel(ResourceLocation location, JsonObject model) {
        File file = this.root.resolve(BLOCK_STATE_MODEL_PATH.formatted(location.getNamespace())).
            resolve(location.getPath() + ".json").toFile();
        this.writeJson(model, file);
    }

    public void addItemModel(ResourceLocation location, JsonObject model) {
        File file = this.root.resolve(ITEM_MODEL_PATH.formatted(location.getNamespace())).
            resolve(location.getPath() + ".json").toFile();
        this.writeJson(model, file);
    }

    public void addLangEnUs(ResourceLocation location, JsonObject lang) {
        File file = this.root.resolve(LANG_PATH.formatted(location.getNamespace())).
            resolve(location.getPath() + ".json").toFile();
        this.writeJson(lang, file);
    }
}
