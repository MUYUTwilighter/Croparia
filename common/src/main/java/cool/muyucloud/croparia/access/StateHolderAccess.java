package cool.muyucloud.croparia.access;

import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface StateHolderAccess {
    Property<?> croparia_if$getProperty(String key);

    @Nullable
    String croparia_if$getValue(String key);

    void croparia_if$setValue(String key, String value);

    Map<String, String> croparia_if$getProperties();
}
