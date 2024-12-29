package cool.muyucloud.recipe;

import com.google.gson.JsonObject;
import cool.muyucloud.predicate.BlockStatePredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class OldRitualRecipe extends RitualRecipe {
    public OldRitualRecipe(
        @NotNull ResourceLocation id,
        int tier,
        @NotNull BlockStatePredicate state,
        @NotNull ItemStack ingredient,
        @NotNull ItemStack result
    ) {
        super(id, tier, state, ingredient, result);
    }

    public Optional<Block> extractBlock() {
        if (this.getStateBuilder().isSpecified()) {
            @Nullable ResourceLocation id = ResourceLocation.tryParse(this.getBlock().getBuilder().getBlock());
            @Nullable Block block = BuiltInRegistries.BLOCK.get(id);
            return Optional.of(block);
        } else {
            return Optional.empty();
        }
    }

    public static @NotNull OldRitualRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        int tier = GsonHelper.getAsInt(json, "tier");
        BlockStatePredicate block = BlockStatePredicate.Builder.create()
            .block(GsonHelper.getAsString(json, "block")).build();
        ItemStack ingredient = Optional.of(
            BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "input")))
        ).orElseThrow(
            () -> new IllegalArgumentException("Invalid or missing input item in recipe %s".formatted(id))
        ).getDefaultInstance();
        ItemStack result = Optional.of(
            BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "output")))
        ).orElseThrow(
            () -> new IllegalArgumentException("Invalid or missing input item in recipe %s".formatted(id))
        ).getDefaultInstance();
        int count = GsonHelper.getAsInt(json, "count");
        result.setCount(count);
        return new OldRitualRecipe(id, tier, block, ingredient, result);
    }

    public static @NotNull OldRitualRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        int tier = buf.readInt();
        ItemStack ingredient = buf.readItem();
        BlockStatePredicate block = BlockStatePredicate.builder()
            .block(Objects.requireNonNull(buf.readItem().getItem().arch$registryName()).toString())
            .build();
        ItemStack result = buf.readItem();
        result.setCount(buf.readInt());
        return new OldRitualRecipe(id, tier, block, ingredient, result);
    }

    public int getResultCount() {
        return this.getResult().getCount();
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf) {
        if (this.getBlock().isSpecified()) {
            buf.writeInt(this.getTier());
            buf.writeItem(this.getIngredient());
            this.extractBlock().ifPresentOrElse(block -> buf.writeItem(new ItemStack(block)), () -> {
                throw new AssertionError("The block specified in the block predicate is invalid");
            });
            buf.writeItem(this.getResult());
            buf.writeInt(this.getResultCount());
        } else {
            throw new AssertionError("The block predicate does not specify a block");
        }
    }
}
