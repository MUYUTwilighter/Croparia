package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import cool.muyucloud.croparia.api.core.recipe.util.BlockStatePredicate;
import cool.muyucloud.croparia.api.core.recipe.util.GenericIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class RitualRecipeSerializer implements RecipeSerializer<RitualRecipe> {
    public static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.INT.fieldOf("tier").forGetter(RitualRecipe::getTier),
        BlockStatePredicate.Builder.CODEC.fieldOf("block").forGetter(recipe -> recipe.getBlock().getBuilder()),
        GenericIngredient.CODEC.fieldOf("ingredient").forGetter(RitualRecipe::getIngredient),
        ItemStack.CODEC.fieldOf("result").forGetter(RitualRecipe::getResult)
    ).apply(instance, (tier, block, ingredient, result) -> new RitualRecipe(tier, block.build(), ingredient, result)));
    public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = StreamCodec.ofMember(
        (recipe, buf) -> {
            buf.writeInt(recipe.getTier());
            buf.writeJsonWithCodec(BlockStatePredicate.Builder.CODEC, recipe.getStateBuilder());
            buf.writeJsonWithCodec(GenericIngredient.CODEC, recipe.getIngredient());
            buf.writeJsonWithCodec(ItemStack.CODEC, recipe.getResult());
        },
        (buf) -> {
            int tier = buf.readInt();
            if (tier < 1) {
                throw new IllegalArgumentException("Tier must be at least 1");
            }
            BlockStatePredicate block = buf.readJsonWithCodec(BlockStatePredicate.Builder.CODEC).build();
            GenericIngredient ingredient = buf.readJsonWithCodec(GenericIngredient.CODEC);
            ItemStack result = buf.readJsonWithCodec(ItemStack.CODEC);
            return new RitualRecipe(tier, block, ingredient, result);
        }
    );

    @Override
    public @NotNull MapCodec<RitualRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
