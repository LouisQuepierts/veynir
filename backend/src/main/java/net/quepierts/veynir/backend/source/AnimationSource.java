package net.quepierts.veynir.backend.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.core.pipeline.AnimationPipeline;
import net.quepierts.veynir.backend.sampler.AnimationSampler;
import net.quepierts.veynir.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;

@Getter
@RequiredArgsConstructor
public abstract class AnimationSource {

    private final LocationLookup channels;

    public abstract @NonNull AnimationSampler link(@NonNull AnimationPipeline pipeline);

    public abstract float getDuration();

}
