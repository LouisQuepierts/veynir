package net.quepierts.animata4j.core.dsl.preprocess.expr;

import lombok.AllArgsConstructor;
import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public final class PPBinaryExpr extends PPExpr {

    private final PPExpr left;
    private final PPExpr right;
    private final Operator operator;

    @Override
    public long eval(final PreprocessContext context) {
        long left = this.left.eval(context);
        long right = this.right.eval(context);

        long result = 0;
        switch (operator) {
            case ADD: {
                result = left + right;
                break;
            }
            case SUB: {
                result = left - right;
                break;
            }
            case MUL: {
                result = left * right;
                break;
            }
            case DIV: {
                result = left / right;
                break;
            }
            case MOD: {
                result = left % right;
                break;
            }
            case EQ: {
                result = left == right ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case NE: {
                result = left != right ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case GT: {
                result = left > right ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case LT: {
                result = left < right ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case GE: {
                result = left >= right ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case LE: {
                result = left <= right ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case AND: {
                result = left != 0 && right != 0 ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case OR: {
                result = left != 0 || right != 0 ? BOOL_TRUE : BOOL_FALSE;
            }
            case BAND: {
                result = left & right;
                break;
            }
            case BOR: {
                result = left | right;
                break;
            }
        }
        return result;
    }

    public enum Operator {
        ADD, SUB, MUL, DIV, MOD,
        EQ, NE, GT, LT, GE, LE,
        AND, OR,
        BAND, BOR
    }
}
