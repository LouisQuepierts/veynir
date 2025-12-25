package net.quepierts.animata4j.core.pipeline.datasource.timeline;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.math.ease.Ease;
import net.quepierts.animata4j.core.math.interpolation.BiInterpolation;
import net.quepierts.animata4j.core.math.interpolation.Interpolation;
import net.quepierts.animata4j.core.pipeline.common.ReadonlyAnimationContext;
import net.quepierts.animata4j.core.pipeline.common.ValueRef;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Segment {

    @Getter
    private final int startFrame;
    @Getter
    private final int endFrame;
    @Getter
    private final float invLength;

    private final Ease ease;

    @ApiStatus.Internal
    public static Segment create(
            int length,
            int start, int end,
            ValueRef[] ref,
            Interpolation interpolation,
            Ease ease
    ) {
        float invLength = 1.0f / (end - start);
        ValueRef x0 = ref[0];
        ValueRef x1 = ref[1];
        if (interpolation instanceof BiInterpolation) {
            switch (length) {
                case 1: {
                    return new Bi.T1(start, end, invLength, ease, x0, x1, (BiInterpolation) interpolation);
                }
                case 2: {
                    return new Bi.T2(start, end, invLength, ease, x0, x1, (BiInterpolation) interpolation);
                }
                case 3: {
                    return new Bi.T3(start, end, invLength, ease, x0, x1, (BiInterpolation) interpolation);
                }
                case 4: {
                    return new Bi.T4(start, end, invLength, ease, x0, x1, (BiInterpolation) interpolation);
                }
                default: {
                    throw new IllegalArgumentException("Invalid length: " + length);
                }
            }
        } else {
            ValueRef x2 = ref[2];
            ValueRef x3 = ref[3];
            switch (length) {
                case 1: {
                    return new Quad.T1(start, end, invLength, ease, x0, x1, x2, x3, interpolation);
                }
                case 2: {
                    return new Quad.T2(start, end, invLength, ease, x0, x1, x2, x3, interpolation);
                }
                case 3: {
                    return new Quad.T3(start, end, invLength, ease, x0, x1, x2, x3, interpolation);
                }
                case 4: {
                    return new Quad.T4(start, end, invLength, ease, x0, x1, x2, x3, interpolation);
                }
                default: {
                    throw new IllegalArgumentException("Invalid length: " + length);
                }
            }
        }
    }

    public final void sample(
            @NotNull AnimationWritableTarget target,
            int frame
    ) {
        var t = getDeltaTime(frame);
        sample(target, t);
    }

    public abstract void sample(
            @NotNull AnimationWritableTarget target,
            float t
    );

    public float getDeltaTime(int frame) {
        return ease.ease((frame - this.startFrame) * this.invLength);
    }

    // generated
    private static abstract class Bi extends Segment {

        protected final ValueRef x0, x1;
        protected final BiInterpolation interpolation;

        private Bi(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, BiInterpolation interpolation) {
            super(startFrame, endFrame, invLength, ease);
            this.x0 = x0;
            this.x1 = x1;
            this.interpolation = interpolation;
        }

        private static final class T1 extends Bi {
            private T1(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, BiInterpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, interpolation);
            }

            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(0, interpolation.interpolate(x0.getX(), x1.getX(), t));
            }
        }

        private static final class T2 extends Bi {
            private T2(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, BiInterpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, interpolation);
            }

            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(
                        0,
                        interpolation.interpolate(x0.getX(), x1.getX(), t),
                        interpolation.interpolate(x0.getY(), x1.getY(), t)
                );
            }
        }

        private static final class T3 extends Bi {
            private T3(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, BiInterpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, interpolation);
            }

            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(
                        0,
                        interpolation.interpolate(x0.getX(), x1.getX(), t),
                        interpolation.interpolate(x0.getY(), x1.getY(), t),
                        interpolation.interpolate(x0.getZ(), x1.getZ(), t)
                );
            }
        }

        private static final class T4 extends Bi {
            private T4(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, BiInterpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, interpolation);
            }

            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(
                        0,
                        interpolation.interpolate(x0.getX(), x1.getX(), t),
                        interpolation.interpolate(x0.getY(), x1.getY(), t),
                        interpolation.interpolate(x0.getZ(), x1.getZ(), t),
                        interpolation.interpolate(x0.getW(), x1.getW(), t)
                );
            }
        }
    }

    private static abstract class Quad extends Segment { 
        
        protected final ValueRef x0, x1, x2, x3;
        protected final Interpolation interpolation;
        
        private Quad(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, ValueRef x2, ValueRef x3, Interpolation interpolation) {
            super(startFrame, endFrame, invLength, ease);
            this.x0 = x0;
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
            this.interpolation = interpolation;
        }
        
        private static final class T1 extends Quad {
            private T1(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, ValueRef x2, ValueRef x3, Interpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, x2, x3, interpolation);
            }
            
            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(0, interpolation.interpolate(x0.getX(), x1.getX(), x2.getX(), x3.getX(), t));
            }
        }
        
        private static final class T2 extends Quad {
            private T2(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, ValueRef x2, ValueRef x3, Interpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, x2, x3, interpolation);
            }
            
            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(
                        0,
                        interpolation.interpolate(x0.getX(), x1.getX(), x2.getX(), x3.getX(), t),
                        interpolation.interpolate(x0.getY(), x1.getY(), x2.getY(), x3.getY(), t)
                );
            }
        }
        
        private static final class T3 extends Quad { 
            private T3(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, ValueRef x2, ValueRef x3, Interpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, x2, x3, interpolation);
            }
            
            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(
                        0,
                        interpolation.interpolate(x0.getX(), x1.getX(), x2.getX(), x3.getX(), t),
                        interpolation.interpolate(x0.getY(), x1.getY(), x2.getY(), x3.getY(), t),
                        interpolation.interpolate(x0.getZ(), x1.getZ(), x2.getZ(), x3.getZ(), t)
                );
            }
        }
        
        private static final class T4 extends Quad {
            private T4(int startFrame, int endFrame, float invLength, Ease ease, ValueRef x0, ValueRef x1, ValueRef x2, ValueRef x3, Interpolation interpolation) {
                super(startFrame, endFrame, invLength, ease, x0, x1, x2, x3, interpolation);
            }
            
            @Override
            public void sample(@NotNull AnimationWritableTarget target,float t) {
                target.write(
                        0,
                        interpolation.interpolate(x0.getX(), x1.getX(), x2.getX(), x3.getX(), t),
                        interpolation.interpolate(x0.getY(), x1.getY(), x2.getY(), x3.getY(), t),
                        interpolation.interpolate(x0.getZ(), x1.getZ(), x2.getZ(), x3.getZ(), t),
                        interpolation.interpolate(x0.getW(), x1.getW(), x2.getW(), x3.getW(), t)
                );
            }
        }
    }

}
