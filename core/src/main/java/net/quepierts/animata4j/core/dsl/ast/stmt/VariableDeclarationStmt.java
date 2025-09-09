package net.quepierts.animata4j.core.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.capability.ConstCapability;
import net.quepierts.animata4j.core.dsl.ast.decl.VariableDecl;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

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
