package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.capability.HasTargetCapability;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public final class CallExpr
        extends Expression
        implements HasTargetCapability {

    @Nullable
    private final Expression scope;
    private final String functionName;
    private final List<Expression> arguments;

    public CallExpr(
            final @NotNull SourceSpan span,
            final @Nullable Expression scope,
            final @NotNull String functionName,
            final @NotNull List<Expression> arguments
    ) {
        super(span);
        this.scope = scope;
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_CALL;
    }
}
