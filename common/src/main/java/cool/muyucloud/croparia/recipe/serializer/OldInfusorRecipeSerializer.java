package cool.muyucloud.croparia.recipe.serializer;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.data.ElementsEnum;
import cool.muyucloud.croparia.recipe.OldInfusorRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Infusor recipe serializer for old infusor recipes developed by Dalarion
 */
public class OldInfusorRecipeSerializer implements RecipeSerializer<OldInfusorRecipe> {
    @Override
    public @NotNull OldInfusorRecipe fromJson(ResourceLocation id, JsonObject json) {
        String input = GsonHelper.getAsString(json, "input");
        String output = GsonHelper.getAsString(json, "output");
        ElementsEnum element = ElementsEnum.valueOf(GsonHelper.getAsString(json, "element").toUpperCase());
        int count = GsonHelper.getAsInt(json, "count");

        OldInfusorRecipe recipe = new OldInfusorRecipe();
        recipe.setId(id);
        recipe.setInput(input);
        recipe.setOutput(output);
        recipe.setCount(count);
        recipe.setElement(element);
        return recipe;
    }

    @Override
    public @NotNull OldInfusorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        Item input = buf.readItem().getItem();
        ElementsEnum element = buf.readEnum(ElementsEnum.class);
        Item output = buf.readItem().getItem();
        int count = buf.readInt();

        OldInfusorRecipe recipe = new OldInfusorRecipe();
        recipe.setId(id);
        recipe.setInput(input);
        recipe.setOutput(output);
        recipe.setCount(count);
        recipe.setElement(element);
        return recipe;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, OldInfusorRecipe recipe) {
        buf.writeItem(recipe.getInput());
        buf.writeEnum(recipe.getElement());
        buf.writeItem(recipe.getResult());
        buf.writeInt(recipe.getCount());
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
