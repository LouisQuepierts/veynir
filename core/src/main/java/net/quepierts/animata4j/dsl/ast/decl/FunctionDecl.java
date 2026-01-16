package net.quepierts.animata4j.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.type.Type;
import net.quepierts.animata4j.dsl.source.SourceSpan;

import java.util.List;

@Getter
public class FunctionDecl extends Declaration {

    private final Type returnType;
    private final String name;
    private final List<Parameter> parameters;

    public FunctionDecl(SourceSpan span, Type returnType, String name, List<Parameter> parameters) {
        super(span);
        this.returnType = returnType;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_FUNCTION;
    }
}
