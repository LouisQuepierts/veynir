package net.quepierts.veynir.dsl.preprocess.expr;

import net.quepierts.veynir.dsl.preprocess.PreprocessContext;

public abstract class PPExpr {
    public static final long BOOL_TRUE = 1L;
    public static final long BOOL_FALSE = 0L;

    public abstract long eval(final PreprocessContext context);

    public boolean isConstant() {
        return false;
    }

    public String getLiteral() {
        return "";
    }
}
