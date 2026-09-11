package net.quepierts.veynir.dsl.ast.stmt;

import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourceSpan;

public final class BreakStmt extends Statement {

    public BreakStmt(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_BREAK;
    }

}
