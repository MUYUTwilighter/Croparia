package cool.muyucloud.croparia.util.supplier;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class OnLoadSupplier<T> implements Supplier<T> {
    public static long LAST_DATA_LOAD = 0L;

    public static <T> OnLoadSupplier<T> of(Supplier<T> creator) {
        return new OnLoadSupplier<>(creator);
    }

    @NotNull
    private final Supplier<T> creator;
    private T cache = null;
    private long lastCreate = 0L;

    public OnLoadSupplier(@NotNull Supplier<T> creator) {
        this.creator = creator;
    }

    @Override
    public T get() {
        if (this.getLastCreate() < LAST_DATA_LOAD) {
            this.cache = this.creator.get();
            this.onCreated();
        }
        return this.cache;
    }

    public long getLastCreate() {
        return lastCreate;
    }

    protected void onCreated() {
        this.lastCreate = System.currentTimeMillis();
    }
}
