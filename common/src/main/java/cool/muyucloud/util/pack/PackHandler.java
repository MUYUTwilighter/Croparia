package cool.muyucloud.util.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.SharedConstants;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

public class PackHandler {
    public static final Gson GSON = new Gson();

    protected final Path root;

    public PackHandler(Path path) {
        this.root = path;
        this.addMetaFile();
    }

    protected void addMetaFile() {
        Path path = this.root.resolve("pack.mcmeta");
        this.writeJson(GSON.toJsonTree(this.generateMetaFile()), path.toFile());
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

    protected void writeJson(JsonElement element, File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {    // FileWriter will auto create the file if it doesn't exist
            JsonWriter jsonWriter = new JsonWriter(writer);
            GSON.toJson(element, writer);
            jsonWriter.close();
        } catch (Exception ignored) {
        }
    }
}
