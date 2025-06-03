package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class AlwaysEnabledFileResourcePackProvider extends FolderRepositorySource {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final JsonObject META = new JsonObject();

    static {
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        pack.addProperty("description", "Croparia mandatory pack in %s.\nPlease do not modify data / assets folders!".formatted(CropariaIf.CONFIG.getPackPath()));
        META.add("pack", pack);
    }

    private final Path packsDir;
    private final PackType type;
    private final PackSource source;

    public AlwaysEnabledFileResourcePackProvider(Path packsDir, PackType type, PackSource source) {
        super(packsDir, type, source);
        this.packsDir = packsDir;
        this.type = type;
        this.source = source;
    }

    @Override
    public void loadPacks(Consumer<Pack> profileAdder) {
        try {
            FileUtil.createDirectoriesSafe(this.packsDir);
            String fileName = nameFromPath(this.packsDir);
            JsonWriter writer = new JsonWriter(new FileWriter(this.packsDir.resolve("pack.mcmeta").toFile()));
            new Gson().toJson(META, writer);
            writer.close();
            Pack.ResourcesSupplier packFactory = detectPackResources(this.packsDir, true);
            Pack datapackProfile = Pack.readMetaAndCreate(
                "file/" + fileName, Component.literal(fileName), true, packFactory, this.type,
                Pack.Position.BOTTOM, this.source
            );
            profileAdder.accept(datapackProfile);
        } catch (IOException e) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, e);
        }
    }

    private static String nameFromPath(Path path) {
        return path.getFileName().toString();
    }
}
