package cool.muyucloud.forge;

import dev.architectury.platform.forge.EventBuses;
import cool.muyucloud.CropariaIf;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropariaIf.MOD_ID)
public class CropariaIfForge {
    public CropariaIfForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(CropariaIf.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CropariaIf.init();
    }
}
