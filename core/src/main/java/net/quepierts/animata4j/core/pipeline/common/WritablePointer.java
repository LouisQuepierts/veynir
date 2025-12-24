package net.quepierts.animata4j.core.pipeline.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(staticName = "of")
public final class WritablePointer implements AnimationWritableTarget {

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
    public void fill(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fill(float value, int offset, int length) {
        this.target.fill(value, offset + this.offset, length);
    }

    @Override
    public void setDouble(int index, double value) {
        this.target.setDouble(index + this.offset, value);
    }

    @Override
    public void setDouble(int index, double[] value) {
        this.target.setDouble(index + this.offset, value);
    }

    @Override
    public void setByte(int index, byte value) {
        this.target.setByte(index + this.offset, value);
    }

    @Override
    public void setByte(int index, byte[] value) {
        this.target.setByte(index + this.offset, value);
    }

    @Override
    public void setInteger(int index, int value) {
        this.target.setInteger(index + this.offset, value);
    }

    @Override
    public void setInteger(int index, int[] value) {
        this.target.setInteger(index + this.offset, value);
    }

    @Override
    public void setLong(int index, long value) {
        this.target.setLong(index + this.offset, value);
    }

    @Override
    public void setLong(int index, long[] value) {
        this.target.setLong(index + this.offset, value);
    }

}
