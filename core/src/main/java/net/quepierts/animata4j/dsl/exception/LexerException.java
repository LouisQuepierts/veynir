package net.quepierts.animata4j.dsl.exception;

import net.quepierts.animata4j.dsl.source.SourceProvider;
import net.quepierts.animata4j.dsl.source.SourcePointer;
import net.quepierts.animata4j.dsl.source.SourcePos;
import org.jetbrains.annotations.NotNull;

public class LexerException extends CompileException {

    public LexerException(
            @NotNull String message,
            @NotNull SourcePos pos,
            @NotNull SourceProvider source
    ) {
        super("Lexer Error", message, pos, source);
    }

    public LexerException(
            @NotNull String message,
            SourcePointer pointer
    ) {
        this(message, pointer.toSourcePos(), pointer.getProvider());
    }

    protected static String formatMessage(@NotNull String message, @NotNull SourcePointer pos, String line) {
        int col = pos.getCol();
        return "Lexer Error at line " + pos.getLine() + ", column " + col +
                ": " + message + "\n" +
                line + "\n" +
                " ".repeat(Math.max(0, col - 1)) +
                "^";
    }
}
