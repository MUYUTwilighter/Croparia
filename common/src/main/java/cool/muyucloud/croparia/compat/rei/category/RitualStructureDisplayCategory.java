package cool.muyucloud.croparia.compat.rei.category;

import com.google.common.collect.ImmutableMap;
import cool.muyucloud.croparia.api.core.recipe.RitualStructure;
import cool.muyucloud.croparia.api.recipe.TypedSerializer;
import cool.muyucloud.croparia.api.recipe.entry.BlockInput;
import cool.muyucloud.croparia.compat.rei.Util;
import cool.muyucloud.croparia.compat.rei.display.SimpleCategory;
import cool.muyucloud.croparia.compat.rei.display.SimpleDisplay;
import cool.muyucloud.croparia.compat.rei.widget.Item2DWidget;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.Constants;
import cool.muyucloud.croparia.util.text.Texts;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
public class RitualStructureDisplayCategory extends SimpleCategory<RitualStructure> {
    private static final ItemStack INPUT = CropariaItems.PLACEHOLDER.get().getDefaultInstance();

    static {
        INPUT.set(DataComponents.CUSTOM_NAME, Texts.translatable("tooltip.croparia.input"));
    }

    public static final SimpleCategory<RitualStructure> INSTANCE = new RitualStructureDisplayCategory(
        RitualStructure.class, RitualStructure.TYPED_SERIALIZER
    );
    public static final int SLOT_SIZE = 18;
    public static final int LABEL_MARGIN = 6;
    public static final int FRAME_PADDING = 9;
    public static final int BUTTON_SIZE = 10;

    public RitualStructureDisplayCategory(Class<RitualStructure> recipeClass, TypedSerializer<RitualStructure> recipeType) {
        super(recipeClass, recipeType);
    }

    @Override
    public Component getTitle() {
        return Constants.RITUAL_STRUCTURE_TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CropariaItems.RITUAL_STAND.get().getDefaultInstance());
    }

    @Override
    public List<Widget> setupDisplay(SimpleDisplay<RitualStructure> display, Rectangle bounds) {
        RitualStructure recipe = display.getRecipe();
        AtomicInteger y = new AtomicInteger();
        Vec3i slotSize = display.getRecipe().size();
        Rectangle layerBound = new Rectangle(
            bounds.x + FRAME_PADDING, bounds.y + FRAME_PADDING, bounds.width - 2 * FRAME_PADDING, bounds.height - 2 * FRAME_PADDING - SLOT_SIZE
        );
        Widget background = Widgets.createSlotBase(
            new Rectangle(layerBound.x - 1, layerBound.y - 1, layerBound.width + 2, layerBound.height + 2)
        );
        Widget lower = Widgets.createButton(
            new Rectangle(
                bounds.x + FRAME_PADDING,
                bounds.y + bounds.height - FRAME_PADDING - (SLOT_SIZE + BUTTON_SIZE) / 2,
                BUTTON_SIZE, BUTTON_SIZE
            ),
            Texts.literal("<")
        ).onClick(button -> {
            if (y.get() > 0) y.getAndDecrement();
        }).tooltipLine(Constants.RITUAL_STRUCTURE_LOWER);
        Widget upper = Widgets.createButton(
            new Rectangle(bounds.x + bounds.width - FRAME_PADDING - BUTTON_SIZE,
                bounds.y + bounds.height - FRAME_PADDING - (SLOT_SIZE + BUTTON_SIZE) / 2,
                BUTTON_SIZE, BUTTON_SIZE),
            Texts.literal(">")
        ).onClick(button -> {
            if (y.get() < display.getRecipe().size().getY() - 1) y.getAndIncrement();
        }).tooltipLine(Constants.RITUAL_STRUCTURE_UPPER);
        Widget label = Widgets.createDrawableWidget(
            (graphics, mouseX, mouseY, delta) -> Widgets.createLabel(
                new Point(bounds.x + bounds.width / 2,
                    bounds.y + bounds.height - FRAME_PADDING - SLOT_SIZE + LABEL_MARGIN),
                Texts.translatable("gui.croparia.ritual_structure.label", y.get() + 1)
            ).render(graphics, mouseX, mouseY, delta)
        );
        ArrayList<Item2DWidget> layers = new ArrayList<>(slotSize.getY());
        for (int i = 0; i < slotSize.getY(); i++) {
            int finalI = i;
            layers.add(Item2DWidget.create().items((posX, posZ) -> {
                char c = recipe.getPattern().get(posX, finalI, posZ);
                if (c == '.') {
                    return Collections.singleton(EntryStacks.of(BlockInput.STACK_AIR));
                } else if (c == '$') {
                    return Collections.singleton(EntryStacks.of(INPUT));
                } else if (c == '*') {
                    return display.getInput("*");
                } else if (c == ' ') {
                    return Collections.singleton(EntryStacks.of(BlockInput.STACK_ANY));
                } else {
                    return Util.toIngredient(recipe.getKeys().get(c));
                }
            }).cols(slotSize.getX()).rows(slotSize.getZ()));
        }
        Widget layer = Widgets.overflowed(
            new Rectangle(
                bounds.x + FRAME_PADDING, bounds.y + FRAME_PADDING, bounds.width - 2 * FRAME_PADDING,
                bounds.height - 2 * FRAME_PADDING - SLOT_SIZE
            ),
            Widgets.delegateWithBounds(() -> layers.get(y.get()))
        );
        return List.of(background, lower, upper, label, layer);
    }

    @Override
    public int getDisplayHeight() {
        return super.getDisplayHeight() * 2 + FRAME_PADDING * 2;
    }

    @Override
    public Map<String, Supplier<EntryIngredient>> inputEntries(RecipeHolder<RitualStructure> holder) {
        RitualStructure recipe = holder.value();
        Map<String, Supplier<EntryIngredient>> map = new HashMap<>();
        for (Map.Entry<Character, BlockInput> entry : recipe.getKeys().entrySet()) {
            char c = entry.getKey();
            map.put(String.valueOf(c), () -> Util.toIngredient(entry.getValue(), recipe.getPattern().count(c)));
        }
        ResourceLocation id = holder.id().location();
        map.put("*", () -> Util.toIngredient(BuiltInRegistries.ITEM.getValue(id)));
        return ImmutableMap.copyOf(map);
    }

    @Override
    public Map<String, Supplier<EntryIngredient>> outputEntries(RecipeHolder<RitualStructure> holder) {
        return Map.of("*", () -> Util.toIngredient(BuiltInRegistries.ITEM.getValue(holder.id().location())));
    }

    @Override
    public EntryIngredient[] stations() {
        return new EntryIngredient[0];
    }
}
