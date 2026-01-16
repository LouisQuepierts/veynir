package net.quepierts.animata4j.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndifDirective implements ConditionOperationalDirective {

    public static @NotNull EndifDirective construct(String @NotNull [] args) {
        return new EndifDirective();
    }

    @Override
    public void handle(PreprocessContext context) {
        context.popCondition();
    }
}
