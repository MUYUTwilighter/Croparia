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

    private final PathPackResources resourcePack = new PathPackResources("croparia", root, true);

    @Override
    public boolean beforeLoad() {
        this.dump();
        return true;
    }

    @Override
    public boolean afterLoad() {
        return false;
    }

    public ResourcePackHandler(Path path) {
        super(path);
    }

    public PackResources getResourcePack() {
        return this.resourcePack;
    }

    public void addBlockStateModel(ResourceLocation location, JsonObject model) {
        String path = "assets/%s/blockstates/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, model);
    }

    public void addItemModel(ResourceLocation location, JsonObject model) {
        String path = "assets/%s/models/item/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, model);
    }

    public void addLang(ResourceLocation location, JsonObject lang) {
        String path = "assets/%s/lang/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, lang);
    }

    @Override
    public void clear() {
        File file = this.root.resolve("assets").toFile();
        if (file.isDirectory()) {
            file.delete();
        }
    }
}
