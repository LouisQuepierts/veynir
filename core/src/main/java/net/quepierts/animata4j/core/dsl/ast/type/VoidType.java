package net.quepierts.animata4j.core.dsl.ast.type;

import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

public final class VoidType extends Type {
    public VoidType(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_VOID;
    }
}
