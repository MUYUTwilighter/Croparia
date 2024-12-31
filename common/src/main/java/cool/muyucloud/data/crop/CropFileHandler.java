package cool.muyucloud.data.crop;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
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

    public static void saveCrop(Crop crop, List<List<String>> dependencies) {
        File file = CropariaIf.CONFIG.getCropPath().resolve(crop.getName() + ".json").toFile();
        if (file.exists() && !CropariaIf.CONFIG.getOverride()) {
            return;
        }
        try (JsonWriter writer = new JsonWriter(new FileWriter(file))) {
            writer.setIndent("  ");
            RawCrop raw = new RawCrop(
                crop.getName(), crop.getMaterial().toString(), null, crop.getType().getModelName(),
                crop.getTranslationKey(), "0x%X%n".formatted(crop.getColor()), crop.getTier(), crop.getTranslations(),
                dependencies
            );
            GSON.toJson(raw, raw.getClass(), writer);
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Failed to save crop {}", crop.getName(), e);
        }
    }
}
