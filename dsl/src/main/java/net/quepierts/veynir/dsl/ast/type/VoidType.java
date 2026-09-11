package net.quepierts.veynir.dsl.ast.type;

import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

public final class VoidType extends Type {
    public VoidType(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_VOID;
    }
}
