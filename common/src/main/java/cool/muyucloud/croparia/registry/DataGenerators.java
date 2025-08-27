package cool.muyucloud.croparia.registry;

import com.mojang.serialization.MapCodec;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.generator.AggregatedGenerator;
import cool.muyucloud.croparia.api.generator.DataGenerator;
import cool.muyucloud.croparia.api.generator.LangGenerator;

@SuppressWarnings("unused")
public class DataGenerators {
    public static final MapCodec<DataGenerator> GENERATOR = DataGenerator.register(CropariaIf.of("generator"), DataGenerator.CODEC);
    public static final MapCodec<AggregatedGenerator> AGGREGATED = DataGenerator.register(CropariaIf.of("aggregated"), AggregatedGenerator.CODEC);
    public static final MapCodec<LangGenerator> LANG = DataGenerator.register(CropariaIf.of("lang"), LangGenerator.CODEC);

    public static void register() {
        CropariaIf.LOGGER.debug("Registering data generators");
    }
}
