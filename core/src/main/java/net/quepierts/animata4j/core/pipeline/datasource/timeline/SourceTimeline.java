package net.quepierts.animata4j.core.pipeline.datasource.timeline;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.pipeline.common.value.ValueRef;
import org.jetbrains.annotations.Contract;

@RequiredArgsConstructor
public final class SourceTimeline {

    static final ValueRef EMPTY_REF = ValueRef.empty();

    private final int frameRate;
    private final int componentCount;

    private final KeyFrame[] frames;

    /**
     * Compile the source timeline to compiled timeline
     * @param source the source timeline
     * @return the compiled timeline, which can be used to evaluate the animation faster
     */
    @Contract(pure = true)
    public static CompiledTimeline compile(SourceTimeline source) {
        final var frames = source.frames;
        int frameAmount = frames.length;

        if (frameAmount < 2) {
            throw new IllegalArgumentException("Source timeline must have at least 2 frames.");
        }

        final var segments = new Segment[frameAmount - 1];
        final ValueRef[] refs = {
                EMPTY_REF,
                EMPTY_REF,
                EMPTY_REF,
                EMPTY_REF
        };

        var last = frames[0];
        var current = frames[1];

        refs[1] = ValueRef.create(last.post());
        refs[2] = ValueRef.create(current.pre());

        int i = 0;
        for (; i < frameAmount - 2; i++) {
            var next = frames[i + 2];

            refs[3] = ValueRef.create(next.pre());

            segments[i] = Segment.create(
                    source.componentCount,
                    last.frame,
                    current.frame,
                    refs,
                    current.interpolation,
                    current.ease
            );

            last = current;
            current = next;

            refs[0] = refs[1];
            refs[1] = ValueRef.create(last.post());
            refs[2] = refs[3];
        }

        refs[3] = EMPTY_REF;
        segments[i] = Segment.create(
                source.componentCount,
                last.frame,
                current.frame,
                refs,
                current.interpolation,
                current.ease
        );

        int lastFrame = current.frame;
        float inverseFrameRate = 1.0f / source.frameRate;
        float duration = lastFrame * inverseFrameRate;

        return new CompiledTimeline(duration, source.frameRate, source.componentCount, segments);
    }

}
