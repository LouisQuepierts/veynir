package net.quepierts.animata4j.core.dsl.ast;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public abstract class Node {
    private final SourceSpan span;

    protected Node(SourceSpan span) {
        this.span = span.copy();
    }

    public abstract NodeType getType();

    public boolean is(NodeType type) {
        return this.getType() == type;
    }
}
