package net.quepierts.animata4j.dsl.ast.expr;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.ast.NodeType;
import net.quepierts.animata4j.dsl.lexer.TokenType;
import net.quepierts.animata4j.dsl.source.SourceSpan;

@Getter
public final class BinaryExpr extends Expression {

    private final Node left;
    private final Node right;
    private final Operator opr;

    public static BinaryExpr of(SourceSpan span, Node left, Node right, Operator operator) {
        return new BinaryExpr(span, left, right, operator);
    }

    public static BinaryExpr calculate(
            SourceSpan span,
            Node left,
            Node right,
            TokenType tokenType
    ) {
       Operator operator;
       switch (tokenType) {
           case PLUS:
               operator = Operator.ADD;
               break;
           case MINUS:
               operator = Operator.SUB;
               break;
           case STAR:
               operator = Operator.MUL;
               break;
           case SLASH:
               operator = Operator.DIV;
               break;
           case PERCENT:
               operator = Operator.MOD;
               break;
           case LSHIFT:
               operator = Operator.LSHIFT;
               break;
           case RSHIFT:
               operator = Operator.RSHIFT;
               break;
           case AND:
               operator = Operator.BIT_AND;
               break;
           case OR:
               operator = Operator.BIT_OR;
               break;
           case XOR:
               operator = Operator.BIT_XOR;
               break;

           default:
               throw new IllegalArgumentException("Invalid binary operator: " + tokenType);
       }
       return new BinaryExpr(span, left, right, operator);
    }

    public static BinaryExpr logic(
            SourceSpan span,
            Node left,
            Node right,
            TokenType tokenType
    ) {
        Operator operator;
        switch (tokenType) {
            case EQEQ:
                operator = Operator.EQ;
                break;
            case NOTEQ:
                operator = Operator.NOTEQ;
                break;
            case LT:
                operator = Operator.LT;
                break;
            case GT:
                operator = Operator.GT;
                break;
            case LTEQ:
                operator = Operator.LTEQ;
                break;
            case GTEQ:
                operator = Operator.GTEQ;
                break;
            case AND2:
                operator = Operator.LOGIC_AND;
                break;
            case OR2:
                operator = Operator.LOGIC_OR;
                break;
            default:
                throw new IllegalArgumentException("Invalid binary operator: " + tokenType);
        }

        return new BinaryExpr(span, left, right, operator);
    }

    private BinaryExpr(
            SourceSpan span,
            Node left,
            Node right,
            Operator operator
    ) {
        super(span);
        this.left = left;
        this.right = right;
        this.opr = operator;
    }

    @Override
    public NodeType getType() {
        return NodeType.EXPR_BINARY;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Operator {
        ADD(false),
        SUB(false),
        MUL(false),
        DIV(false),
        MOD(false),
        LSHIFT(false),
        RSHIFT(false),
        BIT_AND(false),
        BIT_OR(false),
        BIT_XOR(false),

        EQ(true),
        NOTEQ(true),
        LT(true),
        GT(true),
        LTEQ(true),
        GTEQ(true),
        LOGIC_AND(true),
        LOGIC_OR(true),
        LOGIC_XOR(true);

        final boolean isLogic;
    }
}
