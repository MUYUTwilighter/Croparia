package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.util.Color;
import cool.muyucloud.croparia.api.element.Element;

@SuppressWarnings("unused")
public class Elements {
    public static final Element AIR = new Element(CropariaIf.of("air"), Color.of(6453378), attr -> {
    });
    public static final Element EARTH = new Element(CropariaIf.of("earth"), Color.of(11884313), attr -> {
    });
    public static final Element ELEMENTAL = new Element(CropariaIf.of("elemental"), Color.of(6238065), attr -> {
    });
    public static final Element FIRE = new Element(CropariaIf.of("fire"), Color.of(11010055), attr -> {
    });
    public static final Element WATER = new Element(CropariaIf.of("water"), Color.of(750238), attr -> {
    });

    public static void register() {
        CropariaIf.LOGGER.debug("Registering elements");
    }
}
