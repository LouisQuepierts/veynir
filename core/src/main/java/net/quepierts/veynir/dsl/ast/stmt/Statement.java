package net.quepierts.veynir.dsl.ast.stmt;

import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.source.SourceSpan;

public abstract class Statement extends Node {

    public static Statement empty() {
        return EmptyStmt.INSTANCE;
    }

    protected Statement(SourceSpan span) {
        super(span);
    }
}
