package cool.muyucloud.croparia.api.generator;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.*;
import cool.muyucloud.croparia.util.codec.CodecUtil;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Basic data generator, used to generate "per element" files like recipes, loot tables, etc.</p>
 * <p>To generate aggregated files like lang, tags, etc. use {@link LangGenerator} or {@link AggregatedGenerator}.</p>
 */
public class DataGenerator {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ResourceLocation, MapCodec<? extends DataGenerator>> REGISTRY = new HashMap<>();

    /**
     * Registers a data generator codec, used to get Generator API known of custom data generator types.
     *
     * @param id the id of the data generator
     * @param codec the codec of the data generator
     * @param <G> the type of the data generator
     * @param <C> the type of the codec
     * @return the codec
     */
    public static <G extends DataGenerator, C extends MapCodec<G>> C register(ResourceLocation id, C codec) {
        REGISTRY.put(id, codec);
        return codec;
    }

    /**
     * Read a data generator from a file, and classifies it based on the {@code @type} (optional, default to {@code croparia:generator}) meta tag.
     *
     * @param file the file to read
     * @return the read data generator
     * @throws IOException if an I/O error occurs
     */
    public static DataGenerator read(File file) throws IOException {
        if (file.getName().endsWith(".cdg")) {
            JsonObject json = DgCompiler.compile(file);
            JsonElement type = json.get("type");
            ResourceLocation id = ResourceLocation.parse(type == null ? "croparia:generator" : type.getAsString());
            return CodecUtil.decodeJson(json, REGISTRY.get(id));
        } else throw new IllegalArgumentException("Invalid file suffix: " + file.getName());
    }

    public static final MapCodec<DataGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(DataGenerator::optionalEnabled),
        Codec.BOOL.optionalFieldOf("startup").forGetter(DataGenerator::optionalStartup),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(DataGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(DataGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(DataGenerator::getPath),
        DgRegistry.CODEC.fieldOf("registry").forGetter(DataGenerator::getRegistry),
        Codec.STRING.fieldOf("template").forGetter(DataGenerator::getTemplate)
    ).apply(instance, (enabled, startup, dependencies, whitelist, path, registry, template) -> new DataGenerator(
        enabled.orElse(true), startup.orElse(false), dependencies.orElse(Dependencies.EMPTY), whitelist.orElse(List.of()), path, registry, template
    )));

    private final boolean enabled;
    private final boolean startup;
    private final Dependencies dependencies;
    private final List<ResourceLocation> whitelist;
    private final String path;
    private final DgRegistry<? extends DgElement> registry;
    private final String template;
    private final transient LazySupplier<Boolean> load = LazySupplier.of(() -> this.getDependencies().available());

    public DataGenerator(boolean enabled, boolean startup, Dependencies dependencies, List<ResourceLocation> whitelist,
                         String path, DgRegistry<? extends DgElement> registry, String template) {
        this.enabled = enabled;
        this.startup = startup;
        this.dependencies = dependencies;
        this.whitelist = whitelist instanceof ImmutableList<ResourceLocation> immutable ? immutable : ImmutableList.copyOf(whitelist);
        this.path = path;
        this.registry = registry;
        this.template = template;
    }

    /**
     * Whether meta tag {@code @enabled} is set to {@code true}
     */
    public boolean isEnabled() {
        return enabled;
    }

    public Optional<Boolean> optionalEnabled() {
        return this.isEnabled() ? Optional.empty() : Optional.of(false);
    }

    /**
     * Whether the generator should be loaded before server is started.
     */
    public boolean isStartup() {
        return startup;
    }

    public Optional<Boolean> optionalStartup() {
        return this.isStartup() ? Optional.of(true) : Optional.empty();
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

    public String getPath(DgElement element) {
        return replace(this.getPath(), element);
    }

    public DgRegistry<? extends DgElement> getRegistry() {
        return registry;
    }

    public String getTemplate() {
        return template;
    }

    public String getTemplate(DgElement element) {
        return replace(this.getTemplate(), element);
    }

    /**
     * Whether the dependencies are present
     */
    public boolean isAvailable() {
        return load.get();
    }

    public void generate(PackHandler pack) {
        if (this.isStartup() || CropariaIf.isServerStarted()) {
            if (this.getWhitelist().isEmpty()) {
                for (DgElement element : this.getRegistry()) {
                    if (element.shouldLoad()) {
                        this.generate(element, pack);
                    }
                }
            } else {
                for (ResourceLocation id : this.getWhitelist()) {
                    this.getRegistry().forName(id).ifPresent(e -> this.generate(e, pack));
                }
            }
        }
    }

    protected void generate(DgElement element, PackHandler pack) {
        String relative = this.getPath(element);
        String replaced = this.getTemplate(element);
        pack.addFile(relative, replaced);
    }

    protected String replace(String template, DgElement element) {
        for (Placeholder<? extends DgElement> placeholder : element.placeholders()) {
            template = placeholder.mapAll(template, element);
        }
        return template;
    }
}
