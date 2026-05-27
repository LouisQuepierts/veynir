package net.quepierts.animata4j.backend.source;

import lombok.Getter;
import net.quepierts.animata4j.backend.buffer.AnimationBuffer;
import net.quepierts.animata4j.backend.model.Timeline;
import net.quepierts.animata4j.backend.pipeline.AnimationPipeline;
import net.quepierts.animata4j.backend.sampler.TimelineSampler;
import net.quepierts.animata4j.core.misc.LocationLookup;
import org.jetbrains.annotations.NotNull;

@Getter
public final class TimelineSource extends AnimationSource {

    private final Timeline[]        timelines;
    private final AnimationBuffer   constants;
    private final boolean           loop;
    private final float             duration;

    public TimelineSource(
            String[]            channels,
            Timeline[]          timelines,
            AnimationBuffer     constants,
            boolean             loop,
            float               duration
    ) {
        super(LocationLookup.of(channels));

        this.timelines          = timelines;
        this.constants          = constants;
        this.loop               = loop;
        this.duration           = duration;
    }

    public Timeline getTimeline(int channel) {
        return this.timelines[channel];
    }

    @Override
    public @NotNull TimelineSampler link(@NotNull AnimationPipeline pipeline) {
        return TimelineSampler.of(this, pipeline.getChannelLayout());
    }
}
