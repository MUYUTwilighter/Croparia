package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.element.Element;

@SuppressWarnings("unused")
public class Elements {
    public static final Element AIR = new Element(CropariaIf.of("air"), attr -> {
    });
    public static final Element EARTH = new Element(CropariaIf.of("earth"), attr -> {
    });
    public static final Element ELEMENTAL = new Element(CropariaIf.of("elemental"), attr -> {
    });
    public static final Element FIRE = new Element(CropariaIf.of("fire"), attr -> {
    });
    public static final Element WATER = new Element(CropariaIf.of("water"), attr -> {
    });

    public static void register() {
        CropariaIf.LOGGER.debug("Registering elements");
    }
}
