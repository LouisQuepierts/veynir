package net.quepierts.animata4j.core.buffer;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for a readable target which allows animation to read data from a target.
 * */
@SuppressWarnings("unused")
public interface AnimationReadableTarget {
    float read(int index);

    void read(int index, float[] out);

    void read(int index, float[] out, int offset, int length);

    void read(int index, @NotNull AnimationWritableTarget dst, int length);

    void read(int index, @NotNull AnimationWritableTarget dst, int dstOffset, int length);
}
