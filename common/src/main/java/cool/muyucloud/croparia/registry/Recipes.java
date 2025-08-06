package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.api.core.recipe.SoakRecipe;
import cool.muyucloud.croparia.api.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.api.recipe.TypedSerializer;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.Optional;
import java.util.function.Supplier;

public class Recipes {
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_TYPE);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_SERIALIZER);
    private static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_DISPLAY);
    private static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_BOOK_CATEGORY);

    public static final RegistrySupplier<TypedSerializer<InfusorRecipe>> INFUSOR = register("infusor", () -> InfusorRecipe.TYPED_SERIALIZER);
    @SuppressWarnings("unused")
    public static final RegistrySupplier<TypedSerializer<InfusorRecipe>> INFUSOR_OLD = register("infusor_type", () -> InfusorRecipe.OLD_TYPED_SERIALIZER);
    public static final RegistrySupplier<TypedSerializer<RitualRecipe>> RITUAL = register("ritual", () -> RitualRecipe.TYPED_SERIALIZER);
    @SuppressWarnings("unused")
    public static final RegistrySupplier<TypedSerializer<RitualRecipe>> RITUAL_OLD = register("ritual_type", () -> RitualRecipe.OLD_TYPED_SERIALIZER);
    public static final RegistrySupplier<TypedSerializer<RitualStructure>> RITUAL_STRUCTURE = register("ritual_structure", () -> RitualStructure.TYPED_SERIALIZER);
    @SuppressWarnings("unused")
    public static final RegistrySupplier<TypedSerializer<SoakRecipe>> SOAK = register("soak", () -> SoakRecipe.TYPED_SERIALIZER);

    public static <R extends DisplayableRecipe<?>> RegistrySupplier<TypedSerializer<R>> register(String path, Supplier<TypedSerializer<R>> supplier) {
        RegistrySupplier<TypedSerializer<R>> type = RECIPE_TYPES.register(path, supplier);
        RECIPE_SERIALIZERS.register(path, supplier);
        RECIPE_BOOK_CATEGORIES.register(path, supplier);
        RECIPE_DISPLAYS.register(path, () -> supplier.get().displayType());
        return type;
    }

    public static Optional<ResourceLocation> get(TypedSerializer<?> serializer) {
        return Optional.ofNullable(BuiltInRegistries.RECIPE_TYPE.getResourceKey(serializer).map(ResourceKey::location).orElse(
            BuiltInRegistries.RECIPE_SERIALIZER.getResourceKey(serializer).map(ResourceKey::location).orElse(
                BuiltInRegistries.RECIPE_BOOK_CATEGORY.getResourceKey(serializer).map(ResourceKey::location).orElse(
                    BuiltInRegistries.RECIPE_DISPLAY.getResourceKey(serializer.displayType()).map(ResourceKey::location).orElse(null)
                )
            )
        ));
    }

    public static void register() {
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
        RECIPE_DISPLAYS.register();
        RECIPE_BOOK_CATEGORIES.register();
    }
}
