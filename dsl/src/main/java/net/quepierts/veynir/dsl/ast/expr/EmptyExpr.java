package net.quepierts.veynir.dsl.ast.expr;

import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourcePos;
import net.quepierts.veynir.dsl.source.SourceSpan;

public final class EmptyExpr extends Expression {
    public static final EmptyExpr INSTANCE = new EmptyExpr(SourceSpan.of(SourcePos.of(0, 0), SourcePos.of(0, 0)));

    private EmptyExpr(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_EMPTY;
    }
}
