package net.quepierts.animata4j.core.data.block;

import net.quepierts.animata4j.core.misc.UnsafeUtil;

import static net.quepierts.animata4j.core.misc.UnsafeUtil.*;

public class DirectDataBlock implements DataBlock {
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
        this.address = UnsafeUtil.malloc(size);
    }

    @Override
    public void free() {
        UnsafeUtil.free(this.address);
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
    public void getByte(final long offset, final byte[] bytes, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                bytes,
                BYTE_ARRAY_OFFSET,
                length
        );
    }

    @Override
    public void getShort(long offset, short[] shorts, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                shorts,
                SHORT_ARRAY_OFFSET,
                (long) length * Short.BYTES
        );
    }

    @Override
    public void getInt(long offset, int[] ints, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                ints,
                INT_ARRAY_OFFSET,
                (long) length * Integer.BYTES
        );
    }

    @Override
    public void getLong(long offset, long[] longs, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                longs,
                LONG_ARRAY_OFFSET,
                (long) length * Long.BYTES
        );
    }

    @Override
    public void getFloat(long offset, float[] floats, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                floats,
                FLOAT_ARRAY_OFFSET,
                (long) length * Float.BYTES
        );
    }

    @Override
    public void getDouble(long offset, double[] doubles, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                doubles,
                DOUBLE_ARRAY_OFFSET,
                (long) length * Double.BYTES
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
    public void putByte(long offset, byte[] bytes, final int length) {
        UNSAFE.copyMemory(
                bytes,
                BYTE_ARRAY_OFFSET,
                null,
                this.address + offset,
                length
        );
    }

    @Override
    public void putShort(long offset, short[] shorts, final int length) {
        UNSAFE.copyMemory(
                shorts,
                SHORT_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) length * Short.BYTES
        );
    }

    @Override
    public void putInt(long offset, int[] ints, final int length) {
        UNSAFE.copyMemory(
                ints,
                INT_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) length * Integer.BYTES
        );
    }

    @Override
    public void putLong(long offset, long[] longs, final int length) {
        UNSAFE.copyMemory(
                longs,
                LONG_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) length * Long.BYTES
        );
    }

    @Override
    public void putFloat(long offset, float[] floats, final int length) {
        UNSAFE.copyMemory(
                floats,
                FLOAT_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) length * Float.BYTES
        );
    }

    @Override
    public void putDouble(long offset, double[] doubles, final int length) {
        UNSAFE.copyMemory(
                doubles,
                DOUBLE_ARRAY_OFFSET,
                null,
                this.address + offset,
                (long) length * Double.BYTES
        );
    }

    @Override
    public long size() {
        return this.size;
    }

    @Override
    public String toString() {
        // print memory by 16 bytes / line
        StringBuilder builder = new StringBuilder("Memory in address [")
                .append(this.address)
                .append("]:\n");

        final byte[] buffer = new byte[16];
        for (int i = 0; i < this.size; i += 16) {
            int length = (int) Math.min(16, this.size - i);
            this.getByte(i, buffer, length);

            for (int j = 0; j < length; j++) {
                builder.append(String.format("%02X ", buffer[j]));
            }

            if (length == 16) {
                builder.append('\n');
            }
        }

        return builder.toString();
    }
}
