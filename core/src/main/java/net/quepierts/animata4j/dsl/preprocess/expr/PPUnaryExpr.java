package net.quepierts.animata4j.dsl.preprocess.expr;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;

@RequiredArgsConstructor
public final class PPUnaryExpr extends PPExpr {

    private final Operator operator;
    private final PPExpr expr;

    @Override
    public long eval(final PreprocessContext context) {
        long result = 0;

        switch (operator) {
            case NOT: {
                result = expr.eval(context) == 0 ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
            case MINUS: {
                result = -expr.eval(context);
                break;
            }
            case DEFINED: {
                result = context.getDefined(expr.getLiteral()) != null ? BOOL_TRUE : BOOL_FALSE;
                break;
            }
        }

        return 0;
    }

    public enum Operator {
        NOT, MINUS, DEFINED
    }
}
