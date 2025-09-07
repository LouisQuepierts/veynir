package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class UnaryExpr extends Expression {

    private final Expression expr;
    private final TokenType opr;

    public UnaryExpr(
            SourceSpan span,
            Expression expr,
            TokenType type
    ) {
        super(span);
        this.expr = expr;
        this.opr = type;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_UNARY;
    }
}
