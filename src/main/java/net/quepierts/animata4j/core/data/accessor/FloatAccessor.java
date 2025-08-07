package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface FloatAccessor {
    void putFloat(final long offset, final float value);

    void putFloat(final long offset, final float[] floats, final int arrayOffset, final int length);

    default void putFloat(final long offset, final float[] floats) {
        this.putFloat(offset, floats, 0, floats.length);
    }

    float getFloat(final long offset);

    void getFloat(final long offset, final float[] floats, final int arrayOffset, final int length);

    default void getFloat(final long offset, final float[] floats) {
        this.getFloat(offset, floats, 0, floats.length);
    }
}
