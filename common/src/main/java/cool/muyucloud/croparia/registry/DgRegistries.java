package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropRegistry;
import cool.muyucloud.croparia.api.element.Element;
import cool.muyucloud.croparia.api.generator.util.DgRegistry;

public class DgRegistries {
    public static final CropRegistry<Crop> CROPS = DgRegistry.register(
        CropariaIf.of("crops"), new CropRegistry<>(CropariaIf.CONFIG.getFilePath().resolve("crops"), Crop.CODEC)
    );
    @SuppressWarnings("unused")
    public static final DgRegistry<Element> ELEMENTS = DgRegistry.register(
        CropariaIf.of("elements"), DgRegistry.map(Element.REGISTRY)
    );

    public static void register() {
        CropariaIf.LOGGER.debug("Registering data generator iterables");
    }
}
