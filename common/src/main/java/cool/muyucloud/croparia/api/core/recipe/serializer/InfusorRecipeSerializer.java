package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import cool.muyucloud.croparia.api.core.recipe.InfusorRecipe;
import cool.muyucloud.croparia.api.core.recipe.predicate.GenericIngredient;
import cool.muyucloud.croparia.api.element.ElementsEnum;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class InfusorRecipeSerializer implements RecipeSerializer<InfusorRecipe> {

    @Override
    public @NotNull InfusorRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
        ElementsEnum element = ElementsEnum.valueOf(jsonObject.get("element").getAsString().toUpperCase());
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

        InfusorRecipe recipe = new InfusorRecipe();
        recipe.setId(id);
        recipe.setElement(element);
        recipe.setIngredient(ingredient);
        recipe.setResult(result);
        return recipe;
    }

    @Override
    public @NotNull InfusorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        ElementsEnum element = buf.readEnum(ElementsEnum.class);
        GenericIngredient ingredient = buf.readWithCodec(GenericIngredient.CODEC);
        ItemStack result = buf.readItem();

        InfusorRecipe recipe = new InfusorRecipe();
        recipe.setId(id);
        recipe.setElement(element);
        recipe.setIngredient(ingredient);
        recipe.setResult(result);
        return recipe;
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, InfusorRecipe recipe) {
        friendlyByteBuf.writeEnum(recipe.getElement());
        friendlyByteBuf.writeWithCodec(GenericIngredient.CODEC, recipe.getIngredient());
        friendlyByteBuf.writeItem(recipe.getResult());
    }

    // IDK what are these used for but forge needs it
    private ResourceLocation registryName;

    public Object setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName;
        return this;
    }

    public ResourceLocation getRegistryName() {
        return this.registryName;
    }
}
