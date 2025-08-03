package cool.muyucloud.croparia.registry;

import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.CompositeGenerator;
import cool.muyucloud.croparia.api.generator.DataGenerator;
import cool.muyucloud.croparia.api.generator.LangGenerator;

@SuppressWarnings("unused")
public class DataGenerators {
    public static final MapCodec<DataGenerator<?>> GENERATOR = DataGenerator.register(CropariaIf.of("generator"), DataGenerator.CODEC);
    public static final MapCodec<CompositeGenerator<?>> COMPOSITE = DataGenerator.register(CropariaIf.of("composite"), CompositeGenerator.CODEC);
    public static final MapCodec<LangGenerator<?>> LANG = DataGenerator.register(CropariaIf.of("lang"), LangGenerator.CODEC);

    public static void register() {
        CropariaIf.LOGGER.debug("Registering data generators");
    }
}
