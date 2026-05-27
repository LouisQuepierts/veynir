package net.quepierts.animata4j.core.adapter;

import net.quepierts.animata4j.backend.buffer.WritableBuffer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PropertyProvider {

    void write(final int offset, final @NotNull WritableBuffer target);

}
