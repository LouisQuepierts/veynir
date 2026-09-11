package net.quepierts.veynir.dsl.ast.decl;

import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.source.SourceSpan;

public abstract class Declaration extends Node {
    protected Declaration(SourceSpan span) {
        super(span);
    }
}
