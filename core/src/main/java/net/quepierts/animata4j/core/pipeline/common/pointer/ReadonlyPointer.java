package net.quepierts.animata4j.core.pipeline.common.pointer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationReadableTarget;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(staticName = "of")
public class ReadonlyPointer implements ReadablePointer {

    private final @NotNull AnimationReadableTarget target;

    @Getter
    @Setter
    private int offset;

    @Override
    public float read(int index) {
        return this.target.read(index + this.offset);
    }

    @Override
    public void read(int index, float[] out) {
        this.target.read(index + this.offset, out);
    }

    @Override
    public double getDouble(int index) {
        return this.target.getDouble(index + this.offset);
    }

    @Override
    public void getDouble(int index, double[] out) {
        this.target.getDouble(index + this.offset, out);
    }

    @Override
    public byte getByte(int index) {
        return this.target.getByte(index + this.offset);
    }

    @Override
    public void getByte(int index, byte[] out) {
        this.target.getByte(index + this.offset, out);
    }

    @Override
    public int getInteger(int index) {
        return this.target.getInteger(index + this.offset);
    }

    @Override
    public void getInteger(int index, int[] out) {
        this.target.getInteger(index + this.offset, out);
    }

    @Override
    public long getLong(int index) {
        return this.target.getLong(index + this.offset);
    }

    @Override
    public void getLong(int index, long[] out) {
        this.target.getLong(index + this.offset, out);
    }
}
