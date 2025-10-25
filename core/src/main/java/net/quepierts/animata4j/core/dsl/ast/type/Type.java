package net.quepierts.animata4j.core.dsl.ast.type;

import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

public abstract class Type extends Node {
    protected Type(SourceSpan span) {
        super(span);
    }
}
