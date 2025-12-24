package net.quepierts.animata4j.core.pipeline.common.target;

/**
 * Interface for a readable target which allows animation to read data from a target.
 * */
@SuppressWarnings("unused")
public interface AnimationReadableTarget {
    float read(int index);

    void read(int index, float[] out);

    double getDouble(int index);

    void getDouble(int index, double[] out);

    byte getByte(int index);

    void getByte(int index, byte[] out);

    int getInteger(int index);

    void getInteger(int index, int[] out);

    long getLong(int index);

    void getLong(int index, long[] out);

    default boolean getBoolean(int index) {
        return this.read(index) > 0.5f;
    }

    default void getBoolean(int index, boolean[] out) {
        int left = index;
        for (int i = 0; i < out.length; i++, left++) {
            out[i] = this.read(left) > 0.5f;
        }
    }
}
