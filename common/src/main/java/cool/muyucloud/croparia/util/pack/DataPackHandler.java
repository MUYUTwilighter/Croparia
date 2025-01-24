package cool.muyucloud.croparia.util.pack;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.generator.DataGenerator;
import cool.muyucloud.croparia.kubejs.DataGeneratorCreator;
import cool.muyucloud.croparia.util.Util;
import dev.architectury.platform.Platform;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler(CropariaIf.CONFIG.getPackPath());

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        root, PackSource.BUILT_IN
    );
    private final Map<Integer, DataGenerator> generators = new HashMap<>();

    @Override
    public void onInitial() {
        super.onInitial();
        if (CropariaIf.CONFIG.getOverride()) {
            this.clear();
        }
    }

    @Override
    public void onSecondary() {
        this.moveBuiltInGenerators();
        this.readGenerators();
        super.onInitial();
        CropariaIf.LOGGER.info("Performing extra data pack reload");
        this.dump();
    }

    @Override
    protected void generate() {
        super.generate();
        for (DataGenerator generator : this.generators.values()) {
            generator.generate(this.root.resolve("data"));
        }
    }

    public DataPackHandler(Path path) {
        super(path);
    }

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    public void addItemTag(ResourceLocation location, JsonObject tag) {
        String path = "data/%s/tags/items/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, tag);
    }

    public void addBlockTag(ResourceLocation location, JsonObject tag) {
        String path = "data/%s/tags/blocks/%s.json".formatted(location.getNamespace(), location.getPath());
        this.addFile(path, tag);
    }

    @Override
    public void clear() {
        Path path = this.root.resolve("data");
        File file = path.toFile();
        if (file.isDirectory()) {
            CropariaIf.LOGGER.info("Clearing data pack directory");
            try {
                Util.deleteDir(file);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to clear data pack directory", e);
            }
        }
    }

    @Override
    protected int getPackVersion() {
        return SharedConstants.getCurrentVersion().getPackVersion(com.mojang.bridge.game.PackType.DATA);
    }

    public void moveBuiltInGenerators() {
        try {
            Path targetDir = this.root.resolve("generators");
            File targetDirFile = targetDir.toFile();
            if (!targetDirFile.isDirectory() && !targetDirFile.mkdirs()) {
                throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(targetDir));
            }
            URL url = CropariaIf.class.getClassLoader().getResource("generators");
            assert url != null : "Built-in generator directory not found";
            if (url.getProtocol().equals("jar")) {
                String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().startsWith("generators/") && !entry.isDirectory()) {
                            String filename = entry.getName().substring("generators/".length());
                            File targetFile = targetDir.resolve(filename).toFile();
                            if (!targetFile.isFile() || Platform.isDevelopmentEnvironment()) {
                                try (OutputStream stream = new FileOutputStream(targetFile)) {
                                    jar.getInputStream(entry).transferTo(stream);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to move built-in generators", e);
        }
    }

    private void saveGeneratorIfAbsent(DataGenerator generator) {
        Integer hash = generator.hashCode();
        if (this.generators.containsKey(hash)) {
            CropariaIf.LOGGER.warn("Skip generator with same path: %s".formatted(generator.path()));
        }
        this.generators.put(hash, generator);
    }

    public void readGenerators() {
        try {
            this.generators.clear();
            DataGeneratorCreator.dumpInto((hash, generator) -> this.saveGeneratorIfAbsent(generator));
            DataGeneratorCreator.clearCache();
            File root = this.root.resolve("generators").toFile();
            if (!root.isDirectory() && !root.mkdirs()) {
                throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(root));
            }
            for (File file : Objects.requireNonNull(root.listFiles())) {
                if (file.isFile()) {
                    DataGenerator.read(file.toPath()).ifPresent(this::saveGeneratorIfAbsent);
                }
            }
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to read generators", e);
        }
    }
}
