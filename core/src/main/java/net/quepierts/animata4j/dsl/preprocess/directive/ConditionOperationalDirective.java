package net.quepierts.animata4j.dsl.preprocess.directive;

public interface ConditionOperationalDirective extends Directive {

    @Override
    default boolean igConditionIgnorable() {
        return true;
    }

}
