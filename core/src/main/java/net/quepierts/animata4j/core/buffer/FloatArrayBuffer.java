package net.quepierts.animata4j.core.buffer;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class FloatArrayBuffer implements AnimationBuffer {

    /**
     * Create a new FloatArrayBuffer with a given size.
     * @param size the size of the buffer.
     * @return a new FloatArrayBuffer.
     * */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull FloatArrayBuffer create(int size) {
        return new FloatArrayBuffer(size);
    }

    /**
     * Create a new FloatArrayBuffer from an existing array.
     * It will directly use the array, instead of copying it.
     * @param array the array to create the buffer from.
     * @return a new FloatArrayBuffer.
     * */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull FloatArrayBuffer of(final float @NotNull [] array) {
        return new FloatArrayBuffer(array);
    }

    private final float[] buffer;

    @Getter
    private final int size;

    private FloatArrayBuffer(int size) {
        this.buffer = new float[size];
        this.size = size;
    }

    private FloatArrayBuffer(final float[] array) {
        this.buffer = array;
        this.size = array.length;
    }

    @Override
    public float read(int index) {
        return this.buffer[index];
    }

    @Override
    public void read(int index, float[] out) {
        final int require = out.length;
        System.arraycopy(this.buffer, index, out, 0, require);
    }

    @Override
    public void read(int index, float[] out, int offset, int length) {
        System.arraycopy(this.buffer, index, out, offset, length);
    }

    @Override
    public void read(int index, @NotNull AnimationWritableTarget dst, int length) {
        dst.write(0, this.buffer, index, length);
    }

    @Override
    public void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length) {
        dst.write(dstOffset, this.buffer, index, length);
    }

    @Override
    public void write(int index, float value) {
        this.buffer[index] = value;
    }

    @Override
    public void write(int index, float x, float y) {
        this.buffer[index] = x;
        this.buffer[index + 1] = y;
    }

    @Override
    public void write(int index, float x, float y, float z) {
        this.buffer[index] = x;
        this.buffer[index + 1] = y;
        this.buffer[index + 2] = z;
    }

    @Override
    public void write(int index, float x, float y, float z, float w) {
        this.buffer[index] = x;
        this.buffer[index + 1] = y;
        this.buffer[index + 2] = z;
        this.buffer[index + 3] = w;
    }

    @Override
    public void write(int index, float[] value) {
        final int require = value.length;
        System.arraycopy(value, 0, this.buffer, index, require);
    }

    @Override
    public void write(int index, float[] value, int offset, int length) {
        System.arraycopy(value, offset, this.buffer, index, length);
    }

    @Override
    public void write(int index, @NotNull AnimationReadableTarget src, int length) {
        src.read(0, this.buffer, index, length);
    }

    @Override
    public void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length) {
        src.read(srcIndex, this.buffer, index, length);
    }

    @Override
    public void fill(float value) {
        Arrays.fill(this.buffer, value);
    }

    @Override
    public void fill(float value, int offset, int length) {
        Arrays.fill(this.buffer, offset, offset + length, value);
    }

    private void checkBufferSize(int index, int require) {
        if (index + require > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size + ", Require: " + require);
        }
    }
}
