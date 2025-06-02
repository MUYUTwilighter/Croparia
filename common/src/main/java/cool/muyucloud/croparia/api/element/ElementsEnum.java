package cool.muyucloud.croparia.api.element;

import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.util.CodecUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ElementsEnum implements StringRepresentable {
    EMPTY,
    WATER,
    FIRE,
    EARTH,
    AIR,
    ELEMENTAL;

    public static final Codec<ElementsEnum> CODEC = StringRepresentable.fromEnum(ElementsEnum::values);
    public static final StreamCodec<RegistryFriendlyByteBuf, ElementsEnum> STREAM_CODEC = CodecUtil.toStream(CODEC);

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }


}
