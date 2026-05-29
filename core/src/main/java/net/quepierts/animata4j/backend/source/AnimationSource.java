package net.quepierts.animata4j.backend.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.pipeline.AnimationPipeline;
import net.quepierts.animata4j.backend.sampler.AnimationSampler;
import net.quepierts.animata4j.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;

@Getter
@RequiredArgsConstructor
public abstract class AnimationSource {

    private final LocationLookup channels;

    public abstract @NonNull AnimationSampler link(@NonNull AnimationPipeline pipeline);

    public abstract float getDuration();

}
