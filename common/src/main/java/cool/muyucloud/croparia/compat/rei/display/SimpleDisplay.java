package cool.muyucloud.croparia.compat.rei.display;

import cool.muyucloud.croparia.api.recipe.DisplayableRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class SimpleDisplay<R extends DisplayableRecipe<?>> implements Display {
    private final R recipe;
    private final ResourceLocation id;
    private final SimpleCategory<R> serializer;
    private final transient Map<String, Supplier<EntryIngredient>> inputEntries;
    private final transient Map<String, Supplier<EntryIngredient>> outputEntries;

    public SimpleDisplay(RecipeHolder<R> holder, SimpleCategory<R> serializer) {
        this.recipe = holder.value();
        this.id = holder.id().location();
        this.serializer = serializer;
        this.inputEntries = serializer.inputEntries(holder);
        this.outputEntries = serializer.outputEntries(holder);
    }

    public SimpleDisplay(R recipe, ResourceLocation id, SimpleCategory<R> serializer) {
        this(new RecipeHolder<>(ResourceKey.create(Registries.RECIPE, id), recipe), serializer);
    }

    public R getRecipe() {
        return recipe;
    }

    public ResourceLocation getId() {
        return id;
    }

    public EntryIngredient getInput(String key) {
        return inputEntries.get(key).get();
    }

    public EntryIngredient getOutput(String key) {
        return outputEntries.get(key).get();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputEntries.values().stream().map(Supplier::get).toList();
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return outputEntries.values().stream().map(Supplier::get).toList();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return serializer.getCategoryIdentifier();
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
