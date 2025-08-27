package cool.muyucloud.croparia.api.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.Dependencies;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.DgRegistry;
import cool.muyucloud.croparia.api.generator.util.TranslatableElement;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;


public class LangGenerator extends AggregatedGenerator {
    public static final MapCodec<LangGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(LangGenerator::optionalEnabled),
        Codec.BOOL.optionalFieldOf("startup").forGetter(LangGenerator::optionalEnabled),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(LangGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(LangGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(LangGenerator::getPath),
        DgRegistry.CODEC.fieldOf("registry").forGetter(LangGenerator::getRegistry),
        Codec.STRING.fieldOf("content").forGetter(LangGenerator::getContent),
        Codec.STRING.fieldOf("template").forGetter(LangGenerator::getTemplate)
    ).apply(instance, (enabled, startup, dependencies, whitelist, path, iterable, content, template) -> {
        try {
            @SuppressWarnings("unchecked")
            DgRegistry<? extends TranslatableElement> translatable = (DgRegistry<? extends TranslatableElement>) iterable;
            for (TranslatableElement element : translatable) {
                element.translate("en_us");
                break;
            }
            return new LangGenerator(
                enabled.orElse(true), startup.orElse(false), dependencies.orElse(Dependencies.EMPTY),
                whitelist.orElse(List.of()), path, translatable, content, template
            );
        } catch (Throwable t) {
            throw new IllegalArgumentException("Iterable %s is not translatable".formatted(iterable), t);
        }
    }));

    public LangGenerator(
        boolean enabled, boolean startup, Dependencies dependencies, List<ResourceLocation> whitelist, String path,
        DgRegistry<? extends TranslatableElement> registry, String content, String template
    ) {
        super(enabled, startup, dependencies, whitelist, path, registry, content, template);
    }

    @Override
    protected void generate(DgElement element, PackHandler pack) {
        if (element instanceof TranslatableElement translatable) {
            for (String lang : translatable.getLangs()) {
                String relative = replace(this.getPath().replaceAll("\\{lang}", lang), element);
                List<String> list = this.cache.computeIfAbsent(relative, k -> new LinkedList<>());
                list.add(replace(this.getContent().replaceAll("\\{lang}", lang), element));
            }
        } else {
            throw new IllegalArgumentException("Element %s is not translatable".formatted(element.getKey()));
        }
    }
}
