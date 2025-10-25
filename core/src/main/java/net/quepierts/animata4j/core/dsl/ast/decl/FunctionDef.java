package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.stmt.Statement;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class FunctionDef extends FunctionDecl {

    private final Statement statement;

    public FunctionDef(
            SourceSpan span,
            String name,
            List<ParameterDecl> parameters,
            Statement statement
    ) {
        super(span, name, parameters);
        this.statement = statement;
    }

    @Override
    public NodeType getType() {
        return NodeType.DEF_FUNCTION;
    }
}
