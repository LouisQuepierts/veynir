package net.quepierts.veynir.dsl.ast.type;

import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.source.SourceSpan;

public abstract class Type extends Node {
    protected Type(SourceSpan span) {
        super(span);
    }
}
