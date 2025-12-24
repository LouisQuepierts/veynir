package net.quepierts.animata4j.core.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class IfndefDirective implements ConditionOperationalDirective {

    private final String macro;

    public static @NotNull IfndefDirective construct(String @NotNull [] args) {
        assert args.length == 2;
        return new IfndefDirective(args[0]);
    }

    @Override
    public void handle(PreprocessContext context) {
        context.pushCondition(!context.isDefined(macro));
    }
}
