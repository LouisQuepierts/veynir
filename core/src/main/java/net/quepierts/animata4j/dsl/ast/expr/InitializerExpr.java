package net.quepierts.animata4j.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

import java.util.List;

@Getter
public final class InitializerExpr extends Expression {

    private final List<Expression> arguments;

    public InitializerExpr(SourceSpan span, List<Expression> arguments) {
        super(span);
        this.arguments = arguments;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_INITIALIZER;
    }
}
