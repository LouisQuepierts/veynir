package net.quepierts.animata4j.core.adapter;

import net.quepierts.animata4j.backend.buffer.WritableBuffer;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface PropertyProvider {

    void write(final int offset, final @NonNull WritableBuffer target);

}
