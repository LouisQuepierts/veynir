package net.quepierts.animata4j.core.dsl.visitor;

import org.jetbrains.annotations.NotNull;

public interface Visitable {
    <R, A> R visit(final @NotNull TypedVisitor<R, A> visitor, final @NotNull A args);

    <A> void visit(final @NotNull VoidVisitor<A> visitor, final @NotNull A args);
}
