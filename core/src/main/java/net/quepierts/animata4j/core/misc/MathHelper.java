package net.quepierts.animata4j.core.misc;

public class MathHelper {
    public static final float PI = (float) Math.PI;
    public static final float TAU = (float) (Math.PI * 2);
    public static final float EPSILON = 0.00001f;
    public static final float EPSILON_SQR = EPSILON * EPSILON;

    public static final float DEG2RAD = PI / 180f;
    public static final float RAD2DEG = 180f / PI;

    public static float clamp(float value, float min, float max) {
        if (Float.isNaN(value)) {
            return min;
        }
        return value < min ? min : value > max ? max : value;
    }

    public static int clamp(int value, int min, int max) {
        return value < min ? min : value > max ? max : value;
    }

    public static float saturate(float value) {
        return clamp(value, 0f, 1f);
    }

    public static float lerp(float a, float b, float delta) {
        return a + (b - a) * delta;
    }
}
