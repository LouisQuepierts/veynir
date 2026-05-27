package net.quepierts.animata4j.backend.sampler;

import net.quepierts.animata4j.backend.buffer.WritableBuffer;
import net.quepierts.animata4j.backend.pipeline.AnimationContext;

public interface AnimationSampler {

     void sample(
            final AnimationContext      context,
            final WritableBuffer        target,
            final SamplingMode          mode,
            final float                 time
     );

}
