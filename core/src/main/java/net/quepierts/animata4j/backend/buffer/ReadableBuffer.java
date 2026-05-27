package net.quepierts.animata4j.backend.buffer;

import net.quepierts.animata4j.core.adapter.Consumer4f;
import net.quepierts.animata4j.core.adapter.Consumer4i;
import org.jetbrains.annotations.NotNull;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@SuppressWarnings("unused")
public interface ReadableBuffer {

    float   readFloat(int location);

    void    readFloat(int location, @NotNull Consumer4f consumer);

    void    readFloat(int location, int length, float @NotNull[] out);

    void    readFloat(int location, int length, @NotNull FloatBuffer out);

    int     readInt(int location);

    void    readInt(int location, @NotNull Consumer4i consumer);

    void    readInt(int location, int length, int @NotNull[] out);

    void    readInt(int location, int length, @NotNull IntBuffer out);

    default boolean readBoolean(int location) {
        return readInt(location) != 0;
    }

}
