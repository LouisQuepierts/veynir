package net.quepierts.veynir.backend.source;

import net.quepierts.veynir.core.pipeline.AnimationPipeline;
import net.quepierts.veynir.backend.sampler.AnimationSampler;
import net.quepierts.veynir.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;

public class CompiledTimelineSource extends AnimationSource {

    public CompiledTimelineSource(final LocationLookup channels) {
        super(channels);
    }

    @Override
    public @NonNull AnimationSampler link(@NonNull final AnimationPipeline pipeline) {
        return null;
    }

    @Override
    public float getDuration() {
        return 0;
    }
}
