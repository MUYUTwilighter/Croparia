package cool.muyucloud.croparia.api.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.Dependencies;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.DgIterable;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CompositeGenerator<E extends DgElement> extends DataGenerator<E> {
    public static final MapCodec<CompositeGenerator<?>> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(CompositeGenerator::optionalEnabled),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(CompositeGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(CompositeGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(CompositeGenerator::getPath),
        DgIterable.CODEC.fieldOf("iterable").forGetter(CompositeGenerator::getIterable),
        Codec.STRING.fieldOf("content").forGetter(CompositeGenerator::getContent),
        Codec.STRING.fieldOf("template").forGetter(CompositeGenerator::getTemplate)
    ).apply(instance, (enabled, dependencies, whitelist, path, iterable, content, template) -> new CompositeGenerator<DgElement>(
        enabled.orElse(true), dependencies.orElse(Dependencies.EMPTY), whitelist.orElse(List.of()), path, iterable, content, template
    )));

    private final String content;
    protected final transient Map<String, List<String>> cache = new HashMap<>();

    public CompositeGenerator(boolean enabled, Dependencies dependencies, List<ResourceLocation> whitelist, String path, DgIterable<? extends E> iterable, String content, String template) {
        super(enabled, dependencies, whitelist, path, iterable, template);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getContent(E element) {
        return replace(this.getContent(), element);
    }

    @Override
    @Deprecated
    public String getTemplate(E element) {
        return super.getTemplate(element);
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
    protected void generate(E element, PackHandler pack) {
        List<String> list = this.cache.computeIfAbsent(this.getPath(element), k -> new LinkedList<>());
        list.add(this.getContent(element));
    }


}
