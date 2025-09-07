package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public abstract class LiteralExpr extends Expression {

    private final String literal;

    protected LiteralExpr(SourceSpan span, String literal) {
        super(span);
        this.literal = literal;
    }
}
