package cool.muyucloud.croparia.compat.emi.recipe;

//import cool.muyucloud.croparia.emi.widget.Button;
//import cool.muyucloud.croparia.emi.widget.DynamicSlot;
//import cool.muyucloud.croparia.recipe.RitualStructure;
//import cool.muyucloud.croparia.registry.CropariaItems;
//import cool.muyucloud.croparia.util.Constants;
//import cool.muyucloud.croparia.util.predicate.BlockStatePredicate;
//import dev.emi.emi.api.recipe.EmiRecipe;
//import dev.emi.emi.api.recipe.EmiRecipeCategory;
//import dev.emi.emi.api.stack.EmiIngredient;
//import dev.emi.emi.api.stack.EmiStack;
//import dev.emi.emi.api.widget.TextWidget;
//import dev.emi.emi.api.widget.WidgetHolder;
//import net.minecraft.core.Vec3i;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeHolder;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class EmiRitualStructure implements EmiRecipe {
//    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(ResourceLocation.tryParse("croparia:ritual_structure"), EmiIngredient.of(Ingredient.of(CropariaItems.RITUAL_STAND.get())));
//    public static final EmiIngredient AIR = EmiStack.of(BlockStatePredicate.STACK_AIR);
//    public static final EmiIngredient ANY = EmiStack.of(BlockStatePredicate.STACK_ANY);
//    public static final EmiIngredient UNKNOWN = EmiStack.of(BlockStatePredicate.STACK_UNKNOWN);
//    public static final EmiIngredient INPUT;
//
//    static {
//        ItemStack inputStack = CropariaItems.PLACEHOLDER.get().getDefaultInstance();
//        inputStack.set(DataComponents.CUSTOM_NAME, Texts.translatable("tooltip.croparia.input"));
//        INPUT = EmiStack.of(inputStack);
//    }
//
//    private static final int BUTTON_SIZE = 12;
//    private static final int SLOT_SIZE = 18;
//
//    private final RitualStructure recipe;
//    private final EmiIngredient[][][] structure;
//    private final EmiIngredient ritual;
//    private final ResourceLocation id;
//    private final List<EmiIngredient> inputs;
//    private final List<EmiStack> outputs;
//    private final Vec3i size;
//    private final Vec3i displaySize;
//    private Vec3i cursor = new Vec3i(0, 0, 0);
//    private Vec3i oldCursor = new Vec3i(0, 0, 0);
//
//    public EmiRitualStructure(RecipeHolder<RitualStructure> holder) {
//        RitualStructure recipe = holder.value();
//        this.recipe = recipe;
//        this.structure = new EmiIngredient[recipe.maxY()][recipe.maxZ()][recipe.maxX()];
//        this.ritual = EmiStack.of(BuiltInRegistries.ITEM.get(holder.id()));
//        this.id = holder.id();
//        this.inputs = recipe.getPredicates().stream().map(predicate -> EmiIngredient.of(predicate.availableBlockItems().stream().map(EmiStack::of).toList())).toList();
//        this.outputs = ritual.getEmiStacks();
//        this.extractStructure(recipe);
//        this.size = new Vec3i(recipe.maxX(), recipe.maxY(), recipe.maxZ());
//        this.displaySize = new Vec3i(Math.min(size.getX(), 6), 1, Math.min(size.getZ(), 6));
//    }
//
//    private void extractStructure(RitualStructure structure) {
//        for (int x = 0; x < structure.maxX(); x++) {
//            for (int y = 0; y < structure.maxY(); y++) {
//                for (int z = 0; z < structure.maxZ(); z++) {
//                    char key = structure.getChar(x, y, z);
//                    if (key == '$') {
//                        this.structure[y][z][x] = INPUT;
//                    } else if (key == '*') {
//                        this.structure[y][z][x] = ritual;
//                    } else if (key == ' ') {
//                        this.structure[y][z][x] = ANY;
//                    } else if (key == '.') {
//                        this.structure[y][z][x] = AIR;
//                    } else {
//                        int finalY = y;
//                        int finalZ = z;
//                        int finalX = x;
//                        structure.getPredicate(key).ifPresentOrElse(predicate -> this.structure[finalY][finalZ][finalX] = EmiIngredient.of(predicate.availableBlockItems().stream().map(EmiStack::of).toList()), () -> this.structure[finalY][finalZ][finalX] = UNKNOWN);
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public EmiRecipeCategory getCategory() {
//        return CATEGORY;
//    }
//
//    @Override
//    public @Nullable ResourceLocation getId() {
//        return this.id;
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
//        return (this.displaySize.getX() + 2) * SLOT_SIZE;
//    }
//
//    @Override
//    public int getDisplayHeight() {
//        return (this.displaySize.getZ() + 3) * 18;
//    }
//
//    public void left() {
//        if (cursor.getX() > 0) {
//            cursor = cursor.offset(-1, 0, 0);
//        }
//    }
//
//    public void right() {
//        if (cursor.getX() < size.getX() - displaySize.getX()) {
//            cursor = cursor.offset(1, 0, 0);
//        }
//    }
//
//    public void forward() {
//        if (cursor.getZ() > 0) {
//            cursor = cursor.offset(0, 0, -1);
//        }
//    }
//
//    public void backward() {
//        if (cursor.getZ() < size.getZ() - displaySize.getZ()) {
//            cursor = cursor.offset(0, 0, 1);
//        }
//    }
//
//    public void down() {
//        if (cursor.getY() > 0) {
//            cursor = cursor.offset(0, -1, 0);
//        }
//    }
//
//    public void up() {
//        if (cursor.getY() < structure.length - 1) {
//            cursor = cursor.offset(0, 1, 0);
//        }
//    }
//
//    public boolean queryCursor() {
//        if (cursor.equals(oldCursor)) {
//            return false;
//        } else {
//            oldCursor = new Vec3i(cursor.getX(), cursor.getY(), cursor.getZ());
//            return true;
//        }
//    }
//
//    @Override
//    public void addWidgets(WidgetHolder widgets) {
//        DynamicSlot[][] slots = new DynamicSlot[displaySize.getX()][displaySize.getZ()];
//        for (int x = 0; x < displaySize.getX(); x++) {
//            for (int z = 0; z < displaySize.getZ(); z++) {
//                slots[x][z] = widgets.add(new DynamicSlot(SLOT_SIZE + x * SLOT_SIZE, SLOT_SIZE + z * SLOT_SIZE));
//                slots[x][z].setStack(structure[cursor.getY()][cursor.getZ() + z][cursor.getX() + x]);
//            }
//        }
//        widgets.addDrawable(SLOT_SIZE, SLOT_SIZE,
//            this.getDisplayWidth() - SLOT_SIZE * 2, this.getDisplayHeight() - SLOT_SIZE * 3,
//            (graphics, mouseX, mouseY, delta) -> {
//                if (!this.queryCursor()) {
//                    return;
//                }
//                for (int z = cursor.getZ(), displayZ = 0; z < Math.min(structure[cursor.getY()].length, cursor.getZ() + displaySize.getZ()); z++, displayZ++) {
//                    for (int x = cursor.getX(), displayX = 0; x < Math.min(structure[cursor.getY()][z].length, cursor.getX() + displaySize.getX()); x++, displayX++) {
//                        slots[displayX][displayZ].setStack(structure[cursor.getY()][z][x]);
//                        int finalDisplayX = displayX;
//                        int finalDisplayZ = displayZ;
//                        recipe.getPredicate(x, cursor.getY(), z).ifPresent(predicate -> slots[finalDisplayX][finalDisplayZ].setTooltips(predicate.tooltip()));
//                    }
//                }
//            }
//        );
//        widgets.add(new Button(
//            (SLOT_SIZE - BUTTON_SIZE) / 2, displaySize.getZ() * SLOT_SIZE / 2 + SLOT_SIZE - BUTTON_SIZE / 2,
//            BUTTON_SIZE, BUTTON_SIZE, Constants.LEFT_DARK, Constants.LEFT_WHITE,
//            () -> true, (mouseX, mouseY, button) -> this.left()
//        ));
//        widgets.add(new Button(
//            this.getDisplayWidth() - BUTTON_SIZE / 2 - SLOT_SIZE / 2,
//            displaySize.getZ() * SLOT_SIZE / 2 + SLOT_SIZE - BUTTON_SIZE / 2,
//            BUTTON_SIZE, BUTTON_SIZE, Constants.RIGHT_DARK, Constants.RIGHT_WHITE,
//            () -> true, (mouseX, mouseY, button) -> this.right()
//        ));
//        widgets.add(new Button(
//            displaySize.getX() * SLOT_SIZE / 2 + SLOT_SIZE - BUTTON_SIZE / 2, (SLOT_SIZE - BUTTON_SIZE) / 2,
//            BUTTON_SIZE, BUTTON_SIZE, Constants.UP_DARK, Constants.UP_WHITE,
//            () -> true, (mouseX, mouseY, button) -> this.forward()
//        ));
//        widgets.add(new Button(
//            displaySize.getX() * SLOT_SIZE / 2 + SLOT_SIZE - BUTTON_SIZE / 2,
//            displaySize.getZ() * SLOT_SIZE + SLOT_SIZE + (SLOT_SIZE - BUTTON_SIZE) / 2,
//            BUTTON_SIZE, BUTTON_SIZE, Constants.DOWN_DARK, Constants.DOWN_WHITE,
//            () -> true, (mouseX, mouseY, button) -> this.backward()
//        ));
//        widgets.add(new Button(
//            SLOT_SIZE, this.getDisplayHeight() - BUTTON_SIZE - SLOT_SIZE / 2,
//            BUTTON_SIZE, BUTTON_SIZE, Constants.LEFT_DARK, Constants.LEFT_WHITE,
//            () -> true, (mouseX, mouseY, button) -> this.down()
//        ));
//        widgets.add(new Button(
//            this.getDisplayWidth() - SLOT_SIZE - BUTTON_SIZE,
//            this.getDisplayHeight() - BUTTON_SIZE - SLOT_SIZE / 2,
//            BUTTON_SIZE, BUTTON_SIZE, Constants.RIGHT_DARK, Constants.RIGHT_WHITE,
//            () -> true, (mouseX, mouseY, button) -> this.up()
//        ));
//        int labelWidth = this.getDisplayWidth() - (SLOT_SIZE + BUTTON_SIZE) * 2;
//        widgets.addDrawable(
//            SLOT_SIZE + BUTTON_SIZE, this.getDisplayHeight() - SLOT_SIZE,
//            labelWidth, SLOT_SIZE,
//            (graphics, mouseX, mouseY, delta) -> new TextWidget(
//                Texts.translatable("gui.croparia.ritual_structure.label", cursor.getY() + 1).getVisualOrderText(),
//                labelWidth / 2 - SLOT_SIZE, 0, 0xFF3F3F3F, false
//            ).render(graphics, mouseX, mouseY, delta)
//        );
//    }
//}

@SuppressWarnings("unused")
public class EmiRitualStructure {
}