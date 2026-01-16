package net.quepierts.animata4j.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class LiteralStringExpr extends LiteralExpr {

    public static LiteralStringExpr of(SourceSpan span, String value) {
        return new LiteralStringExpr(span, value);
    }

    public LiteralStringExpr(SourceSpan span, String value) {
        super(span, value);
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_LITERAL_STRING;
    }
}
