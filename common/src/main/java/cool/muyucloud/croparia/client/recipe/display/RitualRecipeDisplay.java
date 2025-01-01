package cool.muyucloud.croparia.client.recipe.display;

import cool.muyucloud.croparia.client.ReiClient;
import cool.muyucloud.croparia.client.recipe.display.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.recipe.RitualRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class RitualRecipeDisplay implements Display {
    private final RitualRecipe recipe;

    public RitualRecipeDisplay(RitualRecipe recipe) {
        this.recipe = recipe;
    }

    public EntryIngredient getIngredient() {
        return ReiClient.ofIngredient(recipe.getIngredient());
    }

    public EntryIngredient getBlock() {
        return null;
    }

    public EntryIngredient getResult() {
        return EntryIngredient.of(EntryStacks.of(recipe.getResult()));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of();
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return RitualRecipeDisplayCategory.ID;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(recipe.getId());
    }
}
