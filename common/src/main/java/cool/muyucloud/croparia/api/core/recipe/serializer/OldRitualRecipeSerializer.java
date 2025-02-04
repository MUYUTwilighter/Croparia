package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.OldRitualRecipe;
import cool.muyucloud.croparia.api.core.recipe.predicate.BlockStatePredicate;
import jdk.jfr.Experimental;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Experimental
public class OldRitualRecipeSerializer implements RecipeSerializer<OldRitualRecipe> {
    public static final MapCodec<OldRitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.INT.fieldOf("tier").forGetter(OldRitualRecipe::getTier),
        ResourceLocation.CODEC.fieldOf("block").forGetter(OldRitualRecipe::getBlockName),
        ResourceLocation.CODEC.fieldOf("input").forGetter(OldRitualRecipe::getInput),
        ResourceLocation.CODEC.fieldOf("output").forGetter(OldRitualRecipe::getOutput),
        Codec.INT.fieldOf("count").forGetter(OldRitualRecipe::getCount)
    ).apply(instance, (tier, block, input, output, count) -> {
        BlockStatePredicate state = BlockStatePredicate.builder().block(block.toString()).build();
        ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.getValue(input), count);
        ItemStack result = new ItemStack(BuiltInRegistries.ITEM.getValue(output), count);
        return new OldRitualRecipe(tier, state, stack, result);
    }));
    public static final StreamCodec<RegistryFriendlyByteBuf, OldRitualRecipe> STREAM_CODEC = StreamCodec.ofMember(
        (recipe, buf) -> {
            if (recipe.getBlock().isSpecified()) {
                buf.writeInt(recipe.getTier());
                ItemStack stack = recipe.extractItem();
                if (stack.isEmpty()) {
                    throw new AssertionError("The specified item does not exist, or declared as AIR");
                }
                buf.writeJsonWithCodec(ItemStack.CODEC, stack);
                Block block = recipe.extractBlock();
                if (block == Blocks.AIR) {
                    throw new AssertionError("The specified block does not exist, or declared as AIR");
                }
                buf.writeJsonWithCodec(ItemStack.CODEC, new ItemStack(block));
                buf.writeInt(recipe.getResultCount());
            } else {
                throw new AssertionError("The block predicate does not specify a block");
            }
        },
        buf -> {
            int tier = buf.readInt();
            ItemStack ingredient = buf.readJsonWithCodec(ItemStack.CODEC);
            BlockStatePredicate block = BlockStatePredicate.builder()
                .block(Objects.requireNonNull(buf.readJsonWithCodec(ItemStack.CODEC).getItem().arch$registryName()).toString())
                .build();
            ItemStack result = buf.readJsonWithCodec(ItemStack.CODEC);
            result.setCount(buf.readInt());
            return new OldRitualRecipe(tier, block, ingredient, result);
        }
    );

    @Override
    public @NotNull MapCodec<OldRitualRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, OldRitualRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
