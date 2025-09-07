package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class MemberAccessExpr extends Expression {

    private final Expression expression;
    private final IdentifierExpr member;

    public MemberAccessExpr(SourceSpan span, Expression expression, IdentifierExpr member) {
        super(span);
        this.expression = expression;
        this.member = member;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_MEMBER_ACCESS;
    }
}
