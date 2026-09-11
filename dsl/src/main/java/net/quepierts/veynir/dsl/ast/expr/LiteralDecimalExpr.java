package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class LiteralDecimalExpr extends LiteralExpr {

    private final float value;

    public static LiteralDecimalExpr of(SourceSpan span, String literal) {
        return new LiteralDecimalExpr(span, literal, Float.parseFloat(literal));
    }

    public LiteralDecimalExpr(SourceSpan span, String literal, float value) {
        super(span, literal);
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_LITERAL_DECIMAL;
    }
}
