package cool.muyucloud.croparia.util.supplier;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A supplier that provides a value from a registry with given ID and registration methods.
 */
public class HolderSupplier<T> implements DeferredSupplier<T> {
    @SuppressWarnings("unchecked")
    public static <T> HolderSupplier<T> of(Supplier<T> value, ResourceLocation location, Registry<? super T> registry) {
        return new HolderSupplier<>(value, (ResourceKey<T>) ResourceKey.create(registry.key(), location));
    }

    @SuppressWarnings("unchecked")
    public static <S, T extends S> HolderSupplier<T> of(Supplier<T> value, ResourceLocation location, ResourceKey<Registry<S>> registry) {
        return new HolderSupplier<>(value, (ResourceKey<T>) ResourceKey.create(registry, location));
    }

    @NotNull
    private final ResourceKey<T> key;
    @NotNull
    private final Registry<T> registry;
    @NotNull
    private final LazySupplier<T> value;

    @SuppressWarnings("unchecked")
    public HolderSupplier(@NotNull Supplier<T> value, @NotNull ResourceKey<T> key) {
        this.value = value instanceof LazySupplier<T> lazy ? lazy : LazySupplier.of(value);
        this.key = key;
        this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(this.getRegistryId());
        if (this.registry == null) throw new IllegalArgumentException("Invalid registry id: " + this.getRegistryId());
    }

    @Override
    public @NotNull ResourceKey<T> getKey() {
        return this.key;
    }

    public @NotNull Registry<T> getRegistry() {
        return this.registry;
    }

    @Override
    public ResourceLocation getRegistryId() {
        return getKey().registry();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResourceKey<Registry<T>> getRegistryKey() {
        return (ResourceKey<Registry<T>>) this.getRegistry().key();
    }

    @Override
    public ResourceLocation getId() {
        return this.getKey().location();
    }

    @Override
    public boolean isPresent() {
        return this.getRegistry().containsKey(this.getId());
    }

    @Override
    public T get() {
        return this.getRegistry().getValue(this.getId());
    }

    public void register() {
        DeferredRegister<T> register = DeferredRegister.create(this.getId().getNamespace(), getRegistryKey());
        register.register(this.getId().getPath(), this.value);
        register.register();
    }

    public boolean tryRegister() {
        try {
            this.register();
            return true;
        } catch (IllegalStateException e) {
            return false;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
