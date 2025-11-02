package net.quepierts.animata4j.core.dsl.preprocess.directive;

import net.quepierts.animata4j.core.dsl.StringSplitter;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface DirectiveConstructor {
    @NotNull Directive construct(String @NotNull [] args);
}
