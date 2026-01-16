package net.quepierts.animata4j.dsl.ast.stmt;

import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

public final class ContinueStmt extends Statement {

    public ContinueStmt(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_CONTINUE;
    }

}
