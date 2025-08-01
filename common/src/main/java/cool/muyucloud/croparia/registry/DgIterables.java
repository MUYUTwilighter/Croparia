package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropRegistry;
import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.generator.util.DgIterable;

public class DgIterables {
    public static final CropRegistry<Crop> CROPS = DgIterable.register(
        CropariaIf.of("crops"), new CropRegistry<>(CropariaIf.CONFIG.getCropPath(), Crop.CODEC)
    );
    @SuppressWarnings("unused")
    public static final DgIterable<Element> ELEMENTS = DgIterable.register(
        CropariaIf.of("elements"), DgIterable.map(Element.REGISTRY)
    );

    public static void register() {
        CropariaIf.LOGGER.debug("Registering data generator iterables");
    }
}
