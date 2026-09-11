package net.quepierts.veynir.backend.source;

import lombok.Getter;
import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.model.Timeline;
import net.quepierts.veynir.core.pipeline.AnimationPipeline;
import net.quepierts.veynir.backend.sampler.TimelineSampler;
import net.quepierts.veynir.core.util.LocationLookup;
import org.jspecify.annotations.NonNull;

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
    public @NonNull TimelineSampler link(@NonNull AnimationPipeline pipeline) {
        return TimelineSampler.of(this, pipeline.getChannelLayout());
    }
}
