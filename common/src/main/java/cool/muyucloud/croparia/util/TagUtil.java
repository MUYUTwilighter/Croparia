package cool.muyucloud.croparia.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
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

    public static Iterable<Holder<Item>> forItems(TagKey<Item> tag) {
        return BuiltInRegistries.ITEM.getTagOrEmpty(tag);
    }

    public static Iterable<Holder<Block>> forBlocks(TagKey<Block> tag) {
        return BuiltInRegistries.BLOCK.getTagOrEmpty(tag);
    }


    public static <T> boolean isIn(TagKey<T> tagKey, T entry) {
        Optional<? extends Registry<?>> maybeRegistry;
        Objects.requireNonNull(tagKey);
        Objects.requireNonNull(entry);
        maybeRegistry = BuiltInRegistries.REGISTRY.getOptional(tagKey.registry().location());
        if (maybeRegistry.isPresent()) {
            if (tagKey.isFor(maybeRegistry.get().key())) {
                @SuppressWarnings("unchecked")
                Registry<T> registry = (Registry<T>) maybeRegistry.get();
                Optional<ResourceKey<T>> maybeKey = registry.getResourceKey(entry);
                if (maybeKey.isPresent()) {
                    return registry.getHolderOrThrow(maybeKey.get()).is(tagKey);
                }
            }
        }
        return false;
    }
}