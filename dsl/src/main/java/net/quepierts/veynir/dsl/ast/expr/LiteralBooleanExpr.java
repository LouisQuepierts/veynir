package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class LiteralBooleanExpr extends LiteralExpr {

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static LiteralBooleanExpr ofTrue(SourceSpan span) {
        return new LiteralBooleanExpr(span, true);
    }

    public static LiteralBooleanExpr ofFalse(SourceSpan span) {
        return new LiteralBooleanExpr(span, false);
    }

    private final boolean value;

    private LiteralBooleanExpr(SourceSpan span, boolean value) {
        super(span, value ? TRUE : FALSE);
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_LITERAL_BOOLEAN;
    }

}
