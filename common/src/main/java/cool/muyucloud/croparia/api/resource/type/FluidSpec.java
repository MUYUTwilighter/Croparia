package cool.muyucloud.croparia.api.resource.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.resource.ResourceType;
import cool.muyucloud.croparia.api.resource.TypeToken;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class FluidSpec implements ResourceType {
    public static final MapCodec<FluidSpec> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(fluid -> fluid.getFluid().arch$registryName()),
        DataComponentPatch.CODEC.fieldOf("nbt").forGetter(FluidSpec::getNbt)
    ).apply(instance, (id, nbt) -> new FluidSpec(BuiltInRegistries.FLUID.get(id), nbt)));
    public static final FluidSpec EMPTY = FluidSpec.of(Fluids.EMPTY);
    public static final TypeToken<FluidSpec> TYPE = TypeToken.register(CropariaIf.of("fluid_spec"), EMPTY, CODEC).orElseThrow();

    @NotNull
    private final Fluid fluid;
    @NotNull
    private final DataComponentPatch nbt;

    public static FluidSpec of(@NotNull Fluid fluid) {
        return new FluidSpec(fluid, DataComponentPatch.EMPTY);
    }

    public static FluidSpec of(@NotNull Fluid fluid, @NotNull DataComponentPatch nbt) {
        return new FluidSpec(fluid, nbt);
    }

    public FluidSpec(@NotNull Fluid fluid, @NotNull DataComponentPatch nbt) {
        this.fluid = fluid;
        this.nbt = nbt;
    }

    public @NotNull Fluid getFluid() {
        return fluid;
    }

    public @NotNull DataComponentPatch getNbt() {
        return nbt;
    }

    public FluidSpec withFluid(Fluid fluid) {
        return new FluidSpec(fluid, nbt);
    }

    public FluidSpec replaceNbt(@NotNull DataComponentPatch nbt) {
        return new FluidSpec(fluid, nbt);
    }

    public boolean isEmpty() {
        return this.getFluid() == Fluids.EMPTY;
    }

    public TypeToken<FluidSpec> getType() {
        return TYPE;
    }

    public boolean is(@NotNull Fluid fluid) {
        return this.getFluid() == fluid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FluidSpec fluidSpec)) return false;
        return Objects.equals(fluid, fluidSpec.fluid) && Objects.equals(nbt, fluidSpec.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fluid, nbt);
    }

    @Override
    public MapCodec<?> getCodec() {
        return CODEC;
    }
}
