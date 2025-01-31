package cool.muyucloud.croparia.compat.rei.display;

import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.compat.rei.display.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.registry.CropariaItems;
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
import net.minecraft.world.item.TooltipFlag;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InfusorRecipeDisplay implements Display {
    private final InfusorRecipe recipe;

    public InfusorRecipeDisplay(InfusorRecipe recipe) {
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

    public EntryStack<ItemStack> getElement() {
        ItemStack potion = recipe.getPotion().getDefaultInstance();
        List<Component> tooltips = new LinkedList<>(potion.getTooltipLines(null, TooltipFlag.NORMAL));
        tooltips.set(0, Constants.ELEM_INFUSE_TOOLTIP);
        return EntryStacks.of(recipe.getPotion()).tooltip(tooltips);
    }

    public EntryStack<ItemStack> getResult() {
        return EntryStacks.of(this.recipe.getResult());
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(
            EntryIngredients.of(VanillaEntryTypes.ITEM, recipe.getIngredient().availableStacks()),
            EntryIngredients.of(recipe.getPotion()),
            EntryIngredients.of(CropariaItems.INFUSOR.get())
        );
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.getResult()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return InfusorRecipeDisplayCategory.ID;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(recipe.getId());
    }
}
