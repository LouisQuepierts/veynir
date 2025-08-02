package net.quepierts.animata4j.core.data.block;

import sun.misc.Unsafe;

public class DirectDataBlock implements DataBlock {
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();
    private static final long BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
    private static final long SHORT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(short[].class);
    private static final long INT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(int[].class);
    private static final long LONG_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(long[].class);
    private static final long FLOAT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(float[].class);
    private static final long DOUBLE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(double[].class);

    public static DataBlock create() {
        return new DirectDataBlock(DataBlock.DEFAULT_SIZE);
    }

    public static DataBlock create(long size) {
        return new DirectDataBlock(size);
    }

    private final long size;
    private final long address;

    private DirectDataBlock(long size) {
        this.size = size;
        this.address = UNSAFE.allocateMemory(size);
    }

    @Override
    public void free() {
        UNSAFE.freeMemory(address);
    }

    @Override
    public byte getByte(long offset) {
        return UNSAFE.getByte(this.address + offset);
    }

    @Override
    public short getShort(long offset) {
        return UNSAFE.getShort(this.address + offset);
    }

    @Override
    public int getInt(long offset) {
        return UNSAFE.getInt(this.address + offset);
    }

    @Override
    public long getLong(long offset) {
        return UNSAFE.getLong(this.address + offset);
    }

    @Override
    public float getFloat(long offset) {
        return UNSAFE.getFloat(this.address + offset);
    }

    @Override
    public double getDouble(long offset) {
        return UNSAFE.getDouble(this.address + offset);
    }

    @Override
    public void getByte(long offset, byte[] bytes) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                bytes,
                BYTE_ARRAY_OFFSET,
                bytes.length
        );
    }

    @Override
    public void getShort(long offset, short[] shorts) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                shorts,
                SHORT_ARRAY_OFFSET,
                (long) shorts.length * Short.BYTES
        );
    }

    @Override
    public void getInt(long offset, int[] ints) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                ints,
                INT_ARRAY_OFFSET,
                (long) ints.length * Integer.BYTES
        );
    }

    @Override
    public void getLong(long offset, long[] longs) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                longs,
                LONG_ARRAY_OFFSET,
                (long) longs.length * Long.BYTES
        );
    }

    @Override
    public void getFloat(long offset, float[] floats) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                floats,
                FLOAT_ARRAY_OFFSET,
                (long) floats.length * Float.BYTES
        );
    }

    @Override
    public void getDouble(long offset, double[] doubles) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                doubles,
                DOUBLE_ARRAY_OFFSET,
                (long) doubles.length * Double.BYTES
        );
    }

    @Override
    public void putByte(long offset, byte value) {
        UNSAFE.putByte(this.address + offset, value);
    }

    @Override
    public void putShort(long offset, short value) {
        UNSAFE.putShort(this.address + offset, value);
    }

    @Override
    public void putInt(long offset, int value) {
        UNSAFE.putInt(this.address + offset, value);
    }

    @Override
    public void putLong(long offset, long value) {
        UNSAFE.putLong(this.address + offset, value);
    }

    @Override
    public void putFloat(long offset, float value) {
        UNSAFE.putFloat(this.address + offset, value);
    }

    @Override
    public void putDouble(long offset, double value) {
        UNSAFE.putDouble(this.address + offset, value);
    }

    @Override
    public void putByte(long offset, byte[] bytes) {
        UNSAFE.copyMemory(
                bytes,
                BYTE_ARRAY_OFFSET,
                null,
                this.address + offset,
                bytes.length
        );
    }

    @Override
    public void putShort(long offset, short[] shorts) {
        UNSAFE.copyMemory(
                shorts,
                SHORT_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) shorts.length * Short.BYTES
        );
    }

    @Override
    public void putInt(long offset, int[] ints) {
        UNSAFE.copyMemory(
                ints,
                INT_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) ints.length * Integer.BYTES
        );
    }

    @Override
    public void putLong(long offset, long[] longs) {
        UNSAFE.copyMemory(
                longs,
                LONG_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) longs.length * Long.BYTES
        );
    }

    @Override
    public void putFloat(long offset, float[] floats) {
        UNSAFE.copyMemory(
                floats,
                FLOAT_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) floats.length * Float.BYTES
        );
    }

    @Override
    public void putDouble(long offset, double[] doubles) {
        UNSAFE.copyMemory(
                doubles,
                DOUBLE_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) doubles.length * Double.BYTES
        );
    }

    @Override
    public long size() {
        return this.size;
    }
}
