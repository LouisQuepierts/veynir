package net.quepierts.veynir.dsl.ast.stmt;

import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

public final class ContinueStmt extends Statement {

    public ContinueStmt(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_CONTINUE;
    }

}
