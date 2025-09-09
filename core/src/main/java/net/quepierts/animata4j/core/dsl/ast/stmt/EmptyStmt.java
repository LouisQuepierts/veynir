package net.quepierts.animata4j.core.dsl.ast.stmt;

import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

public final class EmptyStmt extends Statement {

    public static final EmptyStmt INSTANCE = new EmptyStmt(SourceSpan.of(SourcePos.of(0, 0), SourcePos.of(0, 0)));

    public EmptyStmt(SourceSpan span) {
        super(span);
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_EMPTY;
    }
}
