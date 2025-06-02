package cool.muyucloud.croparia.compat.rei.display;

import cool.muyucloud.croparia.api.core.recipe.DisplayableRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SimpleDisplay<R extends DisplayableRecipe<?>> implements Display {
    private final R recipe;
    private final ResourceLocation id;
    private final SimpleSerializer<R> serializer;
    private final transient Map<String, EntryIngredient> inputEntries;
    private final transient Map<String, EntryIngredient> outputEntries;

//    @SuppressWarnings("unchecked")
//    public SimpleDisplay(RecipeHolder<? extends DisplayableRecipe<?>> recipe, ResourceLocation id, SimpleSerializer<? extends DisplayableRecipe<?>> serializer) throws ClassCastException {
//        this((R) recipe.value(), id, (SimpleSerializer<R>) serializer);
//    }

    public SimpleDisplay(RecipeHolder<R> holder, SimpleSerializer<R> serializer) {
        this(holder.value(), holder.id().location(), serializer);
    }

    public SimpleDisplay(R recipe, ResourceLocation id, SimpleSerializer<R> serializer) {
        this.recipe = recipe;
        this.id = id;
        this.serializer = serializer;
        this.inputEntries = serializer.parseInput(recipe);
        this.outputEntries = serializer.parseOutput(recipe);
    }

    public R getRecipe() {
        return recipe;
    }

    public ResourceLocation getId() {
        return id;
    }

    public EntryIngredient getInput(String key) {
        return inputEntries.get(key);
    }

    public EntryIngredient getOutput(String key) {
        return outputEntries.get(key);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputEntries.values().stream().toList();
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputEntries.values().stream().toList();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return serializer.getId();
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(getId());
    }

    @Override
    public @Nullable DisplaySerializer<SimpleDisplay<R>> getSerializer() {
        return serializer.getSerializer();
    }
}
