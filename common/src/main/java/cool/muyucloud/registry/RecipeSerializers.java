package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.serializer.InfusorRecipeSerializer;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public class RecipeSerializers {
    public static DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static RegistrySupplier<RecipeSerializer<?>> INFUSOR = register("infusor", InfusorRecipeSerializer::new);

    public static RegistrySupplier<RecipeSerializer<?>> register(String id, Supplier<RecipeSerializer<?>> supplier) {
        return SERIALIZERS.register(id, supplier);
    }

    public static void register() {
        SERIALIZERS.register();
    }
}
