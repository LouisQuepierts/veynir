package net.quepierts.animata4j.core.fsm;

import java.util.Objects;

public final class FSMParameter {
    private final float[] duration;
    private final float[] fadeIn;
    private final float[] fadeOut;

    public FSMParameter(
            float[] duration,
            float[] fadeIn,
            float[] fadeOut
    ) {
        this.duration = duration;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    public float[] duration() {
        return duration;
    }

    public float[] fadeIn() {
        return fadeIn;
    }

    public float[] fadeOut() {
        return fadeOut;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FSMParameter) obj;
        return Objects.equals(this.duration, that.duration) &&
                Objects.equals(this.fadeIn, that.fadeIn) &&
                Objects.equals(this.fadeOut, that.fadeOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, fadeIn, fadeOut);
    }

    @Override
    public String toString() {
        return "FSMParameter[" +
                "duration=" + duration + ", " +
                "fadeIn=" + fadeIn + ", " +
                "fadeOut=" + fadeOut + ']';
    }

}
