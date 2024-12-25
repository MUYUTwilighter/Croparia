package cool.muyucloud.fabric;

import cool.muyucloud.CropariaIf;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;

public class CropariaIfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CropariaIf.init();
    }
}
