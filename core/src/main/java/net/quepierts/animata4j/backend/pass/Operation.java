package net.quepierts.animata4j.backend.pass;

public final class Operation {
    private final Type type;
    private final int dst;
    private final int src0;
    private final int src1;
    private final int arg0;
    private final int arg1;
    private final float param0;
    private final float param1;

    public Operation(
            Type type,

            int dst,
            int src0,
            int src1,

            int arg0,
            int arg1,

            float param0,
            float param1
    ) {
        this.type = type;
        this.dst = dst;
        this.src0 = src0;
        this.src1 = src1;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.param0 = param0;
        this.param1 = param1;
    }

    public static Operation sample(
            int sampler,
            int buffer
    ) {
        return new Operation(
                Type.SAMPLE,
                buffer,
                sampler,
                -1,
                -1,
                -1,
                0.0f,
                0.0f
        );
    }

    public static Operation blend(
            int src0,
            int src1,
            int dst,
            float weight
    ) {
        return new Operation(
                Type.BLEND_P,
                dst,
                src0,
                src1,
                -1,
                -1,
                weight,
                0.0f
        );
    }

    public static Operation blend(
            int src0,
            int src1,
            int dst,
            int weightAddress
    ) {
        return new Operation(
                Type.BLEND_A,
                dst,
                src0,
                src1,
                weightAddress,
                -1,
                0.0f,
                0.0f
        );
    }

    public static Operation assign(
            int src,
            int dst
    ) {
        return new Operation(
                Type.ASSIGN,
                dst,
                src,
                -1,
                -1,
                -1,
                0.0f,
                0.0f
        );
    }

    public static Operation clear(
            int dst
    ) {
        return new Operation(
                Type.CLEAR,
                dst,
                -1,
                -1,
                -1,
                -1,
                0.0f,
                0.0f
        );
    }

    public Type type() {
        return type;
    }

    public int dst() {
        return dst;
    }

    public int src0() {
        return src0;
    }

    public int src1() {
        return src1;
    }

    public int arg0() {
        return arg0;
    }

    public int arg1() {
        return arg1;
    }

    public float param0() {
        return param0;
    }

    public float param1() {
        return param1;
    }


    public enum Type {
        SAMPLE,
        BLEND_P,
        BLEND_A,
        ASSIGN,
        CLEAR,
        CUSTOM
    }

}
