package net.quepierts.animata4j.core.dsl.token;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeKey;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import net.quepierts.animata4j.core.dsl.ast.ExpressionNode;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class PathToken extends Token {

    private PathToken(
            @NotNull String value,
            @NotNull SourceSpan span
    ) {
        super(value, span);
    }

    public static PathToken simple(
            @NotNull String value,
            @NotNull SourceSpan span
    ) {
        return new Simple(value, span);
    }

    public static PathToken subscript(
            @NotNull String value,
            @NotNull SourceSpan span,
            @NotNull ExpressionNode expr
    ) {
        return new Subscript(value, span, expr);
    }

    @Getter
    public static final class Simple extends PathToken {

        public static final TypeIdentifier<Simple> TYPE = TokenTypes.SIMPLE;

        private Simple(
                @NotNull String value,
                @NotNull SourceSpan span
        ) {
            super(value, span);
        }

        @Override
        public TypeIdentifier<Simple> getType() {
            return TYPE;
        }
    }

    @Getter
    public static final class Subscript extends PathToken {

        public static final TypeIdentifier<Subscript> TYPE = TokenTypes.SUBSCRIPT;
        private final @NotNull ExpressionNode expr;

        private Subscript(
                @NotNull String value,
                @NotNull SourceSpan span,
                @NotNull ExpressionNode expr
        ) {
            super(value, span);
            this.expr = expr;
        }

        @Override
        public TypeIdentifier<Subscript> getType() {
            return TYPE;
        }
    }

    public enum Keys implements TypeKey {
        SIMPLE,
        SUBSCRIPT
    }
}
