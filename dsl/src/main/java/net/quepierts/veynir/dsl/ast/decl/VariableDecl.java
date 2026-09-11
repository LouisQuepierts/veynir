package net.quepierts.veynir.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.capability.ConstCapability;
import net.quepierts.veynir.dsl.ast.common.InterfaceQualifier;
import net.quepierts.veynir.dsl.ast.common.Variable;
import net.quepierts.veynir.dsl.ast.expr.Expression;
import net.quepierts.veynir.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class VariableDecl
        extends Declaration
        implements ConstCapability {

    private final InterfaceQualifier qualifier;
    private final Variable variable;
    private final Expression init;
    private final boolean isConst;

    @Nullable
    private final LayoutQualifier layout;

    public static VariableDecl variable(
            @NotNull SourceSpan span,
            @NotNull Variable variable,
            @Nullable Expression init,
            boolean isConst
    ) {
        return new VariableDecl(span, InterfaceQualifier.NONE, variable, init, null, isConst);
    }

    public static VariableDecl intf(
            @NotNull SourceSpan span,
            @Nullable LayoutQualifier layout,
            @NotNull InterfaceQualifier qualifier,
            @NotNull Variable variable
    ) {
        return new VariableDecl(span, qualifier, variable, null, layout, false);
    }

    private VariableDecl(
            @NotNull SourceSpan span,
            @NotNull InterfaceQualifier qualifier,
            @NotNull Variable variable,
            @Nullable Expression init,
            @Nullable LayoutQualifier layout,
            boolean isConst
    ) {
        super(span);
        this.qualifier = qualifier;
        this.variable = variable;
        this.init = init;
        this.isConst = isConst;
        this.layout = layout;
    }

    /*public VariableDecl(
            SourceSpan span,
            @NotNull Variable variable,
            @Nullable Expression init,
            boolean isConst
    ) {
        super(span);
        this.variable = variable;
        this.init = init;
        this.isConst = isConst;
    }*/

    @Override
    public NodeType getType() {
        return NodeType.DECL_VARIABLE;
    }
}
