package net.quepierts.animata4j.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.expr.Expression;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class SwitchStmt extends Statement {

    private final Expression expression;

    public SwitchStmt(SourceSpan span, Expression expression) {
        super(span);
        this.expression = expression;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_SWITCH;
    }

}
