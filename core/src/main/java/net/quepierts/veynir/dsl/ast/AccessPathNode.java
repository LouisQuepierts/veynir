package net.quepierts.veynir.dsl.ast;

import lombok.Getter;
import net.quepierts.veynir.dsl.source.SourceSpan;

import java.util.List;

@Getter
public class AccessPathNode extends Node {
    private final List<Node> path;

    public AccessPathNode(SourceSpan span, List<Node> path) {
        super(span);
        this.path = path;
    }

    @Override
    public NodeType getType() {
        return NodeType.CUSTOM;
    }
}
