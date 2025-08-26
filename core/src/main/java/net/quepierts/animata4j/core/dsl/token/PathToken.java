package net.quepierts.animata4j.core.dsl.token;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeKey;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import net.quepierts.animata4j.core.dsl.ast.ExpressionNode;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class PathToken extends Token {

    private PathToken(@NotNull SourceSpan span) {
        super(span);
    }

    public static PathToken simple(
            @NotNull SourceSpan span,
            @NotNull String word
    ) {
        return new Simple(span, word);
    }

    public static PathToken subscript(
            @NotNull SourceSpan span,
            @NotNull ExpressionNode expr
    ) {
        return new Subscript(span, expr);
    }

    @Getter
    public static final class Simple extends PathToken {

        public static final TypeIdentifier<Simple> TYPE = TokenTypes.SIMPLE;
        private final @NotNull String text;

        private Simple(
                @NotNull SourceSpan span,
                @NotNull String text
        ) {
            super(span);
            this.text = text;
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
                @NotNull SourceSpan span,
                @NotNull ExpressionNode expr
        ) {
            super(span);
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
