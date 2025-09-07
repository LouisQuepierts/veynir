package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class ConstructExpr extends Expression {

    private final String datatype;
    private final List<Expression> arguments;

    public ConstructExpr(SourceSpan span, String datatype, List<Expression> arguments) {
        super(span);
        this.datatype = datatype;
        this.arguments = arguments;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_CONSTRUCT;
    }
}
