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

public class CompositeGenerator extends DataGenerator {
    public static final MapCodec<CompositeGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(CompositeGenerator::optionalEnabled),
        Codec.BOOL.optionalFieldOf("startup").forGetter(CompositeGenerator::optionalStartup),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(CompositeGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(CompositeGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(CompositeGenerator::getPath),
        DgRegistry.CODEC.fieldOf("registry").forGetter(CompositeGenerator::getRegistry),
        Codec.STRING.fieldOf("content").forGetter(CompositeGenerator::getContent),
        Codec.STRING.fieldOf("template").forGetter(CompositeGenerator::getTemplate)
    ).apply(instance, (enabled, startup, dependencies, whitelist, path, iterable, content, template) -> new CompositeGenerator(
        enabled.orElse(true), startup.orElse(false), dependencies.orElse(Dependencies.EMPTY),
        whitelist.orElse(List.of()), path, iterable, content, template
    )));

    private final String content;
    protected final transient Map<String, List<String>> cache = new HashMap<>();

    public CompositeGenerator(boolean enabled, boolean startup, Dependencies dependencies, List<ResourceLocation> whitelist, String path, DgRegistry<? extends DgElement> iterable, String content, String template) {
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
