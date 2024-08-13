package com.defacto34.croparia.handler.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import net.minecraft.util.PathUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public class AlwaysEnabledFileResourcePackProvider extends FileResourcePackProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path packsDir;
    private final ResourceType type;
    private final ResourcePackSource source;

    public AlwaysEnabledFileResourcePackProvider(Path packsDir, ResourceType type, ResourcePackSource source) {
        super(packsDir, type, source, null);
        this.packsDir = packsDir;
        this.type = type;
        this.source = source;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        try {
            PathUtil.createDirectories(this.packsDir);
            ResourcePackProfile.PackFactory packFactory = new DirectoryResourcePack.DirectoryBackedFactory(this.packsDir);
            String fileName = getFileName(this.packsDir);
            ResourcePackInfo info = new ResourcePackInfo("file/" + fileName, Text.literal(fileName), this.source, Optional.empty());
            ResourcePackPosition position = new ResourcePackPosition(true, ResourcePackProfile.InsertionPosition.BOTTOM, false);
            ResourcePackProfile datapackProfile = ResourcePackProfile.create(info, packFactory, this.type, position);
            if (datapackProfile != null) {
                profileAdder.accept(datapackProfile);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to list packs in {}", this.packsDir, e);
        }
    }



    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
