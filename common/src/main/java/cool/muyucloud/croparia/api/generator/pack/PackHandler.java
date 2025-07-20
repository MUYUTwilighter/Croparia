package cool.muyucloud.croparia.api.generator.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import net.minecraft.SharedConstants;
import net.minecraft.server.packs.PackType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class PackHandler {
    public static final Gson GSON = new Gson();
    public static final JsonObject META = new JsonObject();

    static {
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        pack.addProperty("description", "Croparia mandatory pack in %s.\nPlease do not modify data / assets folders!".formatted(CropariaIf.CONFIG.getPackPath()));
        META.add("pack", pack);
    }

    protected final Map<Path, JsonElement> cache = new HashMap<>();
    protected final Set<Runnable> GENERATORS = new HashSet<>();

    public void beforeReload() {
        if (CropariaIf.CONFIG.getOverride()) {
            this.clear();
        }
    }

    public abstract void clear();

    protected void writeJson(JsonElement element, File file) throws IOException {
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Failed to create parent directory");
        }
        if (!file.exists() || file.exists() && CropariaIf.CONFIG.getOverride()) {
            FileWriter writer = new FileWriter(file);   // FileWriter will auto create the file if it doesn't exist
            GSON.toJson(element, writer);
            writer.close();
        }
    }

    public void dump() {
        try {
            this.generate();
            for (Map.Entry<Path, JsonElement> entry : this.cache.entrySet()) {
                this.writeJson(entry.getValue(), entry.getKey().toFile());
            }
        } catch (Exception e) {
            CropariaIf.LOGGER.error("Failed to write pack data to file system", e);
        }
        this.cache.clear();
    }

    public void addFile(String relative, JsonElement element) {
        Path path = CropariaIf.CONFIG.getPackPath().resolve(relative);
        this.cache.put(path, element);
    }

    protected void generate() {
        this.cache.clear();
        for (Runnable generator : this.GENERATORS) {
            generator.run();
        }
    }

    public void registerGenerator(Runnable generator) {
        this.GENERATORS.add(generator);
    }
}
