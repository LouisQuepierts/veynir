package net.quepierts.animata4j.core.pipeline.datasource.timeline;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.math.ease.Ease;
import net.quepierts.animata4j.core.math.interpolation.Interpolation;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Segment {

    private static final float[] EMPTY = new float[16];

    @Getter
    private final int startFrame;
    @Getter
    private final int endFrame;
    @Getter
    private final float invLength;

    protected final float[] x0, x1, x2, x3;

    protected final Interpolation interpolation;
    protected final Ease ease;

    @ApiStatus.Internal
    public static Segment create(
            int length,
            int start, int end,
            float[] x0, float[] x1, float[] x2, float[] x3,
            Interpolation interpolation,
            Ease ease
    ) {
        if (length != x1.length || length != x2.length) {
            throw new IllegalArgumentException("x1.length must be equal to x2.length");
        }

        float[] x00 = interpolation.isQuadratic() ? x0 : EMPTY;
        float[] x33 = interpolation.isQuadratic() ? x3 : EMPTY;
        float invLength = 1.0f / (end - start);

        switch (length) {
            case 0:
                throw new IllegalArgumentException("x0.length must be greater than 0");
            case 1:
                return new $1(start, end, invLength, x00, x1, x2, x33, interpolation, ease);
            case 2:
                return new $2(start, end, invLength, x00, x1, x2, x33, interpolation, ease);
            case 3:
                return new $3(start, end, invLength, x00, x1, x2, x33, interpolation, ease);
            case 4:
                return new $4(start, end, invLength, x00, x1, x2, x33, interpolation, ease);
            default:
                return new $N(start, end, invLength, x00, x1, x2, x33, interpolation, ease);
        }
    }

    public final void sample(
            @NotNull AnimationWritableTarget target,
            @NotNull ReadonlyAnimationContext context,
            int frame
    ) {
        var t = getDeltaTime(frame);
        sample(target, context, t);
    }

    public abstract void sample(
            @NotNull AnimationWritableTarget target,
            @NotNull ReadonlyAnimationContext context,
            float t
    );

    public float getDeltaTime(int frame) {
        return (frame - this.startFrame) * this.invLength;
    }

    // generated code
    private static class $1 extends Segment {
        private $1(int start, int end, float invLength, float[] x0, float[] x1, float[] x2, float[] x3, Interpolation interpolation, Ease ease) {
            super(start, end, invLength, x0, x1, x2, x3, interpolation, ease);
        }

        @Override
        public void sample(@NotNull AnimationWritableTarget target, @NotNull ReadonlyAnimationContext context, float t) {
            target.write(0, interpolation.interpolate(x0[0], x1[0], x2[0], x3[0], ease.ease(t)));
        }
    }

    private static class $2 extends Segment {
        private $2(int start, int end, float invLength, float[] x0, float[] x1, float[] x2, float[] x3, Interpolation interpolation, Ease ease) {
            super(start, end, invLength, x0, x1, x2, x3, interpolation, ease);
        }

        @Override
        public void sample(@NotNull AnimationWritableTarget target, @NotNull ReadonlyAnimationContext context, float t) {
            target.write(
                    0,
                    interpolation.interpolate(x0[0], x1[0], x2[0], x3[0], ease.ease(t)),
                    interpolation.interpolate(x0[1], x1[1], x2[1], x3[1], ease.ease(t))
            );
        }
    }

    private static class $3 extends Segment {
        private $3(int start, int end, float invLength, float[] x0, float[] x1, float[] x2, float[] x3, Interpolation interpolation, Ease ease) {
            super(start, end, invLength, x0, x1, x2, x3, interpolation, ease);
        }

        @Override
        public void sample(@NotNull AnimationWritableTarget target, @NotNull ReadonlyAnimationContext context, float t) {
            target.write(
                    0,
                    interpolation.interpolate(x0[0], x1[0], x2[0], x3[0], ease.ease(t)),
                    interpolation.interpolate(x0[1], x1[1], x2[1], x3[1], ease.ease(t)),
                    interpolation.interpolate(x0[2], x1[2], x2[2], x3[2], ease.ease(t))
            );
        }
    }

    private static class $4 extends Segment {
        private $4(int start, int end, float invLength, float[] x0, float[] x1, float[] x2, float[] x3, Interpolation interpolation, Ease ease) {
            super(start, end, invLength, x0, x1, x2, x3, interpolation, ease);
        }

        @Override
        public void sample(@NotNull AnimationWritableTarget target, @NotNull ReadonlyAnimationContext context, float t) {
            target.write(
                    0,
                    interpolation.interpolate(x0[0], x1[0], x2[0], x3[0], ease.ease(t)),
                    interpolation.interpolate(x0[1], x1[1], x2[1], x3[1], ease.ease(t)),
                    interpolation.interpolate(x0[2], x1[2], x2[2], x3[2], ease.ease(t)),
                    interpolation.interpolate(x0[3], x1[3], x2[3], x3[3], ease.ease(t))
            );
        }
    }

    private static class $N extends Segment {
        private $N(int start, int end, float invLength, float[] x0, float[] x1, float[] x2, float[] x3, Interpolation interpolation, Ease ease) {
            super(start, end, invLength, x0, x1, x2, x3, interpolation, ease);
        }

        @Override
        public void sample(@NotNull AnimationWritableTarget target, @NotNull ReadonlyAnimationContext context, float t) {
            for (int i = 0; i < x0.length; i++) {
                target.write(i, interpolation.interpolate(x0[i], x1[i], x2[i], x3[i], ease.ease(t)));
            }
        }
    }

}
