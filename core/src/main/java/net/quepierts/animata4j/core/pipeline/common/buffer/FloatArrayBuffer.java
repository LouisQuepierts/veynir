package net.quepierts.animata4j.core.pipeline.common.buffer;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FloatArrayBuffer implements AnimationFrameBuffer {

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
    public float getFloat(int index) {
        return this.buffer[index];
    }

    @Override
    public void getFloat(int index, float[] out) {
        final int require = out.length;
        this.checkBufferSize(index, require);

        System.arraycopy(this.buffer, index, out, 0, require);
    }

    @Override
    public void setFloat(int index, float value) {
        this.buffer[index] = value;
    }

    @Override
    public void setFloat(int index, float[] value) {
        final int require = value.length;
        this.checkBufferSize(index, require);

        System.arraycopy(value, 0, this.buffer, index, require);
    }

    private void checkBufferSize(int index, int require) {
        if (index + require > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size + ", Require: " + require);
        }
    }
}
