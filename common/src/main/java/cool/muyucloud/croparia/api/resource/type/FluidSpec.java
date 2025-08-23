package cool.muyucloud.croparia.api.resource.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.resource.TypeToken;
import cool.muyucloud.croparia.api.resource.TypedResource;
import cool.muyucloud.croparia.util.TagUtil;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class FluidSpec implements TypedResource<Fluid>, DataComponentHolder {
    public static final MapCodec<FluidSpec> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(fluidSpec -> fluidSpec.getResource().arch$registryName()),
        DataComponentMap.CODEC.fieldOf("components").forGetter(FluidSpec::getComponents)
    ).apply(instance, (id, nbt) -> new FluidSpec(BuiltInRegistries.FLUID.getValue(id), nbt)));
    public static final FluidSpec EMPTY = FluidSpec.of(Fluids.EMPTY);
    public static final TypeToken<FluidSpec> TYPE = TypeToken.register(CropariaIf.of("fluid_spec"), EMPTY, CODEC).orElseThrow();

    @NotNull
    public static FluidSpec of(@NotNull Fluid fluid) {
        return new FluidSpec(fluid, DataComponentMap.EMPTY);
    }

    @NotNull
    public static FluidSpec of(@NotNull Fluid fluid, @Nullable DataComponentMap nbt) {
        return new FluidSpec(fluid, nbt == null ? DataComponentMap.EMPTY : nbt);
    }

    @NotNull
    private final Fluid resource;
    @NotNull
    private final PatchedDataComponentMap components;

    public FluidSpec(@NotNull Fluid fluid) {
        this(fluid, DataComponentPatch.EMPTY);
    }

    public FluidSpec(@NotNull Fluid fluid, @NotNull DataComponentPatch components) {
        this.resource = fluid;
        this.components = PatchedDataComponentMap.fromPatch(DataComponentMap.EMPTY, components);
    }

    public FluidSpec(@NotNull Fluid fluid, @NotNull DataComponentMap components) {
        this.resource = fluid;
        this.components = new PatchedDataComponentMap(components);
    }

    @NotNull
    public FluidSpec copy() {
        return new FluidSpec(this.getResource(), this.getComponentsPatch());
    }

    @NotNull
    public FluidSpec with(@NotNull Fluid fluid) {
        return new FluidSpec(fluid, this.getComponents());
    }

    @NotNull
    public FluidSpec with(@NotNull DataComponentPatch components) {
        PatchedDataComponentMap patched = new PatchedDataComponentMap(this.getComponents());
        patched.applyPatch(components);
        return new FluidSpec(this.getResource(), patched);
    }

    @NotNull
    public FluidSpec with(@NotNull DataComponentMap components) {
        PatchedDataComponentMap patched = new PatchedDataComponentMap(this.getComponents());
        patched.setAll(components);
        return new FluidSpec(this.getResource(), patched);
    }

    @NotNull
    public FluidSpec with(@NotNull TypedDataComponent<?> component) {
        PatchedDataComponentMap patched = new PatchedDataComponentMap(this.getComponents());
        component.applyTo(patched);
        return new FluidSpec(this.getResource(), patched);
    }

    @NotNull
    public <T> FluidSpec with(@NotNull DataComponentType<T> type, @NotNull T value) {
        return this.with(new TypedDataComponent<>(type, value));
    }

    @NotNull
    public FluidSpec replaceComponents(@NotNull DataComponentMap components) {
        return new FluidSpec(this.getResource(), components);
    }

    public boolean is(@NotNull FluidSpec spec) {
        return this.getResource() == spec.getResource() && this.getComponents().equals(spec.getComponents());
    }

    public boolean is(@NotNull ResourceLocation tag) {
        return TagUtil.isIn(Registries.FLUID, tag, this.getResource());
    }

    public FluidStack toStack(long amount) {
        return FluidStack.create(this.getResource(), amount, this.getComponentsPatch());
    }

    @Override
    public TypeToken<FluidSpec> getType() {
        return TYPE;
    }

    @Override
    public MapCodec<FluidSpec> getCodec() {
        return CODEC;
    }

    @Override
    @NotNull
    public Fluid getResource() {
        return this.resource;
    }

    @Override
    @NotNull
    public DataComponentMap getComponents() {
        return this.components;
    }

    @NotNull
    public DataComponentPatch getComponentsPatch() {
        return this.components.asPatch();
    }
}
