package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface LongAccessor {
    void putLong(final long offset, final long value);

    void putLong(final long offset, final long[] longs, final int arrayOffset, final int length);

    default void putLong(final long offset, final long[] longs) {
        this.putLong(offset, longs, 0, longs.length);
    }

    long getLong(final long offset);

    void getLong(final long offset, final long[] longs, final int arrayOffset, final int length);

    default void getLong(final long offset, final long[] longs) {
        this.getLong(offset, longs, 0, longs.length);
    }
}
