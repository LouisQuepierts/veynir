package net.quepierts.animata4j.core.dsl.exception;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import org.jetbrains.annotations.NotNull;

@Getter
public class ParserException extends RuntimeException {
    private final Token token;
    private final String line;

    public ParserException(@NotNull String message, @NotNull Token token, String line) {
        super(ParserException.formatMessage(message, token.getSpan().getBegin(), line));
        this.token = token;
        this.line = line;
    }

    protected static String formatMessage(@NotNull String message, @NotNull SourcePos pos, String line) {
        int col = pos.getCol();
        return "Parser Error at line " + pos.getLine() + ", column " + col +
                ": " + message + "\n" +
                line + "\n" +
                " ".repeat(Math.max(0, col - 1)) +
                "^";
    }
}
