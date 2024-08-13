//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.defacto34.croparia.core.recipes.rituals.FirstRitual;
import com.defacto34.croparia.core.recipes.rituals.RitualUtils;
import net.minecraft.block.Block;
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

public class RitualRecipe implements Recipe<SingleStackRecipeInput> {
    public static List<RitualRecipe> recipes = new ArrayList();
    private final int tier;
    private final Item input;
    private final Block block;
    private final Item output;
    private final int count;

    public RitualRecipe(int tier, Item input, Block block, Item output, int count) {
        this.input = input;
        this.block = block;
        this.output = output;
        this.tier = tier;
        this.count = count;
    }

    public Item getInput() {
        return this.input;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getCount() {
        return this.count;
    }

    public int getTier() {
        return this.tier;
    }

    public static void addRecipe(int tier, Item input, Block block, Item output, int count) {
        recipes.add(new RitualRecipe(tier, input, block, output, count));
    }

    public static void craft(ItemStack input, World world, BlockPos pos) {
        boolean ret = false;
        int index = 0;

        while (!ret && index < recipes.size()) {
            if (recipes.get(index).input == input.getItem()) {
                if (recipes.get(index).tier == 1) {
                    if (FirstRitual.checkRitual(recipes.get(index).block.getDefaultState(), world, pos, false)) {
                        ret = true;
                    }
                } else {
                    throw new IllegalStateException("Unexpected value: " + recipes.get(index).tier);
                }
            }
            if (!ret) {
                ++index;
            }
        }
        if (ret) {
            RitualUtils.endFirst(recipes.get(index).getOutput().copy(), input, pos, world);
        }
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        return input.getSize() < 1 ? false : this.input.equals(input.getStackInSlot(0).getItem());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.getResult(lookup).copy();
    }

    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return new ItemStack(this.output, this.count);
    }

    public ItemStack getOutput() {
        return new ItemStack(this.output, this.count);
    }

    public RecipeType<?> getType() {
        return RitualRecipe.Type.INSTANCE;
    }

    public RecipeSerializer<?> getSerializer() {
        return RitualRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<RitualRecipe> {
        public static final Type INSTANCE = new Type();
    }
}
