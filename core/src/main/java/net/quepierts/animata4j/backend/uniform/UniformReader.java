package net.quepierts.animata4j.backend.uniform;

import net.quepierts.animata4j.backend.buffer.AnimationBuffer;
import net.quepierts.animata4j.backend.buffer.ReadableBuffer;
import org.jetbrains.annotations.NotNull;

public interface UniformReader {

    int     readInt    (int location);

    float   readFloat  (int location);

    boolean readBool   (int location);

    void    read(
            int             location,
            UniformType     type,
            int[]           out
    );

    void    read(
            int             location,
            UniformType     type,
            float[]         out
    );

    void    read(
            int             location,
            UniformType     type,
            int             offset,
            AnimationBuffer out
    );

    @NotNull ReadableBuffer getRawReader();

    int address(int location);

}
