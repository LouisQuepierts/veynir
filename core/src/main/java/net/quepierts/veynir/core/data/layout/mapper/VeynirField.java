package net.quepierts.veynir.core.data.layout.mapper;

import net.quepierts.veynir.core.data.layout.VeynirDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VeynirField {
    String value() default "";

    int length() default 1;

    Type type() default @Type(value = VeynirDataType.STRUCT);

    @Retention(RetentionPolicy.RUNTIME)
    @interface Type {
        VeynirDataType value() default VeynirDataType.STRUCT;

        int count() default 1;

        String typename() default "";
    }
}
