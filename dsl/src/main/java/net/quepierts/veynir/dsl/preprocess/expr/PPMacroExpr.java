package net.quepierts.veynir.dsl.preprocess.expr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.quepierts.veynir.dsl.preprocess.PreprocessContext;

@Getter
@AllArgsConstructor
public final class PPMacroExpr extends PPExpr {

    private final String literal;

    @Override
    public long eval(final PreprocessContext context) {
        long value = context.getDefined(literal).toLong();

        try {
            return value;
        } catch (Exception e) {
            return 0;
        }
    }
}
