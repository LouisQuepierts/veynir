package net.quepierts.animata4j.core.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.ast.NodeType;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;

@Getter
public final class UnaryExpr extends Expression {

    private final Expression expr;
    private final Operator opr;

    public static UnaryExpr prefix(
            SourceSpan span,
            Expression expr,
            TokenType type
    ) {
        Operator operator;
        switch (type) {
            case PLUS:
                operator = Operator.PLUS;
                break;
            case PLUS2:
                operator = Operator.PRE_INC;
                break;
            case MINUS:
                operator = Operator.MINUS;
                break;
            case MINUS2:
                operator = Operator.PRE_DEC;
                break;
            case NOT:
                operator = Operator.NOT;
                break;
            case WAVE:
                operator = Operator.BIT_NOT;
                break;
            default:
                throw new IllegalArgumentException("Invalid token type: " + type);
        }
        return new UnaryExpr(span, expr, operator);
    }

    public static UnaryExpr postfix(
            SourceSpan span,
            Expression expr,
            TokenType type
    ) {
        Operator operator;
        switch (type) {
            case PLUS2:
                operator = Operator.POST_INC;
                break;
            case MINUS2:
                operator = Operator.POST_DEC;
                break;
            default:
                throw new IllegalArgumentException("Invalid token type: " + type);
        }
        return new UnaryExpr(span, expr, operator);
    }

    private UnaryExpr(
            SourceSpan span,
            Expression expr,
            Operator operator
    ) {
        super(span);
        this.expr = expr;
        this.opr = operator;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_UNARY;
    }

    public enum Operator {
        PLUS,
        MINUS,
        NOT,
        BIT_NOT,
        POST_INC,
        POST_DEC,
        PRE_INC,
        PRE_DEC
    }
}
