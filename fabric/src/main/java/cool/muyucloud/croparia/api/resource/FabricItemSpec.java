package cool.muyucloud.croparia.api.resource;

import cool.muyucloud.croparia.api.resource.type.ItemSpec;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

@SuppressWarnings({"unused"})
public class FabricItemSpec {
    public static ItemVariant of(ItemSpec item) {
        return ItemVariant.of(item.createStack());
    }

    public static ItemSpec from(ItemVariant variant) {
        return new ItemSpec(variant.getItem(), variant.getComponents());
    }

    public static boolean matches(ItemSpec a, ItemVariant b) {
        return a.getResource() == b.getItem() && a.getComponentsPatch().equals(b.getComponents());
    }

    public static boolean matches(ItemVariant a, ItemSpec b) {
        return b.getResource() == a.getItem() && b.getComponentsPatch().equals(a.getComponents());
    }
}
