package cool.muyucloud.croparia.util.supplier;

import java.util.function.Supplier;

/**
 * Supplier that only create the value when it is called via {@link #get()} for the first time.
 */
public class LazySupplier<T> implements Supplier<T> {
    public static <T> LazySupplier<T> empty() {
        return LazySupplier.of(() -> null);
    }

    public static <T> LazySupplier<T> of(Supplier<T> creator) {
        return creator instanceof LazySupplier<T> lazy ? lazy : new LazySupplier<>(creator);
    }

    protected final Supplier<T> creator;
    protected T cache;
    protected boolean loaded = false;

    public LazySupplier(Supplier<T> creator) {
        this.creator = creator;
    }

    @SuppressWarnings("unused")
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public T get() {
        if (!loaded) {
            cache = creator.get();
            loaded = true;
        }
        return cache;
    }
}
