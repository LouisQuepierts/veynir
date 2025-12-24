package net.quepierts.animata4j.core.pipeline.common.target;

/**
 * Interface for a writable target which allows animation to write data from a target.
 */
@SuppressWarnings("unused")
public interface AnimationWritableTarget {
    void write(int index, float value);

    void write(int index, float x, float y);

    void write(int index, float x, float y, float z);

    void write(int index, float x, float y, float z, float w);

    void write(int index, float[] value);

    void write(int index, float[] value, int offset, int length);

    void fill(float value);

    void fill(float value, int offset, int length);

    void setDouble(int index, double value);

    void setDouble(int index, double[] value);

    void setByte(int index, byte value);

    void setByte(int index, byte[] value);

    void setInteger(int index, int value);

    void setInteger(int index, int[] value);

    void setLong(int index, long value);

    void setLong(int index, long[] value);

    default void setBoolean(int index, boolean value) {
        this.write(index, value ? 1.0f : 0.0f);
    }

    default void setBoolean(int index, boolean[] value) {
        int left = index;
        for (int i = 0; i < value.length; i++, left++) {
            this.setBoolean(left, value[i]);
        }
    }
}
