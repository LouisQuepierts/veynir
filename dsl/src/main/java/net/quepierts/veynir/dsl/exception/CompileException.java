package net.quepierts.veynir.dsl.exception;

import lombok.Getter;
import net.quepierts.veynir.dsl.source.SourcePos;
import net.quepierts.veynir.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class CompileException
        extends RuntimeException
        implements Comparable<CompileException> {

    private final String line;
    private final SourcePos pos;
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

        this.pos = pos;
        this.line = provider.getLine(pos.getLine());
        this.lineNumber = provider.getLineNumber(pos.getLine());
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

    @Override
    public int compareTo(@NotNull CompileException o) {
        return pos.compareTo(o.pos);
    }
}
