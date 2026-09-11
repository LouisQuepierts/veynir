package net.quepierts.veynir.dsl.ast.type;

import lombok.Getter;
import net.quepierts.veynir.dsl.Primitive;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class PrimitiveType extends Type {

    private final Primitive primitive;

    public PrimitiveType(SourceSpan span, Primitive primitive) {
        super(span);
        this.primitive = primitive;
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_PRIMITIVE;
    }
}
