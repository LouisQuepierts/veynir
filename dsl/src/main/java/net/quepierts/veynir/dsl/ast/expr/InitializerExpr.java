package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

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
