package cool.muyucloud.croparia.api.generator.util;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface TranslatableElement extends DgElement {
    Placeholder<TranslatableElement> TRANSLATION_KEY = Placeholder.of(
        "\\{translation_key}", TranslatableElement::getTranslationKey
    );
    Placeholder<TranslatableElement> TRANSLATION = Placeholder.of(
        "\\{translation\\.([^}]+)}", (matcher, element) -> element.translate(matcher.group(1))
    );

    Collection<String> getLangs();

    String getTranslationKey();

    @Nullable
    String translate(String lang);

    @Override
    default void buildPlaceholders(Collection<Placeholder<? extends DgElement>> list) {
        DgElement.super.buildPlaceholders(list);
        list.add(TRANSLATION_KEY);
        list.add(TRANSLATION);
    }
}
