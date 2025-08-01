package cool.muyucloud.croparia.api.repo;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.api.resource.TypedResource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public class RepoUnit<T extends TypedResource<?>> implements Repo<T> {
    private final transient Predicate<T> filter;
    private final transient TypeToken<T> type;
    private T resource;
    private long amount = 0;
    private long capacity;
    private boolean consumable = false;
    private boolean acceptable = false;
    private boolean locked = false;
    private boolean changed = true;

    public RepoUnit(TypeToken<T> type, Predicate<T> filter, long capacity) {
        this.type = type;
        this.resource = type.empty();
        this.filter = filter;
        this.capacity = capacity;
    }

    public void load(JsonObject json) {
        this.resource = this.getType().codec().codec().decode(JsonOps.INSTANCE, json.get("resource")).getOrThrow(msg -> {
            throw new IllegalArgumentException(msg);
        }).getFirst();
        this.amount = GsonHelper.getAsLong(json, "amount", 0L);
        this.capacity = GsonHelper.getAsLong(json, "capacity", 0L);
        this.consumable = GsonHelper.getAsBoolean(json, "consumable", false);
        this.acceptable = GsonHelper.getAsBoolean(json, "acceptable", false);
        this.locked = GsonHelper.getAsBoolean(json, "locked", false);
    }

    public void load(CompoundTag nbt) {
        this.setResource(this.getType().codec().codec().decode(NbtOps.INSTANCE, nbt.get("resource")).getOrThrow(msg -> {
            throw new IllegalArgumentException(msg);
        }).getFirst());
        this.setAmount(nbt.getLong("amount"));
        this.setCapacity(nbt.getLong("capacity"));
        this.setConsumable(nbt.getBoolean("consumable"));
        this.setAcceptable(nbt.getBoolean("acceptable"));
        this.setLocked(nbt.getBoolean("locked"));
    }

    public void save(JsonObject json) {
        json.add("resource", this.getType().codec().codec().encodeStart(JsonOps.INSTANCE, this.getResource()).getOrThrow(msg -> {
            throw new IllegalArgumentException(msg);
        }));
        json.addProperty("amount", this.getAmount());
        json.addProperty("capacity", this.getCapacity());
        json.addProperty("consumable", this.isConsumable());
        json.addProperty("acceptable", this.isAcceptable());
        json.addProperty("locked", this.isLocked());
    }

    public void save(CompoundTag nbt) {
        nbt.put("resource", this.getType().codec().codec().encodeStart(NbtOps.INSTANCE, this.getResource()).getOrThrow(msg -> {
            throw new IllegalArgumentException(msg);
        }));
        nbt.putLong("amount", this.getAmount());
        nbt.putLong("capacity", this.getCapacity());
        nbt.putBoolean("consumable", this.isConsumable());
        nbt.putBoolean("acceptable", this.isAcceptable());
        nbt.putBoolean("locked", this.isLocked());
    }

    public @NotNull T getResource() {
        return this.resource;
    }

    public void setResource(@NotNull T resource) {
        this.resource = resource;
    }

    public boolean isFluidValid(T fluid) {
        if (this.isLocked() || this.getAmount() != 0) return this.getResource().equals(fluid);
        else return this.filter.test(fluid);
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public boolean isConsumable() {
        return this.consumable;
    }

    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    public boolean isAcceptable() {
        return this.acceptable;
    }

    public void setAcceptable(boolean acceptable) {
        this.acceptable = acceptable;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public T resourceFor(int i) {
        return i == 0 ? getResource() : null;
    }

    @Override
    public TypeToken<T> getType() {
        return type;
    }

    @Override
    public boolean isEmpty(int i) {
        return i == 0 || this.getResource().isEmpty();
    }

    @Override
    public long simConsume(int i, T resource, long amount) {
        if (i != 0 || !this.isConsumable() || !this.getResource().equals(resource)) return 0L;
        return Math.min(amount, this.getAmount());
    }

    @Override
    public long consume(int i, T resource, long amount) {
        if (i != 0 || !this.isConsumable() || !this.getResource().equals(resource)) return 0L;
        long consumed = Math.min(amount, this.getAmount());
        if (consumed <= 0) return 0L;
        this.setAmount(this.getAmount() - consumed);
        this.setChanged(true);
        return consumed;
    }

    public long consume(long amount) {
        return this.consume(this.getResource(), amount);
    }

    @Override
    public long simAccept(int i, T resource, long amount) {
        if (i != 0 || !this.isAcceptable() || !this.isFluidValid(resource)) return 0L;
        return Math.min(this.getCapacity() - this.getAmount(), amount);
    }

    @Override
    public long accept(int i, T resource, long amount) {
        if (i != 0 || !this.isAcceptable() || !this.isFluidValid(resource)) return 0L;
        long accepted = Math.min(this.getCapacity() - this.getAmount(), amount);
        if (accepted <= 0) return 0L;
        this.setAmount(this.getAmount() + amount);
        this.setResource(resource);
        this.setChanged(true);
        return accepted;
    }

    @Override
    public long capacityFor(int i, T resource) {
        if (i != 0) return 0L;
        return this.isFluidValid(resource) ? this.getCapacity() : 0L;
    }

    @Override
    public long amountFor(int i, T resource) {
        return i == 0 && this.getResource().equals(resource) ? this.getAmount() : 0L;
    }
}
