package cool.muyucloud.registry;

import cool.muyucloud.recipe.InfusorRecipe;
import cool.muyucloud.recipe.RitualRecipe;
import cool.muyucloud.recipe.RitualStructure;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Recipe Types, used for help Minecraft identify the recipes.
 * <br/>
 *
 * @see RecipeSerializers
 * @see cool.muyucloud.serializer
 */
public class RecipeTypes {
    /**
     * This is new recipe types after porting to architectury
     */
    public static final RecipeType<InfusorRecipe> INFUSOR = RecipeType.register("infusor");
    public static final RecipeType<RitualRecipe> RITUAL = RecipeType.register("ritual");
    public static final RecipeType<RitualStructure> RITUAL_STRUCTURE = RecipeType.register("ritual_structure");

    /**
     * Old recipe types formed by Dalarion.
     * Used for compatibility.
     */
    public static final RecipeType<InfusorRecipe> INFUSOR_OLD = RecipeType.register("infusor_recipe");

    public static void register() {
    }
}
