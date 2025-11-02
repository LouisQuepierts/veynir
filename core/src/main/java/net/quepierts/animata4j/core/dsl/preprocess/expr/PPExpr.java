package net.quepierts.animata4j.core.dsl.preprocess.expr;

import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;

import java.util.Map;
import java.util.Objects;

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
