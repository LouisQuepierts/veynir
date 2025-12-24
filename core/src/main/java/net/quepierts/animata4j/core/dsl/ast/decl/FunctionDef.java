package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.stmt.Statement;
import net.quepierts.animata4j.core.dsl.ast.type.Type;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class FunctionDef extends FunctionDecl {

    private final Statement statement;

    public FunctionDef(
            SourceSpan span,
            Type returnType,
            String name,
            List<Parameter> parameters,
            Statement statement
    ) {
        super(span, returnType, name, parameters);
        this.statement = statement;
    }

    @Override
    public NodeType getType() {
        return NodeType.DEF_FUNCTION;
    }
}
