package net.quepierts.animata4j.core.data.accessor;

@SuppressWarnings("unused")
public interface IntegerAccessor {
    void putInteger(final long offset, final int value);

    void putInteger(final long offset, final int[] ints, final int arrayOffset, final int length);

    default void putInteger(final long offset, final int[] ints) {
        this.putInteger(offset, ints, 0, ints.length);
    }

    int getInteger(final long offset);

    void getInteger(final long offset, final int[] ints, final int arrayOffset, final int length);

    default void getInteger(final long offset, final int[] ints) {
        this.getInteger(offset, ints, 0, ints.length);
    }
}
