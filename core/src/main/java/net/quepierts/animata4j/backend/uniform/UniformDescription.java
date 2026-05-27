package net.quepierts.animata4j.backend.uniform;

public final class UniformDescription {
    private final String name;
    private final UniformType type;
    private final int length;

    public UniformDescription(
            String name,
            UniformType type,
            int length
    ) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

    public String name() {
        return name;
    }

    public UniformType type() {
        return type;
    }

    public int length() {
        return length;
    }

    @Override
    public String toString() {
        return "UniformDescription[" +
                "name=" + name + ", " +
                "type=" + type + ", " +
                "length=" + length + ']';
    }

}
