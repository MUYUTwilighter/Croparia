package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.serializer.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public class RecipeSerializers {
    public static DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static RegistrySupplier<InfusorRecipeSerializer> INFUSOR = register("infusor", InfusorRecipeSerializer::new);
    public static RegistrySupplier<RitualRecipeSerializer> RITUAL = register("ritual", RitualRecipeSerializer::new);
    public static RegistrySupplier<RitualStructureSerializer> RITUAL_STRUCTURE = register("ritual_structure", RitualStructureSerializer::new);

    public static RegistrySupplier<OldInfusorRecipeSerializer> INFUSOR_OLD = register("infusor_recipe", OldInfusorRecipeSerializer::new);
    public static RegistrySupplier<OldRitualRecipeSerializer> RITUAL_OLD = register("ritual_recipe", OldRitualRecipeSerializer::new);

    public static <T extends RecipeSerializer<?>> RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        return SERIALIZERS.register(id, supplier);
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering recipe serializers");
        SERIALIZERS.register();
    }
}
