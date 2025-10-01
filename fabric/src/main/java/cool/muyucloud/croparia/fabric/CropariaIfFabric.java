package cool.muyucloud.croparia.fabric;

import cool.muyucloud.croparia.CropariaIf;
import net.fabricmc.api.ModInitializer;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CropariaIf.init();
    }
}
