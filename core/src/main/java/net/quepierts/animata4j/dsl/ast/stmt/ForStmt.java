package net.quepierts.animata4j.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.expr.Expression;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class ForStmt extends Statement {

    private final Statement init;
    private final Expression condition;
    private final Expression update;
    private final Statement body;

    public ForStmt(SourceSpan span, Statement init, Expression condition, Expression update, Statement body) {
        super(span);
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_FOR;
    }
}
