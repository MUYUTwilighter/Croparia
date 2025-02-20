package cool.muyucloud.croparia.api.resource;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface TypeRepo {
    void forEachType(Consumer<TypeToken<?>> consumer);

    boolean isTypeValid(TypeToken<?> type);
}
