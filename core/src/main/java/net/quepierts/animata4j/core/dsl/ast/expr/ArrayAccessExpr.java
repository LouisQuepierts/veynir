package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.capability.HasTargetCapability;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class ArrayAccessExpr
        extends Expression
        implements HasTargetCapability {

    private final Expression scope;
    private final Expression index;

    public ArrayAccessExpr(SourceSpan span, Expression scope, Expression index) {
        super(span);
        this.scope = scope;
        this.index = index;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_ARRAY_ACCESS;
    }
}
