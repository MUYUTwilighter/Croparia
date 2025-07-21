package cool.muyucloud.croparia.api.generator.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class AlwaysEnabledFileResourcePackProvider extends FolderRepositorySource {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path packsDir;
    private final PackType type;
    private final PackSource source;

    public AlwaysEnabledFileResourcePackProvider(Path packsDir, PackType type, PackSource source) {
        super(packsDir, type, source, null);
        this.packsDir = packsDir;
        this.type = type;
        this.source = source;
    }

    @Override
    public void loadPacks(Consumer<Pack> profileAdder) {
        try {
            String fileName = nameFromPath(this.packsDir);
            Pack.ResourcesSupplier packFactory = new PathPackResources.PathResourcesSupplier(this.packsDir);
            PackLocationInfo info = new PackLocationInfo("file/" + fileName, Component.literal(fileName), this.source, Optional.empty());
            PackSelectionConfig config = new PackSelectionConfig(true, Pack.Position.BOTTOM, false);
            Pack datapackProfile = Pack.readMetaAndCreate(info, packFactory, this.type, config);
            if (datapackProfile != null) {
                profileAdder.accept(datapackProfile);
            }
        } catch (Throwable t) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, t);
        }
    }

    private static String nameFromPath(Path path) {
        return path.getFileName().toString();
    }
}
