package cool.muyucloud.croparia.rei.display;

import cool.muyucloud.croparia.recipe.RitualRecipe;
import cool.muyucloud.croparia.recipe.serializer.RitualRecipeSerializer;
import cool.muyucloud.croparia.rei.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.util.Constants;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RitualRecipeDisplay implements SimpleDisplay<RitualRecipe> {
    public static final DisplaySerializer<RitualRecipeDisplay> SERIALIZER = DisplayFactory.serializer(
        RitualRecipeSerializer.CODEC.codec(), RitualRecipeDisplay::new
    );

    @NotNull
    private final RitualRecipe recipe;
    @NotNull
    private final ResourceLocation id;

    public RitualRecipeDisplay(@NotNull RitualRecipe recipe, @NotNull ResourceLocation id) {
        this.recipe = recipe;
        this.id = id;
    }

    public @NotNull RitualRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public ResourceLocation getRecipeId() {
        return this.id;
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
        return EntryStacks.of(recipe.getRitualItem());
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
        return Optional.of(id);
    }

    @Override
    public @Nullable DisplaySerializer<? extends RitualRecipeDisplay> getSerializer() {
        return SERIALIZER;
    }
}
