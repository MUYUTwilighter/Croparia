package cool.muyucloud.croparia.rei.display;

import cool.muyucloud.croparia.recipe.RitualStructure;
import cool.muyucloud.croparia.recipe.serializer.RitualStructureSerializer;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.rei.category.RitualStructureDisplayCategory;
import cool.muyucloud.croparia.util.predicate.BlockStatePredicate;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RitualStructureDisplay implements SimpleDisplay<RitualStructure> {
    public static final DisplaySerializer<RitualStructureDisplay> SERIALIZER = DisplayFactory.serializer(RitualStructureSerializer.CODEC.codec(), RitualStructureDisplay::new);

    private static final EntryStack<ItemStack> AIR = EntryStacks.of(BlockStatePredicate.STACK_AIR);
    private static final EntryStack<ItemStack> ANY = EntryStacks.of(BlockStatePredicate.STACK_ANY);
    private static final EntryStack<ItemStack> UNKNOWN = EntryStacks.of(BlockStatePredicate.STACK_UNKNOWN);
    private static final EntryStack<ItemStack> INPUT;

    static {
        ItemStack stack = CropariaItems.PLACEHOLDER.get().getDefaultInstance();
        stack.set(DataComponents.CUSTOM_NAME, Component.translatable("tooltip.croparia.input"));
        INPUT = EntryStacks.of(stack);
    }

    private final RitualStructure recipe;
    private final List<EntryIngredient> input;
    private final Collection<EntryStack<ItemStack>>[][][] structure;
    private final EntryStack<ItemStack> ritual;
    private final ResourceLocation id;
    private int lastRead = 0;

    public RitualStructureDisplay(RitualStructure structure, ResourceLocation id) {
        this.recipe = structure;
        this.structure = new Collection[structure.maxY()][structure.maxZ()][structure.maxX()];
        this.input = structure.getPredicates().stream().map(predicate -> EntryIngredients.of(VanillaEntryTypes.ITEM, predicate.availableBlockItems())).toList();
        this.id = id;
        this.ritual = EntryStacks.of(BuiltInRegistries.ITEM.getValue(id));
        this.extractStructure(structure);
    }

    public RitualStructure getRecipe() {
        return this.recipe;
    }

    @Override
    public ResourceLocation getRecipeId() {
        return this.id;
    }

    private void extractStructure(RitualStructure structure) {
        for (int x = 0; x < structure.maxX(); x++) {
            for (int y = 0; y < structure.maxY(); y++) {
                for (int z = 0; z < structure.maxZ(); z++) {
                    char key = structure.getChar(x, y, z);
                    if (key == '$') {
                        this.structure[y][z][x] = Collections.singleton(INPUT);
                    } else if (key == '*') {
                        this.structure[y][z][x] = Collections.singleton(ritual);
                    } else if (key == ' ') {
                        this.structure[y][z][x] = Collections.singleton(ANY);
                    } else if (key == '.') {
                        this.structure[y][z][x] = Collections.singleton(AIR);
                    } else {
                        int finalY = y;
                        int finalZ = z;
                        int finalX = x;
                        structure.getPredicate(key).ifPresentOrElse(predicate -> this.structure[finalY][finalZ][finalX] = predicate.availableBlockItems().stream().map(stack -> {
                            EntryStack<ItemStack> result = EntryStacks.of(stack);
                            result.tooltip(predicate.tooltip());
                            return result;
                        }).toList(), () -> this.structure[finalY][finalZ][finalX] = Collections.singleton(UNKNOWN));
                    }
                }
            }
        }
    }

    public int lastRead() {
        return this.lastRead;
    }

    public void lower() {
        if (this.lastRead > 0) {
            this.lastRead -= 1;
        }
    }

    public void upper() {
        this.lastRead++;
        if (this.lastRead >= this.structure.length) {
            this.lastRead = this.structure.length - 1;
        }
    }

    public Vec3i size() {
        if (this.structure.length == 0) {
            return new Vec3i(0, 0, 0);
        }
        if (this.structure[0].length == 0) {
            return new Vec3i(0, this.structure.length, 0);
        }
        if (this.structure[0][0].length == 0) {
            return new Vec3i(0, this.structure.length, this.structure[0].length);
        }
        return new Vec3i(this.structure[0][0].length, this.structure.length, this.structure[0].length);
    }

    public Collection<EntryStack<ItemStack>> get(int x, int y, int z) {
        if (y >= this.structure.length || y < 0) {
            return Collections.singleton(AIR);
        }
        if (z >= this.structure[y].length || z < 0) {
            return Collections.singleton(AIR);
        }
        if (x >= this.structure[y][z].length || x < 0) {
            return Collections.singleton(AIR);
        }
        return this.structure[y][z][x];
    }

    public Collection<EntryStack<ItemStack>> get(int x, int z) {
        return this.get(x, this.lastRead, z);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredient.of(ritual));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return RitualStructureDisplayCategory.ID;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(id);
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
