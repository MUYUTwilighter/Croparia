package cool.muyucloud.croparia.api.core.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.api.core.recipe.container.SoakContainer;
import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.api.recipe.TypedSerializer;
import cool.muyucloud.croparia.api.recipe.entry.BlockInput;
import cool.muyucloud.croparia.api.recipe.entry.BlockOutput;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SoakRecipe implements DisplayableRecipe<SoakContainer> {
    public static final TypedSerializer<SoakRecipe> TYPED_SERIALIZER = new TypedSerializer<>(
        RecordCodecBuilder.mapCodec(instance -> instance.group(
            Element.CODEC.fieldOf("element").forGetter(SoakRecipe::getElement),
            Codec.FLOAT.fieldOf("probability").forGetter(SoakRecipe::getProbability),
            BlockInput.CODEC.fieldOf("input").forGetter(SoakRecipe::getInput),
            BlockOutput.CODEC.fieldOf("output").forGetter(SoakRecipe::getOutput)).apply(instance, SoakRecipe::new)
        )
    );
    public static final LazySupplier<SlotDisplay> STATION = LazySupplier.of(() -> new SlotDisplay.ItemSlotDisplay(CropariaItems.ELEMENTAL_STONE.get()));

    private final Element element;
    private final float probability;
    private final BlockInput input;
    private final BlockOutput output;

    public SoakRecipe(Element element, float probability, BlockInput input, BlockOutput output) {
        this.element = element;
        this.probability = probability;
        this.input = input;
        this.output = output;
    }

    public Element getElement() {
        return element;
    }

    public float getProbability() {
        return probability;
    }

    public BlockInput getInput() {
        return input;
    }

    public BlockOutput getOutput() {
        return output;
    }

    @Override
    public boolean matches(SoakContainer input, Level level) {
        return this.getInput().matches(input.getState()) && this.getElement() == input.getElement() && input.getRandom() < this.getProbability();
    }

    @Override
    public TypedSerializer<SoakRecipe> getTypedSerializer() {
        return TYPED_SERIALIZER;
    }

    @Override
    @Deprecated
    public @NotNull ItemStack assemble(SoakContainer recipeInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull SlotDisplay result() {
        return this.getOutput();
    }

    @Override
    public @NotNull SlotDisplay craftingStation() {
        return STATION.get();
    }
}
