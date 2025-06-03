package cool.muyucloud.croparia.api.core.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.croparia.api.core.recipe.container.RitualContainer;
import cool.muyucloud.croparia.api.core.recipe.predicate.BlockStatePredicate;
import cool.muyucloud.croparia.api.core.recipe.predicate.GenericIngredient;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.RecipeSerializers;
import cool.muyucloud.croparia.registry.RecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RitualRecipe implements Recipe<RitualContainer> {
    @NotNull
    private final ResourceLocation id;
    private final int tier;
    @NotNull
    private final BlockStatePredicate block;
    @NotNull
    private final GenericIngredient ingredient;
    @NotNull
    private final ItemStack result;

    public RitualRecipe(
        @NotNull ResourceLocation id, int tier, @NotNull BlockStatePredicate state,
        @NotNull GenericIngredient ingredient, @NotNull ItemStack result
    ) {
        this.id = id;
        if (tier < 1) {
            throw new IllegalArgumentException("Tier must be at least 1");
        }
        this.tier = tier;
        this.block = state;
        this.ingredient = ingredient;
        this.result = result;
    }

    public static RitualRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
        int tier = GsonHelper.getAsInt(jsonObject, "tier");
        if (tier < 1) {
            throw new IllegalArgumentException("Tier must be at least 1");
        }
        BlockStatePredicate block = BlockStatePredicate.Builder.CODEC.parse(
            JsonOps.INSTANCE, jsonObject.get("block")
        ).getOrThrow(false, msg -> {
            throw new IllegalArgumentException(msg);
        }).build();
        GenericIngredient ingredient = GenericIngredient.CODEC.parse(
            JsonOps.INSTANCE, jsonObject.get("ingredient")
        ).getOrThrow(false, msg -> {
            throw new IllegalArgumentException(msg);
        });
        ItemStack result = ItemStack.CODEC.parse(
            JsonOps.INSTANCE, jsonObject.get("result")
        ).getOrThrow(false, msg -> {
            throw new IllegalArgumentException(msg);
        });
        return new RitualRecipe(id, tier, block, ingredient, result);
    }

    public static RitualRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        int tier = buf.readInt();
        if (tier < 1) {
            throw new IllegalArgumentException("Tier must be at least 1");
        }
        BlockStatePredicate block = buf.readJsonWithCodec(BlockStatePredicate.Builder.CODEC).build();
        GenericIngredient ingredient = buf.readJsonWithCodec(GenericIngredient.CODEC);
        ItemStack result = buf.readItem();
        return new RitualRecipe(id, tier, block, ingredient, result);
    }

    public void toNetwork(@NotNull FriendlyByteBuf buf) {
        buf.writeInt(this.getTier());
        buf.writeJsonWithCodec(BlockStatePredicate.Builder.CODEC, this.getStateBuilder());
        buf.writeJsonWithCodec(GenericIngredient.CODEC, this.getIngredient());
        buf.writeItem(this.getResult());
    }

    public @NotNull ItemStack getRitualItem() {
        return CropariaItems.getRitualStand(this.tier).get().getDefaultInstance();
    }

    public @NotNull Collection<ItemStack> extractBlockItems() {
        return this.block.availableBlockItems();
    }

    public @NotNull GenericIngredient getIngredient() {
        return ingredient;
    }

    public @NotNull BlockStatePredicate getBlock() {
        return block;
    }

    public @NotNull ItemStack getResult() {
        return result;
    }

    protected BlockStatePredicate.Builder getStateBuilder() {
        return block.getBuilder();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public int getTier() {
        return tier;
    }

    public boolean matches(RitualContainer container) {
        int tier = container.tier();
        ItemStack input = container.item();
        BlockState state = container.state();
        return this.ingredient.test(input)
            && this.block.test(state)
            && tier >= this.tier;
    }

    @Override
    public boolean matches(RitualContainer container, Level level) {
        return matches(container);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RitualContainer container, @NotNull RegistryAccess registryAccess) {
        if (matches(container)) {
            container.item().shrink(this.getIngredient().getCount());
            return getResult().copy();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.RITUAL.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypes.RITUAL.get();
    }
}
