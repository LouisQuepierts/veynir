package net.quepierts.animata4j.core.pipeline.datasource.timeline;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.misc.MathHelper;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import net.quepierts.animata4j.core.pipeline.datasource.AnimationSource;
import net.quepierts.animata4j.core.pipeline.state.SourceState;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class CompiledTimeline implements AnimationSource {

    @Getter
    private final float duration;

    @Getter
    private final int frameRate;

    @Getter
    private final int componentCount;

    private final Segment[] segments;

    @Override
    public void eval(
            @NotNull AnimationWritableTarget target,
            @NotNull ReadonlyAnimationContext context,
            @NotNull SourceState state
    ) {
        int frame = MathHelper.clamp((int) (context.getDeltaTime() * frameRate), 0, segments.length - 1);

        int cursor = state.getLastFrameIndex();
        var segment = getSegment(cursor);

        if (segment.getStartFrame() < frame || segment.getEndFrame() > frame) {
            cursor = findSegment(frame);
            segment = getSegment(cursor);
            state.setLastFrameIndex(cursor);
        }

        segment.sample(target, context, frame);
    }

    @Override
    public boolean isFinished(@NotNull ReadonlyAnimationContext context) {
        return context.getDeltaTime() >= duration;
    }

    private Segment getSegment(int cursor) {
        return segments[cursor];
    }

    private int findSegment(int frame) {
        // bi search in segments

        int low = 0;
        int high = segments.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            var segment = segments[mid];

            if (segment.getStartFrame() > frame) {
                high = mid - 1;
            } else if (segment.getEndFrame() < frame) {
                low = mid + 1;
            } else { // in range
                return mid;
            }
        }

        return MathHelper.clamp(low - 1, 0, segments.length - 1);
    }
}
