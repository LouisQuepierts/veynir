package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface ByteBooleanAccessor {
    void putByteBoolean(final long offset, final boolean value);

    void putByteBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length);

    default void putByteBoolean(final long offset, final boolean[] booleans) {
        this.putByteBoolean(offset, booleans, 0, booleans.length);
    }

    boolean getByteBoolean(final long offset);

    void getByteBoolean(final long offset, final boolean[] booleans, final int arrayOffset, final int length);

    default void getByteBoolean(final long offset, final boolean[] booleans) {
        this.getByteBoolean(offset, booleans, 0, booleans.length);
    }
}
