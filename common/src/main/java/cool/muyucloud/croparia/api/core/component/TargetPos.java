package cool.muyucloud.croparia.api.core.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class TargetPos implements TooltipProvider {
    public static final MapCodec<TargetPos> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("dim").forGetter(TargetPos::getDim),
        BlockPos.CODEC.fieldOf("pos").forGetter(TargetPos::getPos)
    ).apply(instance, TargetPos::new));
    public static final DataComponentType<TargetPos> TYPE = DataComponentType.<TargetPos>builder().persistent(CODEC.codec()).build();

    @NotNull
    private transient final MutableComponent dimName;
    @NotNull
    private transient final ResourceKey<Level> dimKey;
    @NotNull
    private final BlockPos pos;
    private transient final MutableComponent tooltip;

    public TargetPos(@NotNull Entity entity) {
        this(entity.level(), entity.blockPosition());
    }

    public TargetPos(@NotNull Level level, @NotNull BlockPos pos) {
        this(level.dimension(), pos);
    }

    public TargetPos(@NotNull ResourceKey<Level> dim, @NotNull BlockPos pos) {
        this.pos = pos;
        this.dimKey = dim;
        this.dimName = Texts.translatable("dimension.%s.%s".formatted(dim.location().getNamespace(), dim.location().getPath()));
        this.tooltip = Texts.translatable("tooltip.croparia.bounded_position", this.getDimName(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
    }

    public TargetPos(@NotNull ResourceLocation dim, @NotNull BlockPos pos) {
        this.pos = pos;
        this.dimKey = ResourceKey.create(Registries.DIMENSION, dim);
        this.dimName = Texts.translatable("dimension.%s.%s".formatted(dim.getNamespace(), dim.getPath()));
        this.tooltip = Texts.translatable("tooltip.croparia.bounded_position", this.getDimName(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
    }

    @NotNull
    public ResourceLocation getDim() {
        return dimKey.location();
    }

    @NotNull
    public MutableComponent getDimName() {
        return dimName;
    }

    @NotNull
    public ResourceKey<Level> getDimKey() {
        return dimKey;
    }

    public Optional<ServerLevel> getLevel(@NotNull MinecraftServer server) {
        return Optional.ofNullable(server.getLevel(this.getDimKey()));
    }

    @NotNull
    public BlockPos getPos() {
        return pos;
    }

    public MutableComponent getTooltip() {
        return tooltip;
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, @NotNull Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(getTooltip());
    }

    public void teleport(@NotNull Entity entity, @NotNull MinecraftServer server) {
        this.getLevel(server).ifPresent(level -> entity.teleportTo(level, getPos().getX(), getPos().getY(), getPos().getZ(), Relative.ROTATION, 0, 0, true));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TargetPos targetPos)) return false;
        return Objects.equals(dimKey, targetPos.dimKey) && Objects.equals(pos, targetPos.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimKey, pos);
    }
}
