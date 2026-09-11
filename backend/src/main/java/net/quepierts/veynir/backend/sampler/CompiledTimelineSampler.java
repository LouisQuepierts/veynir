package net.quepierts.veynir.backend.sampler;

import net.quepierts.veynir.core.buffer.WritableBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationContext;

public class CompiledTimelineSampler implements AnimationSampler {
    @Override
    public void sample(
            final AnimationContext  context,
            final WritableBuffer    target,
            final net.quepierts.veynir.core.pipeline.SamplingMode mode,
            final float             time
    ) {

    }
}
