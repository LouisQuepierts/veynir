package net.quepierts.veynir.dsl.ast.stmt;

import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.source.SourcePos;
import net.quepierts.veynir.dsl.source.SourceSpan;

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
