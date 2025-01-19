package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.recipe.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Recipe Types, used for help Minecraft identify the recipes.
 * <br/>
 *
 * @see RecipeSerializers
 * @see cool.muyucloud.croparia.recipe.serializer
 */
@SuppressWarnings("unused")
public class RecipeTypes {
    public static DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_TYPE);

    /**
     * This is new recipe types after porting to architectury
     */
    public static final RegistrySupplier<RecipeType<InfusorRecipe>> INFUSOR = register("infusor");
    public static final RegistrySupplier<RecipeType<RitualRecipe>> RITUAL = register("ritual");
    public static final RegistrySupplier<RecipeType<RitualStructure>> RITUAL_STRUCTURE = register("ritual_structure");

    /**
     * Old recipe types formed by Dalarion.
     * Used for compatibility.
     */
    public static final RegistrySupplier<RecipeType<OldInfusorRecipe>> INFUSOR_OLD = register("infusion_recipe");
    public static final RegistrySupplier<RecipeType<OldRitualRecipe>> RITUAL_OLD = register("ritual_recipe");

    public static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return id;
            }
        });
    }

    public static void register() {
        CropariaIf.LOGGER.debug("Registering recipe types");
        RECIPE_TYPES.register();
    }
}
