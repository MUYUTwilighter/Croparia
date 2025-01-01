package cool.muyucloud.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

public class TagUtil {
    public static JsonObject create() {
        JsonObject root = new JsonObject();
        root.addProperty("replace", false);
        root.add("values", new JsonArray());
        return root;
    }

    public static void addValue(@NotNull JsonObject tag, @NotNull String value) {
        JsonArray values = GsonHelper.getAsJsonArray(tag, "values");
        values.add(value);
    }

    public static ResourceLocation compatId(@NotNull String name) {
        if (Platform.isFabric()) return new ResourceLocation("c", name);
        else if (Platform.isForge()) return new ResourceLocation("forge", name);
        else return new ResourceLocation("minecraft", name);
    }
}
