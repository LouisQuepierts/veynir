package net.quepierts.veynir.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Mth {

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static float horner(float t, float a, float b, float c, float d) {
        return ((a * t + b) * t + c) * t + d;
    }

    public static float catmullrom(float delta, float start, float left, float right, float end) {
        final var t2 = delta * delta;
        final var t3 = t2 * delta;
        return catmullrom(delta, t2, t3, start, left, right, end);
    }

    public static float catmullrom(
            float delta, float t2, float t3,
            float p0,
            float p1,
            float p2,
            float p3
    ) {
        return  0.5F * (
                    2.0F * p1 +
                    (-p0 + p2) * delta +
                    (2.0F * p0 - 5.0F * p1 + 4.0F * p2 - p3) * t2 +
                    (-p0 + 3.0F * p1 - 3.0F * p2 + p3) * t3
                );
    }

}
