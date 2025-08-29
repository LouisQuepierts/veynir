package net.quepierts.animata4j.core.dsl.token;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import net.quepierts.animata4j.core.dsl.TypeKey;
import org.jetbrains.annotations.NotNull;

public abstract class ExpressionToken extends Token {

    private ExpressionToken(String value, SourceSpan span) {
        super(value, span);
    }

    public static @NotNull ExpressionToken number(String value, SourceSpan span) {
        return new Number(value, span);
    }

    public static @NotNull ExpressionToken identifier(String value, SourceSpan span) {
        return new Word(value, span);
    }

    public static @NotNull ExpressionToken symbol(char opr, SourceSpan span) {
        return new Symbol(String.valueOf(opr), span, Symbol.map(opr));
    }

    public static final class Number extends ExpressionToken {

        public static final TypeIdentifier<Number> TYPE = TokenTypes.NUMBER;

        public Number(String value, SourceSpan span) {
            super(value, span);
        }

        @Override
        public @NotNull TypeIdentifier<Number> getType() {
            return TYPE;
        }
    }

    public static final class Word extends ExpressionToken {

        public static final TypeIdentifier<Word> TYPE = TokenTypes.WORD;

        public Word(String value, SourceSpan span) {
            super(value, span);
        }

        @Override
        public @NotNull TypeIdentifier<Word> getType() {
            return TYPE;
        }
    }

    @Getter
    public static final class Symbol extends ExpressionToken {

        private final TypeIdentifier<Symbol> type;

        private Symbol(String value, SourceSpan span, TypeIdentifier<Symbol> type) {
            super(value, span);
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
