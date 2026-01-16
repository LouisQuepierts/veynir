package net.quepierts.animata4j.dsl.ast.stmt;

import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.source.SourceSpan;

public abstract class Statement extends Node {

    public static Statement empty() {
        return EmptyStmt.INSTANCE;
    }

    protected Statement(SourceSpan span) {
        super(span);
    }
}
