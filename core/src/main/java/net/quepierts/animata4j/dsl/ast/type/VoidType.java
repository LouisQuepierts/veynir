package net.quepierts.animata4j.dsl.ast.type;

import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

public final class VoidType extends Type {
    public VoidType(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_VOID;
    }
}
