package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class LiteralStringExpr extends LiteralExpr {

    public LiteralStringExpr(SourceSpan span, String value) {
        super(span, value);
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_LITERAL_STRING;
    }
}
