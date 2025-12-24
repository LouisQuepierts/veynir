package net.quepierts.animata4j.core.pipeline.datasource.timeline;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.math.ease.Ease;
import net.quepierts.animata4j.core.math.interpolation.Interpolation;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyFrame {
    private static final float[] EMPTY = new float[16];

    public final int frame;

    public final float[] value;

    public final float[] preValue;
    public final float[] postValue;

    public final Interpolation interpolation;
    public final Ease ease;

    public static KeyFrame of(int frame, float[] value, Interpolation interpolation, Ease ease) {
        return new KeyFrame(frame, value, EMPTY, EMPTY, interpolation, ease);
    }

    public static KeyFrame of(int frame, float[] preValue, float[] postValue, Interpolation interpolation, Ease ease) {
        return new KeyFrame(frame, EMPTY, preValue, postValue, interpolation, ease);
    }
}
