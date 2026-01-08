package net.quepierts.animata4j.core.pipeline.common.buffer;

import net.quepierts.animata4j.core.pipeline.common.target.AnimationReadableTarget;
import net.quepierts.animata4j.core.pipeline.common.target.AnimationWritableTarget;

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
