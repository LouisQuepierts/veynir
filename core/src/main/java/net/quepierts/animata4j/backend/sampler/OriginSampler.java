package net.quepierts.animata4j.backend.sampler;

import net.quepierts.animata4j.backend.buffer.WritableBuffer;
import net.quepierts.animata4j.backend.pipeline.AnimationContext;

public final class OriginSampler implements AnimationSampler {
    @Override
    public void sample(
            AnimationContext context,
            WritableBuffer target,
            final SamplingMode mode,
            final float time
    ) {
        var input       = context.getInputProvider();

        if (input == null) {
            return;
        }

        var layout      = context.getChannelLayout();
        for (int i = 0; i < layout.getChannelCount(); i++) {
            if (!context.getSamplerMask(i)) {
                continue;
            }

            input       .fill(i, i << 2, target);
        }
    }
}
