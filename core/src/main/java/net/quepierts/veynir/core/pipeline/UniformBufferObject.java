package net.quepierts.veynir.core.pipeline;

import net.quepierts.veynir.core.buffer.ReadableBuffer;
import net.quepierts.veynir.core.buffer.WritableBuffer;
import org.jspecify.annotations.NonNull;

public interface UniformBufferObject {

    @NonNull WritableBuffer getRawWriter();

    @NonNull ReadableBuffer getRawReader();

}
