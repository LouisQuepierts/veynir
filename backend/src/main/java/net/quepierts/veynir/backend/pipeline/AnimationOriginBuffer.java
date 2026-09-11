package net.quepierts.veynir.backend.pipeline;

import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.core.pipeline.AnimationOriginView;

public class AnimationOriginBuffer
        extends AnimationBuffer.Slice
        implements AnimationOriginView {
    AnimationOriginBuffer(AnimationBuffer buffer, int offset, int size) {
        super(buffer, offset, size);
    }
}
