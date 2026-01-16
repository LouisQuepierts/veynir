package net.quepierts.animata4j.dsl.preprocess.directive;

import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;

public interface Directive {
    default void init(PreprocessContext context) {}

    default boolean igConditionIgnorable() {
        return false;
    }

    void handle(PreprocessContext context);
}
