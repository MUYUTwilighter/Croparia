package cool.muyucloud.recipe.serializer;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.data.ElementsEnum;
import cool.muyucloud.recipe.InfusorRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class InfusorRecipeSerializer implements RecipeSerializer<InfusorRecipe> {
    @Override
    public @NotNull InfusorRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
        ElementsEnum element = ElementsEnum.valueOf(jsonObject.get("element").getAsString().toUpperCase());
        ItemStack ingredient = ItemStack.CODEC.parse(JsonOps.INSTANCE, jsonObject.get("ingredient"))
            .getOrThrow(false, msg -> {
                throw new IllegalArgumentException(msg);
            });
        ItemStack result = ItemStack.CODEC.parse(JsonOps.INSTANCE, jsonObject.get("result"))
            .getOrThrow(false, msg -> {
                throw new IllegalArgumentException(msg);
            });

        InfusorRecipe recipe = new InfusorRecipe();
        recipe.setId(id);
        recipe.setElement(element);
        recipe.setIngredient(ingredient);
        recipe.setResult(result);
        return recipe;
    }

    @Override
    public @NotNull InfusorRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buf) {
        ElementsEnum element = buf.readEnum(ElementsEnum.class);
        ItemStack ingredient = buf.readItem();
        ItemStack result = buf.readItem();

        InfusorRecipe recipe = new InfusorRecipe();
        recipe.setElement(element);
        recipe.setIngredient(ingredient);
        recipe.setResult(result);
        return recipe;
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, InfusorRecipe recipe) {
        friendlyByteBuf.writeEnum(recipe.getElement());
        friendlyByteBuf.writeItem(recipe.getIngredient());
        friendlyByteBuf.writeItem(recipe.getResult());
    }
}
