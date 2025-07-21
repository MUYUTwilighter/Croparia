package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.util.Optional;

public class ResourcePackHandler extends PackHandler {
    public static final ResourcePackHandler INSTANCE = new ResourcePackHandler();

    private final PathPackResources resourcePack = new PathPackResources(
        new PackLocationInfo(
            "file/croparia",
            Component.literal("croparia"),
            PackSource.BUILT_IN,
            Optional.empty()
        ), CropariaIf.CONFIG.getPackPath()
    );

    @Override
    public void beforeReload() {
        super.beforeReload();
        CropariaIf.LOGGER.info("Generating resource pack data to file system");
        this.dump();
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
        File file =  CropariaIf.CONFIG.getPackPath().resolve("assets").toFile();
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
