package net.quepierts.veynir.core.buffer;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.nio.IntBuffer;

public interface AttributeBufferObject
        extends ReadableBuffer, WritableBuffer {

    @Contract(pure = true)
    @NonNull IntBuffer getBufferView();

}
