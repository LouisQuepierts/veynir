package net.quepierts.animata4j.core.data.block;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

@SuppressWarnings("all")
public class DelegatedDataBlock implements DataBlock {
    public static DelegatedDataBlock create() {
        return new DelegatedDataBlock(DEFAULT_SIZE);
    }

    public static DelegatedDataBlock create(long size) {
        return new DelegatedDataBlock(size);
    }

    private final Arena arena;
    private final MemorySegment segment;

    private DelegatedDataBlock(long size) {
        this.arena = Arena.ofShared();
        this.segment = this.arena.allocate(size);
    }

    @Override
    public void free() {
        this.arena.close();
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

    public void getBatched(final long offset, final MemorySegment segment) {
        segment.copyFrom(this.segment.asSlice(offset, segment.byteSize()));
    }

    @Override
    public void getByte(final long offset, final byte[] bytes) {
        final int length = bytes.length;
        if (length == 0) return;

        if (length == 1) {
            bytes[0] = this.getByte(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.getBatched(offset, MemorySegment.ofArray(bytes));
        } else {
            long off = offset;
            for (int i = 0; i < length; i++) {
                bytes[i] = this.getByte(off++);
            }
        }
    }

    @Override
    public void getShort(final long offset, final short[] shorts) {
        final int length = shorts.length;
        if (length == 0) return;

        if (length == 1) {
            shorts[0] = this.getShort(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.getBatched(offset, MemorySegment.ofArray(shorts));
        } else {
            final long size = ValueLayout.JAVA_SHORT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                shorts[i] = this.getShort(off);
                off += size;
            }
        }
    }

    @Override
    public void getInt(final long offset, final int[] ints) {
        final int length = ints.length;
        if (length == 0) return;

        if (length == 1) {
            ints[0] = this.getInt(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.getBatched(offset, MemorySegment.ofArray(ints));
        } else {
            final long size = ValueLayout.JAVA_INT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                ints[i] = this.getInt(off);
                off += size;
            }
        }
    }

    @Override
    public void getLong(final long offset, final long[] longs) {
        final int length = longs.length;
        if (length == 0) return;

        if (length == 1) {
            longs[0] = this.getLong(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.getBatched(offset, MemorySegment.ofArray(longs));
        } else {
            final long size = ValueLayout.JAVA_LONG.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                longs[i] = this.getLong(off);
                off += size;
            }
        }
    }

    @Override
    public void getFloat(final long offset, final float[] floats) {
        final int length = floats.length;
        if (length == 0) return;

        if (length == 1) {
            floats[0] = this.getFloat(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.getBatched(offset, MemorySegment.ofArray(floats));
        } else {
            final long size = ValueLayout.JAVA_FLOAT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                floats[i] = this.getFloat(off);
                off += size;
            }
        }
    }

    @Override
    public void getDouble(final long offset, final double[] doubles) {
        final int length = doubles.length;
        if (length == 0) return;

        if (length == 1) {
            doubles[0] = this.getDouble(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.getBatched(offset, MemorySegment.ofArray(doubles));
        } else {
            final long size = ValueLayout.JAVA_DOUBLE.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                doubles[i] = this.getDouble(off);
                off += size;
            }
        }
    }

    @Override
    public void putByte(final long offset, final byte value) {
        this.segment.set(ValueLayout.JAVA_BYTE, offset, value);
    }

    @Override
    public void putShort(final long offset, final short value) {
        this.segment.set(ValueLayout.JAVA_SHORT, offset, value);
    }

    @Override
    public void putInt(final long offset, final int value) {
        this.segment.set(ValueLayout.JAVA_INT, offset, value);
    }

    @Override
    public void putLong(final long offset, final long value) {
        this.segment.set(ValueLayout.JAVA_LONG, offset, value);
    }

    @Override
    public void putFloat(final long offset, final float value) {
        this.segment.set(ValueLayout.JAVA_FLOAT, offset, value);
    }

    @Override
    public void putDouble(final long offset, final double value) {
        this.segment.set(ValueLayout.JAVA_DOUBLE, offset, value);
    }

    @Override
    public void putByte(final long offset, final byte[] bytes) {
        final int length = bytes.length;
        if (length == 0) return;

        if (length == 1) {
            this.putByte(offset, bytes[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.putBatched(offset, MemorySegment.ofArray(bytes));
        } else {
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.putByte(off++, bytes[i]);
            }
        }
    }

    @Override
    public void putShort(final long offset, final short[] shorts) {
        final int length = shorts.length;
        if (length == 0) return;

        if (length == 1) {
            this.putShort(offset, shorts[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.putBatched(offset, MemorySegment.ofArray(shorts));
        } else {
            final long size = ValueLayout.JAVA_SHORT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.putShort(off, shorts[i]);
                off += size;
            }
        }
    }

    @Override
    public void putInt(final long offset, final int[] ints) {
        final int length = ints.length;
        if (length == 0) return;

        if (length == 1) {
            this.putInt(offset, ints[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.putBatched(offset, MemorySegment.ofArray(ints));
        } else {
            final long size = ValueLayout.JAVA_INT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.putInt(off, ints[i]);
                off += size;
            }
        }
    }

    @Override
    public void putLong(final long offset, final long[] longs) {
        final int length = longs.length;
        if (length == 0) return;

        if (length == 1) {
            this.putLong(offset, longs[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.putBatched(offset, MemorySegment.ofArray(longs));
        } else {
            final long size = ValueLayout.JAVA_LONG.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.putLong(off, longs[i]);
                off += size;
            }
        }
    }

    @Override
    public void putFloat(final long offset, final float[] floats) {
        final int length = floats.length;
        if (length == 0) return;

        if (length == 1) {
            this.putFloat(offset, floats[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.putBatched(offset, MemorySegment.ofArray(floats));
        } else {
            final long size = ValueLayout.JAVA_FLOAT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.putFloat(off, floats[i]);
                off += size;
            }
        }
    }

    @Override
    public void putDouble(final long offset, final double[] doubles) {
        final int length = doubles.length;
        if (length == 0) return;

        if (length == 1) {
            this.putDouble(offset, doubles[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.putBatched(offset, MemorySegment.ofArray(doubles));
        } else {
            final long size = ValueLayout.JAVA_DOUBLE.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.putDouble(off, doubles[i]);
                off += size;
            }
        }
    }

    public void putBatched(long offset, MemorySegment segment) {
        this.segment.asSlice(offset, segment.byteSize())
                .copyFrom(segment);
    }

    @Override
    public long size() {
        return this.segment.byteSize();
    }
}
