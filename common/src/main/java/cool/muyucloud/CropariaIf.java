package cool.muyucloud;

import com.google.common.base.Suppliers;
import cool.muyucloud.registry.Blocks;
import cool.muyucloud.registry.Crops;
import cool.muyucloud.registry.Items;
import cool.muyucloud.registry.Tabs;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CropariaIf {
    public static final String MOD_ID = "croparia";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    public static void init() {
        Crops.register();
        Tabs.register();
        Items.register();
        Blocks.register();
    }

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
