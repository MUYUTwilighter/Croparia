package cool.muyucloud.croparia.api.resource;

import com.mojang.serialization.MapCodec;

public interface ResourceType extends TypeTokenAccess {
    boolean isEmpty();

    MapCodec<?> getCodec();
}
