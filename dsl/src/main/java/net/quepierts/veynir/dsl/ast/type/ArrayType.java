package net.quepierts.veynir.dsl.ast.type;

import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

public final class ArrayType extends Type {

    public final Type componentType;
    public final int dimension;

    ArrayType(SourceSpan span, Type componentType, int dimension) {
        super(span);
        this.componentType = componentType;
        this.dimension = dimension;
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_ARRAY;
    }
}
