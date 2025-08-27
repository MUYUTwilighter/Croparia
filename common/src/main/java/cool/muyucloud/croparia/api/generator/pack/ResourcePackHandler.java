package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.FileUtil;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ResourcePackHandler extends PackHandler {
    public static final Map<ResourceLocation, ResourcePackHandler> REGISTRY = new HashMap<>();

    public static <P extends ResourcePackHandler> P register(P pack) {
        REGISTRY.put(pack.getId(), pack);
        return pack;
    }

    public static ResourcePackHandler register(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        return register(new ResourcePackHandler(id, path, meta, override));
    }

    private final PathPackResources resourcePack = new PathPackResources(
        new PackLocationInfo(
            this.getId().toString(),
            Texts.literal(this.getId().toString()),
            PackSource.BUILT_IN,
            Optional.empty()
        ), getRoot()
    );

    public ResourcePackHandler(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        super(id, path, meta, override);
    }

    public PackResources getResourcePack() {
        return this.resourcePack;
    }

    @Override
    public void clear() {
        File file = this.getRoot().resolve("assets").toFile();
        if (file.isDirectory()) {
            CropariaIf.LOGGER.info("Clearing resource pack directory");
            try {
                FileUtil.deleteUnder(file);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to clear resourcepack directory", e);
            }
        }
    }

    @Override
    public void addFile(String relative, String content) {
        super.addFile("assets/" + relative, content);
    }
}
