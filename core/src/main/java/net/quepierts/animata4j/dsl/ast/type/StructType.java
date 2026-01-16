package net.quepierts.animata4j.dsl.ast.type;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class StructType extends Type {

    private final String name;

    public StructType(SourceSpan span, String name) {
        super(span);
        this.name = name;
    }

    @Override
    public NodeType getType() {
        return NodeType.TYPE_STRUCT;
    }
}
