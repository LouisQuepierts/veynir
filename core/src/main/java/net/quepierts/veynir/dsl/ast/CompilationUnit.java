package net.quepierts.veynir.dsl.ast;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.decl.Declaration;
import net.quepierts.veynir.dsl.source.SourceSpan;

import java.util.List;

@Getter
public class CompilationUnit extends Node {

    private final List<Declaration> declarations;

    public CompilationUnit(SourceSpan span, List<Declaration> declarations) {
        super(span);
        this.declarations = declarations;
    }

    @Override
    public NodeType getType() {
        return NodeType.COMPILATION_UNIT;
    }

}
