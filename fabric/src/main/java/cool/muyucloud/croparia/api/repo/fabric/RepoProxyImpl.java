package cool.muyucloud.croparia.api.repo.fabric;

import cool.muyucloud.croparia.api.repo.Repo;
import cool.muyucloud.croparia.api.repo.RepoProxy;
import cool.muyucloud.croparia.api.resource.type.FluidSpec;
import cool.muyucloud.croparia.api.resource.type.ItemSpec;

public class RepoProxyImpl {
    public static RepoProxy<FluidSpec> fluid(Repo<FluidSpec> repo) {
        return new FluidRepoProxy(repo);
    }

    public static RepoProxy<ItemSpec> item(Repo<ItemSpec> repo) {
        return new ItemRepoProxy(repo);
    }
}
