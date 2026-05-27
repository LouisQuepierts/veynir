package net.quepierts.animata4j.backend.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.pipeline.AnimationPipeline;
import net.quepierts.animata4j.backend.sampler.AnimationSampler;
import net.quepierts.animata4j.core.misc.LocationLookup;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public abstract class AnimationSource {

    private final LocationLookup channels;

    public abstract @NotNull AnimationSampler link(@NotNull AnimationPipeline pipeline);

    public abstract float getDuration();

}
