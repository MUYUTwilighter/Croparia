//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.defacto34.croparia.core.util.ElementsEnum;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class InfusorRecipeSerializer implements RecipeSerializer<InfusorRecipe> {
    public static final InfusorRecipeSerializer INSTANCE = new InfusorRecipeSerializer();
    public static final Identifier ID = new Identifier("croparia:infusor_recipe");

    private InfusorRecipeSerializer() {
    }

    public InfusorRecipe read(Identifier id, JsonObject json) {
        InfusorRecipeJsonFormat recipeJson = (InfusorRecipeJsonFormat)(new Gson()).fromJson(json, InfusorRecipeJsonFormat.class);
        if (recipeJson.input != null && recipeJson.element != null && recipeJson.output != null) {
            if (recipeJson.count == 0) {
                recipeJson.count = 1;
            }

            Item input = (Item)Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input)).orElseThrow(() -> {
                return new JsonSyntaxException("No such item " + recipeJson.input);
            });
            ElementsEnum element = (ElementsEnum)Enum.valueOf(ElementsEnum.class, recipeJson.element.toUpperCase());
            Item output = (Item)Registries.ITEM.getOrEmpty(new Identifier(recipeJson.output)).orElseThrow(() -> {
                return new JsonSyntaxException("No such item " + recipeJson.output);
            });
            int count = recipeJson.count;
            InfusorRecipe.addRecipe(id, input, element, output, count);
            return new InfusorRecipe(id, input, element, output, count);
        } else {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
    }

    public void write(PacketByteBuf buf, InfusorRecipe recipe) {
        buf.writeItemStack(new ItemStack(recipe.getInput()));
        buf.writeEnumConstant(recipe.getElement());
        buf.writeItemStack(recipe.getOutput());
        buf.writeInt(recipe.getCount());
    }

    public InfusorRecipe read(Identifier id, PacketByteBuf buf) {
        Item input = buf.readItemStack().getItem();
        ElementsEnum element = (ElementsEnum)buf.readEnumConstant(ElementsEnum.class);
        Item output = buf.readItemStack().getItem();
        int count = buf.readInt();
        return new InfusorRecipe(id, input, element, output, count);
    }
}
