package cool.muyucloud.croparia.api.generator;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.*;
import cool.muyucloud.croparia.util.CodecUtil;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataGenerator<E extends DgElement> {
    private static final Map<ResourceLocation, MapCodec<? extends DataGenerator<?>>> REGISTRY = new HashMap<>();

    public static <G extends DataGenerator<? extends DgElement>, C extends MapCodec<G>> C register(ResourceLocation id, C codec) {
        REGISTRY.put(id, codec);
        return codec;
    }

    public static DataGenerator<?> read(File file) throws IOException {
        if (file.getName().endsWith(".cdg")) {
            JsonObject json = DgCompiler.compile(file);
            String rawType = JsonUtils.getStringOr("type", json, "croparia:generator");
            rawType = rawType == null ? "croparia:generator" : rawType;
            ResourceLocation id = ResourceLocation.tryParse(rawType);
            id = id == null ? CropariaIf.of("generator") : id;
            return CodecUtil.decodeJson(json, REGISTRY.get(id));
        } else {
            return null;
        }
    }

    public static final MapCodec<DataGenerator<?>> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(DataGenerator::optionalEnabled),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(DataGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(DataGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(DataGenerator::getPath),
        DgIterable.CODEC.fieldOf("iterable").forGetter(DataGenerator::getIterable),
        Codec.STRING.fieldOf("template").forGetter(DataGenerator::getTemplate)
    ).apply(instance, (enabled, dependencies, whitelist, path, iterable, template) -> new DataGenerator<DgElement>(
        enabled.orElse(true), dependencies.orElse(Dependencies.EMPTY), whitelist.orElse(List.of()), path, iterable, template
    )));

    private final boolean enabled;
    private final Dependencies dependencies;
    private final List<ResourceLocation> whitelist;
    private final String path;
    private final DgIterable<? extends E> iterable;
    private final String template;
    private final transient LazySupplier<Boolean> load = LazySupplier.of(() -> this.getDependencies().available());

    public DataGenerator(
        boolean enabled, Dependencies dependencies, List<ResourceLocation> whitelist, String path, DgIterable<? extends E> iterable, String template
    ) {
        this.enabled = enabled;
        this.dependencies = dependencies;
        this.whitelist = whitelist instanceof ImmutableList<ResourceLocation> immutable ? immutable : ImmutableList.copyOf(whitelist);
        this.path = path;
        this.iterable = iterable;
        this.template = template;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Optional<Boolean> optionalEnabled() {
        return this.isEnabled() ? Optional.empty() : Optional.of(false);
    }

    public Dependencies getDependencies() {
        return dependencies;
    }

    public Optional<Dependencies> optionalDependencies() {
        return this.getDependencies().isEmpty() ? Optional.empty() : Optional.of(this.getDependencies());
    }

    public List<ResourceLocation> getWhitelist() {
        return whitelist;
    }

    public Optional<List<ResourceLocation>> optionalWhitelist() {
        return this.getWhitelist().isEmpty() ? Optional.empty() : Optional.of(this.getWhitelist());
    }

    public String getPath() {
        return path;
    }

    public String getPath(E element) {
        return replace(this.getPath(), element);
    }

    public DgIterable<? extends E> getIterable() {
        return iterable;
    }

    public String getTemplate() {
        return template;
    }

    public String getTemplate(E element) {
        return replace(this.getTemplate(), element);
    }

    public boolean shouldLoad() {
        return load.get();
    }

    public void generate(PackHandler pack) {
        if (isEnabled() && shouldLoad()) {
            if (this.getWhitelist().isEmpty()) {
                for (E element : this.getIterable()) {
                    if (element.shouldLoad()) {
                        this.generate(element, pack);
                    }
                }
            } else {
                for (ResourceLocation id : this.getWhitelist()) {
                    this.getIterable().forName(id).ifPresent(e -> this.generate(e, pack));
                }
            }
        }
    }

    protected void generate(E element, PackHandler pack) {
        String relative = replace(this.getPath(), element);
        String replaced = replace(this.getTemplate(), element);
        pack.addFile(relative, replaced);
    }

    protected String replace(String template, E element) {
        for (Placeholder<? extends DgElement> placeholder : element.placeholders()) {
            template = placeholder.mapAll(template, element);
        }
        return template;
    }
}
