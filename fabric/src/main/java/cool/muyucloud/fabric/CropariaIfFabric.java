package cool.muyucloud.fabric;

import cool.muyucloud.CropariaIf;
import net.fabricmc.api.ModInitializer;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CropariaIf.init();
    }
}
