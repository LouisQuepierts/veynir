package net.quepierts.animata4j.core.buffer;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for a writable target which allows animation to write data from a target.
 */
@SuppressWarnings("unused")
public interface AnimationWritableTarget {
    void write(int index, float value);

    void write(int index, float x, float y);

    void write(int index, float x, float y, float z);

    void write(int index, float x, float y, float z, float w);

    void write(int index, float[] value);

    void write(int index, float[] value, int offset, int length);

    void write(int index, @NotNull AnimationReadableTarget src, int length);

    void write(int index, @NotNull AnimationReadableTarget src, int srcIndex, int length);

    void fill(float value);

    void fill(float value, int offset, int length);
}
