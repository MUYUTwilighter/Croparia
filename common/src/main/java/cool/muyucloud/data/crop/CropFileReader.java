package cool.muyucloud.data.crop;

import com.google.gson.Gson;
import cool.muyucloud.CropariaIf;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CropFileReader {
    private static final Gson GSON = new Gson();

    public static List<RawCrop> readCrops() {
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
}
