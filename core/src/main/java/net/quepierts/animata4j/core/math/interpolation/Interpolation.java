package net.quepierts.animata4j.core.math.interpolation;

public interface Interpolation {
    float interpolate(float x0, float x1, float x2, float x3, float t);

    boolean isQuadratic();
}
