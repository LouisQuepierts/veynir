package net.quepierts.animata4j.core.adapter;

import java.util.function.Consumer;

@FunctionalInterface
public interface Consumer1f extends Consumer<Float> {

    void accept(float value);

    @Override
    default void accept(Float aFloat) {
        this.accept(aFloat.floatValue());
    }
}
