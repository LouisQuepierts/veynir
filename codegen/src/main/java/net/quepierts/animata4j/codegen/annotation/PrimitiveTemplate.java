package net.quepierts.animata4j.codegen.annotation;

public @interface PrimitiveTemplate {
    Type[] types() default {};

    enum Type {
        BYTE("byte", "Byte"),
        SHORT("short", "Short"),
        INTEGER("int", "Integer"),
        LONG("long", "Long"),
        FLOAT("float", "Float"),
        DOUBLE("double", "Double");

        final String typename;
        final String className;

        Type(String typename, String className) {
            this.typename = typename;
            this.className = className;
        }

        public String getTypeName() {
            return typename;
        }

        public String getClassName() {
            return className;
        }
    }
}
