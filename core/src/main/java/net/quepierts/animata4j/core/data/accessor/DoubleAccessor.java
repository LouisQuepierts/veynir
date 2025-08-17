package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface DoubleAccessor {
    void putDouble(final long offset, final double value);

    void putDouble(final long offset, final double[] doubles, final int arrayOffset, final int length);

    default void putDouble(final long offset, final double[] doubles) {
        this.putDouble(offset, doubles, 0, doubles.length);
    }

    double getDouble(final long offset);

    void getDouble(final long offset, final double[] doubles, final int arrayOffset, final int length);

    default void getDouble(final long offset, final double[] doubles) {
        this.getDouble(offset, doubles, 0, doubles.length);
    }
}
