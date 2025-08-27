package cool.muyucloud.croparia.api.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.Dependencies;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.DgRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 **/
public class AggregatedGenerator extends DataGenerator {
    public static final MapCodec<AggregatedGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(AggregatedGenerator::optionalEnabled),
        Codec.BOOL.optionalFieldOf("startup").forGetter(AggregatedGenerator::optionalStartup),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(AggregatedGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(AggregatedGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(AggregatedGenerator::getPath),
        DgRegistry.CODEC.fieldOf("registry").forGetter(AggregatedGenerator::getRegistry),
        Codec.STRING.fieldOf("content").forGetter(AggregatedGenerator::getContent),
        Codec.STRING.fieldOf("template").forGetter(AggregatedGenerator::getTemplate)
    ).apply(instance, (enabled, startup, dependencies, whitelist, path, iterable, content, template) -> new AggregatedGenerator(
        enabled.orElse(true), startup.orElse(false), dependencies.orElse(Dependencies.EMPTY),
        whitelist.orElse(List.of()), path, iterable, content, template
    )));

    private final String content;
    protected final transient Map<String, List<String>> cache = new HashMap<>();

    public AggregatedGenerator(boolean enabled, boolean startup, Dependencies dependencies, List<ResourceLocation> whitelist, String path, DgRegistry<? extends DgElement> iterable, String content, String template) {
        super(enabled, startup, dependencies, whitelist, path, iterable, template);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getContent(DgElement element) {
        return replace(this.getContent(), element);
    }

    @Override
    public String getTemplate(DgElement element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generate(PackHandler pack) {
        super.generate(pack);
        for (Map.Entry<String, List<String>> entry : this.cache.entrySet()) {
            String relative = entry.getKey();
            StringBuilder builder = new StringBuilder();
            for (String s : entry.getValue()) {
                builder.append(s).append(",\n");
            }
            String content = builder.isEmpty() ? "" : builder.substring(0, builder.length() - 2);
            pack.addFile(relative, this.getTemplate().replaceAll("\\{content}", content));
        }
        this.cache.clear();
    }

    @Override
    protected void generate(DgElement element, PackHandler pack) {
        List<String> list = this.cache.computeIfAbsent(this.getPath(element), k -> new LinkedList<>());
        list.add(this.getContent(element));
    }
}
