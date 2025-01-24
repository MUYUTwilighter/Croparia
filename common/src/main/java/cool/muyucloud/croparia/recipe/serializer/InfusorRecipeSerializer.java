package cool.muyucloud.croparia.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.recipe.InfusorRecipe;
import cool.muyucloud.croparia.util.predicate.GenericIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class InfusorRecipeSerializer implements RecipeSerializer<InfusorRecipe> {
    public static final MapCodec<InfusorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.STRING.fieldOf("element").forGetter(InfusorRecipe::getElementName),
            GenericIngredient.CODEC.fieldOf("ingredient").forGetter(InfusorRecipe::getIngredient),
            ItemStack.CODEC.fieldOf("result").forGetter(InfusorRecipe::getResult)
        ).apply(instance, (element, ingredient, result) -> {
            InfusorRecipe recipe = new InfusorRecipe();
            recipe.setElement(ElementsEnum.valueOf(element.toUpperCase()));
            recipe.setIngredient(ingredient);
            recipe.setResult(result);
            return recipe;
        })
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, InfusorRecipe> STREAM_CODEC = StreamCodec.ofMember(
        (recipe, buf) -> {
            buf.writeEnum(recipe.getElement());
            buf.writeJsonWithCodec(GenericIngredient.CODEC, recipe.getIngredient());
            buf.writeJsonWithCodec(ItemStack.CODEC, recipe.getResult());
        },
        (buf) -> {
            ElementsEnum element = buf.readEnum(ElementsEnum.class);
            GenericIngredient ingredient = buf.readJsonWithCodec(GenericIngredient.CODEC);
            ItemStack result = buf.readJsonWithCodec(ItemStack.CODEC);

            InfusorRecipe recipe = new InfusorRecipe();
            recipe.setElement(element);
            recipe.setIngredient(ingredient);
            recipe.setResult(result);
            return recipe;
        }
    );

    @Override
    public @NotNull MapCodec<InfusorRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, InfusorRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
