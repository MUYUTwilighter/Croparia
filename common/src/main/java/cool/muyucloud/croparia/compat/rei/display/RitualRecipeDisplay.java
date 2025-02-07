package cool.muyucloud.croparia.compat.rei.display;

import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.compat.rei.display.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.util.Constants;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RitualRecipeDisplay implements Display {
    private final RitualRecipe recipe;

    public RitualRecipeDisplay(RitualRecipe recipe) {
        this.recipe = recipe;
    }

    public Collection<EntryStack<ItemStack>> getIngredient() {
        return recipe.getIngredient().availableStacks().stream().map(stack -> {
            List<Component> tooltips = new LinkedList<>();
            tooltips.add(Constants.ITEM_DROP_TOOLTIP);
            recipe.getIngredient().nbtTooltip().ifPresent(tooltips::add);
            return EntryStacks.of(stack).tooltip(tooltips);
        }).toList();
    }

    public Collection<EntryStack<ItemStack>> getBlockItems() {
        return recipe.extractBlockItems().stream().map(item -> {
            List<Component> tooltips = new LinkedList<>();
            tooltips.add(Constants.BLOCK_PLACE_TOOLTIP);
            tooltips.addAll(recipe.getBlock().tooltip());
            return EntryStacks.of(item).tooltip(tooltips);
        }).toList();
    }

    public EntryStack<ItemStack> getResult() {
        return EntryStacks.of(recipe.getResult());
    }

    public EntryStack<ItemStack> getRitual() {
        return EntryStacks.of(recipe.getRitualItem()).tooltip(Constants.TOOLTIP_RITUAL);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(
            EntryIngredients.of(VanillaEntryTypes.ITEM, recipe.extractBlockItems()),
            EntryIngredients.of(VanillaEntryTypes.ITEM, recipe.getIngredient().availableStacks()),
            EntryIngredients.of(recipe.getRitualItem())
        );
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.getResult()));
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
