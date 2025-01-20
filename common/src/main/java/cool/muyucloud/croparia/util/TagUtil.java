package cool.muyucloud.croparia.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.architectury.platform.Platform;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

    public static Iterable<Holder<Item>> forItems(TagKey<Item> tag) {
        return Registry.ITEM.getTagOrEmpty(tag);
    }

    public static Iterable<Holder<Block>> forBlocks(TagKey<Block> tag) {
        return Registry.BLOCK.getTagOrEmpty(tag);
    }
}
