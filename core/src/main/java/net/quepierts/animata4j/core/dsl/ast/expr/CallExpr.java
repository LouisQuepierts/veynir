package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public final class CallExpr extends Expression {

    private final String functionName;
    private final List<Expression> arguments;

    public CallExpr(
            final @NotNull SourceSpan span,
            final @NotNull String functionName,
            final @NotNull List<Expression> arguments
    ) {
        super(span);
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_CALL;
    }
}
