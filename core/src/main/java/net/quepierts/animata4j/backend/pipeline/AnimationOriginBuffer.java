package net.quepierts.animata4j.backend.pipeline;

import net.quepierts.animata4j.backend.buffer.AnimationBuffer;

public class AnimationOriginBuffer
        extends AnimationBuffer.Slice
        implements AnimationOriginView {
    AnimationOriginBuffer(AnimationBuffer buffer, int offset, int size) {
        super(buffer, offset, size);
    }
}
