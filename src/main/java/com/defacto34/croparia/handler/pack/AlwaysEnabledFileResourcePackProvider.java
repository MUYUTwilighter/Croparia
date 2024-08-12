package com.defacto34.croparia.handler.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import net.minecraft.util.PathUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class AlwaysEnabledFileResourcePackProvider extends FileResourcePackProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path packsDir;
    private final ResourceType type;
    private final ResourcePackSource source;

    public AlwaysEnabledFileResourcePackProvider(Path packsDir, ResourceType type, ResourcePackSource source) {
        super(packsDir, type, source);
        this.packsDir = packsDir;
        this.type = type;
        this.source = source;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        try {
            PathUtil.createDirectories(this.packsDir);
            ResourcePackProfile.PackFactory packFactory = getFactory(this.packsDir, true);
            String fileName = getFileName(this.packsDir);
            ResourcePackProfile datapackProfile = ResourcePackProfile.create(
                "file/" + fileName, Text.literal(fileName),
                true, packFactory, this.type,
                ResourcePackProfile.InsertionPosition.BOTTOM, this.source);
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
