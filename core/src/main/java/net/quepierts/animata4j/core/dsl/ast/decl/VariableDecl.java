package net.quepierts.animata4j.core.dsl.ast.decl;

import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import lombok.Setter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.capability.ConstCapability;
import net.quepierts.animata4j.core.dsl.ast.common.Variable;
import net.quepierts.animata4j.core.dsl.ast.expr.Expression;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class VariableDecl
        extends Declaration
        implements ConstCapability {

    private final Variable variable;
    private final Expression init;
    private final boolean isConst;

    public VariableDecl(
            SourceSpan span,
            @NotNull Variable variable,
            @Nullable Expression init,
            boolean isConst
    ) {
        super(span);
        this.variable = variable;
        this.init = init;
        this.isConst = isConst;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_VARIABLE;
    }
}
