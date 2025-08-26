package net.quepierts.animata4j.core.dsl.token;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import net.quepierts.animata4j.core.dsl.TypeKey;
import org.jetbrains.annotations.NotNull;

public abstract class ExpressionToken extends Token {

    private ExpressionToken(SourceSpan span) {
        super(span);
    }

    public static @NotNull ExpressionToken number(SourceSpan span, String value) {
        return new Number(span, value);
    }

    public static @NotNull ExpressionToken word(SourceSpan span, String value) {
        return new Word(span, value);
    }

    public static @NotNull ExpressionToken symbol(SourceSpan span, char value) {
        return new Symbol(span, Symbol.map(value));
    }

    @Getter
    public static final class Number extends ExpressionToken {

        public static final TypeIdentifier<Number> TYPE = TokenTypes.NUMBER;
        private final String value;

        public Number(SourceSpan span, String value) {
            super(span);
            this.value = value;
        }

        @Override
        public @NotNull TypeIdentifier<Number> getType() {
            return TYPE;
        }
    }

    @Getter
    public static final class Word extends ExpressionToken {

        public static final TypeIdentifier<Word> TYPE = TokenTypes.WORD;
        private final String value;

        public Word(SourceSpan span, String value) {
            super(span);
            this.value = value;
        }

        @Override
        public @NotNull TypeIdentifier<Word> getType() {
            return TYPE;
        }
    }

    @Getter
    public static final class Symbol extends ExpressionToken {

        private final TypeIdentifier<Symbol> type;

        private Symbol(SourceSpan span, TypeIdentifier<Symbol> type) {
            super(span);
            this.type = type;
        }

        private static TypeIdentifier<Symbol> map(char value) {
            switch (value) {
                case '+':
                    return TokenTypes.ADD;
                case '-':
                    return TokenTypes.SUB;
                case '*':
                    return TokenTypes.MUL;
                case '/':
                    return TokenTypes.DIV;
                case '%':
                    return TokenTypes.MOD;
                case '^':
                    return TokenTypes.POW;
                case '(':
                    return TokenTypes.LPAREN;
                case ')':
                    return TokenTypes.RPAREN;
                default:
                    throw new IllegalArgumentException("Invalid symbol: " + value);
            }
        }
    }

    public enum Keys implements TypeKey {
        NUMBER, WORD, ADD, SUB, MUL, DIV, MOD, POW, LPAREN, RPAREN
    }
}
