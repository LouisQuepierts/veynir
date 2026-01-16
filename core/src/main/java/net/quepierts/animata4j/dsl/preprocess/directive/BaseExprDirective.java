package net.quepierts.animata4j.dsl.preprocess.directive;

import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;
import net.quepierts.animata4j.dsl.preprocess.expr.PPExpr;
import net.quepierts.animata4j.dsl.preprocess.expr.PPExprParser;
import org.jetbrains.annotations.NotNull;

public abstract class BaseExprDirective implements Directive {

    @NotNull
    private final PPExpr expression;

    protected BaseExprDirective(@NotNull String expression) {
        this.expression = PPExprParser.parse(expression);
    }

    protected long eval(@NotNull PreprocessContext context) {
        return expression.eval(context);
    }
}
