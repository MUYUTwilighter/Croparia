package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.text.Texts;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class Tabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> CROPS = registerTab(
        "crops",
        () -> CreativeTabRegistry.create(
            Texts.translatable("tab." + CropariaIf.MOD_ID + ".crops"),
            () -> CropariaItems.CROPARIA.get().getDefaultInstance()
        )
    );
    public static final RegistrySupplier<CreativeModeTab> MAIN = registerTab(
        "main",
        () -> CreativeTabRegistry.create(
            Texts.translatable("tab." + CropariaIf.MOD_ID + ".main"),
            () -> Elements.ELEMENTAL.getGem().get().getDefaultInstance()
        )
    );

    public static RegistrySupplier<CreativeModeTab> registerTab(String name, Supplier<CreativeModeTab> supplier) {
        return TABS.register(name, supplier);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering tabs");
        TABS.register();
    }

    public static CreativeModeTab get(String id) {
        return BuiltInRegistries.CREATIVE_MODE_TAB.getValue(ResourceLocation.tryBuild(CropariaIf.MOD_ID, id));
    }
}
