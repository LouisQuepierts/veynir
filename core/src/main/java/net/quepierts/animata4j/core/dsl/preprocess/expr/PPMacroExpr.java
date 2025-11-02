package net.quepierts.animata4j.core.dsl.preprocess.expr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;

@Getter
@AllArgsConstructor
public final class PPMacroExpr extends PPExpr {

    private final String literal;

    @Override
    public long eval(final PreprocessContext context) {
        String defined = context.getDefined(this.literal);

        try {
            return Long.parseLong(defined);
        } catch (Exception e) {
            return 0;
        }
    }
}
