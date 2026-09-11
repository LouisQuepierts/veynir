package net.quepierts.veynir.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.common.Variable;
import net.quepierts.veynir.dsl.source.SourceSpan;

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
