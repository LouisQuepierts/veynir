package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class ArrayAccessExpr extends Expression {

    private final Expression array;
    private final Expression index;

    public ArrayAccessExpr(SourceSpan span, Expression array, Expression index) {
        super(span);
        this.array = array;
        this.index = index;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_ARRAY_ACCESS;
    }
}
