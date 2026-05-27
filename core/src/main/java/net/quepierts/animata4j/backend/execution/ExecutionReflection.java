package net.quepierts.animata4j.backend.execution;

import org.jetbrains.annotations.NotNull;

public interface ExecutionReflection {

    int oid(final @NotNull String semantic);

    int location(final @NotNull String semantic);

}
