package net.quepierts.animata4j.core.data.block;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class DelegatedDataBlock implements DataBlock {
    public static DelegatedDataBlock create() {
        return new DelegatedDataBlock(DEFAULT_SIZE);
    }

    public static DelegatedDataBlock create(long size) {
        return new DelegatedDataBlock(size);
    }

    private final MemorySegment segment;

    private DelegatedDataBlock(long size) {
        try (Arena arena = Arena.ofConfined()) {
            this.segment = arena.allocate(size);
        }
    }

    @Override
    public void free() {
        this.segment.unload();
    }

    @Override
    public byte getByte(final long offset) {
        return this.segment.get(ValueLayout.JAVA_BYTE, offset);
    }

    @Override
    public short getShort(final long offset) {
        return this.segment.get(ValueLayout.JAVA_SHORT, offset);
    }

    @Override
    public int getInt(final long offset) {
        return this.segment.get(ValueLayout.JAVA_INT, offset);
    }

    @Override
    public long getLong(final long offset) {
        return this.segment.get(ValueLayout.JAVA_LONG, offset);
    }

    @Override
    public float getFloat(final long offset) {
        return this.segment.get(ValueLayout.JAVA_FLOAT, offset);
    }

    @Override
    public double getDouble(final long offset) {
        return this.segment.get(ValueLayout.JAVA_DOUBLE, offset);
    }

    @Override
    public boolean getBoolean(final long offset) {
        return this.segment.get(ValueLayout.JAVA_BOOLEAN, offset);
    }

    @Override
    public void get(final long offset, final MemorySegment segment) {
        segment.copyFrom(this.segment.asSlice(offset, segment.byteSize()));
    }

    @Override
    public void put(final long offset, final byte value) {
        this.segment.set(ValueLayout.JAVA_BYTE, offset, value);
    }

    @Override
    public void put(final long offset, final short value) {
        this.segment.set(ValueLayout.JAVA_SHORT, offset, value);
    }

    @Override
    public void put(final long offset, final int value) {
        this.segment.set(ValueLayout.JAVA_INT, offset, value);
    }

    @Override
    public void put(final long offset, final long value) {
        this.segment.set(ValueLayout.JAVA_LONG, offset, value);
    }

    @Override
    public void put(final long offset, final float value) {
        this.segment.set(ValueLayout.JAVA_FLOAT, offset, value);
    }

    @Override
    public void put(final long offset, final double value) {
        this.segment.set(ValueLayout.JAVA_DOUBLE, offset, value);
    }

    @Override
    public void put(final long offset, final boolean value) {
        this.segment.set(ValueLayout.JAVA_BOOLEAN, offset, value);
    }

    @Override
    public void put(long offset, MemorySegment segment) {
        this.segment.asSlice(offset, segment.byteSize())
                .copyFrom(segment);
    }

    @Override
    public long size() {
        return this.segment.byteSize();
    }
}
