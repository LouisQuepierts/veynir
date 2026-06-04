package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.capability.HasTargetCapability;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class MemberAccessExpr
        extends Expression
        implements HasTargetCapability {

    private final Expression scope;
    private final IdentifierExpr member;

    public MemberAccessExpr(SourceSpan span, Expression scope, IdentifierExpr member) {
        super(span);
        this.scope = scope;
        this.member = member;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_MEMBER_ACCESS;
    }
}
