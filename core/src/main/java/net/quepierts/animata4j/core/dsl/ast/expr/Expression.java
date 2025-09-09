package net.quepierts.animata4j.core.dsl.ast.expr;

import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

public abstract class Expression extends Node {

    public static Expression empty() {
        return EmptyExpr.INSTANCE;
    }

    protected Expression(SourceSpan span) {
        super(span);
    }

}
