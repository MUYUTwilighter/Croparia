package cool.muyucloud.croparia.compat.rei;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.recipe.DisplayableRecipe;
import cool.muyucloud.croparia.compat.rei.category.InfusorRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.category.RitualRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.category.RitualStructureDisplayCategory;
import cool.muyucloud.croparia.compat.rei.category.SoakRecipeDisplayCategory;
import cool.muyucloud.croparia.compat.rei.display.SimpleCategory;
import cool.muyucloud.croparia.compat.rei.display.SimpleDisplay;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ReiCommon implements REICommonPlugin {
    private static final Set<SimpleCategory<? extends DisplayableRecipe<?>>> SERIALIZERS = new HashSet<>();

    public static void forEach(Consumer<SimpleCategory<? extends DisplayableRecipe<?>>> consumer) {
        SERIALIZERS.forEach(consumer);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        CropariaIf.LOGGER.info("Registering rei recipe fillers...");
        forEach(serializer -> registerDisplay(registry, serializer));
    }

    private static <R extends DisplayableRecipe<?>> void registerDisplay(ServerDisplayRegistry registry, SimpleCategory<R> serializer) {
        registry.beginRecipeFiller(serializer.getRecipeClass())
            .filterType(serializer.getRecipeType())
            .fill(holder -> new SimpleDisplay<>(holder, serializer));
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        SERIALIZERS.add(InfusorRecipeDisplayCategory.INSTANCE);
        SERIALIZERS.add(RitualRecipeDisplayCategory.INSTANCE);
        SERIALIZERS.add(RitualStructureDisplayCategory.INSTANCE);
        SERIALIZERS.add(SoakRecipeDisplayCategory.INSTANCE);
        CropariaIf.LOGGER.info("Registering rei recipe display serializers...");
        forEach(serializer -> registry.register(serializer.getId(), serializer.getSerializer()));
    }
}
