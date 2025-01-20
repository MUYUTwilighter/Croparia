package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class Tabs {

    public static final CreativeModeTab CROPS = registerTab(
        "crops",
        CropariaItems.CROPARIA.get()::getDefaultInstance
    );
    public static final CreativeModeTab MAIN = registerTab(
        "main",
        CropariaItems.ELEMATILIUS.get()::getDefaultInstance
    );

    public static CreativeModeTab registerTab(String name, Supplier<ItemStack> icon) {
        return CreativeTabRegistry.create(CropariaIf.of(name), icon);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering creative mode tabs");
    }
}
