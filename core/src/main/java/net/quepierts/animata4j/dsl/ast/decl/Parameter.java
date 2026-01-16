package net.quepierts.animata4j.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.common.InterfaceQualifier;
import net.quepierts.animata4j.dsl.ast.type.Type;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class Parameter extends Node {

    private final boolean isConst;
    private final InterfaceQualifier qualifier;
    private final Type datatype;
    private final String name;

    public Parameter(
            SourceSpan span,
            boolean isConst,
            InterfaceQualifier qualifier,
            Type type,
            String name
    ) {
        super(span);
        this.isConst = isConst;
        this.datatype = type;
        this.name = name;

        if (qualifier == InterfaceQualifier.UNIFORM || qualifier == InterfaceQualifier.BUFFER) {
            throw new IllegalArgumentException("Cannot use uniform or buffer qualifier for parameter");
        }

        this.qualifier = qualifier;
    }

    public NodeType getType() {
        return NodeType.DECL_PARAMETER;
    }
}
