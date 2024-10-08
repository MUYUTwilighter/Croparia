//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.core.block.Infusor;
import com.defacto34.croparia.core.util.ElementsEnum;
import com.defacto34.croparia.init.BlockInit;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfusorRecipe implements Recipe<SimpleInventory> {
    public static List<InfusorRecipe> recipes = new ArrayList();
    private final Item input;
    private final ElementsEnum element;
    private final Item output;
    private final int count;
    private final Identifier id;

    public InfusorRecipe(Identifier id, Item input, ElementsEnum element, Item output, int count) {
        this.input = input;
        this.element = element;
        this.output = output;
        this.count = count;
        this.id = id;
    }

    public Item getInput() {
        return this.input;
    }

    public ElementsEnum getElement() {
        return this.element;
    }

    public int getCount() {
        return this.count;
    }

    public static void addRecipe(String id, Item input, ElementsEnum type, Item output, int count) {
        recipes.add(new InfusorRecipe(new Identifier(Croparia.MOD_ID + id), input, type, output, count));
    }

    public static void addRecipe(Identifier id, Item input, ElementsEnum type, Item output, int count) {
        recipes.add(new InfusorRecipe(id, input, type, output, count));
    }

    public static void craft(ItemStack input, World world, BlockPos pos) {
        boolean ret = false;
        int index = 0;

        while(!ret && index < recipes.size()) {
            if (recipes.get(index).input == input.getItem() && world.getBlockState(pos).get(Infusor.TYPE) == ((InfusorRecipe)recipes.get(index)).element) {
                ret = true;
            } else {
                ++index;
            }
        }

        if (ret) {
            world.spawnEntity(new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, ((InfusorRecipe)recipes.get(index)).getOutput().copy()));
            world.setBlockState(pos, BlockInit.INFUSOR.getDefaultState().with(Infusor.TYPE, ElementsEnum.EMPTY));
            input.decrement(1);
        }

    }

    public boolean matches(SimpleInventory inventory, World world) {
        return inventory.size() < 1 ? false : this.input.equals(inventory.getStack(1).getItem());
    }

    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return new ItemStack(this.output, this.count);
    }

    public ItemStack getOutput() {
        return new ItemStack(this.output, this.count);
    }

    public boolean fits(int width, int height) {
        return true;
    }

    public Identifier getId() {
        return this.id;
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

        private Type() {
        }
    }
}
