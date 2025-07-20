package cool.muyucloud.croparia.api.generator.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

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
            String fileName = nameFromPath(this.packsDir);
            Pack.ResourcesSupplier packFactory = detectPackResources(this.packsDir, true);
            Pack datapackProfile = Pack.readMetaAndCreate(
                "file/" + fileName, Component.literal(fileName), true, packFactory, this.type,
                Pack.Position.BOTTOM, this.source
            );
            profileAdder.accept(datapackProfile);
        } catch (Exception e) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, e);
        }
    }

    private static String nameFromPath(Path path) {
        return path.getFileName().toString();
    }
}
