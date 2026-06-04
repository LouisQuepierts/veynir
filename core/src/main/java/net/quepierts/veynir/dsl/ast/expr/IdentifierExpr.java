package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class IdentifierExpr extends Expression {

    private final String value;

    public IdentifierExpr(SourceSpan span, String value) {
        super(span);
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_IDENTIFIER;
    }
}
