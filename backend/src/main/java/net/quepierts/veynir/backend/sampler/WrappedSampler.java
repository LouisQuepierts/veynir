package net.quepierts.veynir.backend.sampler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.core.buffer.WritableBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationContext;

@SuppressWarnings("unused")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class WrappedSampler implements AnimationSampler {

    public static WrappedSampler wrap(
            final AnimationSampler  sampler,
            final net.quepierts.veynir.core.pipeline.SamplingMode mode
    ) {

        if (sampler instanceof WrappedSampler wrapped) {
            return wrapped.mode == mode ? wrapped : new WrappedSampler(wrapped.delegate, mode);
        }

        return new WrappedSampler(sampler, mode);

    }

    private final AnimationSampler  delegate;
    private final net.quepierts.veynir.core.pipeline.SamplingMode mode;

    @Override
    public void sample(
            final AnimationContext context,
            final WritableBuffer target,
            final net.quepierts.veynir.core.pipeline.SamplingMode mode,
            final float time
    ) {
        this.delegate.sample(context, target, this.mode, time);
    }
}
