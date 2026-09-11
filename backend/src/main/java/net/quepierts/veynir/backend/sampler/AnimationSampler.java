package net.quepierts.veynir.backend.sampler;

import net.quepierts.veynir.core.buffer.WritableBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationContext;
import net.quepierts.veynir.core.pipeline.AnimationSamplerObject;

public interface AnimationSampler extends AnimationSamplerObject {

     void sample(
            final AnimationContext      context,
            final WritableBuffer        target,
            final net.quepierts.veynir.core.pipeline.SamplingMode mode,
            final float                 time
     );

}
