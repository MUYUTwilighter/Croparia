package cool.muyucloud.croparia.api.core.recipe.serializer;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class RitualStructureSerializer implements RecipeSerializer<RitualStructure> {
    @Override
    public @NotNull RitualStructure fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        return RitualStructure.fromJson(resourceLocation, jsonObject);
    }

    @Override
    public @NotNull RitualStructure fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buf) {
        return RitualStructure.fromNetwork(resourceLocation, buf);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, RitualStructure recipe) {
        recipe.toNetwork(buf);
    }
}
