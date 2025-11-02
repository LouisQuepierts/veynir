package net.quepierts.animata4j.core.dsl.preprocess.expr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.quepierts.animata4j.core.dsl.preprocess.PreprocessContext;

import java.util.Map;
import java.util.Objects;

public final class PPNumberExpr extends PPExpr {

    private final long value;
    @Getter
    private final String literal;

    public PPNumberExpr(long value) {
        this.value = value;
        this.literal = String.valueOf(value);
    }

    @Override
    public long eval(final PreprocessContext context) {
        return this.value;
    }

    @Override
    public boolean isConstant() {
        return true;
    }
}
