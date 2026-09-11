package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.capability.HasTargetCapability;
import net.quepierts.veynir.dsl.source.SourceSpan;
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
