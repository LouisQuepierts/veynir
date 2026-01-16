package net.quepierts.animata4j.dsl.ast.stmt;

import lombok.Getter;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.ast.expr.Expression;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public class CaseStmt extends Statement {

    private final Expression expr;

    public CaseStmt(SourceSpan span, Expression expr) {
        super(span);
        this.expr = expr;
    }

    @Override
    public NodeType getType() {
        return NodeType.STMT_CASE;
    }

}
