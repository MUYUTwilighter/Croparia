package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.OldInfusorRecipe;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import cool.muyucloud.croparia.api.core.recipe.util.GenericIngredient;
import jdk.jfr.Experimental;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Infusor recipe serializer for old infusor recipes developed by Dalarion
 */
@Experimental
public class OldInfusorRecipeSerializer implements RecipeSerializer<OldInfusorRecipe> {
    public static final MapCodec<OldInfusorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.STRING.fieldOf("element").forGetter(InfusorRecipe::getElementName),
            Codec.STRING.fieldOf("input").forGetter(recipe -> Objects.requireNonNull(recipe.getInput().getItem().arch$registryName()).toString()),
            Codec.STRING.fieldOf("output").forGetter(recipe -> Objects.requireNonNull(recipe.getResult().getItem().arch$registryName()).toString()),
            Codec.INT.fieldOf("count").forGetter(OldInfusorRecipe::getCount)
        ).apply(instance, (input, output, element, count) -> {
            OldInfusorRecipe recipe = new OldInfusorRecipe();
            recipe.setInput(input);
            recipe.setOutput(output);
            recipe.setCount(count);
            recipe.setElement(ElementsEnum.valueOf(element.toUpperCase()));
            return recipe;
        })
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, OldInfusorRecipe> STREAM_CODEC = StreamCodec.ofMember(
        (recipe, buf) -> {
            buf.writeEnum(recipe.getElement());
            buf.writeJsonWithCodec(GenericIngredient.CODEC, recipe.getIngredient());
            buf.writeJsonWithCodec(ItemStack.CODEC, recipe.getResult());
        },
        (buf) -> {
            ElementsEnum element = buf.readEnum(ElementsEnum.class);
            GenericIngredient ingredient = buf.readJsonWithCodec(GenericIngredient.CODEC);
            ItemStack result = buf.readJsonWithCodec(ItemStack.CODEC);
            OldInfusorRecipe recipe = new OldInfusorRecipe();
            recipe.setElement(element);
            recipe.setIngredient(ingredient);
            recipe.setResult(result);
            return recipe;
        }
    );

    @Override
    public @NotNull MapCodec<OldInfusorRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, OldInfusorRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
