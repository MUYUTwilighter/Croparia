package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.entry.ItemInput;
import cool.muyucloud.croparia.api.core.recipe.entry.ItemOutput;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SlotDisplays {
    public static final DeferredRegister<SlotDisplay.Type<? extends SlotDisplay>> SLOT_DISPLAYS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.SLOT_DISPLAY);

    public static final RegistrySupplier<SlotDisplay.Type<ItemInput>> ITEM_INPUT = register("item_input", () -> ItemInput.TYPE);
    public static final RegistrySupplier<SlotDisplay.Type<ItemOutput>> ITEM_OUTPUT = register("item_output", () -> ItemOutput.TYPE);

    public static <T extends SlotDisplay> RegistrySupplier<SlotDisplay.Type<T>> register(String path, Supplier<SlotDisplay.Type<T>> supplier) {
        return SLOT_DISPLAYS.register(CropariaIf.of(path), supplier);
    }
}
