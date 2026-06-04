package net.quepierts.veynir.backend.source;

import lombok.Getter;
import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.pipeline.AnimationPipeline;
import net.quepierts.veynir.backend.sampler.AnimationBufferSampler;
import net.quepierts.veynir.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;

@Getter
public final class AnimationBufferSource extends AnimationSource {

    private final AnimationBuffer.Slice buffer;

    public AnimationBufferSource(LocationLookup channels, AnimationBuffer.Slice buffer) {
        super(channels);
        this.buffer = buffer;
    }

    @Override
    public @NonNull AnimationBufferSampler link(@NonNull AnimationPipeline pipeline) {
        return AnimationBufferSampler.of(this, pipeline.getChannelLayout());
    }

    @Override
    public float getDuration() {
        return 0;
    }
}
