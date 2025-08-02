package net.quepierts.animata4j.core.data.block;

import org.jetbrains.annotations.Contract;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

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

    boolean getBoolean(final long offset);

    default boolean getBoolean(final long offset, final int shift) {
        if (shift < 0 || shift > 7) {
            throw new IllegalArgumentException("Shift must be between 0 and 7");
        }

        byte b = this.getByte(offset);
        return (b & (1 << shift)) != 0;
    }

    void get(final long offset, final MemorySegment segment);

    default void get(final long offset, final byte[] bytes) {
        final int length = bytes.length;
        if (length == 0) return;

        if (length == 1) {
            bytes[0] = this.getByte(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.get(offset, MemorySegment.ofArray(bytes));
        } else {
            long off = offset;
            for (int i = 0; i < length; i++) {
                bytes[i] = this.getByte(off++);
            }
        }
    }


    default void get(final long offset, final short[] shorts) {
        final int length = shorts.length;
        if (length == 0) return;

        if (length == 1) {
            shorts[0] = this.getShort(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.get(offset, MemorySegment.ofArray(shorts));
        } else {
            final long size = ValueLayout.JAVA_SHORT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                shorts[i] = this.getShort(off);
                off += size;
            }
        }
    }

    default void get(final long offset, final int[] ints) {
        final int length = ints.length;
        if (length == 0) return;

        if (length == 1) {
            ints[0] = this.getInt(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.get(offset, MemorySegment.ofArray(ints));
        } else {
            final long size = ValueLayout.JAVA_INT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                ints[i] = this.getInt(off);
                off += size;
            }
        }
    }

    default void get(final long offset, final long[] longs) {
        final int length = longs.length;
        if (length == 0) return;

        if (length == 1) {
            longs[0] = this.getLong(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.get(offset, MemorySegment.ofArray(longs));
        } else {
            final long size = ValueLayout.JAVA_LONG.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                longs[i] = this.getLong(off);
                off += size;
            }
        }
    }

    default void get(final long offset, final float[] floats) {
        final int length = floats.length;
        if (length == 0) return;

        if (length == 1) {
            floats[0] = this.getFloat(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.get(offset, MemorySegment.ofArray(floats));
        } else {
            final long size = ValueLayout.JAVA_FLOAT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                floats[i] = this.getFloat(off);
                off += size;
            }
        }
    }

    default void get(final long offset, final double[] doubles) {
        final int length = doubles.length;
        if (length == 0) return;

        if (length == 1) {
            doubles[0] = this.getDouble(offset);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.get(offset, MemorySegment.ofArray(doubles));
        } else {
            final long size = ValueLayout.JAVA_DOUBLE.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                doubles[i] = this.getDouble(off);
                off += size;
            }
        }
    }

    default void get(final long offset, final boolean[] booleans) {
        final int length = booleans.length;
        if (length == 0) return;

        final int byteCount = (length + 7) / 8;
        final byte[] bytes = new byte[byteCount];
        this.get(offset, bytes);

        for (int i = 0; i < byteCount; i++) {
            final byte b = bytes[i];
            for (int j = 0; j < 8; j++) {
                int index = i * 8 + j;
                if (index < length) {
                    booleans[index] = (b & (1 << j)) != 0;
                }
            }
        }
    }


    void put(final long offset, final byte value);

    void put(final long offset, final short value);

    void put(final long offset, final int value);

    void put(final long offset, final long value);

    void put(final long offset, final float value);

    void put(final long offset, final double value);

    void put(final long offset, final boolean value);

    void put(final long offset, final MemorySegment segment);

    default void put(final long offset, final byte[] bytes) {
        final int length = bytes.length;
        if (length == 0) return;

        if (length == 1) {
            this.put(offset, bytes[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.put(offset, MemorySegment.ofArray(bytes));
        } else {
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.put(off++, bytes[i]);
            }
        }
    }

    default void put(final long offset, final short[] shorts) {
        final int length = shorts.length;
        if (length == 0) return;

        if (length == 1) {
            this.put(offset, shorts[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.put(offset, MemorySegment.ofArray(shorts));
        } else {
            final long size = ValueLayout.JAVA_SHORT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.put(off, shorts[i]);
                off += size;
            }
        }
    }

    default void put(final long offset, final int[] ints) {
        final int length = ints.length;
        if (length == 0) return;

        if (length == 1) {
            this.put(offset, ints[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.put(offset, MemorySegment.ofArray(ints));
        } else {
            final long size = ValueLayout.JAVA_INT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.put(off, ints[i]);
                off += size;
            }
        }
    }

    default void put(final long offset, final long[] longs) {
        final int length = longs.length;
        if (length == 0) return;

        if (length == 1) {
            this.put(offset, longs[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.put(offset, MemorySegment.ofArray(longs));
        } else {
            final long size = ValueLayout.JAVA_LONG.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.put(off, longs[i]);
                off += size;
            }
        }
    }

    default void put(final long offset, final float[] floats) {
        final int length = floats.length;
        if (length == 0) return;

        if (length == 1) {
            this.put(offset, floats[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.put(offset, MemorySegment.ofArray(floats));
        } else {
            final long size = ValueLayout.JAVA_FLOAT.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.put(off, floats[i]);
                off += size;
            }
        }
    }

    default void put(final long offset, final double[] doubles) {
        final int length = doubles.length;
        if (length == 0) return;

        if (length == 1) {
            this.put(offset, doubles[0]);
            return;
        }

        if (length > THRESHOLD_USE_SEG) {
            this.put(offset, MemorySegment.ofArray(doubles));
        } else {
            final long size = ValueLayout.JAVA_DOUBLE.byteSize();
            long off = offset;
            for (int i = 0; i < length; i++) {
                this.put(off, doubles[i]);
                off += size;
            }
        }
    }

    default void put(final long offset, final int shift, final boolean value) {
        if (shift < 0 || shift > 7) {
            throw new IllegalArgumentException("Shift must be between 0 and 7");
        }

        byte b = this.getByte(offset);
        if (value) {
            b |= (byte) (1 << shift);
        } else {
            b &= (byte) ~(1 << shift);
        }
        this.put(offset, b);
    }

    long size();
}
