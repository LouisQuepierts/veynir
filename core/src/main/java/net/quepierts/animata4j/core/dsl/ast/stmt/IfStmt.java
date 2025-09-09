package net.quepierts.animata4j.core.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.ast.expr.Expression;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class IfStmt extends Statement {

    public final Expression condition;
    public final Statement thenStmt;
    public final Statement elseStmt;

    public IfStmt(SourceSpan span, Expression condition, Statement thenStmt, Statement elseStmt) {
        super(span);
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_IF;
    }
}
