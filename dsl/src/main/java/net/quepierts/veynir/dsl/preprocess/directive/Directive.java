package net.quepierts.veynir.dsl.preprocess.directive;

import net.quepierts.veynir.dsl.preprocess.PreprocessContext;

public interface Directive {
    default void init(PreprocessContext context) {}

    default boolean igConditionIgnorable() {
        return false;
    }

    void handle(PreprocessContext context);
}
