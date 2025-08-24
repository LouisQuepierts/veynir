package net.quepierts.animata4j.core.data.layout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface MemoryLayoutProvider {
    @Nullable
    MemoryLayout getLayout(@NotNull final String name);
}
