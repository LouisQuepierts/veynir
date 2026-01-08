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
public interface AnimationFrameBuffer extends AnimationBuffer {

    static AnimationFrameBuffer create(int size) {
        return new Impl(size);
    }

    int getSize();

    @Contract(value = "_ -> new", pure = true)
    default @NotNull AnimationFrameBuffer slice(int offset) {
        return new Sliced(this, offset, this.getSize() - offset);
    }

    @Override
    default void fill(float value) {
        this.fill(value, 0, this.getSize());
    }

    final class Impl implements AnimationFrameBuffer {

        private final AnimationBuffer buffer;

        private Impl(int size) {
            this.buffer = FloatArrayBuffer.create(size);
        }

        @Override
        public int getSize() {
            return this.buffer.getSize();
        }

        @Override
        public float read(int index) {
            return this.buffer.read(index);
        }

        @Override
        public void read(int index, float[] out) {
            this.buffer.read(index, out);
        }

        @Override
        public void read(int index, float[] out, int offset, int length) {
            this.buffer.read(index, out, offset, length);
        }

        @Override
        public void read(int index, @NotNull AnimationWritableTarget dst, int length) {
            this.buffer.read(index, dst, length);
        }

        @Override
        public void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length) {
            this.buffer.read(index, dst, dstOffset, length);
        }

        @Override
        public void write(int index, float value) {
            this.buffer.write(index, value);
        }

        @Override
        public void write(int index, float x, float y) {
            this.buffer.write(index, x, y);
        }

        @Override
        public void write(int index, float x, float y, float z) {
            this.buffer.write(index, x, y, z);
        }

        @Override
        public void write(int index, float x, float y, float z, float w) {
            this.buffer.write(index, x, y, z, w);
        }

        @Override
        public void write(int index, float[] value) {
            this.buffer.write(index, value);
        }

        @Override
        public void write(int index, float[] value, int offset, int length) {
            this.buffer.write(index, value, offset, length);
        }

        @Override
        public void write(int index, @NotNull AnimationReadableTarget src, int length) {
            this.buffer.write(index, src, length);
        }

        @Override
        public void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length) {
            this.buffer.write(index, src, srcIndex, length);
        }

        @Override
        public void fill(float value, int offset, int length) {
            this.buffer.fill(value, offset, length);
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
            this.validate(offset);
            return new Sliced(
                    this.delegate,
                    this.offset + offset,
                    this.size - offset
            );
        }

        @Override
        public float read(int index) {
            this.validate(index);
            return this.delegate.read(this.offset + index);
        }

        @Override
        public void read(int index, float[] floats) {
            this.validate(index, floats.length);
            this.delegate.read(this.offset + index, floats);
        }

        @Override
        public void read(int index, float[] out, int offset, int length) {

            this.delegate.read(this.offset + index, out, offset, length);
        }

        @Override
        public void read(int index, @NotNull AnimationWritableTarget dst, int length) {
            this.validate(index, length);
            this.delegate.read(this.offset + index, dst, length);
        }

        @Override
        public void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length) {
            this.validate(index, length);
            this.delegate.read(this.offset + index, dst, dstOffset, length);
        }

        @Override
        public void write(int index, float value) {
            this.validate(index);
            this.delegate.write(this.offset + index, value);
        }

        @Override
        public void write(int index, float x, float y) {
            this.validate(index);
            this.delegate.write(this.offset + index, x, y);
        }

        @Override
        public void write(int index, float x, float y, float z) {
            this.validate(index);
            this.delegate.write(this.offset + index, x, y, z);
        }

        @Override
        public void write(int index, float x, float y, float z, float w) {
            this.validate(index);
            this.delegate.write(this.offset + index, x, y, z, w);
        }

        @Override
        public void write(int index, float[] value) {
            this.validate(index);
            this.delegate.write(this.offset + index, value);
        }

        @Override
        public void write(int index, float[] value, int offset, int length) {
            this.validate(index, length);
            this.delegate.write(this.offset + index, value, offset, length);
        }

        @Override
        public void write(int index, @NotNull AnimationReadableTarget src, int length) {
            this.validate(index, length);
            this.delegate.write(this.offset + index, src, length);
        }

        @Override
        public void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length) {
            this.validate(index, length);
            this.delegate.write(this.offset + index, src, srcIndex, length);
        }

        @Override
        public void fill(float value, int offset, int length) {
            validate(offset, length);
            this.delegate.fill(value, this.offset + offset, length);
        }

        private void validate(int index) {
            if (index >= this.size) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
            }
        }

        private void validate(int index, int length) {
            if (index + length >= this.size) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
            }
        }
    }
}
