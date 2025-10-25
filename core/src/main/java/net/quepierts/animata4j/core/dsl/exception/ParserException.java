package net.quepierts.animata4j.core.dsl.exception;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

@Getter
public class ParserException extends CompileException {
    private final Token token;

    public ParserException(
            @NotNull String message,
            @NotNull Token token,
            @NotNull SourceProvider provider
    ) {
        super("Parser Error", message, token.getSpan().getBegin(), provider);
        this.token = token;
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
