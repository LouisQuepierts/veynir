package net.quepierts.animata4j.core.data.block;

import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public interface DataBlock {
    long DEFAULT_SIZE = 2 << 9;
    long THRESHOLD_USE_SEG = 2 << 5 - 1;

    @Contract(value = "-> new", pure = true)
    static DelegatedDataBlock create() {
        return DelegatedDataBlock.create();
    }

    @Contract(value = "_ -> new", pure = true)
    static DelegatedDataBlock create(long size) {
        return DelegatedDataBlock.create(size);
    }

    void free();

    byte getByte(final long offset);

    short getShort(final long offset);

    int getInt(final long offset);

    long getLong(final long offset);

    float getFloat(final long offset);

    double getDouble(final long offset);

    void getByte(final long offset, final byte[] bytes);

    void getShort(final long offset, final short[] shorts);

    void getInt(final long offset, final int[] ints);

    void getLong(final long offset, final long[] longs);

    void getFloat(final long offset, final float[] floats);

    void getDouble(final long offset, final double[] doubles);


    void putByte(final long offset, final byte value);

    void putShort(final long offset, final short value);

    void putInt(final long offset, final int value);

    void putLong(final long offset, final long value);

    void putFloat(final long offset, final float value);

    void putDouble(final long offset, final double value);

    void putByte(final long offset, final byte[] bytes);

    void putShort(final long offset, final short[] shorts);

    void putInt(final long offset, final int[] ints);

    void putLong(final long offset, final long[] longs);

    void putFloat(final long offset, final float[] floats);

    void putDouble(final long offset, final double[] doubles);

    long size();
}
