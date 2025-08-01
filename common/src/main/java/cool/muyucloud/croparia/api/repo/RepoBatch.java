package cool.muyucloud.croparia.api.repo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.api.resource.TypedResource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A batch of {@link RepoUnit}s
 * */
@SuppressWarnings("unused")
public class RepoBatch<T extends TypedResource<?>> implements Repo<T>, Iterable<RepoUnit<T>> {
    public static <T extends TypedResource<?>> RepoBatch<T> of(TypeToken<T> type) {
        return new RepoBatch<>(type);
    }

    @SafeVarargs
    public static <T extends TypedResource<?>> RepoBatch<T> of(TypeToken<T> type, RepoUnit<T>... units) {
        return new RepoBatch<>(type, units);
    }

    @SafeVarargs
    public static <T extends TypedResource<?>> RepoBatch<T> of(TypeToken<T> type, RepoBatch<T>... batches) {
        RepoBatch<T> result = new RepoBatch<>(type);
        for (RepoBatch<T> batch : batches) {
            for (RepoUnit<T> unit : batch) {
                result.add(unit);
            }
        }
        return result;
    }

    private final ArrayList<RepoUnit<T>> units = new ArrayList<>();
    private final TypeToken<T> type;

    @SafeVarargs
    public RepoBatch(@NotNull TypeToken<T> type, @NotNull RepoUnit<T>... units) {
        this.units.addAll(List.of(units));
        this.units.trimToSize();
        this.type = type;
    }

    @Override
    public TypeToken<T> getType() {
        return type;
    }

    public void load(@NotNull JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            JsonObject unit = json.get(i).getAsJsonObject();
            units.get(i).load(unit);
        }
    }

    public void load(@NotNull ListTag nbt) {
        for (int i = 0; i < units.size(); i++) {
            CompoundTag unit = nbt.getCompound(i);
            units.get(i).load(unit);
        }
    }

    public void save(@NotNull JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            JsonObject unit = new JsonObject();
            units.get(i).save(unit);
            json.add(unit);
        }
    }

    public void save(@NotNull ListTag nbt) {
        for (RepoUnit<T> tRepoUnit : units) {
            CompoundTag unit = new CompoundTag();
            tRepoUnit.save(unit);
            nbt.add(unit);
        }
    }

    public boolean isChanged() {
        for (RepoUnit<T> unit : units) {
            if (unit.isChanged()) {
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    public final void add(RepoUnit<T>... unit) {
        units.addAll(List.of(unit));
        units.trimToSize();
    }

    @SafeVarargs
    public final void add(RepoBatch<T>... batches) {
        for (RepoBatch<T> batch : batches) {
            for (RepoUnit<T> unit : batch) {
                units.add(unit);
            }
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public RepoUnit<T> remove(int i) {
        return units.remove(i);
    }

    public void clear() {
        units.clear();
    }

    @Override
    public int size() {
        return units.size();
    }

    @Override
    public boolean isEmpty(int i) {
        return units.get(i).isEmpty(0);
    }

    @Override
    public T resourceFor(int i) {
        return units.get(i).resourceFor(0);
    }

    @Override
    public long simConsume(int i, T resource, long amount) {
        return units.get(i).simConsume(0, resource, amount);
    }

    @Override
    public long consume(int i, T resource, long amount) {
        return units.get(i).consume(0, resource, amount);
    }

    @Override
    public long simAccept(int i, T resource, long amount) {
        return units.get(i).simAccept(0, resource, amount);
    }

    @Override
    public long accept(int i, T resource, long amount) {
        return units.get(i).accept(0, resource, amount);
    }

    @Override
    public long capacityFor(int i, T resource) {
        return units.get(i).capacityFor(0, resource);
    }

    @Override
    public long amountFor(int i, T resource) {
        return units.get(i).amountFor(0, resource);
    }

    @NotNull
    @Override
    public Iterator<RepoUnit<T>> iterator() {
        return units.iterator();
    }
}
