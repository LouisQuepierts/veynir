package net.quepierts.animata4j.core.buffer.pointer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.quepierts.animata4j.core.buffer.AnimationBuffer;
import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
public final class BufferPointer implements ReadablePointer, WritablePointer {

    private final AnimationBuffer buffer;

    @Getter
    @Setter
    private int offset;

    @Override
    public float read(int index) {
        return this.buffer.read(index + this.offset);
    }

    @Override
    public void read(int index, float[] out) {
        this.buffer.read(index + this.offset, out);
    }

    @Override
    public void read(int index, float[] out, int offset, int length) {
        this.buffer.read(index + this.offset, out, offset, length);
    }

    @Override
    public void read(int index, @NotNull AnimationWritableTarget dst, int length) {
        this.buffer.read(index + this.offset, dst, length);
    }

    @Override
    public void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length) {
        this.buffer.read(index + this.offset, dst, dstOffset, length);
    }

    @Override
    public void write(int index, float value) {
        this.buffer.write(index + this.offset, value);
    }

    @Override
    public void write(int index, float x, float y) {
        this.buffer.write(index + this.offset, x, y);
    }

    @Override
    public void write(int index, float x, float y, float z) {
        this.buffer.write(index + this.offset, x, y, z);
    }

    @Override
    public void write(int index, float x, float y, float z, float w) {
        this.buffer.write(index + this.offset, x, y, z, w);
    }

    @Override
    public void write(int index, float[] value) {
        this.buffer.write(index + this.offset, value);
    }

    @Override
    public void write(int index, float[] value, int offset, int length) {
        this.buffer.write(index + this.offset, value, offset, length);
    }

    @Override
    public void write(int index, @NotNull AnimationReadableTarget src, int length) {
        this.buffer.write(index + this.offset, src, length);
    }

    @Override
    public void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length) {
        this.buffer.write(index + this.offset, src, srcIndex, length);
    }

    @Override
    public void fill(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fill(float value, int offset, int length) {
        this.buffer.fill(value, offset + this.offset, length);
    }
}
