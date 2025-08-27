package cool.muyucloud.croparia.registry;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import cool.muyucloud.croparia.api.generator.pack.ResourcePackHandler;
import cool.muyucloud.croparia.api.json.JsonBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.server.packs.PackType;

@SuppressWarnings("unused")
public class PackHandlers {
    public static final DataPackHandler DATAPACK = DataPackHandler.register(
        CropariaIf.of("datapack"), CropariaIf.CONFIG.getFilePath().resolve("datapack"), JsonBuilder.map(
            "pack", JsonBuilder.map(
                "pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                "description", "Croparia mandatory datapack, please do not modify"
            )
        ), CropariaIf.CONFIG::getOverride
    );
    public static final ResourcePackHandler RESOURCEPACK = ResourcePackHandler.register(
        CropariaIf.of("resourcepack"), CropariaIf.CONFIG.getFilePath().resolve("resourcepack"), JsonBuilder.map(
            "pack", JsonBuilder.map(
                "pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES),
                "description", "Croparia mandatory resourcepack, please do not modify"
            )
        ), CropariaIf.CONFIG::getOverride
    );

    public static void register() {
        CropariaIf.LOGGER.debug("Registering pack handlers");
    }
}
