package net.quepierts.animata4j.core.dsl.exception;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourcePointer;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;

@Getter
public class LexerException extends RuntimeException {
    private final SourcePos pos;
    private final String line;

    public LexerException(@NotNull String message, @NotNull SourcePos pos, String line) {
        super(LexerException.formatMessage(message, pos, line));

        this.pos = pos;
        this.line = line;
    }

    public LexerException(@NotNull String message, SourcePointer pointer) {
        this(message, pointer.toSourcePos(), pointer.getCurrentLine());
    }

    protected static String formatMessage(@NotNull String message, @NotNull SourcePos pos, String line) {
        int col = pos.getCol();
        return "Lexer Error at line " + pos.getLine() + ", column " + col +
                ": " + message + "\n" +
                line + "\n" +
                " ".repeat(Math.max(0, col - 1)) +
                "^";
    }
}
