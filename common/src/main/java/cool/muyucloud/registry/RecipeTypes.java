package cool.muyucloud.registry;

import cool.muyucloud.recipe.InfusorRecipe;
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
    public static final RecipeType<?> RITUAL = RecipeType.register("ritual");

    /**
     * Old recipe types formed by Dalarion.
     * Used for compatibility.
     */
    public static final RecipeType<InfusorRecipe> INFUSOR_OLD = RecipeType.register("infusor_recipe");

    public static void register() {
    }
}
