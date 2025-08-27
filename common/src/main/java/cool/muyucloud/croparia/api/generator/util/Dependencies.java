package cool.muyucloud.croparia.api.generator.util;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.codec.GenericListCodec;
import dev.architectury.platform.Platform;

import java.util.List;

public class Dependencies {
    public static final Codec<Dependencies> CODEC = GenericListCodec.of(GenericListCodec.of(Codec.STRING)).xmap(
        Dependencies::new, Dependencies::getDependencies);
    public static final Dependencies EMPTY = new Dependencies(List.of());

    private final List<List<String>> dependencies;

    public Dependencies(List<List<String>> dependencies) {
        this.dependencies = ImmutableList.copyOf(dependencies.stream().map(
            list -> list instanceof ImmutableList<String> immutableList ? immutableList : ImmutableList.copyOf(list)
        ).toList());
    }

    public List<List<String>> getDependencies() {
        return dependencies;
    }

    public boolean isEmpty() {
        return this.getDependencies().stream().allMatch(List::isEmpty);
    }

    public boolean available() {
        return this.getDependencies().stream().allMatch(list -> list.stream().anyMatch(this::available));
    }

    protected boolean available(String e) {
        return Platform.isModLoaded(e);
    }
}
