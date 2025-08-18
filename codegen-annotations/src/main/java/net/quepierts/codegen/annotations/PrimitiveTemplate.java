package net.quepierts.codegen.annotations;

public @interface PrimitiveTemplate {
    Type[] types() default {};

    enum Type {
        BYTE,
        SHORT,
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE;
    }
}
