package net.quepierts.veynir.dsl.preprocess.directive;

public interface ConditionOperationalDirective extends Directive {

    @Override
    default boolean igConditionIgnorable() {
        return true;
    }

}
