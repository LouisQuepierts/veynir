package net.quepierts.animata4j.core.dsl.ast.stmt;

import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

public abstract class Statement extends Node {

    public static Statement empty() {
        return EmptyStmt.INSTANCE;
    }

    protected Statement(SourceSpan span) {
        super(span);
    }
}
