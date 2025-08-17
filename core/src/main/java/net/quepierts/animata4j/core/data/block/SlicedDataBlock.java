package net.quepierts.animata4j.core.data.block;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class SlicedDataBlock implements DataBlock {
    private final DataBlock delegate;
    private final long offset;

    @Override
    public void free() {
        throw new UnsupportedOperationException("Cannot free a sliced data block");
    }

    @Override
    public long size() {
        return this.delegate.size() - this.offset;
    }

    @Override
    public void expand(long size) {
        throw new UnsupportedOperationException("Cannot expand a sliced data block");
    }

    @Override
    public boolean isFreed() {
        return this.delegate.isFreed();
    }

    @Override
    public void putByte(long offset, byte value) {
        this.delegate.putByte(this.offset + offset, value);
    }

    @Override
    public void putByte(long offset, byte[] bytes, int arrayOffset, int length) {
        this.delegate.putByte(this.offset + offset, bytes, arrayOffset, length);
    }

    @Override
    public byte getByte(long offset) {
        return this.delegate.getByte(this.offset + offset);
    }

    @Override
    public void getByte(long offset, byte[] bytes, int arrayOffset, int length) {
        this.delegate.getByte(this.offset + offset, bytes, arrayOffset, length);
    }

    @Override
    public void putDouble(long offset, double value) {
        this.delegate.putDouble(this.offset + offset, value);
    }

    @Override
    public void putDouble(long offset, double[] doubles, int arrayOffset, int length) {
        this.delegate.putDouble(this.offset + offset, doubles, arrayOffset, length);
    }

    @Override
    public double getDouble(long offset) {
        return this.delegate.getDouble(this.offset + offset);
    }

    @Override
    public void getDouble(long offset, double[] doubles, int arrayOffset, int length) {
        this.delegate.getDouble(this.offset + offset, doubles, arrayOffset, length);
    }

    @Override
    public void putFloat(long offset, float value) {
        this.delegate.putFloat(this.offset + offset, value);
    }

    @Override
    public void putFloat(long offset, float[] floats, int arrayOffset, int length) {
        this.delegate.putFloat(this.offset + offset, floats, arrayOffset, length);
    }

    @Override
    public float getFloat(long offset) {
        return this.delegate.getFloat(this.offset + offset);
    }

    @Override
    public void getFloat(long offset, float[] floats, int arrayOffset, int length) {
        this.delegate.getFloat(this.offset + offset, floats, arrayOffset, length);
    }

    @Override
    public void putInteger(long offset, int value) {
        this.delegate.putInteger(this.offset + offset, value);
    }

    @Override
    public void putInteger(long offset, int[] ints, int arrayOffset, int length) {
        this.delegate.putInteger(this.offset + offset, ints, arrayOffset, length);
    }

    @Override
    public int getInteger(long offset) {
        return this.delegate.getInteger(this.offset + offset);
    }

    @Override
    public void getInteger(long offset, int[] ints, int arrayOffset, int length) {
        this.delegate.getInteger(this.offset + offset, ints, arrayOffset, length);
    }

    @Override
    public void putLong(long offset, long value) {
        this.delegate.putLong(this.offset + offset, value);
    }

    @Override
    public void putLong(long offset, long[] longs, int arrayOffset, int length) {
        this.delegate.putLong(this.offset + offset, longs, arrayOffset, length);
    }

    @Override
    public long getLong(long offset) {
        return this.delegate.getLong(this.offset + offset);
    }

    @Override
    public void getLong(long offset, long[] longs, int arrayOffset, int length) {
        this.delegate.getLong(this.offset + offset, longs, arrayOffset, length);
    }

    @Override
    public void putShort(long offset, short value) {
        this.delegate.putShort(this.offset + offset, value);
    }

    @Override
    public void putShort(long offset, short[] shorts, int arrayOffset, int length) {
        this.delegate.putShort(this.offset + offset, shorts, arrayOffset, length);
    }

    @Override
    public short getShort(long offset) {
        return this.delegate.getShort(this.offset + offset);
    }

    @Override
    public void getShort(long offset, short[] shorts, int arrayOffset, int length) {
        this.delegate.getShort(this.offset + offset, shorts, arrayOffset, length);
    }

    @Override
    public DataBlock slice(long offset) {
        return new SlicedDataBlock(this.delegate, this.offset + offset);
    }
}
