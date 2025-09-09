package net.quepierts.animata4j.core.dsl.exception;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class CompileException extends RuntimeException {

    private final String line;
    private final int processingLineNumber;
    private final int processingColNumber;
    private final int lineNumber;

    public CompileException(
            final String errorType,
            final String message,
            final SourcePos pos,
            final SourceProvider provider
    ) {
        super(formatMessage(
                errorType,
                message,
                pos,
                provider
        ));

        this.processingLineNumber = pos.getLine();
        this.processingColNumber = pos.getCol();
        this.line = provider.getLine(this.processingLineNumber);
        this.lineNumber = provider.getLineNumber(this.processingLineNumber);
    }

    private static String formatMessage(
            @NotNull final String errorType,
            @NotNull final String message,
            @NotNull final SourcePos pos,
            @NotNull final SourceProvider provider
    ) {
        int col = pos.getCol() + 1;
        int processingLineNumber = pos.getLine();
        int sourceLineNumber = provider.getLineNumber(processingLineNumber);
        String lineContent = provider.getLine(processingLineNumber);
        return  errorType + " at line " + sourceLineNumber + ", column " + col +
                ": " + message + "\n" +
                lineContent + "\n" +
                " ".repeat(Math.max(0, col - 1)) +
                "^";
    }
}
