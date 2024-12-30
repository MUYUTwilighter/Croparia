package cool.muyucloud.recipe;

import cool.muyucloud.recipe.serializer.OldInfusorRecipeSerializer;
import cool.muyucloud.registry.RecipeSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Old infusor recipe data entity formed by Dalarion.<br/>
 * Once this object is deserialized by {@link OldInfusorRecipeSerializer},
 * it should be treated as the new version of {@link InfusorRecipe}. <br/>
 * The instances of this class are merged with the new infusor recipe data entities by invoking
 * {@link net.minecraft.world.item.crafting.RecipeManager#getRecipeFor(RecipeType, Container, Level)}.
 */
public class OldInfusorRecipe extends InfusorRecipe {
    public int getCount() {
        return this.getResult().getCount();
    }

    public void setInput(@NotNull String input) {
        this.setIngredient(BuiltInRegistries.ITEM.getOptional(
            ResourceLocation.tryParse(input)
        ).orElseThrow(
            () -> new IllegalArgumentException("Invalid item item in recipe %s".formatted(this.getId()))
        ).getDefaultInstance());
    }

    public void setInput(@NotNull Item input) {
        this.setIngredient(input.getDefaultInstance());
    }

    public void setOutput(@NotNull String output) {
        this.setResult(BuiltInRegistries.ITEM.getOptional(
            ResourceLocation.tryParse(output)
        ).orElseThrow(
            () -> new IllegalArgumentException("Invalid output item in recipe %s".formatted(this.getId()))
        ).getDefaultInstance());
    }

    public void setOutput(@NotNull Item output) {
        this.setIngredient(output.getDefaultInstance());
    }

    public void setCount(int count) {
        this.result.setCount(Math.max(count, 1));
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.INFUSOR_OLD.get();
    }
}
