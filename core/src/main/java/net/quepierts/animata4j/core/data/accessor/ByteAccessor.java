package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface ByteAccessor {
    void putByte(final long offset, final byte value);

    void putByte(final long offset, final byte[] bytes, final int arrayOffset, final int length);

    default void putByte(final long offset, final byte[] bytes) {
        this.putByte(offset, bytes, 0, bytes.length);
    }

    byte getByte(final long offset);

    void getByte(final long offset, final byte[] bytes, final int arrayOffset, final int length);

    default void getByte(final long offset, final byte[] bytes) {
        this.getByte(offset, bytes, 0, bytes.length);
    }
}
