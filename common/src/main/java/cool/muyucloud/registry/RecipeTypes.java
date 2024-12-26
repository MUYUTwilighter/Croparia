package cool.muyucloud.registry;

import cool.muyucloud.CropariaIf;
import cool.muyucloud.recipe.InfusorRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(CropariaIf.MOD_ID, Registries.RECIPE_TYPE);

    public static final RecipeType<InfusorRecipe> INFUSOR = RecipeType.register("infusor");
    public static final RecipeType<?> RITUAL = RecipeType.register("ritual");

    public static void register() {
        RECIPES.register();
    }
}
