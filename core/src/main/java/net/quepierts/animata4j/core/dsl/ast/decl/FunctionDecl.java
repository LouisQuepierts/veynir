package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

import java.util.List;

@Getter
public class FunctionDecl extends Declaration {

    private final String name;
    private final List<ParameterDecl> parameters;

    public FunctionDecl(SourceSpan span, String name, List<ParameterDecl> parameters) {
        super(span);
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_FUNCTION;
    }
}
