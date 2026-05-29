package net.quepierts.animata4j.backend.execution;

import org.jspecify.annotations.NonNull;

public interface ExecutionReflection {

    int oid(final @NonNull String semantic);

    int location(final @NonNull String semantic);

}
