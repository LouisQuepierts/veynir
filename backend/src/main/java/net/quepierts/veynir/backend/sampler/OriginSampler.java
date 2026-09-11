package net.quepierts.veynir.backend.sampler;

import net.quepierts.veynir.core.buffer.WritableBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationContext;

public final class OriginSampler implements AnimationSampler {
    @Override
    public void sample(
            AnimationContext context,
            WritableBuffer target,
            final net.quepierts.veynir.core.pipeline.SamplingMode mode,
            final float time
    ) {
        var input       = context.getInputProvider();

        if (input == null) {
            return;
        }

        var layout      = context.getChannelLayout();
        for (int i = 0; i < layout.getChannelCount(); i++) {
            if (!context.getChannelMask(i)) {
                continue;
            }

            input       .fill(i, i << 2, target);
        }
    }
}
