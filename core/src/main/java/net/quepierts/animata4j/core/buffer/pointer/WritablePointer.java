package net.quepierts.animata4j.core.buffer.pointer;

import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;

/**
 * A pointer that can be written to a buffer
 */
public interface WritablePointer extends AnimationWritableTarget {

    static WritablePointer of(AnimationWritableTarget target) {
        return WriteonlyPointer.of(target);
    }

    /**
     * Get the offset of the pointer
     * @return the offset of the pointer
     */
    int getOffset();

    /**
     * Set the offset of the pointer
     * @param value the offset of the pointer
     */
    void setOffset(int value);

}
