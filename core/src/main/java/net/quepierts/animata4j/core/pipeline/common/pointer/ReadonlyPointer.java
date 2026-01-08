package net.quepierts.animata4j.core.pipeline.common.pointer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationReadableTarget;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
public final class ReadonlyPointer implements ReadablePointer {

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
    public void read(int index, float[] out, int offset, int length) {
        this.target.read(index + this.offset, out, offset, length);
    }

    @Override
    public void read(int index, @NotNull AnimationWritableTarget dst, int length) {
        this.target.read(index + this.offset, dst, length);
    }

    @Override
    public void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length) {
        this.target.read(index + this.offset, dst, dstOffset, length);
    }
}
