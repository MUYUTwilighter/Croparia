package cool.muyucloud.croparia.recipe;

import cool.muyucloud.croparia.recipe.container.InfusorContainer;
import cool.muyucloud.croparia.recipe.serializer.OldInfusorRecipeSerializer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.util.predicate.GenericIngredient;
import jdk.jfr.Experimental;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Old infusor recipe data entity formed by Dalarion.<br/>
 * Once this object is deserialized by {@link OldInfusorRecipeSerializer},
 * it should be treated as the new version of {@link InfusorRecipe}. <br/>
 * The instances of this class are merged with the new infusor recipe data entities by invoking
 * {@link net.minecraft.world.item.crafting.RecipeManager#getRecipeFor(RecipeType, RecipeInput, Level, RecipeHolder)}.
 */
@Experimental
public class OldInfusorRecipe extends InfusorRecipe {
    public int getCount() {
        return this.getResult().getCount();
    }

    public void setInput(@NotNull String input) {
        this.setIngredient(new GenericIngredient(BuiltInRegistries.ITEM.getOptional(
            ResourceLocation.tryParse(input)
        ).orElseThrow(
            () -> new IllegalArgumentException("Invalid item item in recipe %s".formatted(this))
        )));
    }

    public @NotNull ItemStack getInput() {
        List<ItemStack> stacks = this.getIngredient().availableStacks();
        if (stacks.isEmpty()) {
            throw new AssertionError("Empty input item in recipe %s".formatted(this));
        } else {
            return stacks.getFirst();
        }
    }

    public void setInput(@NotNull Item input) {
        if (input == CropariaItems.PLACEHOLDER.get()) {
            throw new IllegalArgumentException("Invalid input item in recipe %s".formatted(this));
        }
        this.setIngredient(new GenericIngredient(input));
    }

    public void setOutput(@NotNull String output) {
        this.setResult(BuiltInRegistries.ITEM.getOptional(
            ResourceLocation.tryParse(output)
        ).orElseThrow(
            () -> new IllegalArgumentException("Invalid output item in recipe %s".formatted(this))
        ).getDefaultInstance());
    }

    public void setOutput(@NotNull Item output) {
        this.result = output.getDefaultInstance();
    }

    public void setCount(int count) {
        this.result.setCount(Math.max(count, 1));
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<InfusorContainer>> getSerializer() {
        return RecipeSerializers.INFUSOR_OLD.get();
    }
}
