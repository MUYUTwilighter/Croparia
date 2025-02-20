package cool.muyucloud.croparia.api.repo.annotation;

import java.lang.annotation.Inherited;

@Inherited
@SuppressWarnings("unused")
public @interface RepeatableUnreliable {
    Unreliable[] value();
}
