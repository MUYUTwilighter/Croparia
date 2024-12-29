package cool.muyucloud.recipe.serializer;

import com.google.gson.JsonObject;
import cool.muyucloud.recipe.OldRitualRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class OldRitualRecipeSerializer implements RecipeSerializer<OldRitualRecipe> {
    @Override
    public @NotNull OldRitualRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        return OldRitualRecipe.fromJson(resourceLocation, jsonObject);
    }

    @Override
    public @NotNull OldRitualRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        return OldRitualRecipe.fromNetwork(resourceLocation, friendlyByteBuf);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, OldRitualRecipe recipe) {
        recipe.toNetwork(friendlyByteBuf);
    }
}
