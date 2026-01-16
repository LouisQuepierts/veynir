package net.quepierts.animata4j.dsl.ast.type;

import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.source.SourceSpan;

public abstract class Type extends Node {
    protected Type(SourceSpan span) {
        super(span);
    }
}
