package net.quepierts.veynir.dsl.preprocess;

import net.quepierts.veynir.dsl.preprocess.macro.Macro;
import org.jetbrains.annotations.NotNull;

public interface PreprocessContext {
    void define(@NotNull String macro);

    void define(@NotNull String macro, @NotNull Macro value);

    void undefine(@NotNull String macro);

    boolean isDefined(@NotNull String macro);

    Macro getDefined(@NotNull String macro);

    void pushCondition(boolean condition);

    boolean popCondition();

    boolean peekCondition();
}
