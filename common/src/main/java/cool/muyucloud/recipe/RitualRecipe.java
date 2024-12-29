package cool.muyucloud.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.container.RitualContainer;
import cool.muyucloud.predicate.BlockStatePredicate;
import cool.muyucloud.registry.RecipeSerializers;
import cool.muyucloud.registry.RecipeTypes;
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

public class RitualRecipe implements Recipe<RitualContainer> {

    @NotNull
    private final ResourceLocation id;
    private final int tier;
    @NotNull
    private final BlockStatePredicate block;
    @NotNull
    private final ItemStack ingredient;
    @NotNull
    private final ItemStack result;

    public RitualRecipe(@NotNull ResourceLocation id, int tier, @NotNull BlockStatePredicate state, @NotNull ItemStack ingredient, @NotNull ItemStack result) {
        this.id = id;
        this.tier = tier;
        this.block = state;
        this.ingredient = ingredient;
        this.result = result;
    }

    public static RitualRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
        int tier = GsonHelper.getAsInt(jsonObject, "tier");
        BlockStatePredicate block = BlockStatePredicate.Builder.CODEC.parse(
            JsonOps.INSTANCE, jsonObject.get("block")
        ).getOrThrow(false, msg -> {
            throw new IllegalArgumentException(msg);
        }).build();
        ItemStack ingredient = ItemStack.CODEC.parse(
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
        BlockStatePredicate block = buf.readJsonWithCodec(BlockStatePredicate.Builder.CODEC).build();
        ItemStack ingredient = buf.readItem();
        ItemStack result = buf.readItem();
        return new RitualRecipe(id, tier, block, ingredient, result);
    }

    public void toNetwork(@NotNull FriendlyByteBuf buf) {
        buf.writeInt(this.getTier());
        buf.writeJsonWithCodec(BlockStatePredicate.Builder.CODEC, this.getStateBuilder());
        buf.writeItem(this.getIngredient());
        buf.writeItem(this.getResult());
    }

    protected @NotNull ItemStack getIngredient() {
        return ingredient;
    }

    protected @NotNull ItemStack getResult() {
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
        return ItemStack.isSameItemSameTags(input, ingredient)
            && input.getCount() >= ingredient.getCount()
            && this.block.test(state)
            && tier >= this.tier;
    }

    @Override
    public boolean matches(RitualContainer container, Level level) {
        return matches(container);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RitualContainer container, @NotNull RegistryAccess registryAccess) {
        return matches(container) ? getResult().copy() : ItemStack.EMPTY;
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
        return RecipeTypes.RITUAL;
    }
}
