package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.common.InterfaceQualifier;
import net.quepierts.animata4j.core.dsl.ast.common.Variable;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class ParameterDecl extends Node {

    private final InterfaceQualifier qualifier;
    private final Variable variable;

    public ParameterDecl(
            SourceSpan span,
            InterfaceQualifier qualifier,
            Variable variable
    ) {
        super(span);

        if (qualifier == InterfaceQualifier.UNIFORM || qualifier == InterfaceQualifier.BUFFER) {
            throw new IllegalArgumentException("Cannot use uniform or buffer qualifier for parameter");
        }

        this.qualifier = qualifier;
        this.variable = variable;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_PARAMETER;
    }
}
