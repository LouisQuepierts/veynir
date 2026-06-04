package net.quepierts.veynir.backend.sampler;

import net.quepierts.veynir.backend.buffer.WritableBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationContext;

public interface AnimationSampler {

     void sample(
            final AnimationContext      context,
            final WritableBuffer        target,
            final SamplingMode          mode,
            final float                 time
     );

}
