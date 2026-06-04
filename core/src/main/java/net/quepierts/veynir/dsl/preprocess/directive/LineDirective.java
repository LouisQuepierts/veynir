package net.quepierts.veynir.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LineDirective implements Directive {

    private final int lineNumber;

    public static @NotNull LineDirective construct(String @NotNull [] args) {
        assert args.length == 2;
        return new LineDirective(Integer.parseInt(args[0]));
    }

    @Override
    public void handle(PreprocessContext context) {

    }
}
