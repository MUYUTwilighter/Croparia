package cool.muyucloud.croparia.api.crop.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.util.codec.AnyCodec;
import cool.muyucloud.croparia.util.supplier.LazySupplier;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CropDependencies implements Iterable<Map.Entry<String, String>> {
    public static final Codec<CropDependencies> CODEC_CROP = Codec.STRING.xmap(s -> new CropDependencies(CropariaIf.MOD_ID, s), keys -> keys.getKey(CropariaIf.MOD_ID));
    public static final Codec<CropDependencies> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(CropDependencies::new, CropDependencies::getMap);
    public static final AnyCodec<CropDependencies> CODEC_ANY = new AnyCodec<>(CODEC, CODEC_CROP);

    private final Map<String, String> map;
    private final LazySupplier<String> key = LazySupplier.of(() -> {
       for (Map.Entry<String, String> entry : this) {
           String modId = entry.getKey();
           if (this.shouldLoad(modId)) return entry.getValue();
       }
        return null;
    });

    public CropDependencies(Map<String, String> map) {
        this.map = ImmutableMap.copyOf(map);
    }

    public CropDependencies(String... entries) {
        if (entries.length % 2 == 0) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < entries.length; i += 2) {
                map.put(entries[i], entries[i + 1]);
            }
            this.map = ImmutableMap.copyOf(map);
        } else {
            throw new IllegalArgumentException("Entries are not in pairs");
        }
    }

    public boolean shouldLoad() {
        for (String modId : this.map.keySet()) {
            if (shouldLoad(modId)) {
                return true;
            }
        }
        return this.isEmpty();
    }

    protected boolean shouldLoad(String modId) {
        return Platform.isModLoaded(modId) && CropariaIf.CONFIG.isModValid(modId);
    }

    protected Map<String, String> getMap() {
        return this.map;
    }

    public @Nullable String getKey(String modId) {
        return this.map.get(modId);
    }

    public String getKey() {
        return key.get();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.map.entrySet().iterator();
    }

    public int size() {
        return this.map.size();
    }
}
