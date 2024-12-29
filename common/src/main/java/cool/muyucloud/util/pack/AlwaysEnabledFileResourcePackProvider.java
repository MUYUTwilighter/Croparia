package cool.muyucloud.util.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.FileUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class AlwaysEnabledFileResourcePackProvider extends FolderRepositorySource {
    private static final Logger LOGGER = LogUtils.getLogger();
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
            Pack.ResourcesSupplier packFactory = detectPackResources(this.packsDir, true);
            String fileName = nameFromPath(this.packsDir);
            Pack datapackProfile = Pack.readMetaAndCreate(
                "file/" + fileName, Component.literal(fileName),
                true, packFactory, this.type,
                Pack.Position.BOTTOM, this.source);
            if (datapackProfile != null) {
                profileAdder.accept(datapackProfile);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, e);
        }
    }

    private static String nameFromPath(Path path) {
        return path.getFileName().toString();
    }
}
