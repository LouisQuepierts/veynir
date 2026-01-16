package net.quepierts.animata4j.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElseDirective implements ConditionOperationalDirective {

    public static @NotNull ElseDirective construct(String @NotNull [] args) {
        return new ElseDirective();
    }

    @Override
    public void handle(PreprocessContext context) {
        boolean condition = context.popCondition();
        context.pushCondition(!condition);
    }
}
