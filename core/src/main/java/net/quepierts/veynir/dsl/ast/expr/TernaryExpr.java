package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

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
