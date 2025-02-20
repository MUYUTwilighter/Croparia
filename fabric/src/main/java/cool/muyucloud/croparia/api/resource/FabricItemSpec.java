package cool.muyucloud.croparia.api.resource;

import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class FabricItemSpec {
    public static ItemVariant of(ItemSpec item) {
        return ItemVariant.of(item.toStack());
    }

    public static ItemSpec from(ItemVariant variant) {
        return new ItemSpec(variant.getItem(), variant.copyNbt());
    }

    public static boolean matches(ItemSpec a, ItemVariant b) {
        return b.matches(a.toStack());
    }

    public static boolean matches(ItemVariant a, ItemSpec b) {
        return a.matches(b.toStack());
    }
}
