package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.JsonObject;
import com.mojang.bridge.game.PackType;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.DataGenerator;
import cool.muyucloud.croparia.kubejs.DataGeneratorCreator;
import cool.muyucloud.croparia.util.Util;
import dev.architectury.platform.Platform;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackSource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler(CropariaIf.CONFIG.getPackPath());

    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        root, PackSource.BUILT_IN
    );
    private final List<DataGenerator> generators = new LinkedList<>();

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
        for (DataGenerator generator : this.generators) {
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
        return SharedConstants.getCurrentVersion().getPackVersion(PackType.DATA);
    }

    public void moveBuiltInGenerators() {
        try {
            Path targetDir = this.root.resolve("generators");
            File targetDirFile = targetDir.toFile();
            if (!targetDirFile.isDirectory() && !targetDirFile.mkdirs()) {
                throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(targetDir));
            }
            Enumeration<URL> urls = CropariaIf.class.getClassLoader().getResources("croparia-if-generators");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String jarPath = unifyUrl(url);
                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().startsWith("croparia-if-generators/") && !entry.isDirectory()) {
                            String filename = entry.getName().substring("croparia-if-generators/".length());
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

    private static @NotNull String unifyUrl(URL url) {
        // Compat with encoding
        String urlPath;
        try {
            urlPath = URLDecoder.decode(url.getPath(), Charset.defaultCharset());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to decode URL", e);
        }
        // Compat with platform
        String jarPath;
        if (Platform.isFabric()) {
            if (url.getProtocol().equals("jar")) {
                jarPath = urlPath.substring(5, url.getPath().indexOf("!"));
            } else {
                throw new IllegalStateException("Unsupported protocol: %s".formatted(url.getProtocol()));
            }
        } else if (Platform.isForge()) {
            if (url.getProtocol().equals("union")) {
                jarPath = urlPath.substring(1, url.getPath().indexOf("%"));
            } else {
                throw new IllegalStateException("Unsupported protocol: %s".formatted(url.getProtocol()));
            }
        } else {
            throw new IllegalStateException("You are running Croparia IF on an unsupported platform");
        }
        // Compat with file system that require "/" prefix
        if (!Path.of(jarPath).isAbsolute()) {
            jarPath = "/" + jarPath;
        }
        return jarPath;
    }

    private void addGenerator(DataGenerator generator) {
        this.generators.add(generator);
    }

    public void readGenerators() {
        try {
            this.generators.clear();
            File root = this.root.resolve("generators").toFile();
            if (!root.isDirectory() && !root.mkdirs()) {
                throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(root));
            }
            for (File file : Objects.requireNonNull(root.listFiles())) {
                if (file.isFile()) {
                    DataGenerator.read(file.toPath()).ifPresent(this::addGenerator);
                }
            }
            DataGeneratorCreator.flushInto(this::addGenerator);
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to read generators", e);
        }
    }
}
