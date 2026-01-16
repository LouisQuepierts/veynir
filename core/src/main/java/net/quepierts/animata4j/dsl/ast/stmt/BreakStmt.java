package net.quepierts.animata4j.dsl.ast.stmt;

import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

public final class BreakStmt extends Statement {

    public BreakStmt(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_BREAK;
    }

}
