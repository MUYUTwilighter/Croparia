package cool.muyucloud.croparia.api.crop;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.util.DgRegistry;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CropRegistry<C extends AbstractCrop> implements DgRegistry<C> {
    public static final CropRegistry<Crop> CROPS = new CropRegistry<>(CropariaIf.CONFIG.getFilePath().resolve("crops"), Crop.CODEC);

    private static final Gson GSON = new Gson();

    private final Path path;
    private final Codec<C> codec;
    private final Map<ResourceLocation, C> all = new HashMap<>();
    private final Map<ResourceLocation, C> loaded = new HashMap<>();

    public CropRegistry(Path path, Codec<C> codec) {
        this.path = path;
        this.codec = codec;
    }

    public Path getPath() {
        return path;
    }

    public Codec<C> getCodec() {
        return codec;
    }

    public void forLoaded(Consumer<C> consumer) {
        this.loaded.values().forEach(consumer);
    }

    public void register(C crop) {
        this.all.put(crop.getKey(), crop);
        if (crop.shouldLoad()) {
            crop.onRegister();
            loaded.put(crop.getKey(), crop);
        } else {
            loaded.remove(crop.getKey());
        }
    }

    public void readCrops() {
        File file = this.getPath().toFile();
        if (!file.isDirectory() && !file.mkdirs()) {
            throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(file));
        }
        this.readCrops(this.getPath());
    }

    protected void readCrops(Path parent) {
        try (Stream<Path> paths = Files.list(parent)) {
            paths.forEach(path -> {
                File file = path.toFile();
                if (file.isFile()) {
                    readCrop(file);
                } else {
                    readCrops(path);
                }
            });
        } catch (Throwable t) {
            CropariaIf.LOGGER.error("Failed to read crops", t);
        }
    }

    protected void readCrop(File file) {
        if (!file.getName().endsWith(".json")) return;
        try (FileReader reader = new FileReader(file)) {
            JsonElement json = GSON.fromJson(reader, JsonElement.class);
            C crop = CodecUtil.decodeJson(json, this.getCodec());
            all.put(crop.getKey(), crop);
            if (crop.shouldLoad()) {
                crop.onRegister();
                loaded.put(crop.getKey(), crop);
            }
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Invalid crop file \"%s\"".formatted(file), e);
        }
    }

    public void dumpCrops() {
        Path dir = this.getPath();
        File dirFile = dir.toFile();
        if (!dirFile.isDirectory() && !dirFile.mkdirs()) {
            throw new IllegalStateException("Failed to create directory " + dir);
        }
        all.values().forEach(crop -> {
            try (JsonWriter writer = new JsonWriter(new FileWriter(dir.resolve(crop.getKey().toString().replace(":", "/") + ".json").toFile()))) {
                writer.setIndent("  ");
                GSON.toJson(CodecUtil.encodeJson(crop, this.getCodec()), writer);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to dump crop \"%s\"".formatted(crop.getKey()), e);
            }
        });
    }

    public Path dumpCrop(@NotNull Crop crop) {
        Path dir = this.getPath();
        File dirFile = dir.toFile();
        if (!dirFile.isDirectory() && !dirFile.mkdirs()) {
            throw new IllegalStateException("Failed to create directory " + dir);
        }
        Path cropPath = dir.resolve(crop.getKey().toString().replace(":", "/") + ".json");
        try (JsonWriter writer = new JsonWriter(new FileWriter(cropPath.toFile()))) {
            writer.setIndent("  ");
            GSON.toJson(CodecUtil.encodeJson(crop, Crop.CODEC), writer);
            return cropPath;
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to dump crop \"%s\"".formatted(crop.getKey()), e);
        }
        return null;
    }

    public boolean exists(ResourceLocation name) {
        return all.containsKey(name);
    }

    @NotNull
    @Override
    public Iterator<C> iterator() {
        return this.all.values().iterator();
    }

    @Override
    public Optional<C> forName(ResourceLocation id) {
        return Optional.ofNullable(this.all.get(id));
    }

    public int size() {
        return this.all.size();
    }
}
