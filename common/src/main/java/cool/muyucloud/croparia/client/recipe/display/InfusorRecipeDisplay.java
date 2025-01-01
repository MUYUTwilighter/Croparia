package cool.muyucloud.croparia.client.recipe.display;

import cool.muyucloud.croparia.client.ReiClient;
import cool.muyucloud.croparia.client.recipe.display.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.recipe.InfusorRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class InfusorRecipeDisplay implements Display {
    private final ResourceLocation id;
    private final EntryIngredient ingredient;
    private final EntryIngredient element;
    private final EntryIngredient result;

    public InfusorRecipeDisplay(InfusorRecipe recipe) {
        this.id = recipe.getId();
        this.ingredient = ReiClient.ofIngredient(recipe.getIngredient());
        this.element = ReiClient.ofElement(recipe.getElement());
        this.result = EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM, recipe.getResult()));
    }

    public EntryStack<?> getIngredient() {
        return ingredient.isEmpty() ? EntryStack.of(VanillaEntryTypes.ITEM, ItemStack.EMPTY) : ingredient.get(0);
    }

    public EntryStack<?> getElement() {
        return element.isEmpty() ? EntryStack.of(VanillaEntryTypes.ITEM, ItemStack.EMPTY) : element.get(0);
    }

    public EntryStack<?> getResult() {
        return result.isEmpty() ? EntryStack.of(VanillaEntryTypes.ITEM, ItemStack.EMPTY) : result.get(0);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(this.ingredient, this.element);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(this.result);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return InfusorRecipeDisplayCategory.ID;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.ofNullable(this.id);
    }
}
