package net.quepierts.animata4j.core.program.buffer;

import net.quepierts.animata4j.core.buffer.AnimationReadableTarget;
import net.quepierts.animata4j.core.buffer.AnimationWritableTarget;
import net.quepierts.animata4j.core.buffer.BufferView;

@SuppressWarnings("unused")
public final class DynamicStorageBuffer
        extends BufferView
        implements AnimationReadableTarget, AnimationWritableTarget {

    public DynamicStorageBuffer(int size) {
        super(size);
    }
}
