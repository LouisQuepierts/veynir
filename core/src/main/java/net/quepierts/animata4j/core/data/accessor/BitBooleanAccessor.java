package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface BitBooleanAccessor {
    void putBitBoolean(final long offset, final int shift, final boolean value);

    void putBitBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length);

    default void putBitBoolean(final long offset, final boolean[] booleans) {
        this.putBitBoolean(offset, booleans, 0, booleans.length);
    }

    boolean getBitBoolean(final long offset, final int shift);

    void getBitBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length);

    default void getBitBoolean(final long offset, final boolean[] booleans) {
        this.getBitBoolean(offset, booleans, 0, booleans.length);
    }
}
