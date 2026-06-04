package net.quepierts.veynir.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.type.Type;
import net.quepierts.veynir.dsl.source.SourceSpan;

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
