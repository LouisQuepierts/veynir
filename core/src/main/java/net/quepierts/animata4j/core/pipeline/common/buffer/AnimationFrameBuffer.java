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
    int getSize();

    @Contract(value = "_ -> new", pure = true)
    default @NotNull AnimationFrameBuffer slice(int offset) {
        return new Sliced(this, offset, this.getSize() - offset);
    }

    @Override
    default double getDouble(int index) {
        return this.getFloat(index);
    }

    @Override
    default void getDouble(int index, double[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getDouble(index + i);
        }
    }

    @Override
    default byte getByte(int index) {
        return (byte) this.getFloat(index);
    }

    @Override
    default void getByte(int index, byte[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getByte(index + i);
        }
    }

    @Override
    default int getInteger(int index) {
        return (int) this.getFloat(index);
    }

    @Override
    default void getInteger(int index, int[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getInteger(index + i);
        }
    }

    @Override
    default long getLong(int index) {
        return (long) this.getFloat(index);
    }

    @Override
    default void getLong(int index, long[] out) {
        for (int i = 0; i < out.length; i++) {
            out[i] = this.getLong(index + i);
        }
    }

    @Override
    default void setDouble(int index, double value) {
        this.setFloat(index, (float) value);
    }

    @Override
    default void setDouble(int index, double[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setDouble(index + i, value[i]);
        }
    }

    @Override
    default void setByte(int index, byte value) {
        this.setFloat(index, value);
    }

    @Override
    default void setByte(int index, byte[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setByte(index + i, value[i]);
        }
    }

    @Override
    default void setInteger(int index, int value) {
        this.setFloat(index, value);
    }

    @Override
    default void setInteger(int index, int[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setInteger(index + i, value[i]);
        }
    }

    @Override
    default void setLong(int index, long value) {
        this.setFloat(index, value);
    }

    @Override
    default void setLong(int index, long[] value) {
        for (int i = 0; i < value.length; i++) {
            this.setLong(index + i, value[i]);
        }
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
        public float getFloat(int index) {
            return this.delegate.getFloat(this.offset + index);
        }

        @Override
        public void getFloat(int index, float[] floats) {
            this.delegate.getFloat(this.offset + index, floats);
        }

        @Override
        public void setFloat(int index, float value) {
            this.delegate.setFloat(this.offset + index, value);
        }

        @Override
        public void setFloat(int index, float[] value) {
            this.delegate.setFloat(this.offset + index, value);
        }
    }
}
