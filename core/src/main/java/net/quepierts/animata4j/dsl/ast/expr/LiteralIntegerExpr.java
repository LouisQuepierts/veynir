package net.quepierts.animata4j.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class LiteralIntegerExpr extends LiteralExpr {

    public static LiteralIntegerExpr dec(SourceSpan span, String literal) {
        return new LiteralIntegerExpr(span, literal, Integer.parseInt(literal));
    }

    public static LiteralIntegerExpr hex(SourceSpan span, String literal) {
        return new LiteralIntegerExpr(span, literal, Integer.parseInt(literal, 16));
    }

    public static LiteralIntegerExpr oct(SourceSpan span, String literal) {
        return new LiteralIntegerExpr(span, literal, Integer.parseInt(literal, 8));
    }

    private final int value;

    private LiteralIntegerExpr(SourceSpan span, String literal, int value) {
        super(span, literal);
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_LITERAL_INTEGER;
    }
}
