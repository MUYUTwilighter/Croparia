package cool.muyucloud.util.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import net.minecraft.SharedConstants;

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

    protected final Path root;
    protected final Map<Path, JsonElement> cache = new HashMap<>();
    protected final Set<Runnable> GENERATORS = new HashSet<>();

    public boolean beforeLoad() {
        try {
            File file = this.root.resolve("pack.mcmeta").toFile();
            this.writeJson(this.generateMetaFile(), file);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return true;
    }

    public abstract boolean afterLoad();

    public abstract void clear();

    public PackHandler(Path path) {
        this.root = path;
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
        Path path = this.root.resolve(relative);
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
