package net.quepierts.animata4j.dsl.preprocess.directive;

import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

public final class ElifDirective
        extends BaseExprDirective
        implements ConditionOperationalDirective {

    public static @NotNull ElifDirective construct(String @NotNull [] args) {
        assert args.length == 1;
        return new ElifDirective(args[0]);
    }

    public ElifDirective(@NotNull String expression) {
        super(expression);
    }

    @Override
    public void handle(PreprocessContext context) {
        context.popCondition();
        long result = this.eval(context);
        context.pushCondition(result != 0);
    }
}
