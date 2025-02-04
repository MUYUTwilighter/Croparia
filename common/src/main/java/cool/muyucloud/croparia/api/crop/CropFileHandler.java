package cool.muyucloud.croparia.api.crop;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import cool.muyucloud.croparia.CropariaIf;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CropFileHandler {
    private static final Gson GSON = new Gson();

    public static @NotNull List<RawCrop> readCrops() {
        List<RawCrop> crops = new LinkedList<>();
        File folder = CropariaIf.CONFIG.getCropPath().toFile();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null) {
                for (File file : files) {
                    Optional<RawCrop> crop = readCrop(file);
                    crop.ifPresent(crops::add);
                }
            }
        }
        return crops;
    }

    public static Optional<RawCrop> readCrop(File file) {
        try (FileReader reader = new FileReader(file)) {
            return Optional.ofNullable(GSON.fromJson(reader, RawCrop.class));
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Invalid crop file \"%s\"".formatted(file), e);
            return Optional.empty();
        }
    }

    public static void dumpCrops() {
        Path dir = CropariaIf.CONFIG.getDumpPath().resolve("crops");
        File dirFile = dir.toFile();
        if (!dirFile.isDirectory() && !dirFile.mkdirs()) {
            throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(dir));
        }
        Crops.forEachCrop(crop -> {
            try (JsonWriter writer = new JsonWriter(new FileWriter(dir.resolve(crop.getName() + ".json").toFile()))) {
                writer.setIndent("  ");
                GSON.toJson(crop.toJson(), writer);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to dump crop \"%s\"".formatted(crop.getName()), e);
            }
        });
    }

    public static void dumpBuiltinCrops() {
        Path dir = CropariaIf.CONFIG.getDumpPath().resolve("builtin_crops");
        File dirFile = dir.toFile();
        if (!dirFile.isDirectory() && !dirFile.mkdirs()) {
            throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(dir));
        }
        Crops.forEachBuiltinCrop(crop -> {
            try (JsonWriter writer = new JsonWriter(new FileWriter(dir.resolve(crop.getName() + ".json").toFile()))) {
                writer.setIndent("  ");
                GSON.toJson(crop.toJson(), writer);
            } catch (Throwable e) {
                CropariaIf.LOGGER.error("Failed to dump crop \"%s\"".formatted(crop.getName()), e);
            }
        });
    }

    public static boolean dumpCrop(@NotNull Crop crop) {
        Path dir = CropariaIf.CONFIG.getDumpPath().resolve("crops");
        File dirFile = dir.toFile();
        if (!dirFile.isDirectory() && !dirFile.mkdirs()) {
            throw new IllegalStateException("Failed to establish directory \"%s\"".formatted(dir));
        }
        try (JsonWriter writer = new JsonWriter(new FileWriter(dir.resolve(crop.getName() + ".json").toFile()))) {
            writer.setIndent("  ");
            GSON.toJson(crop.toJson(), writer);
            return true;
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to dump crop \"%s\"".formatted(crop.getName()), e);
        }
        return false;
    }
}
