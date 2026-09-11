package net.quepierts.veynir.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.common.InterfaceQualifier;
import net.quepierts.veynir.dsl.ast.type.Type;
import net.quepierts.veynir.dsl.source.SourceSpan;

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
