package net.quepierts.animata4j.core.dsl.preprocess;

import org.jetbrains.annotations.NotNull;

public interface PreprocessContext {
    void define(@NotNull String macro);

    void define(@NotNull String macro, @NotNull String value);

    void undefine(@NotNull String macro);

    boolean isDefined(@NotNull String macro);

    String getDefined(@NotNull String macro);

    void pushCondition(boolean condition);

    boolean popCondition();

    boolean peekCondition();
}
