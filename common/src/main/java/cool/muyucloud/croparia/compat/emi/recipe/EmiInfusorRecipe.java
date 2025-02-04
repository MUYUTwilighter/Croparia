package cool.muyucloud.croparia.compat.emi.recipe;

//import cool.muyucloud.croparia.recipe.InfusorRecipe;
//import cool.muyucloud.croparia.registry.CropariaItems;
//import cool.muyucloud.croparia.util.Constants;
//import dev.emi.emi.api.recipe.EmiRecipe;
//import dev.emi.emi.api.recipe.EmiRecipeCategory;
//import dev.emi.emi.api.render.EmiTexture;
//import dev.emi.emi.api.stack.EmiIngredient;
//import dev.emi.emi.api.stack.EmiStack;
//import dev.emi.emi.api.widget.SlotWidget;
//import dev.emi.emi.api.widget.WidgetHolder;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class EmiInfusorRecipe implements EmiRecipe {
//    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(ResourceLocation.tryParse("croparia:infusor"), EmiIngredient.of(Ingredient.of(CropariaItems.INFUSOR.get())));
//    public static final EmiStack WORKSTATION = EmiStack.of(CropariaItems.INFUSOR.get());
//    public static final EmiIngredient INFUSOR = EmiIngredient.of(Ingredient.of(CropariaItems.INFUSOR.get()));
//
//    private final ResourceLocation id;
//    private final EmiIngredient element;
//    private final EmiIngredient ingredient;
//    private final EmiIngredient result;
//    private final List<EmiIngredient> inputs;
//    private final List<EmiStack> outputs;
//
//    public EmiInfusorRecipe(RecipeHolder<InfusorRecipe> holder) {
//        InfusorRecipe recipe = holder.value();
//        this.id = holder.id();
//        this.element = EmiIngredient.of(Ingredient.of(recipe.getPotion()));
//        this.ingredient = EmiIngredient.of(Ingredient.of(recipe.getIngredient().availableStacks().stream()));
//        this.result = EmiIngredient.of(Ingredient.of(recipe.getResult()));
//        this.inputs = List.of(element, ingredient);
//        this.outputs = result.getEmiStacks();
//    }
//
//    @Override
//    public EmiRecipeCategory getCategory() {
//        return CATEGORY;
//    }
//
//    @Override
//    public @Nullable ResourceLocation getId() {
//        return id;
//    }
//
//    @Override
//    public List<EmiIngredient> getInputs() {
//        return inputs;
//    }
//
//    @Override
//    public List<EmiStack> getOutputs() {
//        return outputs;
//    }
//
//    @Override
//    public int getDisplayWidth() {
//        return 134;
//    }
//
//    @Override
//    public int getDisplayHeight() {
//        return 60;
//    }
//
//    @Override
//    public void addWidgets(WidgetHolder widgets) {
//        int centerX = getDisplayWidth() / 2;
//        int centerY = getDisplayHeight() / 2;
//        widgets.add(new SlotWidget(INFUSOR, centerX - 9, centerY + 8).drawBack(false));
//        widgets.add(new SlotWidget(this.ingredient, centerX - 9, centerY - 24).appendTooltip(Constants.ITEM_DROP_TOOLTIP).drawBack(false));
//        widgets.add(new SlotWidget(this.element, centerX - 41, centerY + 8).appendTooltip(Constants.ELEM_INFUSE_TOOLTIP).drawBack(false));
//        widgets.add(new SlotWidget(this.result, centerX + 35, centerY + 8).recipeContext(this).drawBack(false));
//        widgets.addTexture(Constants.ITEM_DROP, centerX - 8, centerY - 8, 16, 16, 0, 0, 16, 16, 16, 16);
//        widgets.addTexture(Constants.ELEM_INFUSE, centerX - 24, centerY + 8, 16, 16, 0, 0, 16, 16, 16, 16);
//        widgets.addTexture(EmiTexture.EMPTY_ARROW, centerX + 8, centerY + 8);
//    }
//}

@SuppressWarnings("unused")
public class EmiInfusorRecipe {}