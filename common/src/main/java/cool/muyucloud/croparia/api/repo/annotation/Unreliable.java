package cool.muyucloud.croparia.api.repo.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;

/**
 * Indicate the field is potentially unreliable if the object is from the specified platform.
 * */
@Inherited
@Repeatable(RepeatableUnreliable.class)
public @interface Unreliable {
    String value() default "BOTH";

    String reason() default "";
}
