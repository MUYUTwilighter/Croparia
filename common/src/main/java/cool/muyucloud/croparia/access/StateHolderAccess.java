package cool.muyucloud.croparia.access;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface StateHolderAccess {
    @Nullable
    String croparia_if$getValue(String key);

    Map<String, String> croparia_if$getProperties();
}
