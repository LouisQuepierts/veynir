package net.quepierts.animata4j.core.buffer.pointer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
public final class WriteonlyPointer implements WritablePointer {

    private final @NotNull AnimationWritableTarget target;

    @Getter
    @Setter
    private int offset;

    @Override
    public void write(int index, float value) {
        this.target.write(index + this.offset, value);
    }

    @Override
    public void write(int index, float x, float y) {
        this.target.write(index + this.offset, x, y);
    }

    @Override
    public void write(int index, float x, float y, float z) {
        this.target.write(index + this.offset, x, y, z);
    }

    @Override
    public void write(int index, float x, float y, float z, float w) {
        this.target.write(index + this.offset, x, y, z, w);
    }

    @Override
    public void write(int index, float[] value) {
        this.target.write(index + this.offset, value);
    }

    @Override
    public void write(int index, float[] value, int offset, int length) {
        this.target.write(index + this.offset, value, offset, length);
    }

    @Override
    public void write(int index, @NotNull AnimationReadableTarget src, int length) {
        this.target.write(index + this.offset, src, length);
    }

    @Override
    public void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length) {
        this.target.write(index + this.offset, src, srcIndex, length);
    }

    @Override
    public void fill(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fill(float value, int offset, int length) {
        this.target.fill(value, offset + this.offset, length);
    }

}
