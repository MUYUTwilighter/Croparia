package cool.muyucloud.croparia.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;

import java.io.File;
import java.nio.file.Path;

public class ResourcePackHandler extends PackHandler {
    public static final ResourcePackHandler INSTANCE = new ResourcePackHandler(CropariaIf.CONFIG.getPackPath());

    private final PathPackResources resourcePack = new PathPackResources("croparia", root, true);

    @Override
    public void onInitial() {
        super.onInitial();
        if (CropariaIf.CONFIG.getOverride()) {
            this.clear();
        }
        this.dump();
    }

    @Override
    public void onSecondary() {
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
            CropariaIf.LOGGER.info("Clearing resource pack directory");
            try {
                Util.deleteDir(file);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to clear resource pack directory", e);
            }
        }
    }
}
