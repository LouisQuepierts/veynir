package net.quepierts.animata4j.core.dsl.lexer;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;

@Getter
public class Token {
    private final TokenType type;
    private final String value;
    private final SourceSpan span;

    public static Token eof(SourceSpan span) {
        return new Token(TokenType.EOF, "<EOF>", span);
    }

    public Token(
            @NotNull TokenType type,
            @NotNull String value,
            @NotNull SourceSpan span
    ) {
        this.type = type;
        this.value = value;
        this.span = span.copy();
    }

    public boolean is(@NotNull TokenType... types) {
        for (TokenType tokenType : types) {
            if (this.type == tokenType) {
                return true;
            }
        }
        return false;
    }

    public boolean is(@NotNull TokenType type) {
        return this.type == type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
