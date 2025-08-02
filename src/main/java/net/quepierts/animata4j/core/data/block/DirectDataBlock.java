package net.quepierts.animata4j.core.data.block;

import sun.misc.Unsafe;

import java.lang.foreign.MemorySegment;

public class DirectDataBlock implements DataBlock {
    private static final Unsafe UNSAFE = Unsafe.getUnsafe();

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
    public boolean getBoolean(long offset) {
        return UNSAFE.getByte(offset) != 0;
    }

    @Override
    public void get(long offset, MemorySegment segment) {
        UNSAFE.copyMemory(this.address + offset, segment.address(), segment.byteSize());
    }

    @Override
    public void put(long offset, byte value) {
        UNSAFE.putByte(this.address + offset, value);
    }

    @Override
    public void put(long offset, short value) {
        UNSAFE.putShort(this.address + offset, value);
    }

    @Override
    public void put(long offset, int value) {
        UNSAFE.putInt(this.address + offset, value);
    }

    @Override
    public void put(long offset, long value) {
        UNSAFE.putLong(this.address + offset, value);
    }

    @Override
    public void put(long offset, float value) {
        UNSAFE.putFloat(this.address + offset, value);
    }

    @Override
    public void put(long offset, double value) {
        UNSAFE.putDouble(this.address + offset, value);
    }

    @Override
    public void put(long offset, boolean value) {
        UNSAFE.putByte(this.address + offset, (byte) (value ? 1 : 0));
    }

    @Override
    public void put(long offset, MemorySegment segment) {
        UNSAFE.copyMemory(segment.address(), this.address + offset, segment.byteSize());
    }

    @Override
    public long size() {
        return this.size;
    }
}
