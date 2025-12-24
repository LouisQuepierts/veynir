package net.quepierts.animata4j.core.pipeline.common.buffer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationReadableTarget;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A buffer for animation data in Standard Animation Pipeline.
 * The basic data type is float.
 */
@SuppressWarnings("unused")
public interface AnimationFrameBuffer extends AnimationReadableTarget, AnimationWritableTarget {

    static AnimationFrameBuffer create(int size) {
        return FloatArrayBuffer.create(size);
    }

    int getSize();

    @Contract(value = "_ -> new", pure = true)
    default @NotNull AnimationFrameBuffer slice(int offset) {
        return new Sliced(this, offset, this.getSize() - offset);
    }

    @Override
    default double getDouble(int index) {
        return this.read(index);
    }

    @Override
    default void getDouble(int index, double[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getDouble(index + i);
        }
    }

    @Override
    default byte getByte(int index) {
        return (byte) this.read(index);
    }

    @Override
    default void getByte(int index, byte[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getByte(index + i);
        }
    }

    @Override
    default int getInteger(int index) {
        return (int) this.read(index);
    }

    @Override
    default void getInteger(int index, int[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getInteger(index + i);
        }
    }

    @Override
    default long getLong(int index) {
        return (long) this.read(index);
    }

    @Override
    default void getLong(int index, long[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getLong(index + i);
        }
    }

    @Override
    default void setDouble(int index, double value) {
        this.write(index, (float) value);
    }

    @Override
    default void setDouble(int index, double[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setDouble(index + i, value[i]);
        }
    }

    @Override
    default void setByte(int index, byte value) {
        this.write(index, value);
    }

    @Override
    default void setByte(int index, byte[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setByte(index + i, value[i]);
        }
    }

    @Override
    default void setInteger(int index, int value) {
        this.write(index, value);
    }

    @Override
    default void setInteger(int index, int[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setInteger(index + i, value[i]);
        }
    }

    @Override
    default void setLong(int index, long value) {
        this.write(index, value);
    }

    @Override
    default void setLong(int index, long[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setLong(index + i, value[i]);
        }
    }

    @Override
    default void fill(float value) {
        this.fill(value, 0, this.getSize());
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    final class Sliced implements AnimationFrameBuffer {
        private final AnimationFrameBuffer delegate;
        private final int offset;

        @Getter
        private final int size;

        @Override
        public @NotNull AnimationFrameBuffer slice(int offset) {
            if (offset >= this.size) {
                throw new IndexOutOfBoundsException("Offset " + offset + " is out of bounds");
            }
            return new Sliced(
                    this.delegate,
                    this.offset + offset,
                    this.size - offset
            );
        }

        @Override
        public float read(int index) {
            return this.delegate.read(this.offset + index);
        }

        @Override
        public void read(int index, float[] floats) {
            this.delegate.read(this.offset + index, floats);
        }

        @Override
        public void write(int index, float value) {
            this.delegate.write(this.offset + index, value);
        }

        @Override
        public void write(int index, float x, float y) {
            this.delegate.write(index, x, y);
        }

        @Override
        public void write(int index, float x, float y, float z) {
            this.delegate.write(index, x, y, z);
        }

        @Override
        public void write(int index, float x, float y, float z, float w) {
            this.delegate.write(index, x, y, z, w);
        }

        @Override
        public void write(int index, float[] value) {
            this.delegate.write(this.offset + index, value);
        }

        @Override
        public void write(int index, float[] value, int offset, int length) {
            this.delegate.write(this.offset + index, value, offset, length);
        }

        @Override
        public void fill(float value, int offset, int length) {
            this.delegate.fill(value, this.offset + offset, length);
        }
    }
}
