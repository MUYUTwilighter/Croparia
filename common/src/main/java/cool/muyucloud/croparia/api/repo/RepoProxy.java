package cool.muyucloud.croparia.api.repo;

import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.api.resource.TypedResource;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Unified storage interface for {@link BlockEntity}.<br>
 * It is used to connect your customized {@link Repo} to the fluid API from fabric / forge.<br>
 * <p>
 * Use {@link #item(Repo)} or {@link #fluid(Repo)} to create a {@link RepoProxy}. <br>
 * <b>DO NOT INSTANTIATE THIS CLASS UNLESS YOU KNOW WHAT YOU ARE DOING</b>
 * </p>
 */
public abstract class RepoProxy<T extends TypedResource<?>> implements Repo<T> {
    /**
     * Create a fluid interface from your customized {@link Repo}. <br>
     * You should only create {@link RepoProxy} from this method,
     * and the implemented {@link RepoProxy} from fabric / forge module is returned.<br>
     *
     * @param repo the fluid repo
     * @return the fluid agent
     */
    @ExpectPlatform
    public static RepoProxy<FluidSpec> fluid(Repo<FluidSpec> repo) {
        throw new AssertionError("Not implemented");
    }

    @ExpectPlatform
    public static RepoProxy<ItemSpec> item(Repo<ItemSpec> repo) {
        throw new AssertionError("Not implemented");
    }

    private final Repo<T> repo;

    protected RepoProxy(Repo<T> repo) {
        this.repo = repo;
    }

    @Override
    public TypeToken<T> getType() {
        return this.get().getType();
    }

    public Repo<T> get() {
        return this.repo;
    }

    @Override
    public int size() {
        return this.get().size();
    }

    @Override
    public boolean isEmpty(int i) {
        return this.get().isEmpty(i);
    }

    @Override
    public T resourceFor(int i) {
        return this.get().resourceFor(i);
    }

    @Override
    public long simConsume(T fluid, long amount) {
        return this.get().simConsume(fluid, amount);
    }

    @Override
    public long simConsume(int i, T resource, long amount) {
        return this.get().simConsume(i, resource, amount);
    }

    @Override
    public long consume(int i, T resource, long amount) {
        return this.get().consume(i, resource, amount);
    }

    @Override
    public long consume(T resource, long amount) {
        return this.get().consume(resource, amount);
    }

    @Override
    public long simAccept(T resource, long amount) {
        return this.get().simAccept(resource, amount);
    }

    @Override
    public long simAccept(int i, T resource, long amount) {
        return this.get().simAccept(i, resource, amount);
    }

    @Override
    public long accept(int i, T fluid, long amount) {
        return this.get().accept(i, fluid, amount);
    }

    @Override
    public long accept(T fluid, long amount) {
        return this.get().accept(fluid, amount);
    }

    @Override
    public long capacityFor(int i, T fluid) {
        return this.get().capacityFor(i, fluid);
    }

    @Override
    public long capacityFor(T fluid) {
        return this.get().capacityFor(fluid);
    }

    @Override
    public long amountFor(int i, T fluid) {
        return this.get().amountFor(i, fluid);
    }

    @Override
    public long amountFor(T fluid) {
        return this.get().amountFor(fluid);
    }
}
