package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.generator.DataGenerator;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class PackHandler {
    public static final Gson GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();

    protected final ResourceLocation id;
    protected final Path root;
    protected final JsonObject meta;
    protected final transient Supplier<Boolean> override;
    protected final transient Map<Path, String> cache = new HashMap<>();
    protected final transient Set<DataGenerator<?>> generators = new HashSet<>();

    public PackHandler(ResourceLocation id, Path path, JsonObject meta, Supplier<Boolean> override) {
        this.id = id;
        this.root = path;
        this.meta = meta;
        this.override = override;
        this.writeMeta();
    }

    public abstract void clear();

    public boolean canOverride() {
        return this.override.get();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void onTriggered() {
        this.writeMeta();
        if (this.canOverride()) {
            this.clear();
        }
        this.moveBuiltInGenerators();
        this.refreshGenerators();
        this.generate();
        this.dump();
    }

    protected void moveBuiltInGenerators() {
        File[] files = Platform.getModsFolder().toFile().listFiles();
        if (files == null) {
            LOGGER.error("Failed to list mods folder");
            return;
        }
        Path targetDir = this.root.resolve("generators");
        File targetDirFile = targetDir.toFile();
        if (!targetDirFile.isDirectory() && !targetDirFile.mkdirs()) {
            LOGGER.error("Failed to establish directory \"%s\"".formatted(targetDir));
        }
        String prefix = "data-generators/%s/%s/".formatted(this.getId().getNamespace(), this.getId().getPath());
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try (JarFile jar = new JarFile(file)) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (!entry.isDirectory() && name.startsWith(prefix) && name.endsWith(".cdg")) {
                            File target = targetDir.resolve(entry.getName().substring(prefix.length())).toFile();
                            File parent = targetDir.getParent().toFile();
                            if (!parent.isDirectory() && parent.mkdirs()) {
                                throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(parent));
                            }
                            if (!target.isFile() || Platform.isDevelopmentEnvironment()) {
                                try (OutputStream stream = new FileOutputStream(target)) {
                                    jar.getInputStream(entry).transferTo(stream);
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.error("Failed to move built-in generators", e);
                }
            }
        }
    }

    protected void refreshGenerators() {
        this.generators.clear();
        File parent = this.getRoot().resolve("generators").toFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(parent));
        }
        File[] files = parent.listFiles();
        if (files == null) throw new IllegalStateException("Failed to list directory \"%s\"".formatted(parent));
        for (File file : files) {
            try {
                DataGenerator<?> generator = DataGenerator.read(file);
                this.generators.add(generator);
            } catch (Throwable t) {
                LOGGER.error("Failed to read generator \"%s\"".formatted(file), t);
            }
        }

    }

    protected void generate() {
        for (DataGenerator<?> generator : this.generators) {
            generator.generate(this);
        }
    }

    protected void dump() {
        try {
            for (Map.Entry<Path, String> entry : this.cache.entrySet()) {
                this.writeFile(entry.getValue(), entry.getKey().toFile());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to write pack data to file system", e);
        }
        this.cache.clear();
    }

    protected void writeMeta() {
        try {
            this.writeFile(GSON.toJson(this.meta), this.root.resolve("pack.mcmeta").toFile());
        } catch (IOException e) {
            LOGGER.error("Failed to write pack metadata to file system", e);
        }
    }

    protected void writeFile(String content, File file) throws IOException {
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Failed to create directory");
        }
        if (!file.exists() || file.exists() && this.canOverride()) {
            FileWriter writer = new FileWriter(file);   // FileWriter will auto create the file if it doesn't exist
            writer.write(content);
            writer.close();
        }
    }

    public void addFile(String relative, String content) {
        Path path = this.root.resolve(relative);
        this.cache.put(path, content);
    }

    @SuppressWarnings("unused")
    public void addFile(String relative, JsonElement element) {
        this.addFile(relative, GSON.toJson(element));
    }

    public Path getRoot() {
        return this.root;
    }
}
