package net.quepierts.animata4j.core.dsl.preprocess.directive;

import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;

public interface Directive {
    default void init(PreprocessContext context) {}

    default boolean igConditionIgnorable() {
        return false;
    }

    void handle(PreprocessContext context);
}
