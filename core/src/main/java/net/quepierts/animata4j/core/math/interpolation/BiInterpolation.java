package net.quepierts.animata4j.core.math.interpolation;

public interface BiInterpolation extends Interpolation {

    float interpolate(float x1, float x2, float t);

    @Override
    default float interpolate(float x0, float x1, float x2, float x3, float t) {
        return interpolate(x1, x2, t);
    }

    @Override
    default boolean isQuadratic() {
        return false;
    }
}
