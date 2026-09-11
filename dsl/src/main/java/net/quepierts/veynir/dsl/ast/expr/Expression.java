package net.quepierts.veynir.dsl.ast.expr;

import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.source.SourceSpan;

public abstract class Expression extends Node {

    public static Expression empty() {
        return EmptyExpr.INSTANCE;
    }

    protected Expression(SourceSpan span) {
        super(span);
    }

}
