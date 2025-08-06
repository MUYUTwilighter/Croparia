package cool.muyucloud.croparia.api.generator.util;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public interface DgRegistry<E extends DgElement> extends Iterable<E> {
    Map<ResourceLocation, DgRegistry<? extends DgElement>> REGISTRY = new HashMap<>();
    Map<DgRegistry<? extends DgElement>, ResourceLocation> BY_INSTANCE = new HashMap<>();
    Codec<DgRegistry<? extends DgElement>> CODEC = ResourceLocation.CODEC.xmap(REGISTRY::get, BY_INSTANCE::get);

    static <E extends DgElement, T extends DgRegistry<E>> T register(ResourceLocation id, T iterable) {
        REGISTRY.put(id, iterable);
        BY_INSTANCE.put(iterable, id);
        return iterable;
    }

    static <E extends DgElement> DgRegistry<E> map(Map<ResourceLocation, E> map) {
        return new WrapMap<>(map);
    }

    default Optional<E> forName(ResourceLocation id) {
        for (E e : this) {
            if (e.getKey().equals(id)) return Optional.of(e);
        }
        return Optional.empty();
    }

    default ResourceLocation getId() {
        return BY_INSTANCE.get(this);
    }

    class WrapMap<E extends DgElement> implements DgRegistry<E> {
        private final Map<ResourceLocation, E> map;

        public WrapMap(Map<ResourceLocation, E> map) {
            this.map = map;
        }

        @Override
        public @NotNull Iterator<E> iterator() {
            return this.map.values().iterator();
        }

        @Override
        public Optional<E> forName(ResourceLocation name) {
            return Optional.ofNullable(this.map.get(name));
        }
    }
}
