package net.quepierts.animata4j.core.data.layout.mapper;

import net.quepierts.animata4j.core.data.layout.AnimataDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnimataField {
    String value() default "";

    int length() default 1;

    Type type() default @Type(value = AnimataDataType.STRUCT);

    @Retention(RetentionPolicy.RUNTIME)
    @interface Type {
        AnimataDataType value() default AnimataDataType.STRUCT;

        int count() default 1;

        String typename() default "";
    }
}
