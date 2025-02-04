package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.core.recipe.RitualRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class RitualRecipeSerializer implements RecipeSerializer<RitualRecipe> {
    @Override
    public @NotNull RitualRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        return RitualRecipe.fromJson(resourceLocation, jsonObject);
    }

    @Override
    public @NotNull RitualRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        return RitualRecipe.fromNetwork(resourceLocation, friendlyByteBuf);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, RitualRecipe recipe) {
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
