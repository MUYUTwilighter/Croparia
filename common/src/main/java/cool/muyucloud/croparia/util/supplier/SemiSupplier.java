package cool.muyucloud.croparia.util.supplier;

import java.util.function.Supplier;

/**
 * A lazy supplier that the flag can be manually refreshed.
 */
public class SemiSupplier<T> extends LazySupplier<T> {
    public static <T> SemiSupplier<T> empty() {
        return SemiSupplier.of(() -> null);
    }

    public static <T> SemiSupplier<T> of(Supplier<T> creator) {
        return creator instanceof SemiSupplier<T> semi ? semi : new SemiSupplier<>(creator);
    }

    public SemiSupplier(Supplier<T> creator) {
        super(creator);
    }

    public void refresh() {
        this.loaded = false;
    }
}
