package cool.muyucloud.data.crop;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.CropariaIf;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
            CropariaIf.LOGGER.error("Failed to read crop file {}", file, e);
            return Optional.empty();
        }
    }

    public static void saveCompatCrop(CompatCrop crop) {
        File cropPath = CropariaIf.CONFIG.getCropPath().toFile();
        if (!cropPath.exists() || !cropPath.isDirectory()) {
            cropPath.mkdirs();
        }
        File file = CropariaIf.CONFIG.getCropPath().resolve(crop.getName() + ".json").toFile();
        if (file.exists() && !CropariaIf.CONFIG.getOverride()) {
            return;
        }
        try (JsonWriter writer = new JsonWriter(new FileWriter(file))) {
            writer.setIndent("  ");
            JsonObject json = CompatCrop.CODEC.encodeStart(JsonOps.INSTANCE, crop).getOrThrow(false, msg -> {
                throw new RuntimeException(msg);
            }).getAsJsonObject();
            GSON.toJson(json, writer);
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Failed to save crop {}", crop.getName(), e);
        }
    }
}
