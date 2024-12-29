package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.recipe.serializer.InfusorRecipeSerializer;
import cool.muyucloud.recipe.serializer.RitualRecipeSerializer;
import cool.muyucloud.recipe.serializer.RitualStructureSerializer;
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

    public static <T extends RecipeSerializer<?>> RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        return SERIALIZERS.register(id, supplier);
    }

    public static void register() {
        SERIALIZERS.register();
    }
}
