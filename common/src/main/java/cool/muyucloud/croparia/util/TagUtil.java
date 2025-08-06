package cool.muyucloud.croparia.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class TagUtil {
    public static <T> Optional<Registry<T>> getRegistry(ResourceKey<? extends Registry<T>> key) {
        Optional<? extends Registry<?>> maybeRegistry = BuiltInRegistries.REGISTRY.getOptional(key.location());
        if (maybeRegistry.isEmpty()) return Optional.empty();
        try {
            if (key.isFor(maybeRegistry.get().key())) {
                @SuppressWarnings("unchecked")
                Registry<T> registry = (Registry<T>) maybeRegistry.get();
                return Optional.of(registry);
            } else {
                return Optional.empty();
            }
        } catch (Throwable e) {
            return Optional.empty();
        }
    }

    public static <T> Iterable<Holder<T>> forEntries(TagKey<T> tag) {
        return getRegistry(tag.registry()).map(registry -> registry.getTagOrEmpty(tag)).orElse(List.of());
    }

    public static <T> Iterable<Holder<T>> forEntries(ResourceKey<? extends Registry<T>> registry, ResourceLocation id) {
        return forEntries(TagKey.create(registry, id));
    }

    public static <T> boolean isIn(ResourceKey<? extends Registry<T>> registry, ResourceLocation id, @NotNull T entry) {
        return isIn(TagKey.create(registry, id), entry);
    }

    public static <T> boolean isIn(@NotNull TagKey<T> tagKey, @NotNull T entry) {
        Optional<Registry<T>> maybeRegistry = getRegistry(tagKey.registry());
        if (maybeRegistry.isEmpty()) return false;
        Registry<T> registry = maybeRegistry.get();
        Optional<ResourceKey<T>> maybeKey = registry.getResourceKey(entry);
        return maybeKey.map(tResourceKey -> registry.getOrThrow(tResourceKey).is(tagKey)).orElse(false);
    }
}