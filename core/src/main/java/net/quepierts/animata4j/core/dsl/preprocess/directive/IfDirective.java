package net.quepierts.animata4j.core.dsl.preprocess.directive;

import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

public class IfDirective
        extends BaseExprDirective
        implements ConditionOperationalDirective {

    public static @NotNull IfDirective construct(String @NotNull [] args) {
        assert args.length == 1;
        return new IfDirective(args[0]);
    }

    public IfDirective(@NotNull String expression) {
        super(expression);
    }

    @Override
    public void handle(PreprocessContext context) {
        long result = this.eval(context);
        context.pushCondition(result != 0);
    }
}
