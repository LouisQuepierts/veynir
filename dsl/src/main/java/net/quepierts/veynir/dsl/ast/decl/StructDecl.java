package net.quepierts.veynir.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class StructDecl extends Declaration {

    private final String name;
    private final List<MemberDecl> members;

    public StructDecl(SourceSpan span, String name, List<MemberDecl> members) {
        super(span);
        this.name = name;
        this.members = members;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_STRUCT;
    }


}
