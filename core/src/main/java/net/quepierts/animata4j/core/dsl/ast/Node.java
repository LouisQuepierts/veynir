package net.quepierts.animata4j.core.dsl.ast;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Node {
    private final SourceSpan span;

    public abstract TypeIdentifier<? extends Node> getType();

    public boolean is(TypeIdentifier<?> type) {
        return this.getType() == type;
    }
}
