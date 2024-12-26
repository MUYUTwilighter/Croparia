package cool.muyucloud.recipe;

import cool.muyucloud.container.InfusorContainer;
import cool.muyucloud.data.ElementsEnum;
import cool.muyucloud.registry.RecipeSerializers;
import cool.muyucloud.registry.RecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfusorRecipe implements Recipe<InfusorContainer> {
    private ResourceLocation id;
    private ElementsEnum element;
    private ItemStack ingredient;
    private ItemStack result;

    public int getMaxUses() {
        return Math.min(
            this.result.getMaxStackSize() / this.result.getCount(),
            this.ingredient.getMaxStackSize() / this.ingredient.getCount()
        );
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public ElementsEnum getElement() {
        return element;
    }

    public void setElement(ElementsEnum element) {
        this.element = element;
    }

    public ItemStack getIngredient() {
        return ingredient;
    }

    public void setIngredient(ItemStack ingredient) {
        this.ingredient = ingredient;
    }

    public boolean matches(InfusorContainer container) {
        ItemStack input = container.getItem(0);
        return ItemStack.isSameItemSameTags(input, ingredient)
            && input.getCount() >= ingredient.getCount()
            && container.getElement() == element;
    }

    public @NotNull ItemStack assemble(@NotNull InfusorContainer container) {
        if (matches(container)) {
            ItemStack input = container.getItem(0);
            input.shrink(ingredient.getCount());
            return getResult();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public @NotNull ItemStack assembleAll(@NotNull InfusorContainer container) {
        if (matches(container)) {
            ItemStack input = container.getItem(0);
            ItemStack result = getResult();
            int uses = Math.min(input.getCount() / ingredient.getCount(), this.getMaxUses());
            input.shrink(uses * ingredient.getCount());
            result.setCount(uses * result.getCount());
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean matches(@NotNull InfusorContainer container, @Nullable Level level) {
        return matches(container);
    }

    @Override
    public @NotNull ItemStack assemble(InfusorContainer container, RegistryAccess registryAccess) {
        return assemble(container);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i >= 1 && j >= 2 || i >= 2 && j >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(@Nullable RegistryAccess registryAccess) {
        return result.copy();
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.INFUSOR.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypes.INFUSOR;
    }
}
