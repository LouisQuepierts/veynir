package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public abstract class LiteralExpr extends Expression {

    private final String literal;

    protected LiteralExpr(SourceSpan span, String literal) {
        super(span);
        this.literal = literal;
    }
}
