package net.quepierts.animata4j.dsl.preprocess.directive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.dsl.preprocess.PreprocessContext;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UndefineDirective implements Directive {

    private final String macro;

    public static @NotNull UndefineDirective construct(String @NotNull [] args) {
        assert args.length == 2;
        return new UndefineDirective(args[0]);
    }

    @Override
    public void handle(PreprocessContext context) {
        context.undefine(this.macro);
    }
}
