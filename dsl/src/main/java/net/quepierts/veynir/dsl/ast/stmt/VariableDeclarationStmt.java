package net.quepierts.veynir.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.capability.ConstCapability;
import net.quepierts.veynir.dsl.ast.decl.VariableDecl;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class VariableDeclarationStmt
        extends Statement
        implements ConstCapability {

    private final VariableDecl declaration;

    public VariableDeclarationStmt(SourceSpan span, VariableDecl declaration) {
        super(span);
        this.declaration = declaration;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_VARIABLE_DECL;
    }

    @Override
    public boolean isConst() {
        return this.declaration.isConst();
    }
}
