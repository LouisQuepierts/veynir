package net.quepierts.animata4j.core.data.block;

import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public interface DataBlock extends AutoCloseable {
    long DEFAULT_SIZE = 2 << 9;
    long THRESHOLD_USE_SEG = 2 << 5 - 1;

    @Contract(value = "-> new", pure = true)
    static DataBlock create() {
        return DirectDataBlock.create();
    }

    @Contract(value = "_ -> new", pure = true)
    static DataBlock create(long size) {
        return DirectDataBlock.create(size);
    }

    void free();

    byte getByte(final long offset);

    short getShort(final long offset);

    int getInt(final long offset);

    long getLong(final long offset);

    float getFloat(final long offset);

    double getDouble(final long offset);
    
    void getByte(final long offset, final byte[] bytes, final int length);
    
    void getShort(final long offset, final short[] shorts, final int length);
    
    void getInt(final long offset, final int[] ints, final int length);
    
    void getLong(final long offset, final long[] longs, final int length);
    
    void getFloat(final long offset, final float[] floats, final int length);
    
    void getDouble(final long offset, final double[] doubles, final int length);

    default void getByte(final long offset, final byte[] bytes) {
        this.getByte(offset, bytes, bytes.length);
    }

    default void getShort(final long offset, final short[] shorts) {
        this.getShort(offset, shorts, shorts.length);
    }

    default void getInt(final long offset, final int[] ints) {
        this.getInt(offset, ints, ints.length);
    }

    default void getLong(final long offset, final long[] longs) {
        this.getLong(offset, longs, longs.length);
    }

    default void getFloat(final long offset, final float[] floats) {
        this.getFloat(offset, floats, floats.length);
    }

    default void getDouble(final long offset, final double[] doubles) {
        this.getDouble(offset, doubles, doubles.length);
    }


    void putByte(final long offset, final byte value);

    void putShort(final long offset, final short value);

    void putInt(final long offset, final int value);

    void putLong(final long offset, final long value);

    void putFloat(final long offset, final float value);

    void putDouble(final long offset, final double value);

    void putByte(final long offset, final byte[] bytes, final int length);

    void putShort(final long offset, final short[] shorts, final int length);

    void putInt(final long offset, final int[] ints, final int length);

    void putLong(final long offset, final long[] longs, final int length);

    void putFloat(final long offset, final float[] floats, final int length);

    void putDouble(final long offset, final double[] doubles, final int length);

    default void putByte(final long offset, final byte[] bytes) {
        this.putByte(offset, bytes, bytes.length);
    }

    default void putShort(final long offset, final short[] shorts) {
        this.putShort(offset, shorts, shorts.length);
    }

    default void putInt(final long offset, final int[] ints) {
        this.putInt(offset, ints, ints.length);
    }

    default void putLong(final long offset, final long[] longs) {
        this.putLong(offset, longs, longs.length);
    }

    default void putFloat(final long offset, final float[] floats) {
        this.putFloat(offset, floats, floats.length);
    }

    default void putDouble(final long offset, final double[] doubles) {
        this.putDouble(offset, doubles, doubles.length);
    }

    long size();

    @Override
    default void close() {
        this.free();
    }
}
