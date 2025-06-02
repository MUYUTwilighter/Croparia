package cool.muyucloud.croparia.util;

import java.util.function.Supplier;

public abstract class LazySupplier<T> implements Supplier<T> {
    public static <T> LazySupplier<T> of(Supplier<T> supplier) {
        return new LazySupplier<>() {
            @Override
            public T create() {
                return supplier.get();
            }
        };
    }

    private T value;
    private boolean loaded = false;

    public boolean isLoaded() {
        return loaded;
    }

    protected abstract T create();

    @Override
    public T get() {
        if (!loaded) {
            value = create();
            loaded = true;
        }
        return value;
    }
}
