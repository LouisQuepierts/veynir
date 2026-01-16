package net.quepierts.animata4j.core.buffer;

public interface AnimationBuffer extends AnimationReadableTarget, AnimationWritableTarget {

    static AnimationBuffer create(int size) {
        return FloatArrayBuffer.create(size);
    }


    /**
     * Get the size of the buffer.
     * @return the size of the buffer.
     */
    int getSize();

}
