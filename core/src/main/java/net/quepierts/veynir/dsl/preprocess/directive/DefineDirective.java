package net.quepierts.veynir.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.dsl.preprocess.PreprocessContext;
import net.quepierts.veynir.dsl.preprocess.macro.Macro;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefineDirective implements Directive {

    private final String name;
    private final Macro macro;
    private final boolean hasValue;

    public static @NotNull DefineDirective construct(String @NotNull [] args) {
        return new DefineDirective(args[0], null, !args[1].isEmpty());
    }

    @Override
    public void handle(PreprocessContext context) {
        if (this.hasValue) {
            context.define(this.name, this.macro);
        } else {
            context.define(this.name);
        }
    }
}
