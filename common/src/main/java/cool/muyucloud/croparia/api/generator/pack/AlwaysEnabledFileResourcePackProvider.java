package cool.muyucloud.croparia.api.generator.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AlwaysEnabledFileResourcePackProvider extends FolderRepositorySource {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path packsDir;
    private final PackSource source;

    public AlwaysEnabledFileResourcePackProvider(Path packsDir, PackSource source) {
        super(packsDir.toFile(), source);
        this.packsDir = packsDir;
        this.source = source;
    }

    public void loadPacks(Consumer<Pack> consumer, Pack.PackConstructor packConstructor) {
        try {
            String fileName = nameFromPath(this.packsDir);
            Supplier<PackResources> resources = () -> new FolderPackResources(this.packsDir.toFile());
            Pack pack = Pack.create(fileName, true, resources, packConstructor, Pack.Position.BOTTOM, this.source);
            consumer.accept(pack);
        } catch (Exception e) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, e);
        }
    }

    private static String nameFromPath(Path path) {
        return path.getFileName().toString();
    }
}
