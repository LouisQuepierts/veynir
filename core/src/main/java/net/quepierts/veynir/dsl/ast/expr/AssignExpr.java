package net.quepierts.veynir.dsl.ast.expr;

import lombok.Getter;
import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.ast.NodeType;
import net.quepierts.veynir.dsl.lexer.TokenType;
import net.quepierts.veynir.dsl.source.SourceSpan;

@Getter
public final class AssignExpr extends Expression {

    private final Node left;
    private final Node right;
    private final Operator operator;

    public static AssignExpr of(Node left, Node right, TokenType type) {
        Operator operator;
        switch (type) {
            case EQUAL:
                operator = Operator.ASSIGN;
                break;
            case PLUS_EQUAL:
                operator = Operator.ADD_ASSIGN;
                break;
            case MINUS_EQUAL:
                operator = Operator.SUB_ASSIGN;
                break;
            case STAR_EQUAL:
                operator = Operator.MUL_ASSIGN;
                break;
            case SLASH_EQUAL:
                operator = Operator.DIV_ASSIGN;
                break;
            case PERCENT_EQUAL:
                operator = Operator.MOD_ASSIGN;
                break;
            case LSHIFT_EQUAL:
                operator = Operator.LSHIFT_ASSIGN;
                break;
            case RSHIFT_EQUAL:
                operator = Operator.RSHIFT_ASSIGN;
                break;
            case AND_EQUAL:
                operator = Operator.BIT_AND_ASSIGN;
                break;
            case OR_EQUAL:
                operator = Operator.BIT_OR_ASSIGN;
                break;
            case XOR_EQUAL:
                operator = Operator.BIT_XOR_ASSIGN;
                break;

            default:
                throw new IllegalArgumentException("Invalid binary operator: " + type);
        }

        return new AssignExpr(SourceSpan.of(left.getSpan(), right.getSpan()), left, right, operator);
    }

    public AssignExpr(SourceSpan span, Node left, Node right, Operator operator) {
        super(span);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_ASSIGN;
    }

    public enum Operator {
        ASSIGN,
        ADD_ASSIGN,
        SUB_ASSIGN,
        MUL_ASSIGN,
        DIV_ASSIGN,
        MOD_ASSIGN,
        LSHIFT_ASSIGN,
        RSHIFT_ASSIGN,
        BIT_AND_ASSIGN,
        BIT_OR_ASSIGN,
        BIT_XOR_ASSIGN,
    }
}
