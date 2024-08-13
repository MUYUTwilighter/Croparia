//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.defacto34.croparia.core.util.ElementsEnum;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class InfusorRecipeSerializer implements RecipeSerializer<InfusorRecipe> {
    public static final MapCodec<InfusorRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            Identifier.CODEC.fieldOf("input").forGetter((recipe) -> Registries.ITEM.getId(recipe.getInput())),
            Codec.STRING.fieldOf("element").forGetter((recipe) -> recipe.getElement().name()),
            Identifier.CODEC.fieldOf("output").forGetter((recipe) -> Registries.ITEM.getId(recipe.getOutput().getItem())),
            Codec.INT.fieldOf("count").forGetter(InfusorRecipe::getCount)
        ).apply(instance, (input, element, output, count) ->
            new InfusorRecipe(Registries.ITEM.get(input), ElementsEnum.valueOf(element), Registries.ITEM.get(output), count)));
    public static final PacketCodec<RegistryByteBuf, InfusorRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        InfusorRecipeSerializer::write, InfusorRecipeSerializer::read);
    public static final InfusorRecipeSerializer INSTANCE = new InfusorRecipeSerializer();
    public static final Identifier ID = Identifier.of("croparia:infusor_recipe");

    public static void write(RegistryByteBuf buf, InfusorRecipe recipe) {
        ItemStack.PACKET_CODEC.encode(buf, recipe.input().getDefaultStack());
        buf.writeEnumConstant(recipe.getElement());
        ItemStack.PACKET_CODEC.encode(buf, recipe.output().getDefaultStack());
        buf.writeInt(recipe.getCount());
    }

    public static InfusorRecipe read(RegistryByteBuf buf) {
        Item input = ItemStack.PACKET_CODEC.decode(buf).getItem();
        ElementsEnum element = buf.readEnumConstant(ElementsEnum.class);
        Item output = ItemStack.PACKET_CODEC.decode(buf).getItem();
        int count = buf.readInt();
        return new InfusorRecipe(input, element, output, count);
    }

    @Override
    public MapCodec<InfusorRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, InfusorRecipe> packetCodec() {
        return PACKET_CODEC;
    }
}
