package cool.muyucloud.croparia.neoforge;

import cool.muyucloud.croparia.CropariaIf;
import net.neoforged.fml.common.Mod;

@Mod(CropariaIf.MOD_ID)
public class CropariaIfNeoForge {
    public CropariaIfNeoForge() {
        CompatCrops.init();
        CropariaIf.init();
    }
}
