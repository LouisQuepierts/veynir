package net.quepierts.animata4j.core.dsl.ast.decl;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.capability.LayoutCapability;
import net.quepierts.animata4j.core.dsl.ast.common.InterfaceQualifier;
import net.quepierts.animata4j.core.dsl.ast.common.Variable;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.Nullable;

@Getter
public final class VarInterfaceDecl
        extends Declaration
        implements LayoutCapability {

    @Nullable
    private final LayoutQualifier layout;
    private final InterfaceQualifier storage;
    private final Variable variable;

    public VarInterfaceDecl(
            SourceSpan span,
            @Nullable LayoutQualifier layout,
            InterfaceQualifier storage,
            Variable variable
    ) {
        super(span);
        this.layout = layout;
        this.storage = storage;
        this.variable = variable;
    }

    @Override
    public NodeType getType() {
        return NodeType.DECL_VAR_INTERFACE;
    }
}
