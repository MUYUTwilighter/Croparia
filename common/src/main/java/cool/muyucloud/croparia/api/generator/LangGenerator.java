package cool.muyucloud.croparia.api.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import cool.muyucloud.croparia.api.generator.util.Dependencies;
import cool.muyucloud.croparia.api.generator.util.DgIterable;
import cool.muyucloud.croparia.api.generator.util.TranslatableElement;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

public class LangGenerator<E extends TranslatableElement> extends CompositeGenerator<E> {
    public static final MapCodec<LangGenerator<?>> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("enabled").forGetter(LangGenerator::optionalEnabled),
        Dependencies.CODEC.optionalFieldOf("dependencies").forGetter(LangGenerator::optionalDependencies),
        ResourceLocation.CODEC.listOf().optionalFieldOf("whitelist").forGetter(LangGenerator::optionalWhitelist),
        Codec.STRING.fieldOf("path").forGetter(LangGenerator::getPath),
        DgIterable.CODEC.fieldOf("iterable").forGetter(LangGenerator::getIterable),
        Codec.STRING.fieldOf("content").forGetter(LangGenerator::getContent),
        Codec.STRING.fieldOf("template").forGetter(LangGenerator::getTemplate)
    ).apply(instance, (enabled, dependencies, whitelist, path, iterable, content, template) -> {
        try {
            @SuppressWarnings("unchecked")
            DgIterable<? extends TranslatableElement> translatable = (DgIterable<? extends TranslatableElement>) iterable;
            for (TranslatableElement element : translatable) {
                element.translate("en_us");
                break;
            }
            return new LangGenerator<>(
                enabled.orElse(true), dependencies.orElse(Dependencies.EMPTY), whitelist.orElse(List.of()), path,
                translatable, content, template
            );
        } catch (Throwable t) {
            throw new IllegalArgumentException("Iterable %s is not translatable".formatted(iterable), t);
        }
    }));

    public LangGenerator(
        boolean enabled, Dependencies dependencies, List<ResourceLocation> whitelist, String path,
        DgIterable<? extends E> iterable, String content, String template
    ) {
        super(enabled, dependencies, whitelist, path, iterable, content, template);
    }

    @Override
    protected void generate(E element, PackHandler pack) {
        for (String lang : element.getLangs()) {
            String relative = replace(this.getPath().replaceAll("\\{lang}", lang), element);
            List<String> list = this.cache.computeIfAbsent(relative, k -> new LinkedList<>());
            list.add(replace(this.getContent().replaceAll("\\{lang}", lang), element));
        }
    }
}
