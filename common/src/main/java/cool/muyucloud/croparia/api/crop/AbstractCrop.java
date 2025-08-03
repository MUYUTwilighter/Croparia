package cool.muyucloud.croparia.api.crop;

import com.google.common.collect.ImmutableSet;
import cool.muyucloud.croparia.api.crop.util.Material;
import cool.muyucloud.croparia.api.generator.util.DgElement;
import cool.muyucloud.croparia.api.generator.util.Placeholder;
import cool.muyucloud.croparia.api.generator.util.TranslatableElement;
import cool.muyucloud.croparia.util.CodecUtil;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public abstract class AbstractCrop implements TranslatableElement {
    public static final Placeholder<AbstractCrop> MATERIAL = Placeholder.of(
        "\\{material}", crop -> crop.getMaterial().getId().toString()
    );
    public static final Placeholder<AbstractCrop> MATERIAL_PATH = Placeholder.of(
        "\\{material_path}", crop -> crop.getMaterial().getId().getPath()
    );
    public static final Placeholder<AbstractCrop> MATERIAL_TYPE = Placeholder.of(
        "\\{material_type}", crop -> crop.getMaterial().isTag() ? "tag" : "item"
    );
    public static final Placeholder<AbstractCrop> MATERIAL_TAGGABLE = Placeholder.of(
        "\\{material_taggable}", crop -> crop.getMaterial().getName()
    );
    public static final Placeholder<AbstractCrop> MATERIAL_COMPONENTS = Placeholder.of(
        "\\{material_components}", crop -> CodecUtil.encodeJson(crop.getMaterial().getComponents(), DataComponentPatch.CODEC).toString()
    );

    private final LazySupplier<Set<Placeholder<?>>> placeholders = LazySupplier.of(() -> {
        ArrayList<Placeholder<?>> list = new ArrayList<>();
        this.buildPlaceholders(list);
        return ImmutableSet.copyOf(list);
    });

    public abstract @NotNull Material getMaterial();

    public ItemStack getMaterialStack() {
        return this.getMaterial().getStack();
    }

    public String getMaterialName() {
        return this.getMaterial().getName();
    }

    public abstract void onRegister();

    @Override
    public Collection<Placeholder<? extends DgElement>> placeholders() {
        return placeholders.get();
    }

    @Override
    public void buildPlaceholders(Collection<Placeholder<?>> set) {
        TranslatableElement.super.buildPlaceholders(set);
        set.add(MATERIAL);
        set.add(MATERIAL_PATH);
        set.add(MATERIAL_TYPE);
        set.add(MATERIAL_TAGGABLE);
        set.add(MATERIAL_COMPONENTS);
    }
}
