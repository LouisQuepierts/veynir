package net.quepierts.animata4j.core.buffer.pointer;

import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;

/**
 * A pointer that can be read from a buffer
 */
public interface ReadablePointer extends AnimationReadableTarget {

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
