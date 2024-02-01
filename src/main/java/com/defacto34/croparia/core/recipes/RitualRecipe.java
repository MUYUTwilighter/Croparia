//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.core.recipes;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.core.recipes.rituals.FirstRitual;
import com.defacto34.croparia.core.recipes.rituals.RitualUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
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

public class RitualRecipe implements Recipe<SimpleInventory> {
    public static List<RitualRecipe> recipes = new ArrayList();
    private final int tier;
    private final Item input;
    private final Block block;
    private final Item output;
    private final int count;
    private final Identifier id;

    public RitualRecipe(Identifier id, int tier, Item input, Block block, Item output, int count) {
        this.input = input;
        this.block = block;
        this.output = output;
        this.id = id;
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

    public static void addRecipe(String id, int tier, Item input, Block block, Item output, int count) {
        recipes.add(new RitualRecipe(new Identifier(Croparia.MOD_ID + id), tier, input, block, output, count));
    }

    public static void addRecipe(Identifier id, int tier, Item input, Block block, Item output, int count) {
        recipes.add(new RitualRecipe(id, tier, input, block, output, count));
    }

    public static void craft(ItemStack input, World world, BlockPos pos) {
        boolean ret = false;
        int index = 0;

        while(!ret && index < recipes.size()) {
            if (((RitualRecipe)recipes.get(index)).input == input.getItem()) {
                switch (((RitualRecipe)recipes.get(index)).tier) {
                    case 1:
                        if (FirstRitual.checkRitual(((RitualRecipe)recipes.get(index)).block.getDefaultState(), world, pos, false)) {
                            ret = true;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + ((RitualRecipe)recipes.get(index)).tier);
                }
            }

            if (!ret) {
                ++index;
            }
        }

        if (ret) {
            RitualUtils.endFirst(((RitualRecipe)recipes.get(index)).getOutput().copy(), input, pos, world);
        }

    }

    public boolean matches(SimpleInventory inventory, World world) {
        return inventory.size() < 1 ? false : this.input.equals(inventory.getStack(1).getItem());
    }

    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    public boolean fits(int width, int height) {
        return true;
    }

    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return new ItemStack(this.output, this.count);
    }

    public ItemStack getOutput() {
        return new ItemStack(this.output, this.count);
    }

    public Identifier getId() {
        return this.id;
    }

    public RecipeType<?> getType() {
        return RitualRecipe.Type.INSTANCE;
    }

    public RecipeSerializer<?> getSerializer() {
        return RitualRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<RitualRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "ritual_recipe";

        private Type() {
        }
    }
}
