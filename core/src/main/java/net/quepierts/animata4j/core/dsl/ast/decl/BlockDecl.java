package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

import java.util.List;

@Getter
public abstract class BlockDecl extends Declaration {

    private final List<VariableDecl> members;

    public BlockDecl(SourceSpan span, List<VariableDecl> members) {
        super(span);
        this.members = members;
    }
}
