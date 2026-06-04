package net.quepierts.veynir.backend.uniform;

import net.quepierts.veynir.backend.buffer.AnimationBuffer;
import net.quepierts.veynir.backend.buffer.ReadableBuffer;
import org.jspecify.annotations.NonNull;

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

    @NonNull ReadableBuffer getRawReader();

    int address(int location);

}
