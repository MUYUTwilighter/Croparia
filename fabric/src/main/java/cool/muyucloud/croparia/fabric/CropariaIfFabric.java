package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.CropariaIf;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getAllMods().forEach(mod -> System.out.println(mod.getOrigin().getPaths()));
        CompatCrops.init();
        CropariaIf.init();
    }
}
