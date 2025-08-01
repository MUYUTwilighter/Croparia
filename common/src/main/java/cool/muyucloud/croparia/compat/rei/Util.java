package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.api.recipe.entry.BlockInput;
import cool.muyucloud.croparia.api.recipe.entry.ItemInput;
import cool.muyucloud.croparia.api.recipe.entry.ItemOutput;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class Util {
    public static EntryIngredient toIngredient(Item input, Consumer<EntryStack<ItemStack>> processor) {
        EntryStack<ItemStack> stack = EntryStack.of(VanillaEntryTypes.ITEM, input.getDefaultInstance());
        processor.accept(stack);
        return EntryIngredient.of(stack);
    }

    public static EntryIngredient toIngredient(Item input) {
        return toIngredient(input, stack -> {});
    }

    public static EntryIngredient toIngredient(BlockInput input, int count) {
        return EntryIngredient.of(input.getDisplayStacks().stream().map(
            stack -> EntryStack.of(VanillaEntryTypes.ITEM, stack.copyWithCount(count))
        ).toList());
    }

    public static EntryIngredient toIngredient(BlockInput input, Consumer<EntryStack<ItemStack>> processor) {
        return EntryIngredient.of(input.getDisplayStacks().stream().map(stack -> {
            EntryStack<ItemStack> entry = EntryStack.of(VanillaEntryTypes.ITEM, stack);
            processor.accept(entry);
            return entry;
        }).toList());
    }

    public static EntryIngredient toIngredient(BlockInput input) {
        return toIngredient(input, stack -> {});
    }

    public static EntryIngredient toIngredient(ItemInput input, Consumer<EntryStack<ItemStack>> processor) {
        return EntryIngredient.of(input.getDisplayStacks().stream().map(stack -> {
            EntryStack<ItemStack> entry = EntryStack.of(VanillaEntryTypes.ITEM, stack);
            processor.accept(entry);
            return entry;
        }).toList());
    }

    @SuppressWarnings("unused")
    public static EntryIngredient toIngredient(ItemInput input) {
        return toIngredient(input, stack -> {});
    }

    public static EntryIngredient toIngredient(ItemOutput output, Consumer<EntryStack<ItemStack>> processor) {
        EntryStack<ItemStack> stack = toStack(output);
        processor.accept(stack);
        return EntryIngredient.of(stack);
    }

    public static EntryIngredient toIngredient(ItemOutput output) {
        return toIngredient(output, stack -> {});
    }

    public static EntryStack<ItemStack> toStack(ItemOutput output) {
        return EntryStack.of(VanillaEntryTypes.ITEM, output.getDisplayStack());
    }
}
