package cool.muyucloud.croparia.api.resource.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.resource.ResourceType;
import cool.muyucloud.croparia.api.resource.TypeToken;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("unused")
public class FluidSpec implements ResourceType {
    public static final MapCodec<FluidSpec> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("id").forGetter(fluid -> fluid.getFluid().arch$registryName()),
        CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(FluidSpec::getNbt)
    ).apply(instance, (id, nbt) -> new FluidSpec(Registry.FLUID.get(id), nbt.orElse(null))));
    public static final FluidSpec EMPTY = new FluidSpec(Fluids.EMPTY, null);
    public static final TypeToken<FluidSpec> TYPE = TypeToken.register(CropariaIf.of("fluid_spec"), EMPTY, CODEC).orElseThrow();

    private final Fluid fluid;
    @Nullable
    private final CompoundTag nbt;

    public static FluidSpec of(@NotNull Fluid fluid) {
        return new FluidSpec(fluid, null);
    }

    public static FluidSpec of(@NotNull Fluid fluid, @Nullable CompoundTag nbt) {
        return new FluidSpec(fluid, nbt);
    }

    public FluidSpec(@NotNull Fluid fluid, @Nullable CompoundTag nbt) {
        this.fluid = fluid;
        this.nbt = nbt;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public Optional<CompoundTag> getNbt() {
        return Optional.ofNullable(nbt);
    }

    public FluidSpec withFluid(Fluid fluid) {
        return new FluidSpec(fluid, nbt);
    }

    public FluidSpec replaceNbt(@NotNull CompoundTag nbt) {
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
