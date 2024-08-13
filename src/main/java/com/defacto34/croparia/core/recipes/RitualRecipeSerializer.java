//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class RitualRecipeSerializer implements RecipeSerializer<RitualRecipe> {
    public static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            Identifier.CODEC.fieldOf("input").forGetter((recipe) -> Registries.ITEM.getId(recipe.getInput())),
            Identifier.CODEC.fieldOf("block").forGetter((recipe) -> Registries.BLOCK.getId(recipe.getBlock())),
            Identifier.CODEC.fieldOf("output").forGetter((recipe) -> Registries.ITEM.getId(recipe.getOutput().getItem())),
            Codec.INT.fieldOf("count").forGetter(RitualRecipe::getCount),
            Codec.INT.fieldOf("tier").forGetter(RitualRecipe::getTier)
        ).apply(instance, (input, block, output, count, tier) ->
            new RitualRecipe(tier, Registries.ITEM.get(input), Registries.BLOCK.get(block), Registries.ITEM.get(output), count)));
    public static final PacketCodec<RegistryByteBuf, RitualRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        RitualRecipeSerializer::write, RitualRecipeSerializer::read);
    public static final RitualRecipeSerializer INSTANCE = new RitualRecipeSerializer();
    public static final Identifier ID = Identifier.of("croparia:ritual_recipe");

    public RitualRecipe read(JsonObject json) {
        RitualRecipeJsonFormat recipeJson = (new Gson()).fromJson(json, RitualRecipeJsonFormat.class);
        if (recipeJson.input != null && recipeJson.block != null && recipeJson.output != null) {
            if (recipeJson.count == 0) {
                recipeJson.count = 1;
            }

            if (recipeJson.tier == 0) {
                recipeJson.tier = 1;
            }

            Item input = Registries.ITEM.getOrEmpty(Identifier.of(recipeJson.input)).orElseThrow(() ->
                new JsonSyntaxException("No such item " + recipeJson.input));
            Block block = Registries.BLOCK.getOrEmpty(Identifier.of(recipeJson.block)).orElseThrow(() ->
                new JsonSyntaxException("No such block " + recipeJson.block));
            Item output = Registries.ITEM.getOrEmpty(Identifier.of(recipeJson.output)).orElseThrow(() ->
                new JsonSyntaxException("No such item " + recipeJson.output));
            int count = recipeJson.count;
            int tier = recipeJson.tier;
            RitualRecipe.addRecipe(tier, input, block, output, count);
            return new RitualRecipe(tier, input, block, output, count);
        } else {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
    }

    public static void write(RegistryByteBuf buf, RitualRecipe recipe) {
        buf.writeInt(recipe.getTier());
        ItemStack.PACKET_CODEC.encode(buf, recipe.getInput().getDefaultStack());
        ItemStack.PACKET_CODEC.encode(buf, new ItemStack(recipe.getBlock()));
        ItemStack.PACKET_CODEC.encode(buf, recipe.getOutput());
        buf.writeInt(recipe.getCount());
    }

    public static RitualRecipe read(RegistryByteBuf buf) {
        int tier = buf.readInt();
        Item input = ItemStack.PACKET_CODEC.decode(buf).getItem();
        Block block = Block.getBlockFromItem(ItemStack.PACKET_CODEC.decode(buf).getItem());
        Item output = ItemStack.PACKET_CODEC.decode(buf).getItem();
        int count = buf.readInt();
        return new RitualRecipe(tier, input, block, output, count);
    }

    @Override
    public MapCodec<RitualRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, RitualRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
