package net.quepierts.veynir.dsl.preprocess.directive;

import net.quepierts.veynir.dsl.preprocess.PreprocessContext;
import net.quepierts.veynir.dsl.preprocess.expr.PPExpr;
import net.quepierts.veynir.dsl.preprocess.expr.PPExprParser;
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
