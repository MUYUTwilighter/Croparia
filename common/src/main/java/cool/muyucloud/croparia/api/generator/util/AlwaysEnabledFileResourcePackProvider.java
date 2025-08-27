package cool.muyucloud.croparia.api.generator.util;

import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.FileUtil;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class AlwaysEnabledFileResourcePackProvider extends FolderRepositorySource {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final String name;
    private final Path packsDir;
    private final PackType type;
    private final PackSource source;

    public AlwaysEnabledFileResourcePackProvider(String name, Path packsDir, PackType type, PackSource source) {
        super(packsDir, type, source, null);
        this.name = name;
        this.packsDir = packsDir;
        this.type = type;
        this.source = source;
    }

    @Override
    public void loadPacks(Consumer<Pack> profileAdder) {
        try {
            FileUtil.createDirectoriesSafe(this.packsDir);
            Pack.ResourcesSupplier packFactory = new PathPackResources.PathResourcesSupplier(this.packsDir);
            PackLocationInfo info = new PackLocationInfo(name, Texts.literal(name), this.source, Optional.empty());
            PackSelectionConfig config = new PackSelectionConfig(true, Pack.Position.BOTTOM, false);
            Pack datapackProfile = Pack.readMetaAndCreate(info, packFactory, this.type, config);
            if (datapackProfile != null) {
                profileAdder.accept(datapackProfile);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, e);
        }
    }
}
