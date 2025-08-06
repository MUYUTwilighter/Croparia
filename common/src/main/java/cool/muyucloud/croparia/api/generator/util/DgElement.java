package cool.muyucloud.croparia.api.generator.util;

import cool.muyucloud.croparia.annotation.PostGen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface DgElement {
    Placeholder<DgElement> ID = Placeholder.of("\\{id}", (matcher, element) -> element.getKey().toString());
    Placeholder<DgElement> ID_NAMESPACE = Placeholder.of("\\{id_namespace}", (matcher, element) -> element.getKey().getNamespace());
    Placeholder<DgElement> ID_PATH = Placeholder.of("\\{id_path}", (matcher, element) -> element.getKey().getPath());

    @NotNull
    ResourceLocation getKey();

    @PostGen
    Collection<Placeholder<? extends DgElement>> placeholders();

    boolean shouldLoad();

    default void buildPlaceholders(Collection<Placeholder<? extends DgElement>> list) {
        list.add(ID);
        list.add(ID_NAMESPACE);
        list.add(ID_PATH);
    }
}
