package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class AssignExpr extends Expression {

    private final Node left;
    private final Node right;
    private final TokenType operator;

    public AssignExpr(SourceSpan span, Node left, Node right, TokenType operator) {
        super(span);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_ASSIGN;
    }
}
