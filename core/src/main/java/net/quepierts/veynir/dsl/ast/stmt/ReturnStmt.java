package net.quepierts.veynir.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.ast.expr.Expression;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class ReturnStmt extends Statement {

    private final Expression expression;

    public ReturnStmt(SourceSpan span, Expression expression) {
        super(span);
        this.expression = expression;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_RETURN;
    }
}
