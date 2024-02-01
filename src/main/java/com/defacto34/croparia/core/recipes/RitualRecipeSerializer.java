//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class RitualRecipeSerializer implements RecipeSerializer<RitualRecipe> {
    public static final RitualRecipeSerializer INSTANCE = new RitualRecipeSerializer();
    public static final Identifier ID = new Identifier("croparia:ritual_recipe");

    private RitualRecipeSerializer() {
    }

    public RitualRecipe read(Identifier id, JsonObject json) {
        RitualRecipeJsonFormat recipeJson = (RitualRecipeJsonFormat)(new Gson()).fromJson(json, RitualRecipeJsonFormat.class);
        if (recipeJson.input != null && recipeJson.block != null && recipeJson.output != null) {
            if (recipeJson.count == 0) {
                recipeJson.count = 1;
            }

            if (recipeJson.tier == 0) {
                recipeJson.tier = 1;
            }

            Item input = (Item)Registries.ITEM.getOrEmpty(new Identifier(recipeJson.input)).orElseThrow(() -> {
                return new JsonSyntaxException("No such item " + recipeJson.input);
            });
            Block block = (Block)Registries.BLOCK.getOrEmpty(new Identifier(recipeJson.block)).orElseThrow(() -> {
                return new JsonSyntaxException("No such block " + recipeJson.block);
            });
            Item output = (Item)Registries.ITEM.getOrEmpty(new Identifier(recipeJson.output)).orElseThrow(() -> {
                return new JsonSyntaxException("No such item " + recipeJson.output);
            });
            int count = recipeJson.count;
            int tier = recipeJson.tier;
            RitualRecipe.addRecipe(id, tier, input, block, output, count);
            return new RitualRecipe(id, tier, input, block, output, count);
        } else {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
    }

    public void write(PacketByteBuf buf, RitualRecipe recipe) {
        buf.writeInt(recipe.getTier());
        buf.writeItemStack(new ItemStack(recipe.getInput()));
        buf.writeItemStack(new ItemStack(recipe.getBlock()));
        buf.writeItemStack(recipe.getOutput());
        buf.writeInt(recipe.getCount());
    }

    public RitualRecipe read(Identifier id, PacketByteBuf buf) {
        int tier = buf.readInt();
        Item input = buf.readItemStack().getItem();
        Block block = Block.getBlockFromItem(buf.readItemStack().getItem());
        Item output = buf.readItemStack().getItem();
        int count = buf.readInt();
        return new RitualRecipe(id, tier, input, block, output, count);
    }
}
