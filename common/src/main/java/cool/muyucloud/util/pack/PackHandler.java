package cool.muyucloud.util.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import cool.muyucloud.CropariaIf;
import net.minecraft.SharedConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class PackHandler {
    public static final Gson GSON = new Gson();

    protected final Path root;
    protected final Map<Path, JsonElement> cache = new HashMap<>();

    public PackHandler(Path path) {
        this.root = path;
        this.addMetaFile();
    }

    protected void addMetaFile() {
        Path path = this.root.resolve("pack.mcmeta");
        this.addFile(path.toString(), this.generateMetaFile());
    }

    protected JsonObject generateMetaFile() {
        JsonObject root = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", SharedConstants.RESOURCE_PACK_FORMAT);
        pack.addProperty("description",
            "Croparia mandatory pack in %s.\nPlease do not modify data / assets folders!".formatted(this.root));
        root.add("pack", pack);
        return root;
    }

    protected void writeJson(JsonElement element, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter writer = new FileWriter(file);   // FileWriter will auto create the file if it doesn't exist
        GSON.toJson(element, writer);
        writer.close();
    }

    protected abstract void clear();

    public void flushCache() {
        try {
            if (CropariaIf.CONFIG.getOverride()) {
                this.clear();
            }
            for (Map.Entry<Path, JsonElement> entry : this.cache.entrySet()) {
                this.writeJson(entry.getValue(), entry.getKey().toFile());
            }
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Failed to write pack data to file system", e);
        }
        this.cache.clear();
    }

    public void addFile(String relative, JsonElement element) {
        Path path = this.root.resolve(relative);
        this.cache.put(path, element);
    }
}
