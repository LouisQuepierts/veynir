package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.buffer.ReadableBuffer;
import net.quepierts.veynir.core.uniform.UniformType;
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

    @NonNull ReadableBuffer getRawReader();

    int address(int location);

}
