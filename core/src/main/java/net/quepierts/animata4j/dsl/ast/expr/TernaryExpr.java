package net.quepierts.animata4j.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class TernaryExpr extends Expression {

    private final Expression condition;
    private final Expression thenExpr;
    private final Expression elseExpr;

    public TernaryExpr(SourceSpan span, Expression condition, Expression thenExpr, Expression elseExpr) {
        super(span);
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_TERNARY;
    }
}
