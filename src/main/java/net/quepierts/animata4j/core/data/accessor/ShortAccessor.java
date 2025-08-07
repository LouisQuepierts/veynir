package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface ShortAccessor {
    void putShort(final long offset, final short value);

    void putShort(final long offset, final short[] shorts, final int arrayOffset, final int length);

    default void putShort(final long offset, final short[] shorts) {
        this.putShort(offset, shorts, 0, shorts.length);
    }

    short getShort(final long offset);

    void getShort(final long offset, final short[] shorts, final int arrayOffset, final int length);

    default void getShort(final long offset, final short[] shorts) {
        this.getShort(offset, shorts, 0, shorts.length);
    }
}
