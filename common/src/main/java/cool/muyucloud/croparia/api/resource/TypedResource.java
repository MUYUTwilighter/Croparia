package cool.muyucloud.croparia.api.resource;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

public interface TypedResource<R> extends TypeTokenAccess {
    default boolean isEmpty() {
        TypedResource<R> typed = this.getType().empty();
        return this.is(typed.getResource());
    }

    MapCodec<? extends TypedResource<R>> getCodec();

    R getResource();

    @Override
    TypeToken<? extends TypedResource<R>> getType();

    default boolean is(@Nullable Object resource) {
        return this.getResource().equals(resource);
    }
}
