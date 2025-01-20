package cool.muyucloud.croparia.recipe.serializer;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.recipe.OldRitualRecipe;
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
