package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.generator.DataGenerator;
import cool.muyucloud.croparia.api.generator.util.JarJarEntry;
import cool.muyucloud.croparia.util.FileUtil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

public abstract class PackHandler {
    public static final Gson GSON = new Gson();

    @ExpectPlatform
    public static Map<ResourceLocation, Collection<JarJarEntry>> getBuiltinGenerators() {
        throw new NotImplementedException("Not implemented");
    }

    protected final ResourceLocation id;
    protected final Path root;
    protected final JsonObject meta;
    protected final transient Supplier<Boolean> override;
    protected final transient Map<Path, String> cache = new HashMap<>();
    protected final transient Set<DataGenerator> generators = new HashSet<>();

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
        Path targetRoot = this.getRoot().resolve("generators");
        File targetRootFile = targetRoot.toFile();
        if (!targetRootFile.isDirectory() && !targetRootFile.mkdirs()) {
            DataGenerator.LOGGER.error("Failed to establish directory \"%s\"".formatted(targetRoot));
        }
        String prefix = "data-generators/%s/%s/".formatted(this.getId().getNamespace(), this.getId().getPath());
        getBuiltinGenerators().get(this.getId()).forEach(entry -> {
            String name = entry.getEntry().getName();
            Path targetPath = targetRoot.resolve(name.substring(prefix.length()));
            File target = targetPath.toFile();
            File parent = targetPath.getParent().toFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
                DataGenerator.LOGGER.error("Failed to establish directory \"%s\"".formatted(parent));
                return;
            }
            if (!target.isFile() || Platform.isDevelopmentEnvironment()) {
                try (OutputStream stream = new FileOutputStream(target)) {
                    entry.forInputStream(input -> {
                        try {
                            input.transferTo(stream);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    DataGenerator.LOGGER.error("Failed to move built-in generator \"%s\" from %s".formatted(name, entry.getFile().getName()), e);
                }
            }
        });
    }

    protected void refreshGenerators() {
        this.generators.clear();
        File parent = this.getRoot().resolve("generators").toFile();
        try {
            FileUtil.forFilesIn(parent, file -> {
                try {
                    DataGenerator generator = DataGenerator.read(file);
                    this.generators.add(generator);
                } catch (Throwable t) {
                    DataGenerator.LOGGER.error("Failed in reading generator \"%s\"".formatted(file), t);
                }
            });
        } catch (Throwable t) {
            DataGenerator.LOGGER.error("Failed in reading generators from \"%s\"".formatted(parent), t);
        }
    }

    protected void generate() {
        for (DataGenerator generator : this.generators) {
            generator.generate(this);
        }
    }

    protected void dump() {
        try {
            for (Map.Entry<Path, String> entry : this.cache.entrySet()) {
                this.writeFile(entry.getValue(), entry.getKey().toFile());
            }
        } catch (Exception e) {
            DataGenerator.LOGGER.error("Failed to write pack data to file system", e);
        }
        this.cache.clear();
    }

    protected void writeMeta() {
        try {
            this.writeFile(GSON.toJson(this.meta), this.root.resolve("pack.mcmeta").toFile());
        } catch (IOException e) {
            DataGenerator.LOGGER.error("Failed to write pack metadata to file system", e);
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
