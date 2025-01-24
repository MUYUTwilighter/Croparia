package cool.muyucloud.croparia.recipe;

import cool.muyucloud.croparia.recipe.container.RitualContainer;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.util.predicate.BlockStatePredicate;
import cool.muyucloud.croparia.util.predicate.GenericIngredient;
import jdk.jfr.Experimental;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@Experimental
public class OldRitualRecipe extends RitualRecipe {
    public OldRitualRecipe(
        int tier,
        @NotNull BlockStatePredicate state,
        @NotNull ItemStack stack,
        @NotNull ItemStack result
    ) {
        super(tier, state, new GenericIngredient(stack), result);
    }

    public ResourceLocation getBlockName() {
        return this.extractBlock().arch$registryName();
    }

    public ResourceLocation getInput() {
        return this.extractItem().getItem().arch$registryName();
    }

    public ResourceLocation getOutput() {
        return this.getResult().getItem().arch$registryName();
    }

    public int getCount() {
        return this.getResult().getCount();
    }

    public Block extractBlock() {
        if (this.getStateBuilder().isTag()) {
            @Nullable ResourceLocation id = ResourceLocation.tryParse(Objects.requireNonNull(this.getBlock().getBuilder().getBlock()));
            return BuiltInRegistries.BLOCK.getValue(id);
        } else {
            return Blocks.AIR;
        }
    }

    public ItemStack extractItem() {
        List<ItemStack> stacks = this.getIngredient().availableStacks();
        if (stacks.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            return stacks.getFirst();
        }
    }

    public int getResultCount() {
        return this.getResult().getCount();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<RitualContainer>> getSerializer() {
        return RecipeSerializers.RITUAL_OLD.get();
    }
}
