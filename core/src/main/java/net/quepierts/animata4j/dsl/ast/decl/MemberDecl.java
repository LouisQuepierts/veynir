package net.quepierts.animata4j.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.common.Variable;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class MemberDecl extends Node {

    private final Variable variable;

    public MemberDecl(
            SourceSpan span,
            Variable variable
    ) {
        super(span);
        this.variable = variable;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_MEMBER;
    }
}
