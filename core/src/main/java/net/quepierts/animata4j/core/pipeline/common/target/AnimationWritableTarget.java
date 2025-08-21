package net.quepierts.animata4j.core.pipeline.common.target;

/**
 * Interface for a writable target which allows animation to write data from a target.
 */
@SuppressWarnings("unused")
public interface AnimationWritableTarget {
    void setFloat(int index, float value);

    void setFloat(int index, float[] value);

    void setDouble(int index, double value);

    void setDouble(int index, double[] value);

    void setByte(int index, byte value);

    void setByte(int index, byte[] value);

    void setInteger(int index, int value);

    void setInteger(int index, int[] value);

    void setLong(int index, long value);

    void setLong(int index, long[] value);

    default void setBoolean(int index, boolean value) {
        this.setFloat(index, value ? 1.0f : 0.0f);
    }

    default void setBoolean(int index, boolean[] value) {
        int left = index;
        for (int i = 0; i < value.length; i++, left++) {
            this.setBoolean(left, value[i]);
        }
    }
}
