package net.quepierts.animata4j.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.expr.Expression;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class WhileStmt extends Statement {

    public final Expression condition;
    public final Statement body;
    public final boolean isDoWhile;

    public WhileStmt(SourceSpan span, Expression condition, Statement body, boolean isDoWhile) {
        super(span);
        this.condition = condition;
        this.body = body;
        this.isDoWhile = isDoWhile;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_WHILE;
    }

}
