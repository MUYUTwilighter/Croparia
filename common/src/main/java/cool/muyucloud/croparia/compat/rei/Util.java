package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.api.recipe.entry.BlockInput;
import cool.muyucloud.croparia.api.recipe.entry.BlockOutput;
import cool.muyucloud.croparia.api.recipe.entry.ItemInput;
import cool.muyucloud.croparia.api.recipe.entry.ItemOutput;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
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
        return toIngredient(input, stack -> {
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    public static EntryIngredient toIngredient(BlockInput input, int count) {
        if (input.getTag().isPresent()) {
            return EntryIngredients.ofTag(
                BasicDisplay.registryAccess(), input.getTag().get(), holder -> EntryStacks.of(holder.value(), count)
            );
        } else {
            return EntryIngredient.of(input.getDisplayStacks().stream().map(
                stack -> EntryStack.of(VanillaEntryTypes.ITEM, stack.copyWithCount(count))
            ).toList());
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static EntryIngredient toIngredient(BlockInput input, Consumer<EntryStack<ItemStack>> processor) {
        if (input.getTag().isPresent()) {
            return EntryIngredients.ofTag(BasicDisplay.registryAccess(), input.getTag().get(), holder -> {
                EntryStack<ItemStack> stack = EntryStacks.of(holder.value());
                processor.accept(stack);
                return stack;
            });
        } else {
            return EntryIngredient.of(input.getDisplayStacks().stream().map(stack -> {
                EntryStack<ItemStack> entry = EntryStack.of(VanillaEntryTypes.ITEM, stack);
                processor.accept(entry);
                return entry;
            }).toList());
        }
    }

    public static EntryIngredient toIngredient(BlockInput input) {
        return toIngredient(input, stack -> {});
    }

    public static EntryIngredient toIngredient(BlockOutput output) {
        return toIngredient(output, stack -> {
        });
    }

    public static EntryIngredient toIngredient(BlockOutput output, Consumer<EntryStack<ItemStack>> processor) {
        EntryStack<ItemStack> stack = EntryStack.of(VanillaEntryTypes.ITEM, output.getDisplayStack());
        processor.accept(stack);
        return EntryIngredient.of(stack);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static EntryIngredient toIngredient(ItemInput input, Consumer<EntryStack<ItemStack>> processor) {
        if (input.getTag().isPresent()) {
            return EntryIngredients.ofTag(BasicDisplay.registryAccess(), input.getTag().get(), holder -> {
                EntryStack<ItemStack> stack = EntryStacks.of(holder.value(), (int) input.getAmount());
                processor.accept(stack);
                return stack;
            });
        } else {
            return EntryIngredient.of(input.getDisplayStacks().stream().map(stack -> {
                EntryStack<ItemStack> entry = EntryStack.of(VanillaEntryTypes.ITEM, stack);
                processor.accept(entry);
                return entry;
            }).toList());
        }
    }

    @SuppressWarnings("unused")
    public static EntryIngredient toIngredient(ItemInput input) {
        return toIngredient(input, stack -> {
        });
    }

    public static EntryIngredient toIngredient(ItemOutput output, Consumer<EntryStack<ItemStack>> processor) {
        EntryStack<ItemStack> stack = toStack(output);
        processor.accept(stack);
        return EntryIngredient.of(stack);
    }

    public static EntryIngredient toIngredient(ItemOutput output) {
        return toIngredient(output, stack -> {
        });
    }

    public static EntryStack<ItemStack> toStack(ItemOutput output) {
        return EntryStack.of(VanillaEntryTypes.ITEM, output.getDisplayStack());
    }
}
