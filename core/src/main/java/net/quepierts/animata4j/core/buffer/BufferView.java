package net.quepierts.animata4j.core.buffer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BufferView {

    protected final AnimationBuffer buffer;
    protected final int offset;

    @Getter
    protected final int size;

    public BufferView(int size) {
        this(AnimationBuffer.create(size), 0, size);
    }

    public float read(int index) {
        return buffer.read(offset + index);
    }

    public void read(int index, float[] out) {
        buffer.read(offset + index, out);
    }

    public void read(int index, float[] out, int offset, int length) {
        buffer.read(offset + index, out, offset, length);
    }

    public void read(int index, @NotNull AnimationWritableTarget dst, int length) {
        buffer.read(offset + index, dst, length);
    }

    public void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length) {
        buffer.read(offset + index, dst, dstOffset, length);
    }

    public void write(int index, float value) {
        buffer.write(offset + index, value);
    }

    public void write(int index, float x, float y) {
        buffer.write(offset + index, x, y);
    }

    public void write(int index, float x, float y, float z) {
        buffer.write(offset + index, x, y, z);
    }

    public void write(int index, float x, float y, float z, float w) {
        buffer.write(offset + index, x, y, z, w);
    }

    public void write(int index, float[] value) {
        buffer.write(offset + index, value);
    }

    public void write(int index, float[] value, int offset, int length) {
        buffer.write(offset + index, value, offset, length);
    }

    public void write(int index, @NotNull AnimationReadableTarget src, int length) {
        buffer.write(offset + index, src, length);
    }

    public void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length) {
        buffer.write(offset + index, src, srcIndex, length);
    }

    public void fill(float value) {
        buffer.fill(value, offset, size);
    }

    public void fill(float value, int offset, int length) {
        buffer.fill(value, offset, length);
    }
}
