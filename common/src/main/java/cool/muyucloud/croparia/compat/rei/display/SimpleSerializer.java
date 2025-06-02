package cool.muyucloud.croparia.compat.rei.display;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.api.core.recipe.TypedSerializer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SimpleSerializer<R extends DisplayableRecipe<?>> {
    private final Class<R> recipeClass;
    private final TypedSerializer<R> recipeSerializer;
    private final CategoryIdentifier<SimpleDisplay<R>> categoryIdentifier;
    private final DisplaySerializer<SimpleDisplay<R>> serializer;
    private final BiConsumer<R, Map<String, EntryIngredient>> inputParser;
    private final BiConsumer<R, Map<String, EntryIngredient>> outputParser;

    public SimpleSerializer(
        Class<R> recipeClass,
        TypedSerializer<R> recipeSerializer,
        BiConsumer<R, Map<String, EntryIngredient>> inputParser,
        BiConsumer<R, Map<String, EntryIngredient>> outputParser
    ) {
        this.recipeClass = recipeClass;
        this.recipeSerializer = recipeSerializer;
        this.categoryIdentifier = CategoryIdentifier.of(recipeSerializer.getId().orElseThrow());
        this.serializer = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                recipeSerializer.codec().fieldOf("recipe").forGetter(SimpleDisplay::getRecipe),
                ResourceLocation.CODEC.fieldOf("id").forGetter(SimpleDisplay::getId)
            ).apply(instance, (recipe, id) -> new SimpleDisplay<>(recipe, id, this))),
            StreamCodec.of((buf, display) -> {
                buf.writeJsonWithCodec(recipeSerializer.codec().codec(), display.getRecipe());
                buf.writeResourceLocation(display.getId());
            }, buf -> {
                R recipe = buf.readJsonWithCodec(recipeSerializer.codec().codec());
                ResourceLocation id = buf.readResourceLocation();
                return new SimpleDisplay<>(recipe, id, this);
            })
        );
        this.inputParser = inputParser;
        this.outputParser = outputParser;
    }

    public TypedSerializer<R> getRecipeSerializer() {
        return recipeSerializer;
    }

    public Class<R> getRecipeClass() {
        return recipeClass;
    }

    public CategoryIdentifier<SimpleDisplay<R>> getId() {
        return categoryIdentifier;
    }

    public DisplaySerializer<SimpleDisplay<R>> getSerializer() {
        return serializer;
    }

    public Map<String, EntryIngredient> parseInput(R recipe) {
        Map<String, EntryIngredient> map = new HashMap<>();
        inputParser.accept(recipe, map);
        return map;
    }

    public Map<String, EntryIngredient> parseOutput(R recipe) {
        Map<String, EntryIngredient> map = new HashMap<>();
        outputParser.accept(recipe, map);
        return map;
    }
}
