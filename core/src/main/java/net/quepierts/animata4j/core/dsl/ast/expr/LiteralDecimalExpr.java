package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class LiteralDecimalExpr extends LiteralExpr {

    private final float value;

    public LiteralDecimalExpr(SourceSpan span, String literal) {
        super(span, literal);
        this.value = Float.parseFloat(literal);
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_LITERAL_DECIMAL;
    }
}
