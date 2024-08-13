//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.defacto34.croparia.core.block.Infusor;
import com.defacto34.croparia.core.util.ElementsEnum;
import com.defacto34.croparia.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public record InfusorRecipe(Item input, ElementsEnum element, Item output, int count) implements Recipe<SingleStackRecipeInput> {
    public static List<InfusorRecipe> recipes = new ArrayList();

    public Item getInput() {
        return this.input;
    }

    public ElementsEnum getElement() {
        return this.element;
    }

    public int getCount() {
        return this.count;
    }

    public static void addRecipe(Item input, ElementsEnum type, Item output, int count) {
        recipes.add(new InfusorRecipe(input, type, output, count));
    }

    public static void craft(ItemStack input, World world, BlockPos pos) {
        boolean ret = false;
        int index = 0;

        while (!ret && index < recipes.size()) {
            if (recipes.get(index).input == input.getItem() && world.getBlockState(pos).get(Infusor.TYPE) == recipes.get(index).element) {
                ret = true;
            } else {
                ++index;
            }
        }

        if (ret) {
            world.spawnEntity(new ItemEntity(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, ((InfusorRecipe) recipes.get(index)).getOutput().copy()));
            world.setBlockState(pos, (BlockState) BlockInit.INFUSOR.getDefaultState().with(Infusor.TYPE, ElementsEnum.EMPTY));
            input.decrement(1);
        }

    }

    public boolean matches(SingleStackRecipeInput input, World world) {
        return input.getSize() < 1 ? false : this.input.equals(input.getStackInSlot(0).getItem());
    }

    public ItemStack craft(SingleStackRecipeInput inventory, RegistryWrapper.WrapperLookup lookup) {
        return this.getResult(lookup).copy();
    }

    public ItemStack getResult(RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(this.output, this.count);
    }

    public ItemStack getOutput() {
        return new ItemStack(this.output, this.count);
    }

    public boolean fits(int width, int height) {
        return true;
    }

    public RecipeType<?> getType() {
        return InfusorRecipe.Type.INSTANCE;
    }

    public RecipeSerializer<?> getSerializer() {
        return InfusorRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<InfusorRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "infusor_recipe";
    }


}
