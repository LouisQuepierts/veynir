package net.quepierts.veynir.core.adapter;

import net.quepierts.veynir.core.buffer.WritableBuffer;
import org.jspecify.annotations.NonNull;

@FunctionalInterface
public interface PropertyProvider {

    void write(final int offset, final @NonNull WritableBuffer target);

}
