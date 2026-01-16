package net.quepierts.animata4j.dsl.preprocess.directive;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface DirectiveConstructor {
    @NotNull Directive construct(String @NotNull [] args);
}
