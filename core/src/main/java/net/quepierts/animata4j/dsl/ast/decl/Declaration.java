package net.quepierts.animata4j.dsl.ast.decl;

import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.source.SourceSpan;

public abstract class Declaration extends Node {
    protected Declaration(SourceSpan span) {
        super(span);
    }
}
