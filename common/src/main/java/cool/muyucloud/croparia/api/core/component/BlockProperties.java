package cool.muyucloud.croparia.api.core.component;

import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.access.StateHolderAccess;
import cool.muyucloud.croparia.util.text.Texts;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class BlockProperties implements TooltipProvider, Iterable<Map.Entry<String, String>> {
    public static final Codec<BlockProperties> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(BlockProperties::new, BlockProperties::getProperties);
    public static final StreamCodec<FriendlyByteBuf, BlockProperties> STREAM_CODEC = StreamCodec.of(
        (buf, component) -> buf.writeJsonWithCodec(Codec.unboundedMap(Codec.STRING, Codec.STRING), component.getProperties()),
        buf -> new BlockProperties(buf.readJsonWithCodec(Codec.unboundedMap(Codec.STRING, Codec.STRING)))
    );
    public static final DataComponentType<BlockProperties> TYPE;
    public static final BlockProperties EMPTY = new BlockProperties(Map.of());

    static {
        DataComponentType.Builder<BlockProperties> builder = DataComponentType.builder();
        builder.persistent(CODEC).networkSynchronized(STREAM_CODEC);
        TYPE = builder.build();
    }

    public static BlockProperties create(@NotNull BlockState state) {
        return create(((StateHolderAccess) state).cif$getProperties());
    }

    @NotNull
    public static BlockProperties create(@NotNull Map<String, String> properties) {
        return properties.isEmpty() ? EMPTY : new BlockProperties(properties);
    }

    public static final Component TITLE = Texts.translatable("tooltip.croparia.block_properties");
    private final Map<String, String> properties;

    protected BlockProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @NotNull
    protected Map<String, String> getProperties() {
        return properties;
    }

    public boolean isEmpty() {
        return this.getProperties().isEmpty();
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        if (this.getProperties().isEmpty()) return;
        consumer.accept(TITLE);
        this.getProperties().forEach((key, value) -> consumer.accept(Texts.literal("%s=%s".formatted(key, value))));
    }

    public boolean isSubsetOf(StateHolderAccess access) {
        for (Map.Entry<String, String> entry : this.getProperties().entrySet()) {
            @NotNull String key = entry.getKey();
            @Nullable String value = entry.getValue();
            @Nullable String blockVal = access.cif$getValue(key);
            if (value == null && blockVal != null) {
                continue;
            }
            if (!Objects.equals(blockVal, value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockProperties that)) return false;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(properties);
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.getProperties().entrySet().iterator();
    }
}
