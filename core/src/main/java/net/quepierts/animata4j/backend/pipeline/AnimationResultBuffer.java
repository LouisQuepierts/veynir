package net.quepierts.animata4j.backend.pipeline;

import net.quepierts.animata4j.backend.buffer.AnimationBuffer;
import net.quepierts.animata4j.core.adapter.Consumer4f;

public final class AnimationResultBuffer
        extends AnimationBuffer.Slice
        implements AnimationResultView {
    AnimationResultBuffer(AnimationBuffer buffer, int size) {
        super(buffer, 0, size);
    }

    @Override
    public void read(int channel, Consumer4f consumer) {
        this.buffer.readFloat(channel << 2, consumer);
    }

    @Override
    public void read(int channel, float[] out) {
        this.buffer.readFloat(channel << 2, 4, out);
    }

    public void clear() {
        this.buffer.fill(0, this.size, Float.NaN);
    }

}
