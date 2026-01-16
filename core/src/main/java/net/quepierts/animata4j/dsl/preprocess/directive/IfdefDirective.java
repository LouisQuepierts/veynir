package net.quepierts.animata4j.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class IfdefDirective implements ConditionOperationalDirective {

    private final String macro;

    public static @NotNull IfdefDirective construct(String @NotNull [] args) {
        assert args.length == 2;
        return new IfdefDirective(args[0]);
    }

    @Override
    public void handle(PreprocessContext context) {
        context.pushCondition(context.isDefined(macro));
    }
}
