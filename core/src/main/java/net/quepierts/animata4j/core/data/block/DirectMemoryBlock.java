package net.quepierts.animata4j.core.data.block;

import lombok.Getter;
import net.quepierts.animata4j.core.misc.UnsafeUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static net.quepierts.animata4j.core.misc.UnsafeUtil.*;

@Getter
public class DirectMemoryBlock implements MemoryBlock {
    public static MemoryBlock create() {
        return new DirectMemoryBlock(MemoryBlock.DEFAULT_SIZE);
    }

    public static MemoryBlock create(long size) {
        return new DirectMemoryBlock(size);
    }

    private long size;
    private long address;
    private boolean freed = false;

    private DirectMemoryBlock(long size) {
        this.size = size;
        this.address = UnsafeUtil.malloc(size);
    }

    private DirectMemoryBlock(long size, long address) {
        this.size = size;
        this.address = address;
    }

    @Override
    public void free() {
        UnsafeUtil.free(this.address);
        this.freed = true;
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
    public int getInteger(long offset) {
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
    public void getByte(final long offset, final byte[] bytes, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                bytes,
                BYTE_ARRAY_OFFSET + arrayOffset,
                length
        );
    }

    @Override
    public void getShort(long offset, short[] shorts, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                shorts,
                SHORT_ARRAY_OFFSET + (long) arrayOffset * Short.BYTES,
                (long) length * Short.BYTES
        );
    }

    @Override
    public void getInteger(long offset, int[] ints, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                ints,
                INT_ARRAY_OFFSET + (long) arrayOffset * Integer.BYTES,
                (long) length * Integer.BYTES
        );
    }

    @Override
    public void getLong(long offset, long[] longs, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                longs,
                LONG_ARRAY_OFFSET + (long) arrayOffset * Long.BYTES,
                (long) length * Long.BYTES
        );
    }

    @Override
    public void getFloat(long offset, float[] floats, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                floats,
                FLOAT_ARRAY_OFFSET + (long) arrayOffset * Float.BYTES,
                (long) length * Float.BYTES
        );
    }

    @Override
    public void getDouble(long offset, double[] doubles, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                null,
                this.address + offset,
                doubles,
                DOUBLE_ARRAY_OFFSET + (long) arrayOffset * Double.BYTES,
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
    public void putInteger(long offset, int value) {
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
    public void putByte(long offset, byte[] bytes, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                bytes,
                BYTE_ARRAY_OFFSET + arrayOffset,
                null,
                this.address + offset,
                length
        );
    }

    @Override
    public void putShort(long offset, short[] shorts, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                shorts,
                SHORT_ARRAY_OFFSET + (long) arrayOffset * Short.BYTES,
                null,
                this.address + offset,
                (long) length * Short.BYTES
        );
    }

    @Override
    public void putInteger(long offset, int[] ints, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                ints,
                INT_ARRAY_OFFSET + (long) arrayOffset * Integer.BYTES,
                null,
                this.address + offset,
                (long) length * Integer.BYTES
        );
    }

    @Override
    public void putLong(long offset, long[] longs, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                longs,
                LONG_ARRAY_OFFSET + (long) arrayOffset * Long.BYTES,
                null,
                this.address + offset,
                (long) length * Long.BYTES
        );
    }

    @Override
    public void putFloat(long offset, float[] floats, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                floats,
                FLOAT_ARRAY_OFFSET + (long) arrayOffset * Float.BYTES,
                null,
                this.address + offset,
                (long) length * Float.BYTES
        );
    }

    @Override
    public void putDouble(long offset, double[] doubles, final int arrayOffset, final int length) {
        UNSAFE.copyMemory(
                doubles,
                DOUBLE_ARRAY_OFFSET + (long) arrayOffset * Double.BYTES,
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
    public void expand(long size) {
        final long newSize = this.size + size;
        final long newAddress = UNSAFE.allocateMemory(newSize);
        UNSAFE.copyMemory(this.address, newAddress, this.size);
        UNSAFE.setMemory(newAddress + this.size, size, (byte) 0);
        UNSAFE.freeMemory(this.address);
        this.address = newAddress;
        this.size = newSize;
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
            this.getByte(i, buffer, 0, length);

            for (int j = 0; j < length; j++) {
                builder.append(String.format("%02X ", buffer[j]));
            }

            if (length == 16) {
                builder.append('\n');
            }
        }

        return builder.toString();
    }

    @Override
    public MemoryBlock slice(long offset) {
        if (offset >= this.size) {
            throw new IndexOutOfBoundsException("Offset " + offset + " is out of bounds");
        }
        return new Sliced(
                this.size - offset,
                this.address + offset,
                this
        );
    }

    private static class Sliced extends DirectMemoryBlock {
        private final @NotNull DirectMemoryBlock parent;

        private Sliced(long size, long address, @NotNull DirectMemoryBlock parent) {
            super(size, address);
            this.parent = parent;
        }

        @Override
        public void free() {

        }

        @Override
        public boolean isFreed() {
            return this.parent.isFreed();
        }

        @Override
        @Contract(value = "_ -> new", pure = true)
        public MemoryBlock slice(long offset) {
            if (offset >= this.getSize()) {
                throw new IndexOutOfBoundsException("Offset " + offset + " is out of bounds");
            }
            return new Sliced(
                    this.getSize() - offset,
                    this.getAddress() + offset,
                    this.parent
            );
        }
    }
}
