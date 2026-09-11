package net.quepierts.veynir.core.buffer;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.nio.FloatBuffer;

public interface AnimationBufferObject
        extends ReadableBuffer, WritableBuffer {

    @Contract(pure = true)
    @NonNull FloatBuffer getBufferView();

}
