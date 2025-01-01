package cool.muyucloud.croparia.access;

import org.jetbrains.annotations.Nullable;

public interface StateHolderAccess {
    @Nullable
    String croparia_if$getValue(String key);
}
