package net.quepierts.veynir.dsl.lexer;

import net.quepierts.veynir.dsl.source.SourcePointer;
import net.quepierts.veynir.dsl.source.SourcePos;
import net.quepierts.veynir.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;

public interface LexerView {

    boolean isEof();

    boolean isEol();

    default boolean hasNext() {
        return !this.isEof();
    }

    default boolean hasNext(boolean lineMode) {
        return !(lineMode ? this.isEol() : this.isEof());
    }

    char peek();

    char last();

    char advance();

    @NotNull SourcePointer pointer();

    @NotNull SourceSpan span(final @NotNull SourcePos start);

    void error(final @NotNull String message);
}
